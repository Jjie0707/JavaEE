package jjie.msgqueue.server;

import jjie.msgqueue.common.Request;
import jjie.msgqueue.common.Response;
import jjie.msgqueue.common.arguments.*;
import jjie.msgqueue.common.consumer.Consumer;
import jjie.msgqueue.common.exception.MqException;
import jjie.msgqueue.common.returns.BasicReturns;
import jjie.msgqueue.common.returns.SubscribeReturns;
import jjie.msgqueue.common.tools.BinaryTool;
import jjie.msgqueue.server.core.BasicProperties;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrokerServer {
    private ServerSocket serverSocket = null;

    // 当前考虑一个 BrokerServer 上只有一个 虚拟主机
    private VirtualHost virtualHost = new VirtualHost("default");
    // 使用这个 哈希表 表示当前的所有会话(也就是说有哪些客户端正在和咱们的服务器进行通信)
    // 此处的 key 是 channelId, value 为对应的 Socket 对象
    private ConcurrentHashMap<String, Socket> sessions = new ConcurrentHashMap<String, Socket>();
    // 引入一个线程池, 来处理多个客户端的请求.
    private ExecutorService executorService = null;
    // 引入一个 boolean 变量控制服务器是否继续运行
    private volatile boolean runnable = true;

    public BrokerServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void stop() throws IOException {
        runnable = false;
        // 把线程池中的任务都放弃了. 让线程都销毁.
        executorService.shutdownNow();
        serverSocket.close();
    }

    public void start() throws IOException {
        System.out.println("[BrokerServer] 启动!");
        //开启一个线程池用来处理连接的请求
        executorService = Executors.newCachedThreadPool();
        try {
            while (runnable) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> {
                    //真正处理连接请求的方法
                    processConnection(clientSocket);
                });
            }
        } catch (SocketException e) {
            System.out.println("[BrokerServer] 服务器停止运行或者运行失败!");
            e.printStackTrace();
        }
    }

    private void processConnection(Socket clientSocket) {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            // 这里需要按照特定格式来读取并解析. 此时就需要用到 DataInputStream 和 DataOutputStream
            try (DataInputStream dataInputStream = new DataInputStream(inputStream);
                 DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
                while (true) {
                    // 1. 读取请求并解析.
                    Request request = readRequest(dataInputStream);
                    // 2. 根据请求计算响应
                    Response response = process(request, clientSocket);
                    // 3. 把响应写回给客户端
                    writeResponse(dataOutputStream, response);
                }
            } catch (EOFException | SocketException e) {
                // 对于这个代码, DataInputStream 如果读到 EOF , 就会抛出一个 EOFException 异常.
                // 需要借助这个异常来结束循环
                System.out.println("[BrokerServer] connection 关闭! 客户端的地址: " + clientSocket.getInetAddress().toString()
                        + ":" + clientSocket.getPort());
            }
        } catch (IOException | ClassNotFoundException | MqException e) {
            System.out.println("[BrokerServer] connection 出现异常!");
            e.printStackTrace();
        } finally {
            try {
                // 当连接处理完了, 就需要记得关闭 socket
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                // 一个 TCP 连接中, 可能包含多个 channel. 需要把当前这个 socket 对应的所有 channel 也顺便清理掉.
                clearClosedSession(clientSocket);
            }
        }
    }

    private Request readRequest(DataInputStream dataInputStream) throws IOException {
        Request request = new Request();
        request.setType(dataInputStream.readInt());
        int length = dataInputStream.readInt();
        if (length < 0) {
            throw new IOException("非法请求长度: " + length);
        }

        byte[] payload = new byte[length];
        dataInputStream.readFully(payload);
        request.setLength(length);
        request.setPayload(payload);
        return request;
    }

    private void writeResponse(DataOutputStream dataOutputStream, Response response) throws IOException {
        dataOutputStream.writeInt(response.getType());
        dataOutputStream.writeInt(response.getLength());
        dataOutputStream.write(response.getPayload());
        // 这个刷新缓冲区也是重要的操作!!
        dataOutputStream.flush();
    }

    private Response process(Request request, Socket clientSocket) throws IOException, ClassNotFoundException, MqException {
        // 1. 把 request 中的 payload 做一个初步的解析.
        BasicArguments basicArguments = (BasicArguments) BinaryTool.from(request.getPayload());
        System.out.println("[Request] rid=" + basicArguments.getRid() + ", channelId=" + basicArguments.getChannelId()
                + ", type=" + request.getType() + ", length=" + request.getLength());
        // 2. 根据 type 的值, 来进一步区分接下来这次请求要干啥.
        String channelId = basicArguments.getChannelId();
        boolean ok = true;
        if (request.getType() == 0x1) {
            ok = channelId != null && sessions.putIfAbsent(channelId, clientSocket) == null;
        } else if (channelId == null || sessions.get(channelId) != clientSocket) {
            ok = false;
        } else if (request.getType() == 0x2) {
            ok = sessions.remove(channelId, clientSocket);
        } else if (request.getType() == 0x3) {
            ExchangeDeclareArguments arguments = (ExchangeDeclareArguments) basicArguments;
            ok = virtualHost.exchangeDeclare(arguments.getExchangeName(), arguments.getExchangeType(),
                    arguments.isDurable(), arguments.isAutoDelete(), arguments.getArguments());
        } else if (request.getType() == 0x4) {
            ExchangeDeleteArguments arguments = (ExchangeDeleteArguments) basicArguments;
            ok = virtualHost.exchangeDelete(arguments.getExchangeName());
        } else if (request.getType() == 0x5) {
            QueueDeclareArguments arguments = (QueueDeclareArguments) basicArguments;
            ok = virtualHost.queueDeclare(arguments.getQueueName(), arguments.isDurable(),
                    arguments.isExclusive(), arguments.isAutoDelete(), arguments.getArguments());
        } else if (request.getType() == 0x6) {
            QueueDeleteArguments arguments = (QueueDeleteArguments) basicArguments;
            ok = virtualHost.queueDelete((arguments.getQueueName()));
        } else if (request.getType() == 0x7) {
            QueueBindArguments arguments = (QueueBindArguments) basicArguments;
            ok = virtualHost.queueBind(arguments.getQueueName(), arguments.getExchangeName(), arguments.getBindingKey());
        } else if (request.getType() == 0x8) {
            QueueUnbindArguments arguments = (QueueUnbindArguments) basicArguments;
            ok = virtualHost.queueUnbind(arguments.getQueueName(), arguments.getExchangeName());
        } else if (request.getType() == 0x9) {
            BasicPublishArguments arguments = (BasicPublishArguments) basicArguments;
            ok = virtualHost.basicPublish(arguments.getExchangeName(), arguments.getRoutingKey(),
                    arguments.getBasicProperties(), arguments.getBody());
        } else if (request.getType() == 0xa) {
            BasicConsumeArguments arguments = (BasicConsumeArguments) basicArguments;
            ok = virtualHost.basicConsume(arguments.getConsumerTag(), arguments.getQueueName(), arguments.isAutoAck(),
                    new Consumer() {
                        // 这个回调函数要做的工作, 就是把服务器收到的消息可以直接推送回对应的消费者客户端
                        @Override
                        public void handleDelivery(String consumerTag, BasicProperties basicProperties, byte[] body) throws MqException, IOException {
                            // 先知道当前这个收到的消息, 要发给哪个客户端.
                            // 此处 consumerTag 其实是 channelId. 根据 channelId 去 sessions 中查询, 就可以得到对应的
                            // socket 对象了, 从而可以往里面发送数据了
                            // 1. 根据 channelId 找到 socket 对象
                            Socket clientSocket = sessions.get(consumerTag);
                            if (clientSocket == null || clientSocket.isClosed()) {
                                throw new MqException("[BrokerServer] 订阅消息的客户端已经关闭!");
                            }
                            // 2. 构造响应数据
                            SubscribeReturns subScribeReturns = new SubscribeReturns();
                            subScribeReturns.setChannelId(consumerTag);
                            subScribeReturns.setRid("");
                            subScribeReturns.setOk(true);
                            subScribeReturns.setConsumerTag(consumerTag);
                            subScribeReturns.setBasicProperties(basicProperties);
                            subScribeReturns.setBody(body);
                            byte[] payload = BinaryTool.toBinary(subScribeReturns);
                            Response response = new Response();
                            // 0xc 表示服务器给消费者客户端推送的消息数据.
                            response.setType(0xc);
                            // response 的 payload 就是一个 SubScribeReturns
                            response.setLength(payload.length);
                            response.setPayload(payload);
                            // 3. 把数据写回给客户端.
                            //    注意! 此处的 dataOutputStream 这个对象不能 close !!!
                            //    如果 把 dataOutputStream 关闭, 就会直接把 clientSocket 里的 outputStream 也关了.
                            //    此时就无法继续往 socket 中写入后续数据了.
                            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                            writeResponse(dataOutputStream, response);
                        }
                    });
        } else if (request.getType() == 0xb) {
            // 调用 basicAck 确认消息.
            BasicAckArguments arguments = (BasicAckArguments) basicArguments;
            ok = virtualHost.basicAck(arguments.getQueueName(), arguments.getMessageId());
        } else {
            // 当前的 type 是非法的.
            throw new MqException("[BrokerServer] 未知的 type! type=" + request.getType());
        }
        // 3. 构造响应
        BasicReturns basicReturns = new BasicReturns();
        basicReturns.setChannelId(basicArguments.getChannelId());
        basicReturns.setRid(basicArguments.getRid());
        basicReturns.setOk(ok);
        byte[] payload = BinaryTool.toBinary(basicReturns);
        Response response = new Response();
        response.setType(request.getType());
        response.setLength(payload.length);
        response.setPayload(payload);
        System.out.println("[Response] rid=" + basicReturns.getRid() + ", channelId=" + basicReturns.getChannelId()
                + ", type=" + response.getType() + ", length=" + response.getLength());
        return response;
    }

    private void clearClosedSession(Socket clientSocket) {
        // 这里要做的事情, 主要就是遍历上述 sessions hash 表, 把该被关闭的 socket 对应的键值对, 统统删掉.
        List<String> toDeleteChannelId = new ArrayList<>();
        for (Map.Entry<String, Socket> entry : sessions.entrySet()) {
            if (entry.getValue() == clientSocket) {
                toDeleteChannelId.add(entry.getKey());
            }
        }
        for (String channelId : toDeleteChannelId) {
            sessions.remove(channelId);
        }
        System.out.println("[BrokerServer] 清理 session 完成! 被清理的 channelId=" + toDeleteChannelId);
    }
}

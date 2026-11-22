package jjie.msgqueue.client;

import jjie.msgqueue.common.Request;
import jjie.msgqueue.common.Response;
import jjie.msgqueue.common.exception.MqException;
import jjie.msgqueue.common.returns.BasicReturns;
import jjie.msgqueue.common.returns.SubscribeReturns;
import jjie.msgqueue.common.tools.BinaryTool;
import lombok.Data;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
public class Connection {
    private Socket socket = null;
    // 需要管理多个 channel. 使用一个 hash 表把若干个 channel 组织起来. channelId -> channel
    private ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    private InputStream inputStream;
    private OutputStream outputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    //用来执行客户端回调函数的线程池
    private ExecutorService callbackPool = null;

    private final Object lifecycleLock = new Object();
    private volatile IOException closeCause;

    public Connection(String host, int port) throws IOException {
        socket = new Socket(host, port);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        dataInputStream = new DataInputStream(inputStream);
        dataOutputStream = new DataOutputStream(outputStream);

        callbackPool = Executors.newFixedThreadPool(4);

        // 创建一个扫描线程, 由这个线程负责不停的从 socket 中读取响应数据. 把这个响应数据再交给对应的 channel 负责处理.
        Thread t = new Thread(() -> {
            try {
                while (true) {
                    dispatchResponse(readResponse());
                }
            } catch (IOException e) {
                failConnection(e);
            } catch (ClassNotFoundException | MqException | RuntimeException e) {
                failConnection(new IOException("处理响应失败", e));
            }
        });
        t.start();
    }

    public void close() {
        failConnection(new IOException("客户端主动关闭连接"));
    }

    void checkOpen() throws IOException {
        IOException cause = closeCause;
        if (cause != null) {
            throw new IOException("连接已关闭", cause);
        }
    }

    private void failConnection(IOException cause) {
        List<Channel> channels;
        synchronized (lifecycleLock) {
            if (closeCause != null) {
                return;
            }
            closeCause = cause;
            channels = List.copyOf(channelMap.values());
            channelMap.clear();
        }

        channels.forEach(Channel::wakeWaiters);
        try {
            socket.close();
        } catch (IOException e) {
            cause.addSuppressed(e);
        } finally {
            callbackPool.shutdownNow();
        }
    }

    // 使用这个方法来分别处理, 当前的响应是一个针对控制请求的响应, 还是服务器推送的消息.
    private void dispatchResponse(Response response) throws IOException, ClassNotFoundException, MqException {
        if (response.getType() == 0xc) {
            // 服务器推送来的消息数据
            SubscribeReturns subScribeReturns = (SubscribeReturns) BinaryTool.from(response.getPayload());
            // 根据 channelId 找到对应的 channel 对象
            String channelId = subScribeReturns.getChannelId();
            Channel channel = channelId == null ? null : channelMap.get(channelId);
            if (channel == null) {
                return;
            }
            // 执行该 channel 对象内部的回调.
            callbackPool.submit(() -> {
                try {
                    channel.getConsumer().handleDelivery(subScribeReturns.getConsumerTag(), subScribeReturns.getBasicProperties(),
                            subScribeReturns.getBody());
                } catch (MqException | IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            // 当前响应是针对刚才的控制请求的响应
            BasicReturns basicReturns = (BasicReturns) BinaryTool.from(response.getPayload());
            // 把这个结果放到对应的 channel 的 hash 表中.
            String channelId = basicReturns.getChannelId();
            Channel channel = channelId == null ? null : channelMap.get(channelId);
            if (channel == null) {
                return;
            }
            channel.putReturns(basicReturns);
        }
    }

    // 发送请求
    public synchronized void writeRequest(Request request) throws IOException {
        checkOpen();
        try {
            dataOutputStream.writeInt(request.getType());
            dataOutputStream.writeInt(request.getLength());
            dataOutputStream.write(request.getPayload());
            dataOutputStream.flush();
        } catch (IOException e) {
            failConnection(e);
            throw e;
        }

        System.out.println("[Connection] 发送请求! type="
                + request.getType() + ", length=" + request.getLength());
    }

    // 读取响应
    public Response readResponse() throws IOException {
        Response response = new Response();
        response.setType(dataInputStream.readInt());
        int length = dataInputStream.readInt();
        if (length < 0) {
            throw new IOException("非法响应长度: " + length);
        }

        byte[] payload = new byte[length];
        dataInputStream.readFully(payload);
        response.setLength(length);
        response.setPayload(payload);
        System.out.println("[Connection] 收到响应! type=" + response.getType() + ", length=" + response.getLength());
        return response;
    }

    // 通过这个方法, 在 Connection 中能够创建出一个 Channel
    public Channel createChannel() throws IOException {
        String channelId = "C-" + UUID.randomUUID();
        Channel channel = new Channel(channelId, this);
        // 把这个 channel 对象放到 Connection 管理 channel 的 哈希表 中.
        synchronized (lifecycleLock) {
            checkOpen();
            channelMap.put(channelId, channel);
        }

        boolean created = false;
        try {
            created = channel.createChannel();
            return created ? channel : null;
        } finally {
            //这里也是,如果创建失败了的话,要回滚掉已经写入内存的数据
            if (!created) {
                channelMap.remove(channelId, channel);
            }
        }
    }
}

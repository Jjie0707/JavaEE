package jjie.msgqueue.client;

import jjie.msgqueue.common.Request;
import jjie.msgqueue.common.arguments.*;
import jjie.msgqueue.common.consumer.Consumer;
import jjie.msgqueue.common.exception.MqException;
import jjie.msgqueue.common.returns.BasicReturns;
import jjie.msgqueue.common.tools.BinaryTool;
import jjie.msgqueue.server.core.BasicProperties;
import jjie.msgqueue.server.core.ExchangeType;
import lombok.Data;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
@Data
public class Channel {
    private String channelId;
    // 当前这个 channel 属于哪个连接.
    private Connection connection;
    // 用来存储后续客户端收到的服务器的响应.
    private ConcurrentHashMap<String, BasicReturns> basicReturnsMap = new ConcurrentHashMap<>();
    // 如果当前 Channel 订阅了某个队列, 就需要在此处记录下对应回调是啥. 当该队列的消息返回回来的时候, 调用回调.
    // 此处约定一个 Channel 中只能有一个回调.
    private volatile Consumer consumer;
    private volatile boolean closed;

    public Channel(String channelId, Connection connection) {
        this.channelId = channelId;
        this.connection = connection;
    }

    private String generateRid() throws IOException {
        if (closed) {
            throw new IOException("Channel 已关闭，channelId=" + channelId);
        }
        return "R-" + UUID.randomUUID().toString();
    }

    public synchronized void  putReturns(BasicReturns basicReturns) {
        basicReturnsMap.put(basicReturns.getRid(), basicReturns);

        // 把所有的等待的线程都唤醒.
        notifyAll();

    }

    // 期望使用这个方法来阻塞等待服务器的响应.
    private synchronized BasicReturns waitResult(String rid) throws IOException {
        BasicReturns result;
        while ((result = basicReturnsMap.get(rid)) == null) {
            if (closed) {
                throw new IOException("Channel 已关闭，channelId=" + channelId);
            }
            connection.checkOpen();
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                java.io.InterruptedIOException failure =
                        new java.io.InterruptedIOException("等待响应被中断，rid=" + rid);
                failure.initCause(e);
                throw failure;
            }
        }

        basicReturnsMap.remove(rid);
        return result;
    }

    synchronized void wakeWaiters() {
        notifyAll();
    }


    // 在这个方法中, 和服务器进行交互, 告知服务器, 此处客户端创建了新的 channel 了.
    public boolean createChannel() throws IOException {
        BasicArguments basicArguments = new BasicArguments();
        basicArguments.setChannelId(channelId);
        basicArguments.setRid(generateRid());
        byte[] payload = BinaryTool.toBinary(basicArguments);

        Request request = new Request();
        request.setType(0x1);
        request.setLength(payload.length);
        request.setPayload(payload);

        // 构造出完整请求之后, 就可以发送这个请求了.
        connection.writeRequest(request);
        // 等待服务器的响应
        BasicReturns basicReturns = waitResult(basicArguments.getRid());
        return basicReturns.isOk();
    }


    // 关闭 channel, 给服务器发送一个 type = 0x2 的请求
    public boolean close() throws IOException {
        BasicArguments basicArguments = new BasicArguments();
        basicArguments.setRid(generateRid());
        basicArguments.setChannelId(channelId);
        byte[] payload = BinaryTool.toBinary(basicArguments);

        Request request = new Request();
        request.setType(0x2);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        //若关闭成功,要唤醒所有的等待线程,不让他们继续空等
        boolean ok = waitResult(basicArguments.getRid()).isOk();
        if (ok) {
            synchronized (this) {
                closed = true;
                notifyAll();
            }
            connection.getChannelMap().remove(channelId, this);
        }
        return ok;
    }

    // 创建交换机
    public boolean exchangeDeclare(String exchangeName, ExchangeType exchangeType, boolean durable, boolean autoDelete,
                                   Map<String, Object> arguments) throws IOException {
        ExchangeDeclareArguments exchangeDeclareArguments = new ExchangeDeclareArguments();
        exchangeDeclareArguments.setRid(generateRid());
        exchangeDeclareArguments.setChannelId(channelId);
        exchangeDeclareArguments.setExchangeName(exchangeName);
        exchangeDeclareArguments.setExchangeType(exchangeType);
        exchangeDeclareArguments.setDurable(durable);
        exchangeDeclareArguments.setAutoDelete(autoDelete);
        exchangeDeclareArguments.setArguments(arguments);
        byte[] payload = BinaryTool.toBinary(exchangeDeclareArguments);

        Request request = new Request();
        request.setType(0x3);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(exchangeDeclareArguments.getRid());
        return basicReturns.isOk();
    }

    // 删除交换机
    public boolean exchangeDelete(String exchangeName) throws IOException {
        ExchangeDeleteArguments arguments = new ExchangeDeleteArguments();
        arguments.setRid(generateRid());
        arguments.setChannelId(channelId);
        arguments.setExchangeName(exchangeName);
        byte[] payload = BinaryTool.toBinary(arguments);

        Request request = new Request();
        request.setType(0x4);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(arguments.getRid());
        return basicReturns.isOk();
    }

    // 创建队列
    public boolean queueDeclare(String queueName, boolean durable, boolean exclusive, boolean autoDelete,
                                Map<String, Object> arguments) throws IOException {
        QueueDeclareArguments queueDeclareArguments = new QueueDeclareArguments();
        queueDeclareArguments.setRid(generateRid());
        queueDeclareArguments.setChannelId(channelId);
        queueDeclareArguments.setQueueName(queueName);
        queueDeclareArguments.setDurable(durable);
        queueDeclareArguments.setExclusive(exclusive);
        queueDeclareArguments.setAutoDelete(autoDelete);
        queueDeclareArguments.setArguments(arguments);
        byte[] payload = BinaryTool.toBinary(queueDeclareArguments);

        Request request = new Request();
        request.setType(0x5);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(queueDeclareArguments.getRid());
        return basicReturns.isOk();
    }

    // 删除队列
    public boolean queueDelete(String queueName) throws IOException {
        QueueDeleteArguments arguments = new QueueDeleteArguments();
        arguments.setRid(generateRid());
        arguments.setChannelId(channelId);
        arguments.setQueueName(queueName);
        byte[] payload = BinaryTool.toBinary(arguments);

        Request request = new Request();
        request.setType(0x6);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(arguments.getRid());
        return basicReturns.isOk();
    }

    // 创建绑定
    public boolean queueBind(String queueName, String exchangeName, String bindingKey) throws IOException {
        QueueBindArguments arguments = new QueueBindArguments();
        arguments.setRid(generateRid());
        arguments.setChannelId(channelId);
        arguments.setQueueName(queueName);
        arguments.setExchangeName(exchangeName);
        arguments.setBindingKey(bindingKey);
        byte[] payload = BinaryTool.toBinary(arguments);

        Request request = new Request();
        request.setType(0x7);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(arguments.getRid());
        return basicReturns.isOk();
    }

    // 解除绑定
    public boolean queueUnbind(String queueName, String exchangeName) throws IOException {
        QueueUnbindArguments arguments = new QueueUnbindArguments();
        arguments.setRid(generateRid());
        arguments.setChannelId(channelId);
        arguments.setQueueName(queueName);
        arguments.setExchangeName(exchangeName);
        byte[] payload = BinaryTool.toBinary(arguments);

        Request request = new Request();
        request.setType(0x8);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(arguments.getRid());
        return basicReturns.isOk();
    }

    // 发送消息
    public boolean basicPublish(String exchangeName, String routingKey, BasicProperties basicProperties, byte[] body) throws IOException {
        BasicPublishArguments arguments = new BasicPublishArguments();
        arguments.setRid(generateRid());
        arguments.setChannelId(channelId);
        arguments.setExchangeName(exchangeName);
        arguments.setRoutingKey(routingKey);
        arguments.setBasicProperties(basicProperties);
        arguments.setBody(body);
        byte[] payload = BinaryTool.toBinary(arguments);

        Request request = new Request();
        request.setType(0x9);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(arguments.getRid());
        return basicReturns.isOk();
    }
    // 订阅消息
    public boolean basicConsume(String queueName, boolean autoAck, Consumer consumer)
            throws MqException, IOException {
        BasicConsumeArguments arguments = new BasicConsumeArguments();
        arguments.setRid(generateRid());
        arguments.setChannelId(channelId);
        arguments.setConsumerTag(channelId);
        arguments.setQueueName(queueName);
        arguments.setAutoAck(autoAck);
        byte[] payload = BinaryTool.toBinary(arguments);

        Request request = new Request();
        request.setType(0xa);
        request.setLength(payload.length);
        request.setPayload(payload);

        java.util.Objects.requireNonNull(consumer, "consumer");
        synchronized (this) {
            if (this.consumer != null) {
                throw new MqException("该 channel 已经设置过消费消息的回调了, 不能重复设置!");
            }
            this.consumer = consumer;
        }

        boolean ok = false;
        try {
            connection.writeRequest(request);
            ok = waitResult(arguments.getRid()).isOk();
            return ok;
        } catch (java.io.InterruptedIOException e) {
            connection.close();
            throw e;
        } finally {
            //最后如果订阅队列失败了,一定要回滚已经设置了的回调函数
            if (!ok) {
                synchronized (this) {
                    this.consumer = null;
                }
            }
        }
    }

    // 确认消息
    public boolean basicAck(String queueName, String messageId) throws IOException {
        BasicAckArguments arguments = new BasicAckArguments();
        arguments.setRid(generateRid());
        arguments.setChannelId(channelId);
        arguments.setQueueName(queueName);
        arguments.setMessageId(messageId);
        byte[] payload = BinaryTool.toBinary(arguments);

        Request request = new Request();
        request.setType(0xb);
        request.setLength(payload.length);
        request.setPayload(payload);

        connection.writeRequest(request);
        BasicReturns basicReturns = waitResult(arguments.getRid());
        return basicReturns.isOk();
    }
}

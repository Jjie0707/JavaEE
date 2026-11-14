package jjie.msgqueue.server.mapper.Manager;

import jjie.msgqueue.common.Consumer;
import jjie.msgqueue.common.ConsumerEnv;
import jjie.msgqueue.common.MqException;
import jjie.msgqueue.server.VirtualHost;
import jjie.msgqueue.server.core.BasicProperties;
import jjie.msgqueue.server.core.Message;
import jjie.msgqueue.server.core.MsgQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsumerManager {
    // 持有上层的 VirtualHost 对象的引用. 用来操作数据.
    private VirtualHost parent;
    // 指定一个线程池, 负责去执行具体的回调任务.
    private ExecutorService workerPool = Executors.newFixedThreadPool(4);
    // 存放令牌的队列
    private BlockingQueue<String> tokenQueue = new LinkedBlockingQueue<>();
    // 扫描线程
    private Thread scannerThread = null;
    public ConsumerManager(VirtualHost p) {
        parent = p;

        scannerThread = new Thread(() -> {
            while (true) {
                try {
                    // 1. 拿到令牌
                    String queueName = tokenQueue.take();
                    // 2. 根据令牌, 找到队列
                    MsgQueue queue = parent.getMemoryDataManager().getQueue(queueName);
                    if (queue == null) {
                        throw new MqException("[ConsumerManager] 取令牌后发现, 该队列名不存在! queueName=" + queueName);
                    }
                    // 3. 从这个队列中消费一个消息.
                    synchronized (queue) {
                        consumeMessage(queue);
                    }
                } catch (InterruptedException | MqException e) {
                    e.printStackTrace();
                }
            }
        });
        // 把线程设为后台线程.
        scannerThread.setDaemon(true);
        scannerThread.start();
    }

    // 这个方法的调用时机就是发送消息的时候.
    public void notifyConsume(String queueName) throws InterruptedException {
        tokenQueue.put(queueName);
    }

    public void addConsumer(String consumerTag, String queueName, boolean autoAck, Consumer consumer) throws MqException {
        // 找到对应的队列.
        MsgQueue queue = parent.getMemoryDataManager().getQueue(queueName);
        if (queue == null) {
            throw new MqException("[ConsumerManager] 队列不存在! queueName=" + queueName);
        }
        ConsumerEnv consumerEnv = new ConsumerEnv(consumerTag, queueName, autoAck, consumer);
        synchronized (queue) {
            if (parent.getMemoryDataManager().getQueue(queueName) != queue) {
                throw new MqException(
                        "[ConsumerManager] 队列已经删除! queueName=" + queueName
                );
            }
            queue.addConsumerEnv(consumerEnv);
            // 如果当前队列中已经有了一些消息了, 需要立即就消费掉.
            int n = parent.getMemoryDataManager().getMessageCount(queueName);
            for (int i = 0; i < n; i++) {
                // 这个方法调用一次就消费一条消息.
                consumeMessage(queue);
            }
        }
    }
    private void consumeMessage(MsgQueue queue) {
        String queueName = queue.getName();

        // 调用这个方法的两个位置，都已经持有 synchronized (queue)。
        if (parent.getMemoryDataManager().getQueue(queueName) != queue) {
            return;
        }

        ConsumerEnv consumerEnv = queue.chooseConsumer();
        if (consumerEnv == null) {
            return;
        }

        Message message =
                parent.getMemoryDataManager().pollMessage(queueName);

        if (message == null) {
            return;
        }

        workerPool.submit(() -> {
            try {
                String messageId = message.getBasicProperties().getId();

                // 必须先登记待确认记录，再执行消费者的回调。
                synchronized (queue) {
                    if (parent.getMemoryDataManager().getQueue(queueName) != queue) {
                        return;
                    }

                    parent.getMemoryDataManager()
                            .addMessageUnAck(queueName, message);
                }

                // 给消费者一份属性副本，ID 与服务端消息一致。
                BasicProperties deliveryProperties = new BasicProperties();
                deliveryProperties.setId(messageId);
                deliveryProperties.setRoutingKey(message.getRoutingKey());
                deliveryProperties.setDeliverMode(message.getDeliverMode());

                // 回调在队列锁外执行，回调里面可以调用 basicAck。
                consumerEnv.getConsumer().handleDelivery(
                        consumerEnv.getConsumerTag(),
                        deliveryProperties,
                        message.getBody()
                );

                // 回调正常返回后，才执行自动确认。
                if (consumerEnv.isAutoAck()) {
                    parent.acknowledgeDelivery(queue, messageId);
                }
            } catch (Exception e) {
                // 回调失败时，待确认记录仍然保留。
                e.printStackTrace();
            }
        });
    }
}

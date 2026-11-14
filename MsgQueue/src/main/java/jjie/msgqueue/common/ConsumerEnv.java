package jjie.msgqueue.common;

import lombok.Data;

@Data
public class ConsumerEnv {
//    consumerTag, queueName, autoAck, consumer
private String consumerTag;
    private String queueName;
    private boolean autoAck;
    // 通过这个回调来处理收到的消息.
    private Consumer consumer;

    public ConsumerEnv(String consumerTag, String queueName, boolean autoAck, Consumer consumer) {
        this.consumerTag = consumerTag;
        this.queueName = queueName;
        this.autoAck = autoAck;
        this.consumer = consumer;
    }
}

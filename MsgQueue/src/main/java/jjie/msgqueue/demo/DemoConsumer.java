package jjie.msgqueue.demo;

import jjie.msgqueue.client.Channel;
import jjie.msgqueue.client.Connection;
import jjie.msgqueue.client.ConnectionFactory;
import jjie.msgqueue.common.consumer.Consumer;
import jjie.msgqueue.common.exception.MqException;
import jjie.msgqueue.server.core.BasicProperties;
import jjie.msgqueue.server.core.ExchangeType;

import java.io.IOException;

public class DemoConsumer {
    public static void main(String[] args) throws IOException, MqException, InterruptedException {
        System.out.println("启动消费者!");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(9090);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("testExchange", ExchangeType.DIRECT, true, false, null);
        channel.queueDeclare("testQueue", true, false, false, null);

        channel.basicConsume("testQueue", true, new Consumer() {
            @Override
            public void handleDelivery(String consumerTag, BasicProperties basicProperties, byte[] body) throws MqException, IOException {
                System.out.println("[消费数据] 开始!");
                System.out.println("consumerTag=" + consumerTag);
                System.out.println("basicProperties=" + basicProperties);
                String bodyString = new String(body, 0, body.length);
                System.out.println("body=" + bodyString);
                System.out.println("[消费数据] 结束!");
            }
        });

    }
}

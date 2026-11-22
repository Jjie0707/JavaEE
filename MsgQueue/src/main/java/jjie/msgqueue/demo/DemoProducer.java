package jjie.msgqueue.demo;

import jjie.msgqueue.client.Channel;
import jjie.msgqueue.client.Connection;
import jjie.msgqueue.client.ConnectionFactory;
import jjie.msgqueue.server.core.ExchangeType;

import java.io.IOException;
import java.util.Scanner;

public class DemoProducer {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("启动生产者");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(9090);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 创建交换机和队列
        channel.exchangeDeclare("testExchange", ExchangeType.DIRECT, true, false, null);
        channel.queueDeclare("testQueue", true, false, false, null);
        channel.queueBind("testQueue", "testExchange", "6666");

        Scanner s=new Scanner(System.in);
        while(true){
            System.out.println("输入1生产消息,输入其他结束连接!");
            int input=s.nextInt();
            if(input==1){
                // 创建一个消息并发送
                byte[] body = "hello".getBytes();
                boolean ok = channel.basicPublish("testExchange", "testQueue", null, body);
                System.out.println("消息投递完成! ok=" + ok);
            }else{
                try {
                    channel.close();
                } finally {
                    connection.close();
                }
            }

        }
    }
}

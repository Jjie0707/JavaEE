package jjie.msgqueue;

import jjie.msgqueue.server.BrokerServer;
import jjie.msgqueue.server.VirtualHost;
import jjie.msgqueue.server.mapper.MetaMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class MsgQueueApplication {
    public static ConfigurableApplicationContext context;
    public static void main(String[] args) throws IOException {
        context=SpringApplication.run(MsgQueueApplication.class, args);
        BrokerServer brokerServer = new BrokerServer(9090);
        brokerServer.start();
    }
}

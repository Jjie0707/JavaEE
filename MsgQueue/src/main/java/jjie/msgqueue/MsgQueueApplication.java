package jjie.msgqueue;

import jjie.msgqueue.server.mapper.MetaMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MsgQueueApplication {
    public static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        context=SpringApplication.run(MsgQueueApplication.class, args);
    }

}

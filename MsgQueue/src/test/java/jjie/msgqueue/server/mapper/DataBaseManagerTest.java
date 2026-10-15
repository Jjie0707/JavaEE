package jjie.msgqueue.server.mapper;

import jjie.msgqueue.MsgQueueApplication;
import jjie.msgqueue.server.core.Binding;
import jjie.msgqueue.server.core.Exchange;
import jjie.msgqueue.server.core.ExchangeType;
import jjie.msgqueue.server.core.MsgQueue;
import jjie.msgqueue.server.mapper.Manager.DataBaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootTest
class DataBaseManagerTest {

    @Autowired
    private ConfigurableApplicationContext testContext;
    private DataBaseManager dataBaseManager;

    @BeforeEach
    public void setUp(){
        MsgQueueApplication.context=testContext;
        dataBaseManager =new DataBaseManager();
        dataBaseManager.init();
    }
    @Test
    void insertExchange() {
        Exchange exchange=new Exchange();
        exchange.setName("测试");
        exchange.setExchangeType(ExchangeType.FANOUT);
        dataBaseManager.insertExchange(exchange);
    }

    @Test
    void selectAllExchanges() {
        dataBaseManager.selectAllExchanges();
    }

    @Test
    void deleteDBExchangeByName() {
        dataBaseManager.deleteExchangeByName("2104950392252420098");
    }

    @Test
    void insertQueue() {
        MsgQueue queue=new MsgQueue();
        queue.setName("测试队列2");queue.setExclusive(true);
        dataBaseManager.insertQueue(queue);
    }

    @Test
    void selectAllQueues() {
        dataBaseManager.selectAllQueues();
    }

    @Test
    void deleteDBQueueByName() {
        dataBaseManager.deleteQueueByName("测试队列");
    }

    @Test
    void insertBinding() {
        Binding binding=new Binding();
        binding.setExchangeName("exchange");
        binding.setQueueName("queue");
        binding.setBindingKey("缔结契约");
        dataBaseManager.insertBinding(binding);
    }

    @Test
    void selectAllBindings() {
        dataBaseManager.selectAllBindings();
    }

    @Test
    void deleteDBBinding() {
        Binding binding=new Binding();
        binding.setExchangeName("exchange");
        binding.setQueueName("queue");
        binding.setBindingKey("缔结契约");
        dataBaseManager.deleteBinding(binding);
    }
}
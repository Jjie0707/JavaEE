package jjie.msgqueue.server.mapper;

import jjie.msgqueue.MsgQueueApplication;
import jjie.msgqueue.server.core.Binding;
import jjie.msgqueue.server.core.Exchange;
import jjie.msgqueue.server.core.ExchangeType;
import jjie.msgqueue.server.core.MsgQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootTest
class DataManagerTest {

    @Autowired
    private ConfigurableApplicationContext testContext;
    private DataManager dataManager;

    @BeforeEach
    public void setUp(){
        MsgQueueApplication.context=testContext;
        dataManager=new DataManager();
        dataManager.init();
    }
    @Test
    void insertExchange() {
        Exchange exchange=new Exchange();
        exchange.setName("测试");
        exchange.setExchangeType(ExchangeType.FANOUT);
        dataManager.insertExchange(exchange);
    }

    @Test
    void selectAllExchanges() {
        dataManager.selectAllExchanges();
    }

    @Test
    void deleteDBExchangeByName() {
        dataManager.deleteExchangeByName("2104950392252420098");
    }

    @Test
    void insertQueue() {
        MsgQueue queue=new MsgQueue();
        queue.setName("测试队列2");queue.setExclusive(true);
        dataManager.insertQueue(queue);
    }

    @Test
    void selectAllQueues() {
        dataManager.selectAllQueues();
    }

    @Test
    void deleteDBQueueByName() {
        dataManager.deleteQueueByName("测试队列");
    }

    @Test
    void insertBinding() {
        Binding binding=new Binding();
        binding.setExchangeName("exchange");
        binding.setQueueName("queue");
        binding.setBindingKey("缔结契约");
        dataManager.insertBinding(binding);
    }

    @Test
    void selectAllBindings() {
        dataManager.selectAllBindings();
    }

    @Test
    void deleteDBBinding() {
        Binding binding=new Binding();
        binding.setExchangeName("exchange");
        binding.setQueueName("queue");
        binding.setBindingKey("缔结契约");
        dataManager.deleteBinding(binding);
    }
}
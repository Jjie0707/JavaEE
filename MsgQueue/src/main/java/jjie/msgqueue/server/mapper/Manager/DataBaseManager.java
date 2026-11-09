package jjie.msgqueue.server.mapper.Manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jjie.msgqueue.MsgQueueApplication;
import jjie.msgqueue.server.core.Binding;
import jjie.msgqueue.server.core.Exchange;
import jjie.msgqueue.server.core.ExchangeType;
import jjie.msgqueue.server.core.MsgQueue;
import jjie.msgqueue.server.mapper.BindingMapper;
import jjie.msgqueue.server.mapper.ExchangeMapper;
import jjie.msgqueue.server.mapper.MetaMapper;
import jjie.msgqueue.server.mapper.MsgQueueMapper;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.List;

public class DataBaseManager {
    private ApplicationContext context= MsgQueueApplication.context;

    private MetaMapper metaMapper=context.getBean(MetaMapper.class);
    private BindingMapper bindingMapper=context.getBean(BindingMapper.class);
    private ExchangeMapper exchangeMapper=context.getBean(ExchangeMapper.class);
    private MsgQueueMapper msgQueueMapper=context.getBean(MsgQueueMapper.class);


    public void init(){
        if (!metaMapper.check()) {
            // 数据库不存在, 就进行建建库表操作
            // 先创建一个 data 目录
            File dataDir = new File("./data");
            dataDir.mkdirs();
            // 创建数据表
            metaMapper.createTable();
            // 插入默认数据
            createDefaultData();
            System.out.println("[DataBaseManager] 数据库初始化完成!");
        } else {
            // 数据库已经存在了, 啥都不必做即可
            System.out.println("[DataBaseManager] 数据库已经存在!");
        }
    }
    public void deleteDB(){
        metaMapper.deleteDB();
    }

    private void createDefaultData() {
        // 构造一个默认的交换机.
        Exchange exchange = new Exchange();
        exchange.setName("");
        exchange.setExchangeType(ExchangeType.DIRECT);
        exchange.setDurable(0);
        exchange.setAutoDelete(false);
        this.insertExchange(exchange);
        System.out.println("[DataManager] 创建初始数据完成!");
    }

    /// 把其他的数据库的操作, 也在这个类中封装一下.
    /// Exchange
    public void insertExchange(Exchange exchange) {
        exchangeMapper.insert(exchange);
    }

    public List<Exchange> selectAllExchanges() {
        return exchangeMapper.selectList(new LambdaQueryWrapper<>());
    }

    public void deleteExchangeByName(String exchangeName) {
        exchangeMapper.delete(
                new LambdaQueryWrapper<Exchange>()
                        .eq(Exchange::getName, exchangeName)
        );
    }


    ///MsgQueue
    public void insertQueue(MsgQueue queue) {
        msgQueueMapper.insert(queue);
    }

    public List<MsgQueue> selectAllQueues() {
        return msgQueueMapper.selectList(new LambdaQueryWrapper<>());
    }

    public void deleteQueueByName(String queueName) {
        msgQueueMapper.delete(
                new LambdaQueryWrapper<MsgQueue>()
                        .eq(MsgQueue::getName, queueName)
        );
    }

    /// Binding
    public void insertBinding(Binding binding) {
        bindingMapper.insert(binding);
    }

    public List<Binding> selectAllBindings() {
        return bindingMapper.selectList(new LambdaQueryWrapper<>());
    }

    public void deleteBinding(Binding binding) {
        bindingMapper.delete(
                new LambdaQueryWrapper<Binding>()
                        .eq(Binding::getQueueName, binding.getQueueName())
                        .eq(Binding::getExchangeName,binding.getExchangeName())
        );
    }
}

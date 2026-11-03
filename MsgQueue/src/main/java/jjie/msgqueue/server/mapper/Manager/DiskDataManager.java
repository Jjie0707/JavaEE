package jjie.msgqueue.server.mapper.Manager;

import jjie.msgqueue.common.MqException;
import jjie.msgqueue.server.core.Binding;
import jjie.msgqueue.server.core.Exchange;
import jjie.msgqueue.server.core.Message;
import jjie.msgqueue.server.core.MsgQueue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DiskDataManager {
    private DataBaseManager dataBaseManager=new DataBaseManager();
    private DataFileManager dataFileManager=new DataFileManager();

    public void init() {
        // 针对上述两个实例进行初始化.
        dataBaseManager.init();
        // 当前 messageFileManager.init 是空的方法, 只是先列在这里, 一旦后续需要扩展, 就在这里进行初始化即可.
        dataFileManager.init();
    }

    // 封装交换机操作
    public void insertExchange(Exchange exchange) {
        dataBaseManager.insertExchange(exchange);
    }

    public void deleteExchange(String exchangeName) {
        dataBaseManager.deleteExchangeByName(exchangeName);
    }

    public List<Exchange> selectAllExchanges() {
        return dataBaseManager.selectAllExchanges();
    }

    // 封装队列操作
    public void insertQueue(MsgQueue queue) throws IOException {
        dataBaseManager.insertQueue(queue);
        // 创建队列的同时, 不仅仅是把队列对象写到数据库中, 还需要创建出对应的目录和文件
        dataFileManager.createQueueFiles(queue.getName());
    }

    public void deleteQueue(String queueName) throws IOException {
        dataBaseManager.deleteQueueByName(queueName);
        // 删除队列的同时, 不仅仅是把队列从数据库中删除, 还需要删除对应的目录和文件
        dataFileManager.destroyQueueFiles(queueName);
    }

    public List<MsgQueue> selectAllQueues() {
        return dataBaseManager.selectAllQueues();
    }

    // 封装绑定操作
    public void insertBinding(Binding binding) {
        dataBaseManager.insertBinding(binding);
    }

    public void deleteBinding(Binding binding) {
        dataBaseManager.deleteBinding(binding);
    }

    public List<Binding> selectAllBindings() {
        return dataBaseManager.selectAllBindings();
    }

    // 封装消息操作
    public void sendMessage(MsgQueue queue, Message message) throws IOException, MqException {
        dataFileManager.sendMessage(queue, message);
    }

    public void deleteMessage(MsgQueue queue, Message message) throws IOException, ClassNotFoundException, MqException {
        dataFileManager.deleteMessage(queue, message);
        if (dataFileManager.checkGc(queue.getName())) {
            dataFileManager.gc(queue);
        }
    }

    public LinkedList<Message> loadAllMessageFromQueue(String queueName) throws IOException, MqException, ClassNotFoundException {
        return dataFileManager.getAllMessages(queueName);
    }
}

package jjie.msgqueue.server.mapper.Manager;

import jjie.msgqueue.common.MqException;
import jjie.msgqueue.server.core.Binding;
import jjie.msgqueue.server.core.Exchange;
import jjie.msgqueue.server.core.Message;
import jjie.msgqueue.server.core.MsgQueue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryDataManager {
//    组织内存里的数据
    // key 是 exchangeName, value 是 Exchange 对象(组织Exchange)
    private ConcurrentHashMap<String, Exchange> exchangeMap = new ConcurrentHashMap<>();
    // key 是 queueName, value 是 MSGQueue 对象(组织MsgQueue)
    private ConcurrentHashMap<String, MsgQueue> queueMap = new ConcurrentHashMap<>();
    // 第一个 key 是 exchangeName, 第二个 key 是 queueName(组织Binding)
    private ConcurrentHashMap<String, ConcurrentHashMap<String, Binding>> bindingsMap = new ConcurrentHashMap<>();
    // key 是 queueName, value 是一个 Message 的链表(组织一个队列的所有消息)
    private ConcurrentHashMap<String, LinkedList<Message>> queueMessageMap = new ConcurrentHashMap<>();
    // 第一个 key 是 queueName, 第二个 key 是 messageId(组织未确认的消息)
    private ConcurrentHashMap<String, ConcurrentHashMap<String, Message>> queueMessageUnAckMap = new ConcurrentHashMap<>();


    public void insertExchange(Exchange exchange) {
        exchangeMap.put(exchange.getName(), exchange);
        System.out.println("[MemoryDataCenter] 新交换机添加成功! exchangeName=" + exchange.getName());
    }

    public Exchange getExchange(String exchangeName) {
        return exchangeMap.get(exchangeName);
    }

    public void deleteExchange(String exchangeName) {
        exchangeMap.remove(exchangeName);
        bindingsMap.remove(exchangeName);
        System.out.println("[MemoryDataCenter] 交换机删除成功! exchangeName=" + exchangeName);
    }

    public void insertQueue(MsgQueue queue) {
        queueMap.put(queue.getName(), queue);
        System.out.println("[MemoryDataCenter] 新队列添加成功! queueName=" + queue.getName());
    }

    public MsgQueue getQueue(String queueName) {
        return queueMap.get(queueName);
    }

    public void deleteQueue(String queueName) {
        queueMap.remove(queueName);
        queueMessageMap.remove(queueName);
        queueMessageUnAckMap.remove(queueName);
        // 队列没了, 所有指向它的绑定都得摘掉
        bindingsMap.values().forEach(m -> m.remove(queueName));
        System.out.println("[MemoryDataCenter] 队列删除成功! queueName=" + queueName);
    }

    public void insertBinding(Binding binding) throws MqException {
        if (exchangeMap.get(binding.getExchangeName()) == null) {
            throw new MqException("[MemoryDataCenter] 交换机不存在! exchangeName=" + binding.getExchangeName());
        }
        if (queueMap.get(binding.getQueueName()) == null) {
            throw new MqException("[MemoryDataCenter] 队列不存在! queueName=" + binding.getQueueName());
        }
        // 先使用 exchangeName 查一下, 对应的哈希表是否存在. 不存在就创建一个.
        ConcurrentHashMap<String, Binding> bindingMap = bindingsMap.computeIfAbsent(
                binding.getExchangeName(), k -> new ConcurrentHashMap<>());
        //插入顺便进行校验
        if (bindingMap.putIfAbsent(binding.getQueueName(), binding) != null) {
            throw new MqException("[MemoryDataCenter] 绑定已经存在! exchangeName=" + binding.getExchangeName()
                    + ", queueName=" + binding.getQueueName());
        }

        System.out.println("[MemoryDataCenter] 新绑定添加成功! exchangeName=" + binding.getExchangeName()
                + ", queueName=" + binding.getQueueName());
    }

    // 获取绑定, 两个版本:
    // 1. 根据 exchangeName 和 queueName 确定唯一一个 Binding
    // 2. 根据 exchangeName 获取到所有的 Binding
    public Binding getBinding(String exchangeName, String queueName) {
        ConcurrentHashMap<String, Binding> bindingMap = bindingsMap.get(exchangeName);
        if (bindingMap == null) {
            return null;
        }
        return bindingMap.get(queueName);
    }

    public ConcurrentHashMap<String, Binding> getBindings(String exchangeName) {
        return bindingsMap.getOrDefault(exchangeName, new ConcurrentHashMap<>());
    }

    public void deleteBinding(Binding binding) throws MqException {
        ConcurrentHashMap<String, Binding> bindingMap = bindingsMap.get(binding.getExchangeName());
        if (bindingMap == null || bindingMap.remove(binding.getQueueName()) == null) {
            throw new MqException("[MemoryDataCenter] 绑定不存在! exchangeName=" + binding.getExchangeName()
                    + ", queueName=" + binding.getQueueName());
        }
        System.out.println("[MemoryDataCenter] 绑定删除成功! exchangeName=" + binding.getExchangeName()
                + ", queueName=" + binding.getQueueName());
    }


    // 发送消息到指定队列
    public void sendMessage(MsgQueue queue, Message message) {
        LinkedList<Message> messages = queueMessageMap.computeIfAbsent(
                queue.getName(), name -> new LinkedList<>()
        );

        synchronized (messages) {
            messages.addLast(message);
        }

        System.out.println(
                "[MemoryDataManager] 消息进入队列! queueName="
                        + queue.getName()
                        + ", messageId="
                        + message.getBasicProperties().getId()
        );
    }

    // 从队列中取消息
    public Message pollMessage(String queueName) {
        // 根据队列名, 查找一下, 对应的队列的消息链表.
        LinkedList<Message> messages = queueMessageMap.get(queueName);
        if (messages == null) {
            return null;
        }
        synchronized (messages) {
            // 如果没找到, 说明队列中没有任何消息.
            if (messages.size() == 0) {
                return null;
            }
            // 链表中有元素, 就进行头删.
            Message currentMessage = messages.remove(0);
            System.out.println("[MemoryDataCenter] 消息从队列中取出! messageId=" + currentMessage.getBasicProperties().getId());
            return currentMessage;
        }
    }

    // 获取指定队列中消息的个数
    public int getMessageCount(String queueName) {
        LinkedList<Message> messages = queueMessageMap.get(queueName);
        if (messages == null) {
            // 队列中没有消息
            return 0;
        }
        synchronized (messages) {
            return messages.size();
        }
    }

    // 添加未确认的消息
    public void addMessageUnAck(String queueName, Message message) {
        ConcurrentHashMap<String, Message> messageHashMap = queueMessageUnAckMap.computeIfAbsent(queueName,
                k -> new ConcurrentHashMap<>());
        messageHashMap.put(message.getBasicProperties().getId(), message);
        System.out.println("[MemoryDataCenter] 消息进入待确认队列! messageId=" + message.getBasicProperties().getId());
    }

    // 删除未确认的消息(消息已经确认了)
    public void removeMessageUnAck(String queueName, String messageId) {
        ConcurrentHashMap<String, Message> messageHashMap = queueMessageUnAckMap.get(queueName);
        if (messageHashMap == null) {
            return;
        }
        messageHashMap.remove(messageId);
        System.out.println("[MemoryDataCenter] 消息从待确认队列删除! messageId=" + messageId);
    }

    // 获取指定的未确认的消息
    public Message getMessageUnAck(String queueName, String messageId) {
        ConcurrentHashMap<String, Message> messageHashMap = queueMessageUnAckMap.get(queueName);
        if (messageHashMap == null) {
            return null;
        }
        return messageHashMap.get(messageId);
    }

    // 这个方法就是从硬盘上读取数据, 把硬盘中之前持久化存储的各个维度的数据都恢复到内存中.
    public void recovery(DiskDataManager diskDataManager) throws IOException, MqException, ClassNotFoundException {
        // 0. 清空之前的所有数据
        exchangeMap.clear();
        queueMap.clear();
        bindingsMap.clear();
        queueMessageMap.clear();queueMessageUnAckMap.clear();
        // 1. 恢复所有的交换机数据
        List<Exchange> exchanges = diskDataManager.selectAllExchanges();
        for (Exchange exchange : exchanges) {
            exchangeMap.put(exchange.getName(), exchange);
        }
        // 2. 恢复所有的队列数据
        List<MsgQueue> queues = diskDataManager.selectAllQueues();
        for (MsgQueue queue : queues) {
            queueMap.put(queue.getName(), queue);
        }
        // 3. 恢复所有的绑定数据
        List<Binding> bindings = diskDataManager.selectAllBindings();
        for (Binding binding : bindings) {
            ConcurrentHashMap<String, Binding> bindingMap = bindingsMap.computeIfAbsent(binding.getExchangeName(),
                    k -> new ConcurrentHashMap<>());
            bindingMap.put(binding.getQueueName(), binding);
        }
        // 4. 恢复所有的消息数据
        //    遍历所有的队列, 根据每个队列的名字, 获取到所有的消息.
        //  有一个队列报错时,不摧毁整个恢复程序,只是把恢复失败失败的queue报告出来
        List<String> brokenQueues = new ArrayList<>();
        for (MsgQueue queue : queues) {
            try {
                LinkedList<Message> messages = diskDataManager.loadAllMessageFromQueue(queue.getName());
                queueMessageMap.put(queue.getName(), messages);

            } catch (Exception e) {
                // 队列本身还能用, 只是历史消息读不出来, 先给个空链表
                queueMessageMap.put(queue.getName(), new LinkedList<>());
                brokenQueues.add(queue.getName());
                System.err.println("[MemoryDataManager] 队列恢复失败, 已跳过: "
                        + queue.getName() + " 原因: " + e);
            }
        }
        if (!brokenQueues.isEmpty()) {
            System.err.println("[MemoryDataManager] 共 " + brokenQueues.size()
                    + " 个队列恢复失败, 请检查磁盘: " + brokenQueues);
        }
        // 注意!! 针对 "未确认的消息" 这部分内存中的数据, 不需要从硬盘恢复. 之前考虑硬盘存储的时候, 也没设定这一块.
        // 一旦在等待 ack 的过程中, 服务器重启了, 此时这些 "未被确认的消息", 就恢复成 "未被取走的消息" .
        // 这个消息在硬盘上存储的时候, 就是当做 "未被取走"
    }
}

package jjie.msgqueue.server.mapper.Manager;

import jjie.msgqueue.common.MqException;
import jjie.msgqueue.server.core.Binding;
import jjie.msgqueue.server.core.Exchange;
import jjie.msgqueue.server.core.Message;
import jjie.msgqueue.server.core.MsgQueue;

import java.io.IOException;
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
    // key 是 messageId, value 是 Message 对象(组织message)
    private ConcurrentHashMap<String, Message> messageMap = new ConcurrentHashMap<>();
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
        System.out.println("[MemoryDataCenter] 队列删除成功! queueName=" + queueName);
    }

    public void insertBinding(Binding binding) throws MqException {
        // 先使用 exchangeName 查一下, 对应的哈希表是否存在. 不存在就创建一个.
        ConcurrentHashMap<String, Binding> bindingMap = bindingsMap.computeIfAbsent(binding.getExchangeName(),
                k -> new ConcurrentHashMap<>());
        synchronized (bindingMap) {
            // 再根据 queueName 查一下. 如果已经存在, 就抛出异常. 不存在才能插入.
            if (bindingMap.get(binding.getQueueName()) != null) {
                throw new MqException("[MemoryDataCenter] 绑定已经存在! exchangeName=" + binding.getExchangeName() +
                        ", queueName=" + binding.getQueueName());
            }
            bindingMap.put(binding.getQueueName(), binding);
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
        return bindingsMap.get(exchangeName);
    }

    public void deleteBinding(Binding binding) throws MqException {
        ConcurrentHashMap<String, Binding> bindingMap = bindingsMap.get(binding.getExchangeName());
        if (bindingMap == null) {
            // 该交换机没有绑定任何队列. 报错.
            throw new MqException("[MemoryDataCenter] 绑定不存在! exchangeName=" + binding.getExchangeName()
                    + ", queueName=" + binding.getQueueName());
        }
        bindingMap.remove(binding.getQueueName());
        System.out.println("[MemoryDataCenter] 绑定删除成功! exchangeName=" + binding.getExchangeName()
                + ", queueName=" + binding.getQueueName());
    }

    // 添加消息
    public void addMessage(Message message) {
        messageMap.put(message.getBasicProperties().getId(), message);
        System.out.println("[MemoryDataCenter] 新消息添加成功! messageId=" + message.getBasicProperties().getId());
    }

    // 根据 id 查询消息
    public Message getMessage(String messageId) {
        return messageMap.get(messageId);
    }

    // 根据 id 删除消息
    public void removeMessage(String messageId) {
        messageMap.remove(messageId);
        System.out.println("[MemoryDataCenter] 消息被移除! messageId=" + messageId);
    }

    // 发送消息到指定队列
    public void sendMessage(MsgQueue queue, Message message) {
        // 把消息放到对应的队列数据结构中.
        // 先根据队列的名字, 找到该队列对应的消息链表.
        LinkedList<Message> messages = queueMessageMap.computeIfAbsent(queue.getName(), k -> new LinkedList<>());
        // 再把数据加到 messages 里面
        synchronized (messages) {
            messages.add(message);
        }
        // 在这里把该消息也往消息中心中插入一下. 假设如果 message 已经在消息中心存在, 重复插入也没关系.
        // 主要就是相同 messageId, 对应的 message 的内容一定是一样的. (服务器代码不会对 Message 内容做修改 basicProperties 和 body)
        addMessage(message);
        System.out.println("[MemoryDataCenter] 消息被投递到队列中! messageId=" + message.getBasicProperties().getId());
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
    public void addMessageWaitAck(String queueName, Message message) {
        ConcurrentHashMap<String, Message> messageHashMap = queueMessageUnAckMap.computeIfAbsent(queueName,
                k -> new ConcurrentHashMap<>());
        messageHashMap.put(message.getBasicProperties().getId(), message);
        System.out.println("[MemoryDataCenter] 消息进入待确认队列! messageId=" + message.getBasicProperties().getId());
    }

    // 删除未确认的消息(消息已经确认了)
    public void removeMessageWaitAck(String queueName, String messageId) {
        ConcurrentHashMap<String, Message> messageHashMap = queueMessageUnAckMap.get(queueName);
        if (messageHashMap == null) {
            return;
        }
        messageHashMap.remove(messageId);
        System.out.println("[MemoryDataCenter] 消息从待确认队列删除! messageId=" + messageId);
    }

    // 获取指定的未确认的消息
    public Message getMessageWaitAck(String queueName, String messageId) {
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
        messageMap.clear();
        queueMessageMap.clear();
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
        for (MsgQueue queue : queues) {
            LinkedList<Message> messages = diskDataManager.loadAllMessageFromQueue(queue.getName());
            queueMessageMap.put(queue.getName(), messages);
            for (Message message : messages) {
                messageMap.put(message.getBasicProperties().getId(), message);
            }
        }
        // 注意!! 针对 "未确认的消息" 这部分内存中的数据, 不需要从硬盘恢复. 之前考虑硬盘存储的时候, 也没设定这一块.
        // 一旦在等待 ack 的过程中, 服务器重启了, 此时这些 "未被确认的消息", 就恢复成 "未被取走的消息" .
        // 这个消息在硬盘上存储的时候, 就是当做 "未被取走"
    }
}

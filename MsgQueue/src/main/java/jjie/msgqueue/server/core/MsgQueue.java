package jjie.msgqueue.server.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jjie.msgqueue.common.ConsumerEnv;
import lombok.Data;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@TableName("msgqueue")
public class MsgQueue {
    //作为每个队列唯一的标识
    @TableId(value = "name", type = IdType.INPUT)
    private String name;
    //是否为独占队列
    @TableField("exclusive")
    private Boolean exclusive;
    //数据是否持久化 0否 1是(未实现)
    @TableField("durable")
    private Integer durable=0;
    //是否自动删除队列(未实现)
    @TableField("autodelete")
    private Boolean autoDelete=true;
    //参数列表
    @TableField("arguments")
    private Map<String, Object> arguments=new HashMap<>();

    // 当前队列都有哪些消费者订阅了.
    @TableField(exist = false)
    private List<ConsumerEnv> consumerEnvList = new ArrayList<>();
    // 记录当前取到了第几个消费者. 方便实现轮询策略.
    @TableField(exist = false)
    private AtomicInteger consumerSeq = new AtomicInteger(0);

    public void addConsumerEnv(ConsumerEnv consumerEnv) {
        consumerEnvList.add(consumerEnv);
    }

    // 订阅者的删除暂时先不考虑.
    // 挑选一个订阅者, 用来处理当前的消息. (按照轮询的方式)
    public ConsumerEnv chooseConsumer() {
        if (consumerEnvList.size() == 0) {
            // 该队列没有人订阅的
            return null;
        }
        // 计算一下当前要取的元素的下标.
        int index = consumerSeq.get() % consumerEnvList.size();
        consumerSeq.getAndIncrement();
        return consumerEnvList.get(index);
    }

    public String getArguments() {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(this.arguments);
    }

    public void setArguments(String object) {
        ObjectMapper objectMapper=new ObjectMapper();

        this.arguments = objectMapper.readValue( object, new TypeReference<Map<String,Object>>(){} );
    }
    public void setArguments(Map<String,Object> arguments){
        this.arguments=arguments;
    }
}

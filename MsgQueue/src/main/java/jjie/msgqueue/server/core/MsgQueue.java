package jjie.msgqueue.server.core;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Data
@TableName("msgqueue")
public class MsgQueue {
    //作为每个队列唯一的标识
    @TableId("name")
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

    public String getArguments() {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(this.arguments);
    }

    public void setArguments(String object) {
        ObjectMapper objectMapper=new ObjectMapper();

        this.arguments = objectMapper.readValue( object, new TypeReference<Map<String,Object>>(){} );
    }
}

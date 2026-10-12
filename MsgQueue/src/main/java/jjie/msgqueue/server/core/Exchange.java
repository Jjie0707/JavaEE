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
@TableName("exchange")
public class Exchange {
    //作为每个交换机唯一的标识
    @TableId("name")
    private String name;
    //每个交换机的类型,默认值是直接交换机(direct)
    @TableField("exchangetype")
    private ExchangeType exchangeType= ExchangeType.DIRECT;
    //数据是否持久化 0否 1是(未实现)
    @TableField("durable")
    private Integer durable=0;
    //是否自动删除交换机(未实现)
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

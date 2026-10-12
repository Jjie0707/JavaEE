package jjie.msgqueue.server.core;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("binding")
public class Binding {
    @TableField("exchangename")
    private String exchangeName;
    @TableField("queuename")
    private String queueName;
    @TableField("bindingkey")
    private String bindingKey;
}

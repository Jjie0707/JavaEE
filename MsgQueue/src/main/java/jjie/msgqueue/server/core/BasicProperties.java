package jjie.msgqueue.server.core;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;
@Data
public class BasicProperties implements Serializable {
    //自动生成以‘M-’开头的唯一id
    private String id= "M-"+UUID.randomUUID();
    private String routingKey;
    //1为需要持久化保存 0为不需要
    public Integer deliverMode=0;
}

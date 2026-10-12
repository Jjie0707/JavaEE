package jjie.msgqueue.server.core;

import lombok.Data;

import java.io.Serializable;
@Data
public class Message implements Serializable {
    private BasicProperties basicProperties=new BasicProperties();
    //传递的数据 支持二进制
    private byte[] body;


    //如果持久化存储的话 开始距离文件开头的偏移量和结尾的偏移量(前闭后开)
    private transient Long offsetS;
    private transient Long  offsetE;
    //有效标识 0无效1有效(相当于逻辑删除 deleteFlag)
    private byte isValid=0x1;

    public static Message getInstance(byte[] body,String routingKey,BasicProperties basicProperties){
        Message ret=new Message();
        if(basicProperties!=null) ret.basicProperties=basicProperties;
        ret.basicProperties.setRoutingKey(routingKey);
        ret.body=body;
        return ret;
    }

    public void setRoutingKey(String routingKey){
        this.getBasicProperties().setRoutingKey(routingKey);
    }
    public void setDeliverMode(Integer deliverMode){
        this.getBasicProperties().setDeliverMode(deliverMode);
    }
}

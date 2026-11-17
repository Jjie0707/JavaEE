package jjie.msgqueue.common.returns;

import jjie.msgqueue.server.core.BasicProperties;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubscribeReturns extends BasicReturns implements Serializable {
    private String consumerTag;
    private BasicProperties basicProperties;
    private byte[] body;
}

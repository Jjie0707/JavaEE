package jjie.msgqueue.common.arguments;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueueBindArguments extends BasicArguments implements Serializable {
    private String queueName;
    private String exchangeName;
    private String bindingKey;
}

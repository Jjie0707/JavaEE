package jjie.msgqueue.common.arguments;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
@Data
public class QueueDeclareArguments extends BasicArguments implements Serializable {
    private String queueName;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;
    private Map<String, Object> arguments;
}

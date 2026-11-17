package jjie.msgqueue.common.arguments;

import jjie.msgqueue.server.core.ExchangeType;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ExchangeDeclareArguments extends BasicArguments implements Serializable {
    private String exchangeName;
    private ExchangeType exchangeType;
    private boolean durable;
    private boolean autoDelete;
    private Map<String, Object> arguments;
}

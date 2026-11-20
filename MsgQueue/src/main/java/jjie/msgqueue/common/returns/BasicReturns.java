package jjie.msgqueue.common.returns;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasicReturns implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String rid;
    protected String channelId;
    protected boolean ok;
}

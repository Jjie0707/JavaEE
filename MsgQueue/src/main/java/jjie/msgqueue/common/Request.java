package jjie.msgqueue.common;

import lombok.Data;

@Data
public class Request {
    private int type;
    private int length;
    private byte[] payload;
}

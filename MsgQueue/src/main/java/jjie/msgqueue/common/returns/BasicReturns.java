package jjie.msgqueue.common.returns;

import lombok.Data;

@Data
public class BasicReturns {
    // 用来标识唯一的请求和响应.
    protected String rid;
    // 用来标识一个 channel
    protected String channelId;
    // 表示当前这个远程调用方法的返回值
    protected boolean ok;
}

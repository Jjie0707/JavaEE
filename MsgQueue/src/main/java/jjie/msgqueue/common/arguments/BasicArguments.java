package jjie.msgqueue.common.arguments;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasicArguments implements Serializable {
    // 表示一次请求/响应 的身份标识. 可以把请求和响应对上.
    protected String rid;
    // 这次通信使用的 channel 的身份标识.
    protected String channelId;

}

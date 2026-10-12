package jjie.msgqueue.server.core;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum ExchangeType {
    DIRECT(0),//直接
    FANOUT(1),//扇出
    TOPIC(2),//topic
    ;

    @EnumValue
    private final Integer  val;

    ExchangeType(int val){this.val=val;};

    public Integer getVal() {
        return val;
    }
}

package jjie.msgqueue.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jjie.msgqueue.server.core.MsgQueue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsgQueueMapper extends BaseMapper<MsgQueue> {
}

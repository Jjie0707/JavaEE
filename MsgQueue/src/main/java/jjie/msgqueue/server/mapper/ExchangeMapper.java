package jjie.msgqueue.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jjie.msgqueue.server.core.Exchange;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExchangeMapper extends BaseMapper<Exchange> {
}

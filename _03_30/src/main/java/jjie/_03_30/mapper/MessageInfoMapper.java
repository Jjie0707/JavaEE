package jjie._03_30.mapper;

import jjie._03_30.entity.MessageInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageInfoMapper {

    @Select("select * from message_info where delete_flag=0")
    List<MessageInfo> getList();

    @Insert("insert into message_info (`from`, `to`, `message`) values (#{from},#{to},#{message})")
    Integer insertMessage(MessageInfo messageInfo);
}

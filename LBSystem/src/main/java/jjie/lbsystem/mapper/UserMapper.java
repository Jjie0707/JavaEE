package jjie.lbsystem.mapper;

import jjie.lbsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user_info where user_name=#{userName}")
    public User selectByName(String userName);
}

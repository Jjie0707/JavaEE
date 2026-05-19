package jjie.pracpart.Mapper;

import jjie.pracpart.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Select("select * from user_info")
    List<UserInfo> selectAll();

    @Select("select * from user_info where username=#{name}")
    List<UserInfo> selectName(String name);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user_info (username, password,gender,age) " +
            "values(#{userInfo.username},#{userInfo.password}," +
            "#{userInfo.gender},#{userInfo.age})")
    int insertUser(@Param("userInfo") UserInfo userInfo);
}

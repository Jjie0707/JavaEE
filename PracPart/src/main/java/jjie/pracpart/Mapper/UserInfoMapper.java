package jjie.pracpart.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jjie.pracpart.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
//    到这里已经完成 BaseMapper提供了大量方法

//    @Select("select * from user_info")
//    List<UserInfo> selectAll();
//
//    @Select("select * from user_info where username=#{name}")
//    List<UserInfo> selectName(String name);
//
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @Insert("insert into user_info (username, password,gender,age) " +
//            "values(#{userInfo.username},#{userInfo.password}," +
//            "#{userInfo.gender},#{userInfo.age})")
//    int insertUser(@Param("userInfo") UserInfo userInfo);
}

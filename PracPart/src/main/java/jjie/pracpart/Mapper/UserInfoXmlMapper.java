package jjie.pracpart.Mapper;

import jjie.pracpart.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserInfoXmlMapper {
    List<UserInfo> selectAll() ;
    int insertUser(@Param("userinfo") UserInfo userInfo);
}

package jjie.mybatis_test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jjie.mybatis_test.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    //实现查询整个列表,其实可以通过穿一个空参实

    @Select("select * from user_info ")
    public List<UserInfo> selectAll();
    //已经提供了很多方法
}

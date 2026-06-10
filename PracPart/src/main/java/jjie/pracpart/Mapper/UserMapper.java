package jjie.pracpart.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jjie.pracpart.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

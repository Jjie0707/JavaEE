package jjie.pracpart.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jjie.pracpart.Mapper.UserMapper;
import jjie.pracpart.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class TransService {
    @Autowired
    private UserMapper userMapper;

    public Integer insertUser(User user){
        return userMapper.insert(user);
    }

    @Transactional(propagation = Propagation.NEVER)
    public Integer insert(User user){
        Integer result =0;
        try {
            result =userMapper.insert(user);
            int a=1/1;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return result;
    }

    @Transactional(propagation =Propagation.REQUIRES_NEW)
    public List<User> query(){
        return userMapper.selectList(new QueryWrapper<>());
    }
}

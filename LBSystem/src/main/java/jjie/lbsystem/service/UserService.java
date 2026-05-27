package jjie.lbsystem.service;


import jjie.lbsystem.entity.User;
import jjie.lbsystem.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Integer serviceSelectByName(String userName,String password){
        //  可以保证userName!=null and password!=null

        User user=userMapper.selectByName(userName);

        if(user==null) return 0;

        log.warn("查询到的user.getPassword: "+user.getPassword());
        log.warn("查询到的user.getUserName: "+user.getUserName());
        log.warn("输入的userName: "+userName);
        log.warn("password: "+password);

        if(!( user.getPassword().equals(password) ) ) return 0;
        return 1;//     返回0 不合法;返回1 合法
    }

}

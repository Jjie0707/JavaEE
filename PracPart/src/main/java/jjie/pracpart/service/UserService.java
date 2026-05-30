package jjie.pracpart.service;

import jjie.pracpart.Mapper.UserInfoMapper;
import jjie.pracpart.Mapper.UserInfoXmlMapper;
import jjie.pracpart.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo serviceSelectById(Integer id) {
        return userInfoMapper.selectById(id);
    }

    public Integer serviceInsertUser(UserInfo userInfo){
        return userInfoMapper.insert(userInfo);
    }

    public Integer serviceDeleteById(Integer id){
        return userInfoMapper.deleteById(id);
    }

//    public int serviceInsertByCondition(UserInfo userInfo){
//        return userInfoXmlMapper.insertByCondition(userInfo);
//    }
}

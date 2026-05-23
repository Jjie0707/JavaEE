package jjie.pracpart.service;

import jjie.pracpart.Mapper.UserInfoXmlMapper;
import jjie.pracpart.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserInfoXmlMapper userInfoXmlMapper;

    public int serviceInsertByCondition(UserInfo userInfo){
        return userInfoXmlMapper.insertByCondition(userInfo);
    }
}

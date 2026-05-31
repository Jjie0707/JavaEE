package jjie.mybatis_test.service;

import jjie.mybatis_test.entity.UserInfo;
import jjie.mybatis_test.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> serviceGetUserList() {
        return userInfoMapper.selectAll();
    }
}

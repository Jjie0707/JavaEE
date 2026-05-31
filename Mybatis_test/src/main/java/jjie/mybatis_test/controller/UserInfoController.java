package jjie.mybatis_test.controller;

import jjie.mybatis_test.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jjie.mybatis_test.service.UserInfoService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("getUserList")
    public List<UserInfo> getUserList(){
        return userInfoService.serviceGetUserList();
    }
}

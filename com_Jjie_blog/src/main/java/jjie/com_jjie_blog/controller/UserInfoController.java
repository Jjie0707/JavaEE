package jjie.com_jjie_blog.controller;

import jakarta.validation.Valid;
import jjie.com_jjie_blog.entity.UserInfoResponse;
import jjie.com_jjie_blog.entity.UserRequest;
import jjie.com_jjie_blog.entity.UserResponse;
import jjie.com_jjie_blog.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/login")
    public UserResponse login(@Valid @RequestBody UserRequest userRequest) {
        return userInfoService.login(userRequest);
    }

    @RequestMapping("/getuserinfo")
    //根据登陆用户获得主页
    public UserInfoResponse getUserInfo(@Valid Integer id){
        return userInfoService.getUserInfoById(id);
    }
    @RequestMapping("/getauthor")
    //获得该blog的作者
    public UserInfoResponse getAuthor(Integer id){
        return userInfoService.getAuthor(id);
    }
}

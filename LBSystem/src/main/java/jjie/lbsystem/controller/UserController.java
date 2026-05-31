package jjie.lbsystem.controller;

import jakarta.servlet.http.HttpSession;
import jjie.lbsystem.entity.User;
import jjie.lbsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public boolean logIn(@RequestBody User user, HttpSession session){
        if(!StringUtils.hasLength(user.getUserName()) ||
            !StringUtils.hasLength(user.getPassword())){
            return false;
        }

        Integer result=userService.serviceSelectByName(user.getUserName(),
                                                        user.getPassword());
        log.warn("查询的状态码是:"+result);
        if(result==0) return false;

        session.setAttribute("login_status",true);
        return true;
    }
}

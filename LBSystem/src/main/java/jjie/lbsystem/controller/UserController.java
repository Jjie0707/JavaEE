package jjie.lbsystem.controller;

import jjie.lbsystem.entity.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @PostMapping("login")
    public boolean logIn(@RequestBody User user){
        if(!StringUtils.hasLength(user.getUserName()) ||
            !StringUtils.hasLength(user.getPassword())){
            return false;
        }
        //TODO 暂时写固定 后续再改
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        return ("Jjie".equals(user.getUserName()) && "0822".equals(user.getPassword()));
    }
}

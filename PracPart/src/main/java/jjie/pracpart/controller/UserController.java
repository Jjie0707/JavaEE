package jjie.pracpart.controller;

import jjie.pracpart.entity.UserInfo;
import jjie.pracpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("insert")
    public String  insertByCondition(){
        UserInfo userinfo=new UserInfo();
        userinfo.setUsername("Jjie");
//        userinfo.setGender(1);
//        userinfo.setPhone("1346989");

        int result=userService.serviceInsertByCondition(userinfo);
        return "影响行数:"+result;
    }

}

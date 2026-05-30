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

    @RequestMapping("selectById")
    public UserInfo selectById(Integer id){
        return userService.serviceSelectById(id);
    }

    @RequestMapping("insertUser")
    public String insertUser(UserInfo userInfo){

        if(userInfo==null) return "参数不合法,用户增加失败!";

        Integer result =userService.serviceInsertUser(userInfo);
        if(result==1) return "添加用户成功!";
        else return "添加用户失败!";
    }

    @RequestMapping("deleteById")
    public String deleteBtId(Integer id){
        if(id==null) return "参数不合法,用户删除失败!";

        Integer result =userService.serviceDeleteById(id);
        if(result==1) return "删除用户成功!";
        else return "删除用户失败!";

    }

//    @GetMapping("insert")
//    public String  insertByCondition(){
//        UserInfo userinfo=new UserInfo();
//        userinfo.setUsername("Jjie");
//        userinfo.setGender(1);
//        userinfo.setPhone("1346989");
//
//        int result=userService.serviceInsertByCondition(userinfo);
//        return "影响行数:"+result;
//    }

}

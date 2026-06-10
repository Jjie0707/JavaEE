package jjie.pracpart.controller;

import jjie.pracpart.entity.User;
import jjie.pracpart.service.TransService;
import jjie.pracpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trans")
public class TransController {
    @Autowired
    private TransService transService;
    @Autowired
    private DataSourceTransactionManager manager;
    @Autowired
    private UserService userService;

    @RequestMapping("/r1")
    public String  insertUser(User user){
        TransactionStatus status= manager.getTransaction(new TransactionDefinition() {});
        transService.insertUser(user);
        try {
            int a=1/1;
        }catch (Exception e){
            manager.rollback(status);
            return "注册失败";
        }
        manager.commit(status);
        return "注册成功";
    }

    @RequestMapping("/r2")
    public String  insertUser0(User user){
        int result= transService.insert(user);
        if(result!=1) return "注册失败";
        return "注册成功";
    }

    @RequestMapping("/r3")
    @Transactional
    public String  insertUser1(User user){

        int result= transService.insert(user);
        if(result!=1) return "注册失败";
        return "注册成功";
    }
    @RequestMapping("r4")
    @Transactional(propagation = Propagation.REQUIRED)
    public void func(User user){
        transService.insert(user);
        transService.query();
    }
}

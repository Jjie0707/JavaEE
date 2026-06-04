package jjie.pracpart.controller;

import jjie.pracpart.TargetMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/test")
@RestController
public class AspectController {



    @RequestMapping("/t1")
    public String t1(){
        log.info("t1执行....");
        return "t1";
    }

    @TargetMethod
    @RequestMapping("/t2")
    public String t2(){
        log.info("t2执行....");
        return "t2";
    }
}

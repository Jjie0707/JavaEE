package jjie._0320;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {
    @GetMapping("I1")
     String hello(String name){
        if(name==null){
            return "未接收到参数";
        }
         return "hello,"+name+"!";
     }

     @GetMapping("I2")
    String hello2(Person person){
        return "Information:"+person;
     }
}

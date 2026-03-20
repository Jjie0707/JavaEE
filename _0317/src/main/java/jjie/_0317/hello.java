package jjie._0317;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")//类注解 也要写在url的资源路径里面
public class hello {
    @GetMapping("hello1")
    String hello(){
        return "Hello,Spring!";
    }
}

package jjie._03_26;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.web.bind.annotation.*;

import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("request")
public class RequestController {
    @RequestMapping("r1")
    public String r1 (@RequestBody Person person){
        return "接收到前端的参数:"+person;
    }

    @RequestMapping("r2/{variable}")
    public String r2(@PathVariable("variable") Integer num){
        return "接收到url中的参数:"+num;
    }

    @RequestMapping("r3")
    public String r3(@RequestParam("list") ArrayList<Integer> list){
        String ret="{";
        for(int num:list){
            ret=ret+(num+" ");
        }
        return ret+"}";
    }

    @RequestMapping("r4")
    public String r4(@RequestParam("list") int[] arr){
        String ret="{";
        for(int num:arr){
            ret=ret+(" "+num);
        }
        return ret+" }";
    }

    @RequestMapping("r5")
    public String r5(HttpServletRequest request){
        if(request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
            }
        }
        return "获取cookies成功";
    }

    @RequestMapping("r6")
    public String r6(HttpSession session){

            session.setAttribute("name", "jjie");
            session.setAttribute("age", 18);

        return "设置session成功";
    }

    @RequestMapping("r7")
    public String r7(HttpSession session) {
        Object name = session.getAttribute("name");
        Object age = session.getAttribute("age");
        return "name" + name + ",age:" + age;
    }

    @RequestMapping("r8")
    public Person r8(){
        return new Person(18,"Jjie","man");
    }

}

package jjie._03_26;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/response")
public class ResponseController {

//    @ResponseBody
    @RequestMapping("/r1")
    public String r1(HttpServletResponse response) {
        return "/index.html";
    }

    @ResponseBody

    @RequestMapping("r2")
    public String r2(){
        return "/index.html";
    }
}

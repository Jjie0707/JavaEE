package jjie.pracpart.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jjie.pracpart.entity.CaptchaInfo;
import jjie.pracpart.entity.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("captcha")
public class CaptchaController {

    @Autowired
    private CaptchaInfo info;

    @GetMapping("getcaptcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        ICaptcha captcha= CaptchaUtil.createLineCaptcha(info.getWidth(),
                info.getHeight(),
                info.getCodecount(),
                info.getLinecount());
        session.setAttribute(info.getSession().getSessionName(),captcha.getCode());
        Constant.LOGGER.info(captcha.getCode());
        session.setAttribute(info.getSession().getData(),System.currentTimeMillis());
//        System.out.println(System.currentTimeMillis());
        try {
            captcha.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            response.getOutputStream().close();
        }
    }
    @GetMapping("check")
    public boolean check(String input,HttpSession session){
        if(!StringUtils.hasLength( (String)session.getAttribute(info.getSession().getSessionName()) ) )
            return false;
        Long startTime=(Long)session.getAttribute(info.getSession().getData());
//        System.out.println(startTime);
//        System.out.println("input:"+input);
        if(!StringUtils.hasLength(input) || startTime==null) return false;
        String code=(String)session.getAttribute(info.getSession().getSessionName());
//        System.out.println("code:"+code);
        if( code.equalsIgnoreCase(input) &&
                 System.currentTimeMillis()-startTime <= Constant.GAP_TIME
        ) return true;
        else return false;
    }
}










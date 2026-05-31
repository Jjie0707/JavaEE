package jjie.lbsystem.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.warn("拦截器执行....");
        HttpSession session= request.getSession();
        Boolean flag=(Boolean)session.getAttribute("login_status");
        if(flag==null || !flag) {
            //说明未登陆
            response.setStatus(401);
            return false;
        }
        log.warn("拦截器放行....");
        return true;
    }
}

package jjie.com_jjie_blog.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jjie.com_jjie_blog.common.error.BlogError;
import jjie.com_jjie_blog.common.jwtutils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        // 请求头中没有 Token，或者格式不正确
        if (authorization == null
                || !authorization.startsWith("Bearer ")) {
            throw BlogError.UnLog();
        }

        // 去掉开头的 "Bearer " 获得完整token
        String token = authorization.substring(7);

        //校验不合格
        if (!JwtUtils.checkToken(token)) {
            throw BlogError.UnLog();
        }

        return true;
    }
}

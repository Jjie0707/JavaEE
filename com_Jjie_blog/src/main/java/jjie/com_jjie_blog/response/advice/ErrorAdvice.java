package jjie.com_jjie_blog.response.advice;

import jjie.com_jjie_blog.common.error.BlogError;
import jjie.com_jjie_blog.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorAdvice {
    @ExceptionHandler(BlogError.class)
    public Result handler(BlogError error){
        if(error.getCode()==-1) {
            log.warn("用户未登陆!");
            return Result.unlogin();
        }else {
            log.warn("发生内部错误!");
            //做统一处理,不让客户端看到具体错误日志
            return Result.fail();
        }
    }
    @ExceptionHandler({Exception.class, MethodArgumentNotValidException.class})
    public Result handler(Exception e){
        log.error("捕获到错误 e:",e);
        return Result.fail();
    }
}

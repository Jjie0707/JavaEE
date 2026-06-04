package jjie.pracpart.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Order(5)
public class Demo1Aspect {

    @Around("execution(* jjie.pracpart.controller.*.*(..))")
    public Object aspectDemo1(ProceedingJoinPoint joinPoint) throws Throwable {
        log.warn("demo1.....");
        Object result=joinPoint.proceed();
        log.warn("demo1.....");
        return result;
    }

}

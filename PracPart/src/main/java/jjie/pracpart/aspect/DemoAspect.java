package jjie.pracpart.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(2)
public class DemoAspect {

    /**
     原始顺序是demo0先执行。用@Order注解调节执行顺序。数字越大优先级低
     */
    @Around("execution(* jjie.pracpart.controller.*.*(..))")
    public Object aspectDemo0(ProceedingJoinPoint joinPoint) throws Throwable {
        log.warn("demo0.....");
        Object result=joinPoint.proceed();
        log.warn("demo0.....");
        return result;
    }

//
//    @Around("execution(* jjie.pracpart.controller.*.*(..))")
//    public Object aspectDemo1(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.warn("demo1.....");
//        Object result=joinPoint.proceed();
//        log.warn("demo1.....");
//        return result;
//    }

    @Around("@annotation(jjie.pracpart.TargetMethod)") //    可以验证该切面仅对于t2接口生效
    public Object aspectTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long end=System.currentTimeMillis();
        log.warn("程序总共运行时长:"+(end-start)+"ms");
        return result;
    }
}

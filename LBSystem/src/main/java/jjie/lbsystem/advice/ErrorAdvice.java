package jjie.lbsystem.advice;

import jjie.lbsystem.entity.Result;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ErrorAdvice{
    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class
    })
    public Result<?> handleParamError(Exception e) {
        return Result.paramError();
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleError(Exception e) {
        return Result.error();
    }
}

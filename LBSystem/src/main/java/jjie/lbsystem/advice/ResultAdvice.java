package jjie.lbsystem.advice;

import jjie.lbsystem.entity.Result;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tools.jackson.databind.ObjectMapper;
import java.util.Map;

@ControllerAdvice
public class ResultAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body,
                                            MethodParameter returnType,
                                            MediaType selectedContentType,
                                            Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                            ServerHttpRequest request,
                                            ServerHttpResponse response) {
        if(body instanceof Result) return body;
        if(body instanceof String)
            return objectMapper.writeValueAsString(Result.success(body));
        return Result.success(body);
    }

    @Override
    public @Nullable Map<String, Object> determineWriteHints(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType) {
        return ResponseBodyAdvice.super.determineWriteHints(body, returnType, selectedContentType, selectedConverterType);
    }
}

package jjie.com_jjie_blog.response;

import jjie.com_jjie_blog.common.contant.StatusCode;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String errorMsg;
    private T data;

    public static <T> Result success(T data){
        Result<T> result =new Result<>();
        result.code= StatusCode.NORMAL.getCode();
        result.data=data;
        return result;
    }

    public static <T> Result fail(){
        Result<T> result =new Result<>();
        result.code= StatusCode.FAIL.getCode();
        result.errorMsg=StatusCode.FAIL.getErrorMsg();
        return result;
    }

    public static <T> Result unlogin(){
        Result<T> result =new Result<>();
        result.code= StatusCode.UNLOGIN.getCode();
        result.errorMsg=StatusCode.UNLOGIN.getErrorMsg();
        return result;
    }

}

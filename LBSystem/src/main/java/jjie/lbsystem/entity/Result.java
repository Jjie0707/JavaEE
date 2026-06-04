package jjie.lbsystem.entity;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String errorMsg;
    private T data;

    public static <T> Result<T> success(T data){
        Result result=new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setErrorMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }
    public static <T> Result<T> error(){
        Result result=new Result();
        result.setCode(ResultEnum.ERROR.getCode());
        result.setErrorMsg(ResultEnum.ERROR.getMsg());
        result.setData(null);
        return result;
    }
    public static <T> Result<T> paramError(){
        Result result=new Result();
        result.setCode(ResultEnum.PARAM_EXCEPTION.getCode());
        result.setErrorMsg(ResultEnum.PARAM_EXCEPTION.getMsg());
        result.setData(null);
        return result;
    }
    public static <T> Result<T> unLogin(){
        Result result=new Result();
        result.setCode(ResultEnum.UN_LOGIN.getCode());
        result.setErrorMsg(ResultEnum.UN_LOGIN.getMsg());
        result.setData(null);
        return result;
    }



}

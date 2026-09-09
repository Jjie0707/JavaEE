package jjie.com_jjie_blog.common.contant;

import lombok.Data;


public enum StatusCode {
    NORMAL(200,null),
    UNLOGIN(-1,"用户未登陆"),
    FAIL(0,"内部错误"),
    ;

    private Integer code;
    private String  errorMsg;

    StatusCode(Integer code,String errorMsg){
        this.code=code;
        this.errorMsg=errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

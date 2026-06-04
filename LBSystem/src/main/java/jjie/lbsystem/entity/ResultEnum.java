package jjie.lbsystem.entity;


public enum ResultEnum {
    UN_LOGIN(-100,"未登陆"),
    ERROR(-200,"内部错误"),
    PARAM_EXCEPTION(0,"参数有误"),
    SUCCESS(200,""),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer i, String msg) {
        this.code=i;this.msg=msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}

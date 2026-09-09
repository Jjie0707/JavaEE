package jjie.com_jjie_blog.common.error;

import jjie.com_jjie_blog.common.contant.StatusCode;
import lombok.Data;

@Data
public class BlogError extends RuntimeException{
    private final Integer code;

    BlogError(Integer code,String errorMsg){
        super(errorMsg);
        this.code=code;
    }

    public static BlogError UnLog(){
        BlogError error = new BlogError(StatusCode.UNLOGIN.getCode()
                ,StatusCode.UNLOGIN.getErrorMsg());
        return error;
    }
    public static BlogError InsideError(){
        BlogError error = new BlogError(StatusCode.FAIL.getCode()
                ,StatusCode.FAIL.getErrorMsg());
        return error;
    }

    public Integer getCode() {
        return code;
    }
}

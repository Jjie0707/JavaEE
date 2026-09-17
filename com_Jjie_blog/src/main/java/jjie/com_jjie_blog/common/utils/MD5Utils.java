package jjie.com_jjie_blog.common.utils;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public  class MD5Utils {
    //密码加密
    //约定好盐值放在前面,加盐密码放在后面
    public static String genCode(String password){
        String salt= UUID.randomUUID().toString().replace("-", "");
        String spassword= DigestUtils.md5DigestAsHex((salt+password).getBytes(StandardCharsets.UTF_8));
        String finalPassword=salt+spassword;
        return finalPassword;
    }
    public static Boolean verify(String inputPassword,String finalPassword){
        if(!StringUtils.hasLength(inputPassword) || finalPassword.length()!=64)
            return false;
        String salt=finalPassword.substring(0,32);
        String spassword=DigestUtils.md5DigestAsHex((salt+inputPassword).getBytes(StandardCharsets.UTF_8));
        String password=salt+spassword;
        if(password.equals(finalPassword)){
            return true;
        }
        return false;
    }
}

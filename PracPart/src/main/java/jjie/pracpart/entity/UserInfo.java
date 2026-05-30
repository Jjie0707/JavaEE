package jjie.pracpart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("user_info")
@lombok.Data
public class UserInfo {
    @TableId(type = IdType.AUTO)//自增id
    private Integer id;
    private String password;
    private String username;
    private Integer age;
    private Integer gender ;
    private String phone ;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;
}

package jjie.pracpart.entity;

import java.util.Date;

@lombok.Data
public class UserInfo {
    private int id;
    private int age;
    private String username;
    private String password;
    private int gender ;
    private String phone ;
    private int deleteFlag;
    private Date createTime;
    private Date updateTime;
}

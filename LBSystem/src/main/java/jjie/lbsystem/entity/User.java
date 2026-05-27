package jjie.lbsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String userName;
    private String password;
    private Integer deleteFlag;
    private Date creatTime;
    private Date updateTime;
}

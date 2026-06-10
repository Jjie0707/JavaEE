package jjie.pracpart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_info")
public class User {
    @TableId(value = "id",type =IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private Date createTime;
    private Date updateTime;
}

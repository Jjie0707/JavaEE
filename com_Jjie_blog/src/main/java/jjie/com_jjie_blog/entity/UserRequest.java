package jjie.com_jjie_blog.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "用户名不可为空!")
    private String userName;
    @NotBlank(message = "密码不可为空!")
    private String password;
}

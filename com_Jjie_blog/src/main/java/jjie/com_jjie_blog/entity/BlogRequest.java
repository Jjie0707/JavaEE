package jjie.com_jjie_blog.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogRequest {
    @NotNull(message = "id不为空")
    Integer userId;
    @NotBlank
    String title;
    @NotBlank
    private String content;
}

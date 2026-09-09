package jjie.com_jjie_blog.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BlogInfoResponse {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private LocalDate updateTime;
}

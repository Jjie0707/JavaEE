package jjie.pracpart.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

@Repository
@Data
@ConfigurationProperties(prefix = "captcha")
public class CaptchaInfo {
    private int width;
    private int height;
    private int codecount;
    private int linecount;
    private ISession session;
}

package jjie._03_30.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MessageInfo {
//    DROP TABLE IF EXISTS message_info;
//    CREATE TABLE `message_info` (
//            `id` INT(11) NOT NULL AUTO_INCREMENT,
//    `from` VARCHAR(127) NOT NULL,
//    `to` VARCHAR(127) NOT NULL,
//    `message` VARCHAR(256) NOT NULL,
//    `delete_flag` TINYINT(4) DEFAULT 0 COMMENT '0-正常, 1-删除',
//            `create_time` DATETIME DEFAULT NOW(),
//    `update_time` DATETIME DEFAULT NOW() ON UPDATE NOW(),
//    PRIMARY KEY (`id`)
//) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

    private Integer id;
    private String from;
    private String to;
    private String message;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;
}

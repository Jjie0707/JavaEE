package jjie.lbsystem.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Book {

    //通常和数据库字段一一对应
    private Integer id;
    private String bookName;
    private String author;
    private Integer count;
    private BigDecimal price;
    private String publish;
    private Integer status;
    private String statusCN;
    private Date createTime;
    private Date deleteTime;

}

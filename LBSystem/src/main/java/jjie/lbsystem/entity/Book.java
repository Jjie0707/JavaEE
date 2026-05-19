package jjie.lbsystem.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {

    //通常和数据库字段一一对应
    public String name;
    public Integer book_id;
    public String author;
    public Integer count;
    public BigDecimal price;
    public String publish;
    public Integer status;
    public String statusCN;


}

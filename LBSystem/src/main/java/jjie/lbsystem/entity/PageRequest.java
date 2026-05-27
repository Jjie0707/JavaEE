package jjie.lbsystem.entity;

import lombok.Data;

@Data
public class PageRequest {
    private Integer currentPage=1;

    private Integer pageSize=10;

    private Integer offset;

    public Integer getOffset(){
        return (currentPage-1)*pageSize;
    }
}

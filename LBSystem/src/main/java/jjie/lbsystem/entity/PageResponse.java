package jjie.lbsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse<T> {
    private Integer count;

    private List<T> list;

    private PageRequest pageRequest;//     把前端传来的数据返回去
}

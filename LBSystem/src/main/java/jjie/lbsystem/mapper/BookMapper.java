package jjie.lbsystem.mapper;

import jjie.lbsystem.entity.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from book_info where status != 0")
    public List<Book> getList();

    @Insert("insert into book_info (book_name,author,count,price,publish,status)" +
            "values (#{bookName},#{author},#{count},#{price},#{publish},#{status})")
    public Integer addBook(Book book);
}

package jjie.lbsystem.mapper;

import jjie.lbsystem.entity.Book;
import jjie.lbsystem.entity.PageRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from book_info where status != 0")
    public List<Book> getList();

    @Insert("insert into book_info (book_name,author,count,price,publish,status)" +
            "values (#{bookName},#{author},#{count},#{price},#{publish},#{status})")
    public Integer addBook(Book book);

    @Select("select count(1) from book_info")
    public Integer getCount();

    @Select("select * from book_info limit #{offset},#{pageSize}")
    public List<Book> getPageList(PageRequest pageRequest);

    @Select("select * from book_info where id=#{id}")
    public Book queryById(Integer id);


    public Integer updateById(Book book);
}

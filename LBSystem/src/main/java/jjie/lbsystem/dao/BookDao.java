package jjie.lbsystem.dao;

import jjie.lbsystem.entity.Book;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

@Repository
public class BookDao {

    //TODO 暂时永mock的数据 后续写数据库
    public ArrayList<Book> mockBook(){
        ArrayList<Book> list=new ArrayList<>();
        Random random=new Random();
        for(int i=0;i<15;i++){
            Book book=new Book();

            book.setBook_id(i);
            book.setAuthor("作者是"+i);
            book.setCount(random.nextInt(100));
            book.setName("书名是"+i);
            book.setPrice(BigDecimal.valueOf(random.nextInt(100)));
            book.setPublish("出版社是"+1);
            book.setStatus(i%2);

            list.add(book);
        }
        return list;
    }
}

package jjie.lbsystem.service;

import jjie.lbsystem.entity.Book;
import jjie.lbsystem.entity.PageRequest;
import jjie.lbsystem.entity.PageResponse;
import jjie.lbsystem.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public List<Book> serviceGetList(){
        List<Book> list=bookMapper.getList() ;
        for(Book book:list){
            book.setStatusCN(book.getStatus()==1 ? "可借阅" : "不可借阅");
        }
        return list;
    }

    public Integer serviceAddBook(Book book){
        return bookMapper.addBook(book);
    }

    public boolean check(Book book){
        if(!(StringUtils.hasLength(book.getBookName())) ||
                book.getBookName().length()>=128) return false;

        if(!(StringUtils.hasLength(book.getAuthor())) ||
                book.getAuthor().length()>=128) return false;

        if(book.getCount()==null || book.getCount()<=0) return false;

        if(!(StringUtils.hasLength(book.getPublish())) ||
                book.getPublish().length()>256) return false;

        if(book.getStatus()==null) return false;

        if(book.getStatus()==null || book.getStatus()==0) return false;
        return true;
    }

    public PageResponse serviceGetPageList(PageRequest pageRequest) {

        List<Book> list=bookMapper.getPageList(pageRequest);
        for(Book book:list){
            if(book.getStatus()==1) book.setStatusCN("可借阅");
            else book.setStatusCN("不可借阅");
        }
        Integer count=bookMapper.getCount();

        PageResponse result=new PageResponse<>(count,list,pageRequest);
        return result;
    }

    public Book serviceQueryById(Integer id){
        return bookMapper.queryById(id);
    }


    public Integer serviceUpdateById(Book book) {
        return bookMapper.updateById(book);
    }
}

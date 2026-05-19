package jjie.lbsystem.service;

import jjie.lbsystem.dao.BookDao;
import jjie.lbsystem.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public ArrayList<Book> bookService(){
        ArrayList<Book> list=bookDao.mockBook() ;
        for(Book book:list){
            book.setStatusCN(book.getStatus()==0 ? "不可借阅" : "可借阅");
        }
        return list;
    }
}

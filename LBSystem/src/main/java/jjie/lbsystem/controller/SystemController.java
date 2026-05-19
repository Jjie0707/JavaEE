package jjie.lbsystem.controller;

import jjie.lbsystem.entity.Book;
import jjie.lbsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("system")
public class SystemController {

    @Autowired
    private BookService bookService;
    
    static ArrayList<Book> list=new ArrayList<>();

    @GetMapping("getList")
    public ArrayList<Book> getList(){
        return bookService.bookService();
    }
}

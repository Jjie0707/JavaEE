package jjie.lbsystem.controller;

import jjie.lbsystem.entity.Book;
import jjie.lbsystem.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("system")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("getList")
    public List<Book> getList(){
        return bookService.serviceGetList();
    }

    @RequestMapping("addBook")
    public boolean addBook(@RequestBody Book book){
        //   参数检验
        if(!bookService.check(book)) return false;

        Integer result= bookService.serviceAddBook(book);
        log.warn("插入结果的合格码: "+result);

        if(result==0) return false;
        return true;
    }
}

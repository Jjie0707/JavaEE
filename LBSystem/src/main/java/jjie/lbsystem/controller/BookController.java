package jjie.lbsystem.controller;

import jjie.lbsystem.entity.Book;
import jjie.lbsystem.entity.PageRequest;
import jjie.lbsystem.entity.PageResponse;
import jjie.lbsystem.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("getPageList")
    public PageResponse getPageList(PageRequest pageRequest){

        if(pageRequest==null) return null;
        if(pageRequest.getCurrentPage()<=0) return null;

        PageResponse result=bookService.serviceGetPageList(pageRequest);
        return result;
    }

    @RequestMapping("queryById")
    public Book queryById(Integer id){
        return bookService.serviceQueryById(id);
    }

    @RequestMapping("updateById")
    public String updateById(Book book){
        if(book==null){
            return "数据更新失败";
        }
        Integer result = bookService.serviceUpdateById(book);
        if(result==1) return "";
        else return "数据更新失败";
    }

    @RequestMapping("deleteById")
    public String deleteById(Integer id){
        if(id==null) return "参数不合法";

        Integer result=bookService.serviceDeleteById(id);
        if(result==1) return "";
        else return "删除失败!";
    }

    @RequestMapping("batchDeleteById")
    public String batchDelete(@RequestParam List<Integer> list){
        if(list==null || list.size()==0) return "参数不合法";

        Integer result = bookService.serviceBatchDelete(list);
        if(result>0) return "";
        else return "批量删除失败";
    }
}

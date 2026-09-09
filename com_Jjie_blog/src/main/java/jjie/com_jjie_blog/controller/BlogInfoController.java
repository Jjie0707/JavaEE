package jjie.com_jjie_blog.controller;


import jjie.com_jjie_blog.entity.BlogInfoResponse;
import jjie.com_jjie_blog.service.BlogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogInfoController {

    @Autowired
    private BlogInfoService blogInfoService;

    @RequestMapping("/getlist")
    public List<BlogInfoResponse> getBlogList(){
        return blogInfoService.getAll();
    }

    @RequestMapping("/getdetail")
    public BlogInfoResponse getBlogDetail(Integer id){
        return blogInfoService.getDetail(id);
    }

    @RequestMapping("/getcount")
    public Long getCount(){
        return blogInfoService.getCount();
    }

}

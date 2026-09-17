package jjie.com_jjie_blog.controller;


import jjie.com_jjie_blog.entity.BlogInfoResponse;
import jjie.com_jjie_blog.entity.BlogRequest;
import jjie.com_jjie_blog.service.BlogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/publish")
    public Boolean publish(@Validated @RequestBody BlogRequest blogrequest){
        return blogInfoService.publish(blogrequest);
    }

    @PostMapping("/edit")
    public Boolean edit(@RequestBody BlogRequest blogRequest,Integer id){
        return blogInfoService.edit(blogRequest,id);
    }
    @PostMapping("/delete")
    public Boolean delete(Integer id){
        return blogInfoService.delete(id);
    }
}

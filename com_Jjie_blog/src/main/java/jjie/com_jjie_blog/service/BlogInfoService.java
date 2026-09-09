package jjie.com_jjie_blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jjie.com_jjie_blog.common.utils.ObjectTrans;
import jjie.com_jjie_blog.entity.BlogInfo;
import jjie.com_jjie_blog.entity.BlogInfoResponse;
import jjie.com_jjie_blog.mapper.BlogInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class BlogInfoService {
    @Autowired
    private BlogInfoMapper blogInfoMapper;

    public List<BlogInfoResponse> getAll(){
        List<BlogInfo> result = blogInfoMapper.selectList(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getDeleteFlag,0));

        return
                result.stream().map(blogInfo-> {
            BlogInfoResponse tem=new BlogInfoResponse();
            ObjectTrans.blogTrans(blogInfo,tem);
            return tem;
        }).collect(Collectors.toList());
    }

    public BlogInfoResponse getDetail(Integer id) {
        BlogInfoResponse ret=new BlogInfoResponse();
        ObjectTrans.blogTrans(
                blogInfoMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId,id)
                                .eq(BlogInfo::getDeleteFlag,0)
                )

        ,ret);
        return ret;
    }

    public Long getCount() {
        Long count=blogInfoMapper.selectCount(new LambdaQueryWrapper<BlogInfo>().eq(BlogInfo::getDeleteFlag,0));

        return count;
    }

}

package jjie.com_jjie_blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jjie.com_jjie_blog.common.error.BlogError;
import jjie.com_jjie_blog.common.jwtutils.JwtUtils;
import jjie.com_jjie_blog.common.utils.ObjectTrans;
import jjie.com_jjie_blog.entity.*;
import jjie.com_jjie_blog.mapper.BlogInfoMapper;
import jjie.com_jjie_blog.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoService {

    @Autowired
    private BlogInfoMapper blogInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserResponse login(UserRequest userRequest) {
        UserInfo select=userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserName,userRequest.getUserName())
                .eq(UserInfo::getDeleteFlag,0)
        );
        String token=null;
        if(select==null) return new UserResponse(null,token);
        else{
            if(!select.getPassword().equals(userRequest.getPassword())) return new UserResponse(null,token);
            else{
                Map<String,Object> claims = new HashMap<>();
                claims.put("userName",select.getUserName());
                claims.put("id",select.getId());
                token= JwtUtils.genToken(claims);
                return new UserResponse(select.getId(),token);
            }
        }
    }

    public UserInfoResponse getUserInfoById(Integer id) throws BlogError{
        UserInfo userInfo=userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getId,id)
                .eq(UserInfo::getDeleteFlag,0)
        );
        //查询不到说明未登录 id不为空也不一定能查到信息
        if(userInfo==null) throw BlogError.UnLog();
        UserInfoResponse response= new UserInfoResponse();
        ObjectTrans.userTrans(userInfo,response);
        return response;
    }

    public UserInfoResponse getAuthor(Integer id) {
        BlogInfo blogInfo=blogInfoMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId,id)
                .eq(BlogInfo::getDeleteFlag,0)
        );
        Integer userId=blogInfo.getUserId();
        UserInfo author=userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getDeleteFlag,0)
                .eq(UserInfo::getId,userId)
        );
        UserInfoResponse response=new UserInfoResponse();
        ObjectTrans.userTrans(author,response);
        return response;
    }
}

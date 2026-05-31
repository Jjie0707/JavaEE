package jjie.mybatis_test.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jjie.mybatis_test.entity.UserInfo;
import jjie.mybatis_test.mapper.UserInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class UserInfoServiceTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void testQueryWrapper(){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.select("id","username","age");
        queryWrapper.eq("username","zhangsan");
        List list = userInfoMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void testQueryWrapper1(){
        QueryWrapper queryWrapper=new QueryWrapper<>();
        queryWrapper.lt("age",20);
        List list = userInfoMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void testQueryWrapper2(){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username","璐璐");
        UserInfo userInfo=new UserInfo();
        userInfo.setAge(6);
        userInfoMapper.update(userInfo,queryWrapper);
    }

    @Test
    public void testUpdateWrapper(){
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.set("username","王璐宝宝");
        updateWrapper.set("phone","18327771099");
        updateWrapper.eq("username","璐璐");
        userInfoMapper.update(updateWrapper);
    }

    @Test
    public void testLambdaQuery(){
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(UserInfo::getId,UserInfo::getUsername,UserInfo::getAge);
        List<UserInfo> userInfos = userInfoMapper.selectList(lambdaQueryWrapper);
        userInfos.forEach(System.out::println);
    }

    @Test
    public void testLambdaQuery2(){
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getUsername,"王璐宝宝");
        List<UserInfo> userInfos = userInfoMapper.selectList(lambdaQueryWrapper);
        userInfos.forEach(System.out::println);
    }


    @Test
    public void testSelectAll(){
        List<UserInfo> userInfos = userInfoMapper.selectList(new QueryWrapper<>());
        userInfos.forEach(System.out::println);
    }
}
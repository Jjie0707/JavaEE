package jjie.pracpart.Mapper;

import jjie.pracpart.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class UserInfoXmlMapperTest {


    @Autowired
    UserInfoXmlMapper userInfoXmlMapper;

    @Test
    void selectAll() {
        userInfoXmlMapper.selectAll().forEach(System.out::println);
    }

    @Test
    void insertUser() {
        UserInfo user=new UserInfo();
        user.setUsername("Jjie");
        user.setPassword("Jjie");
        user.setAge(19);
        user.setPhone("1346989");
        int i = userInfoXmlMapper.insertUser(user);
        log.info("本次操作影响行数:"+i+"新增记录的id:"+user.getId());
    }
}
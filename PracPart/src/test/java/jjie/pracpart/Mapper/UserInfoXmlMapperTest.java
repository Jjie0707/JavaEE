package jjie.pracpart.Mapper;

import jjie.pracpart.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback(false)
class UserInfoXmlMapperTest {

    @Autowired
    private UserInfoXmlMapper userInfoXmlMapper;

    @Test
    void insertByCondition_shouldAffectAtLeastOneRow() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("欧阳");
//        userInfo.setGender(1);
//        userInfo.setPhone("13800138000");

        int affectedRows = userInfoXmlMapper.insertByCondition(userInfo);


    }

    @Test
    void selectByCondition() {
        userInfoXmlMapper.selectByCondition("Jjie",null);
    }

    @Test
    void setByCondition() {
        userInfoXmlMapper.setByCondition("璐璐",null);
    }
}

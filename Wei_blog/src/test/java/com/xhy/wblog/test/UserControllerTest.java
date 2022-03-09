package com.xhy.wblog.test;


import com.xhy.wblog.controller.UserController;
import com.xhy.wblog.domain.User;
import com.xhy.wblog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


// 整合工具类默认写法,配置之后就可以直接注入了。

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserControllerTest {

    @Autowired
    private UserService service;

    @Test
    public void getTest() {
        User user = service.get(2);

    }
    @Test
    public void saveTest() {
        User user = new User();
        user.setEmail("111222");
        user.setPassword("sss");
        user.setGender(1);
        service.save(user);
    }
    @Test
    public void deleteTest() {
        service.remove(3);
    }
    @Test
    public void updateTest() {
        User user = new User();
        user.setEmail("1123");
        user.setPassword("1111@qq.com");
        user.setGender(1);
        user.setId(2);
        service.update(user);
    }
    @Test
    public void listTest() {
        List<User> users = service.list();
        for (User user : users) {
            System.out.println(user);
        }
    }
}

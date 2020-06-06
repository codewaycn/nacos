package com.alibaba.nacos.console.service;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.alibaba.nacos.config.server.auth.UserPersistServiceTmp;
import com.alibaba.nacos.config.server.modules.entity.Users;
import com.alibaba.nacos.console.BaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @author zhangshun
 * @version $Id: UserPersistServiceTest.java,v 0.1 2020年06月06日 15:21 $Exp
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPersistServiceTest extends BaseTest {


    private Users users;

    @Before
    public void before() {
        String data = readClassPath("test-data/users.json");
        users = JacksonUtils.toObj(data, Users.class);
    }


    @Autowired
    private UserPersistServiceTmp userPersistServiceTmp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUser() {
        userPersistServiceTmp.createUser(users.getUsername(), passwordEncoder.encode(users.getPassword()));
    }

    @Test
    public void deleteUserTest() {
        userPersistServiceTmp.deleteUser(users.getUsername());
    }

    @Test
    public void updateUserPassword() {
        userPersistServiceTmp.updateUserPassword(users.getUsername(),
            passwordEncoder.encode(users.getPassword() + new Random().nextInt()));
    }

    @Test
    public void findUserByUsernameTest() {
        Users result = userPersistServiceTmp.findUserByUsername(users.getUsername());
        Assert.assertNotNull(result);
    }

    @Test
    public void getUsersTest() {
        Page<Users> page = userPersistServiceTmp.getUsers(0, 10);
        Assert.assertNotNull(page.getContent());
        Assert.assertTrue(page.getContent().size() > 0);
    }

}

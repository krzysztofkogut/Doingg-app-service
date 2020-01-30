package com.example.doingg.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Test
    public void notNull_getEmail() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertNotNull(user.getEmail());
    }

    @Test
    public void notNull_getUsername() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertNotNull(user.getUsername());
    }

    @Test
    public void notNull_getName() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertNotNull(user.getName());
    }

    @Test
    public void notNull_getPassword() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertNotNull(user.getPassword());
    }

    @Test
    public void compare_getEmail() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertEquals(user.getEmail(),"usertest@gmail.com");
    }

    @Test
    public void compare_getUsername() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertEquals(user.getUsername(),"User1");
    }

    @Test
    public void compare_getName() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertEquals(user.getName(),"User");
    }

    @Test
    public void compare_getPassword() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        Assert.assertEquals(user.getPassword(),"user123");
    }

}
package com.example.doingg.repository;

import com.example.doingg.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        userRepository.save(user);
        userRepository.delete(user);
    }

    @Test
    public void findByEmail() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        userRepository.save(user);
        Assert.assertNotNull(userRepository.findByEmail("usertest@gmail.com"));
        userRepository.delete(user);
    }

    @Test
    public void findByUsernameOrEmail() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        userRepository.save(user);
        Assert.assertNotNull(userRepository.findByUsernameOrEmail("User1", "usertest@gmail.com"));
        userRepository.delete(user);
    }

    @Test
    public void findByIdIn() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        userRepository.save(user);
        Assert.assertNotNull(userRepository.findById(user.getId()));
        userRepository.delete(user);
    }

    @Test
    public void findByUsername() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        userRepository.save(user);
        Assert.assertNotNull(userRepository.findByUsername("User1"));
        userRepository.delete(user);
    }

    @Test
    public void existsByUsername() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        userRepository.save(user);
        Assert.assertNotNull(userRepository.existsByUsername("User1"));
        userRepository.delete(user);
    }

    @Test
    public void existsByEmail() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");

        userRepository.save(user);
        Assert.assertNotNull(userRepository.existsByEmail("usertest@gmail.com"));
        userRepository.delete(user);
    }
}
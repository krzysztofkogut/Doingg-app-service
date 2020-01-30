package com.example.doingg.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.doingg.model.RoleName.ROLE_USER;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {

    @Test
    public void notNull_getName() {
        Role role = new Role();
        RoleName roleName = ROLE_USER;
        role.setName(roleName);

        Assert.assertNotNull(role.getName());
    }

    @Test
    public void compare_getName() {
        Role role = new Role();
        RoleName roleName = ROLE_USER;
        role.setName(roleName);

        Assert.assertEquals(role.getName(),ROLE_USER);
    }
}
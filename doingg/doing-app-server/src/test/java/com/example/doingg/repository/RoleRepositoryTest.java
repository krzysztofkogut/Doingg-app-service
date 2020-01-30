package com.example.doingg.repository;

import com.example.doingg.model.RoleName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.doingg.model.RoleName.ROLE_ADMIN;
import static com.example.doingg.model.RoleName.ROLE_USER;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {


    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findByRoleUser() {
        RoleName roleName = ROLE_USER;
        Assert.assertNotNull(roleRepository.findByName(roleName));
    }

    @Test
    public void findByRoleAdmin() {
        RoleName roleName = ROLE_ADMIN;
        Assert.assertNotNull(roleRepository.findByName(roleName));
    }
}
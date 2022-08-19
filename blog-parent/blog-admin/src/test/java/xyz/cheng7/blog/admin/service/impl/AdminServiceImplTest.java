package xyz.cheng7.blog.admin.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.cheng7.blog.admin.service.AdminService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminServiceImplTest {
    @Autowired
    AdminService adminService;
    @Test
    void loadUserByUsername() {
        UserDetails userDetails = adminService.loadUserByUsername("admin");
        System.out.println(userDetails.getAuthorities());
    }
}
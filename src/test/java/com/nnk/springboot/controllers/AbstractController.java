package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
public class AbstractController {

    @Autowired
    private UserService userService;

    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    protected final User user = createTestUser();
    protected final User admin = createTestAdmin();

    private User createTestUser() {
        return User.builder()
                .username("john@doe.com")
                .fullname("John DOE")
                .password(passwordEncoder.encode("password"))
                .role("ROLE_USER")
                .build();
    }

    private User createTestAdmin() {
        return User.builder()
                .username("admin@admin.com")
                .fullname("Admin ADMIN")
                .password(passwordEncoder.encode("password"))
                .role("ROLE_ADMIN")
                .build();
    }

    @BeforeEach
    void setUp() {
        userService.save(user);
        userService.save(admin);
    }

    @AfterEach
    void tearDown() {
        userService.delete(user);
        userService.delete(admin);
    }
}

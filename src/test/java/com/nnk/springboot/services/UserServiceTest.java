package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.jpa.repository.JpaRepository;

class UserServiceTest extends AbstractEntityServiceTest<User> {

    @BeforeEach
    void setUp() {
        this.setService(createService(repository));
    }

    @Override
    protected AbstractEntityService<User> createService(JpaRepository<User, Integer> repository) {
        return new UserService(repository);
    }

    @Override
    protected User createEntity() {
        return User.builder()
                .username("username")
                .password("password")
                .role("ROLE_USER")
                .build();
    }
}
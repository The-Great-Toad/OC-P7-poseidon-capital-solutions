package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceBisTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private JpaRepository<User, Integer> repository;

    @Test
    void getUserByUsernameTest() {
        User user = User.builder()
                .username("username")
                .build();

        when(repository.findOne(any())).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("username");

        assertThat(result)
                .isNotNull()
                .isEqualTo(user);
        verify(repository, times(1)).findOne(any());
    }

    @Test
    void getUserByUsernameTest_OptionalEmpty() {
        when(repository.findOne(any())).thenReturn(Optional.empty());

        User result = userService.getUserByUsername("username");

        assertNull(result);
        verify(repository, times(1)).findOne(any());
    }

    @Test
    void logoutUserTest() {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "username",
                        "password"
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(SecurityContextHolder.getContext().getAuthentication())
                .isNotNull()
                .isEqualTo(authentication);

        userService.logoutUser();

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
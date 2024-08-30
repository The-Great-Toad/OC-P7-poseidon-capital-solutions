package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractEntityService<User> {

    public UserService(JpaRepository<User, Integer> repository) {
        super("UserService", repository);
    }

    public User getUserByUsername(String username) {
        User userExample = User.builder()
                .username(username)
                .build();

        Example<User> example = Example.of(userExample, ExampleMatcher.matching());

        Optional<User> user = repository.findOne(example);

        if (user.isPresent()) {
            LOGGER.info("{} - User found with email: {}", LOG_ID, user.get().getUsername());
            return user.get();
        } else {
            LOGGER.info("{} - User with email {} not found", LOG_ID, username);
            return null;
        }
    }

    public void logoutUser() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}

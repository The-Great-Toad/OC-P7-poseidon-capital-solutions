package com.nnk.springboot.services.user;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String LOG_ID = "[UserService]";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            LOGGER.info("{} - Retrieved user: {}", LOG_ID, optionalUser.get());
            return optionalUser.get();
        } else {
            LOGGER.error("{} - User with ID: {}, not found", LOG_ID, id);
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
    }

    @Override
    public User save(User user) {
        LOGGER.info("{} - Creating user: {}", LOG_ID, user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        LOGGER.info("{} - Updating user: {}", LOG_ID, user);
        return userRepository.save(user);
    }

    @Override
    public boolean delete(User user) {
        userRepository.delete(user);
        LOGGER.info("{} - Deleted user: {}", LOG_ID, user);
        return true;
    }
}

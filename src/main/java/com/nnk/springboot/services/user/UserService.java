package com.nnk.springboot.services.user;

import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getUsers();

    User getUser(Integer id);

    User save(User user);

    User update(User user);

    boolean delete(User user);

    User getUserByUsername(String username);

    void logoutUser();
}

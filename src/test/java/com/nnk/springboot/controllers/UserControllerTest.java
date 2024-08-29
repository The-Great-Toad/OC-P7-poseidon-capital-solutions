package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.AbstractEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AbstractEntityService<User> userService;

    @Test
    void homeTest() throws Exception {
        mockMvc.perform(get("/user/list")
                        .with(user(admin)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("john@doe.com")))
                .andExpect(content().string(containsString("Roles:")))
                .andExpect(content().string(containsString("ROLE_USER")));
    }

    @Test
    void showAddFormTest() throws Exception {
        mockMvc.perform(get("/user/add")
                        .with(user(admin)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/add"));
    }

    @Test
    void validateTest() throws Exception {
        String username = "username";
        String password = "password";
        String fullname = "fullname";
        String role = "ROLE_USER";

        mockMvc.perform(post("/user/validate")
                        .with(user(admin))
                        .with(csrf())
                        .queryParam("username", username)
                        .queryParam("password", password)
                        .queryParam("fullname", fullname)
                        .queryParam("role", role))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute(
                        "success",
                        Messages.SUCCESS_ADDED.formatted("User : " + username + ",")))
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    void showUpdateFormTest() throws Exception {
        User user = User.builder()
                .username("username")
                .password("password")
                .fullname("fullname")
                .role("USER")
                .build();

        user = userService.save(user);

        mockMvc.perform(get("/user/update/" + user.getId().toString())
                        .with(user(admin)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/update"));
    }

    @Test
    void updateUserTest() throws Exception {
        User userTest = User.builder()
                .username("username")
                .password("password")
                .fullname("fullname")
                .role("USER")
                .build();

        userTest = userService.save(userTest);

        String username = "username modified";
        String password = "password modified";
        String fullname = "fullname modified";
        String role = "ROLE_ADMIN";

        mockMvc.perform(post("/user/update/" + userTest.getId().toString())
                        .with(user(admin))
                        .with(csrf())
                        .queryParam("username", username)
                        .queryParam("password", password)
                        .queryParam("fullname", fullname)
                        .queryParam("role", role))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute(
                        "success",
                        Messages.SUCCESS_UPDATED.formatted("User : " + username + ",")))
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    void deleteUserTest() throws Exception {
        User user = User.builder()
                .username("username")
                .password("password")
                .fullname("fullname")
                .role("USER")
                .build();

        user = userService.save(user);

        mockMvc.perform(get("/user/delete/" + user.getId().toString())
                        .with(user(admin))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute(
                        "success",
                        Messages.SUCCESS_DELETED.formatted("User : " + user.getUsername() + ",")))
                .andExpect(view().name("redirect:/user/list"));
    }
}
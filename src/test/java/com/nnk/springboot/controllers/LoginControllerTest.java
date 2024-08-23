package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUserArticles() throws Exception {
        mockMvc.perform(get("/app/secure/article-details")
                        .with(user(admin)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("admin@admin.com")))
                .andExpect(content().string(containsString("ROLE_ADMIN")));
    }

    @Test
    void getAllUserArticles_withUserRole_forbidden() throws Exception {
        mockMvc.perform(get("/app/secure/article-details")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/app/logout")
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
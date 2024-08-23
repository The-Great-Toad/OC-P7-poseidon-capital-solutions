package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home_User() throws Exception {
        mockMvc.perform(get("/")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(0))
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("john@doe.com")))
                .andExpect(content().string(containsString("ROLE_USER")));
    }

    @Test
    void home_Admin() throws Exception {
        mockMvc.perform(get("/")
                        .with(user(admin)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(0))
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("admin@admin.com")))
                .andExpect(content().string(containsString("ROLE_ADMIN")))
                .andExpect(content().string(containsString("User management")));
    }

    @Test
    void adminHome() throws Exception {
        mockMvc.perform(get("/admin/home")
                        .with(user(admin)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().size(0))
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    void adminHome_user_forbidden() throws Exception {
        mockMvc.perform(get("/admin/home")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
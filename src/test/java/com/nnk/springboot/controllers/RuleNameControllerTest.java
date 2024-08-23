package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
class RuleNameControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    void homeTest() throws Exception {
        mockMvc.perform(get("/ruleName/list")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("ruleNames"))
                .andExpect(view().name("ruleName/list"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("john@doe.com")))
                .andExpect(content().string(containsString("Roles:")))
                .andExpect(content().string(containsString("ROLE_USER")));
    }

    @Test
    void showAddFormTest() throws Exception {
        mockMvc.perform(get("/ruleName/add")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void validateTest() throws Exception {
        String name = "name";

        mockMvc.perform(post("/ruleName/validate")
                        .with(user(user))
                        .with(csrf())
                        .queryParam("name", name))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", Messages.SUCCESS_ADDED.formatted("rule")))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    void showUpdateFormTest() throws Exception {
        RuleName ruleName = RuleName.builder()
                .name("name")
                .build();

        ruleName = ruleNameRepository.save(ruleName);

        mockMvc.perform(get("/ruleName/update/" + ruleName.getId().toString())
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(model().attribute("ruleName", ruleName))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    void updateRuleNameTest() throws Exception {
        RuleName ruleName = RuleName.builder()
                .name("name")
                .build();

        ruleName = ruleNameRepository.save(ruleName);

        String name = "name modified";

        mockMvc.perform(post("/ruleName/update/" + ruleName.getId().toString())
                        .with(user(user))
                        .with(csrf())
                        .queryParam("name", name))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_UPDATED.formatted("rule")))
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    void deleteRuleNameTest() throws Exception {
        RuleName ruleName = RuleName.builder()
                .name("name")
                .build();

        ruleName = ruleNameRepository.save(ruleName);

        mockMvc.perform(delete("/ruleName/delete/" + ruleName.getId().toString())
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_DELETED.formatted("rule")))
                .andExpect(view().name("redirect:/ruleName/list"));
    }
}
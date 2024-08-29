package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    void homeTest() throws Exception {
        mockMvc.perform(get("/trade/list")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("trades"))
                .andExpect(view().name("trade/list"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("john@doe.com")))
                .andExpect(content().string(containsString("Roles:")))
                .andExpect(content().string(containsString("ROLE_USER")));
    }

    @Test
    void showAddFormTest() throws Exception {
        mockMvc.perform(get("/trade/add")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("trade"))
                .andExpect(view().name("trade/add"));
    }

    @Test
    void validateTest() throws Exception {
        String account = "account";

        mockMvc.perform(post("/trade/validate")
                        .with(user(user))
                        .with(csrf())
                        .queryParam("account", account))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", Messages.SUCCESS_ADDED.formatted("trade")))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    void showUpdateFormTest() throws Exception {
        Trade trade = Trade.builder()
                .account("account")
                .build();

        trade = tradeRepository.save(trade);

        mockMvc.perform(get("/trade/update/" + trade.getId().toString())
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("trade"))
                .andExpect(model().attribute("trade", trade))
                .andExpect(view().name("trade/update"));
    }

    @Test
    void updateRuleNameTest() throws Exception {
        Trade trade = Trade.builder()
                .account("account")
                .build();

        trade = tradeRepository.save(trade);

        String account = "account modified";

        mockMvc.perform(post("/trade/update/" + trade.getId().toString())
                        .with(user(user))
                        .with(csrf())
                        .queryParam("account", account))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_UPDATED.formatted("trade")))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    void deleteRuleNameTest() throws Exception {
        Trade trade = Trade.builder()
                .account("account")
                .build();

        trade = tradeRepository.save(trade);

        mockMvc.perform(get("/trade/delete/" + trade.getId().toString())
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_DELETED.formatted("trade")))
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    void deleteRuleNameTest_NoSuchElement() throws Exception {
        mockMvc.perform(get("/trade/delete/" + "99")
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("failure", Messages.FAILURE_DELETE.formatted("trade")))
                .andExpect(view().name("redirect:/trade/list"));
    }
}
package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class BidListControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void homeTest() throws Exception {
        mockMvc.perform(get("/bidList/list")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(view().name("bidList/list"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("john@doe.com")))
                .andExpect(content().string(containsString("Roles:")))
                .andExpect(content().string(containsString("USER")));
    }

    @Test
    void addBidFormTest() throws Exception {
        mockMvc.perform(get("/bidList/add")
                    .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void validateTest() throws Exception {
        String account = "account";
        String type = "type";
        double quantity = 23d;

        mockMvc.perform(post("/bidList/validate")
                        .with(user(user))
                        .with(csrf())
                        .queryParam("account", account)
                        .queryParam("type", type)
                        .queryParam("bidQuantity", String.valueOf(quantity)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", Messages.SUCCESS_ADDED.formatted("bid")))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    void showUpdateFormTest() throws Exception {
        BidList bidList = BidList.builder()
                .account("account")
                .type("type")
                .bidQuantity(23.0)
                .build();

        bidList = bidListRepository.save(bidList);

        mockMvc.perform(get("/bidList/update/" + bidList.getId().toString())
                    .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(model().attribute("bidList", bidList))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    void updateBidTest() throws Exception {
        BidList bidList = BidList.builder()
                .account("account")
                .type("type")
                .bidQuantity(23.0)
                .build();

        bidList = bidListRepository.save(bidList);

        String account = "account modified";
        String type = "type modified";
        double quantity = 24d;

        mockMvc.perform(post("/bidList/update/" + bidList.getId().toString())
                        .with(user(user))
                        .with(csrf())
                        .queryParam("account", account)
                        .queryParam("type", type)
                        .queryParam("bidQuantity", String.valueOf(quantity)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_UPDATED.formatted("bid")))
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    void deleteBidTest() throws Exception {
        BidList bidList = BidList.builder()
                .account("account")
                .type("type")
                .bidQuantity(23.0)
                .build();

        bidList = bidListRepository.save(bidList);

        mockMvc.perform(delete("/bidList/delete/" + bidList.getId().toString())
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_DELETED.formatted("bid")))
                .andExpect(view().name("redirect:/bidList/list"));
    }
}
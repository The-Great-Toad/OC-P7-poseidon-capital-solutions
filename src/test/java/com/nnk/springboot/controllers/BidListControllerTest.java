package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.constantes.Messages;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"USER", "ADMIN"})
//@WithUserDetails // For later use, when UserDetailsService is implemented
//https://docs.spring.io/spring-security/site/docs/5.2.0.RELEASE/reference/html/test.html
class BidListControllerTest {

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
        mockMvc.perform(get("/bidList/list"))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(view().name("bidList/list"));
    }

    @Test
    void addBidFormTest() throws Exception {
        mockMvc.perform(get("/bidList/add"))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("bidList"))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void validateTest() throws Exception {
        BidList newBidList = BidList.builder()
                .account("account")
                .type("type")
                .bidQuantity(23.0)
                .build();

        // TODO: ask Mentor why can't we do post request with mocked user ?!!

        mockMvc.perform(post("/bidList/validate")
                        .queryParam("bid", objectMapper.writeValueAsString(newBidList) ))
                .andDo(print())
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

        mockMvc.perform(get("/bidList/update/" + bidList.getId().toString()))
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

        // TODO: ask Mentor why can't we do post request with mocked user ?!!

        mockMvc.perform(post("/bidList/update/" + bidList.getId().toString())
                        .queryParam("bidList", objectMapper.writeValueAsString(bidList)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().size(0))
//                .andExpect(model().attributeExists("bidList"))
//                .andExpect(model().attribute("bidList", bidList))
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

        // TODO: ask Mentor why can't we do delete request with mocked user ?!!

        mockMvc.perform(delete("/bidList/delete/" + bidList.getId().toString()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_DELETED.formatted("bid")))
                .andExpect(view().name("redirect:/bidList/list"));
    }
}
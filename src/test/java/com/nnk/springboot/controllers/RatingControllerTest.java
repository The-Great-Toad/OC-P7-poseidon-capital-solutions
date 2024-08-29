package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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
class RatingControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    void homeTest() throws Exception {
        mockMvc.perform(get("/rating/list")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(view().name("rating/list"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("john@doe.com")))
                .andExpect(content().string(containsString("Roles:")))
                .andExpect(content().string(containsString("ROLE_USER")));
    }

    @Test
    void showAddFormTest() throws Exception {
        mockMvc.perform(get("/rating/add")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/add"));
    }

    @Test
    void validateTest() throws Exception {
        Integer moodysRating = 1;

        mockMvc.perform(post("/rating/validate")
                        .with(user(user))
                        .with(csrf())
                        .queryParam("moodysRating", String.valueOf(moodysRating)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", Messages.SUCCESS_ADDED.formatted("rating")))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    void showUpdateFormTest() throws Exception {
        Rating rating = Rating.builder()
                .moodysRating(1)
                .build();

        rating = ratingRepository.save(rating);

        mockMvc.perform(get("/rating/update/" + rating.getId().toString())
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attribute("rating", rating))
                .andExpect(view().name("rating/update"));
    }

    @Test
    void updateRatingTest() throws Exception {
        Rating rating = Rating.builder()
                .moodysRating(1)
                .build();

        rating = ratingRepository.save(rating);

        Integer moodysRating = 1;

        mockMvc.perform(post("/rating/update/" + rating.getId().toString())
                        .with(user(user))
                        .with(csrf())
                        .queryParam("moodysRating", String.valueOf(moodysRating)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_UPDATED.formatted("rating")))
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    void deleteRatingTest() throws Exception {
        Rating rating = Rating.builder()
                .moodysRating(1)
                .build();

        rating = ratingRepository.save(rating);

        mockMvc.perform(get("/rating/delete/" + rating.getId().toString())
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_DELETED.formatted("rating")))
                .andExpect(view().name("redirect:/rating/list"));
    }
}
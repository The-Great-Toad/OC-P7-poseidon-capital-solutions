package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurveControllerTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    void homeTest() throws Exception {
        mockMvc.perform(get("/curvePoint/list")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(view().name("curvePoint/list"))
                .andExpect(content().string(containsString("Logged in user:")))
                .andExpect(content().string(containsString("john@doe.com")))
                .andExpect(content().string(containsString("Roles:")))
                .andExpect(content().string(containsString("ROLE_USER")));
    }

    @Test
    void showAddFormTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add")
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void validateTest() throws Exception {
        Integer curveId = 1;
        Double term = 2d;
        Double value = 23d;

        mockMvc.perform(post("/curvePoint/validate")
                        .with(user(user))
                        .with(csrf())
                        .queryParam("curveId", String.valueOf(curveId))
                        .queryParam("term", String.valueOf(term))
                        .queryParam("value", String.valueOf(value)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attributeExists("success"))
                .andExpect(flash().attribute("success", Messages.SUCCESS_ADDED.formatted("curve point")))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    void validateTest_BindingResultErrors() throws Exception {
        Integer curveId = null;
        Double term = 2d;
        Double value = 23d;

        mockMvc.perform(post("/curvePoint/validate")
                        .with(user(user))
                        .with(csrf())
                        .queryParam("curveId", String.valueOf(curveId))
                        .queryParam("term", String.valueOf(term))
                        .queryParam("value", String.valueOf(value)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void showUpdateFormTest() throws Exception {
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(2d)
                .value(3d)
                .build();

        curvePoint = curvePointRepository.save(curvePoint);

        mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId().toString())
                        .with(user(user)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", curvePoint))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void updateCurveTest() throws Exception {
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(2d)
                .value(3d)
                .build();

        curvePoint = curvePointRepository.save(curvePoint);

        Integer curveId = 1;
        Double term = 2d;
        Double value = 23d;

        mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId().toString())
                        .with(user(user))
                        .with(csrf())
                        .queryParam("curveId", String.valueOf(curveId))
                        .queryParam("term", String.valueOf(term))
                        .queryParam("value", String.valueOf(value)))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_UPDATED.formatted("curve point")))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    void updateCurveTest_BindingResultErrors() throws Exception {
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(2d)
                .value(3d)
                .build();

        curvePoint = curvePointRepository.save(curvePoint);

        Integer curveId = null;
        Double term = 2d;
        Double value = 23d;

        mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId().toString())
                        .with(user(user))
                        .with(csrf())
                        .queryParam("curveId", String.valueOf(curveId))
                        .queryParam("term", String.valueOf(term))
                        .queryParam("value", String.valueOf(value)))
//                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void deleteCurveTest() throws Exception {
        CurvePoint curvePoint = CurvePoint.builder()
                .curveId(1)
                .term(2d)
                .value(3d)
                .build();

        curvePoint = curvePointRepository.save(curvePoint);

        mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId().toString())
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("success", Messages.SUCCESS_DELETED.formatted("curve point")))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    void deleteCurveTest_NoSuchElement() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/" + "99")
                        .with(user(user))
                        .with(csrf()))
//                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(model().size(0))
                .andExpect(flash().attribute("failure", Messages.FAILURE_DELETE.formatted("curve point")))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }
}
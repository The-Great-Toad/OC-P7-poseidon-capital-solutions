package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.AbstractEntityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
    private static final String LOG_ID = "[RatingController]";

    private final AbstractEntityService<Rating> ratingService;

    public RatingController(AbstractEntityService<Rating> ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.getEntities());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(
            Model model,
            @Valid Rating rating,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "rating/add";
        }

        ratingService.save(rating);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_ADDED.formatted("rating"));

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating", ratingService.getEntity(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(
            @PathVariable("id") Integer id,
            Model model,
            @Valid Rating rating,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "rating/update";
        }

        ratingService.update(rating);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_UPDATED.formatted("rating"));

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        Rating rating = ratingService.getEntity(id);

        if (rating != null) {
            ratingService.delete(rating);
            LOGGER.info("Deleted rating: {}", rating);
            redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_DELETED.formatted("rating"));
        } else {
            LOGGER.info("No rating found with id: {}", id);
            redirectAttributes.addFlashAttribute("failure", Messages.FAILURE_DELETE.formatted("rating"));
        }

        return "redirect:/rating/list";
    }
}

package com.nnk.springboot.controllers;

import com.nnk.springboot.constantes.Messages;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.rating.RatingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
    private static final String LOG_ID = "[RatingController]";

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", ratingService.getRatings());
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
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_RATING_ADDED);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("rating", ratingService.getRating(id));
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
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_RATING_UPDATED);

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        Rating rating = ratingService.getRating(id);

        if (rating != null) {
            ratingService.delete(rating);
            LOGGER.info("Deleted rating: {}", rating);
            redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_RATING_DELETED);
        } else {
            LOGGER.info("No rating found with id: {}", id);
            redirectAttributes.addFlashAttribute("failure", Messages.FAILURE_RATING_DELETE);
        }

        return "redirect:/rating/list";
    }
}

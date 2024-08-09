package com.nnk.springboot.controllers;

import com.nnk.springboot.constantes.Messages;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.curvePoint.CurvePointService;
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
public class CurveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurveController.class);
    private static final String LOG_ID = "[CurveController]";

    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.getCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(
            Model model,
            @Valid CurvePoint curvePoint,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "curvePoint/add";
        }

        curvePointService.save(curvePoint);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_ADDED.formatted("curve point"));

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("curvePoint", curvePointService.getCurvePoint(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(
            @PathVariable("id") Integer id,
            Model model,
            @Valid CurvePoint curvePoint,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "curvePoint/update";
        }

        curvePointService.update(curvePoint);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_UPDATED.formatted("curve point"));

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        CurvePoint curvePoint = curvePointService.getCurvePoint(id);

        if (curvePoint != null) {
            curvePointService.delete(curvePoint);
            LOGGER.info("Deleted curvePoint: {}", curvePoint);
            redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_DELETED.formatted("curve point"));
        } else {
            LOGGER.info("No curvePoint found with id: {}", id);
            redirectAttributes.addFlashAttribute("failure", Messages.FAILURE_DELETE.formatted("curve point"));
        }
        return "redirect:/curvePoint/list";
    }
}

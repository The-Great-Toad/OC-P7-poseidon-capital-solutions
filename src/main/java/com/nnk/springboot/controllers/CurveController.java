package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.CurvePoint;
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
public class CurveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurveController.class);
    private static final String LOG_ID = "[CurveController]";

    private final AbstractEntityService<CurvePoint> curvePointService;

    public CurveController(AbstractEntityService<CurvePoint> curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.getEntities());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String showAddForm(CurvePoint curvePoint) {
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
        model.addAttribute("curvePoint", curvePointService.getEntity(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(
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
    public String deleteCurve(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        CurvePoint curvePoint = curvePointService.getEntity(id);

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

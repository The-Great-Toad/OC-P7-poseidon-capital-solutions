package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.EntityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
public class BidListController implements ControllerInterface<BidList> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);
    private static final String LOG_ID = "[BidListController]";

    private final EntityService<BidList> bidListService;

    public BidListController(EntityService<BidList> bidListService) {
        this.bidListService = bidListService;
    }

    @Override
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.getEntities());
        return "bidList/list";
    }

    @Override
    @GetMapping("/bidList/add")
    public String addEntityForm(BidList bid) {
        return "bidList/add";
    }

    @Override
    @PostMapping("/bidList/validate")
    public String validate(
            Model model,
            @Valid BidList bid,
            BindingResult result,
            RedirectAttributes redirectAttributes) throws SQLException
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "bidList/add";
        }

        bidListService.save(bid);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_ADDED.formatted("bid"));

        return "redirect:/bidList/list";
    }

    @Override
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", bidListService.getEntity(id));
        return "bidList/update";
    }

    @Override
    @PostMapping("/bidList/update/{id}")
    public String updateEntity(
            @PathVariable("id") Integer id,
            @Valid BidList bidList,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "bidList/update";
        }

        bidListService.update(bidList);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_UPDATED.formatted("bid"));

        return "redirect:/bidList/list";
    }

    @Override
    @GetMapping("/bidList/delete/{id}")
    public String deleteEntity(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        BidList bid = bidListService.getEntity(id);

        if (bid != null) {
            bidListService.delete(bid);
            LOGGER.info("Deleted BidList: {}", bid);
            redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_DELETED.formatted("bid"));
        } else {
            LOGGER.info("No BidList found with id: {}", id);
            redirectAttributes.addFlashAttribute("failure", Messages.FAILURE_DELETE.formatted("bid"));
        }

        return "redirect:/bidList/list";
    }
}

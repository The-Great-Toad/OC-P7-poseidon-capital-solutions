package com.nnk.springboot.controllers;

import com.nnk.springboot.constantes.Messages;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.bidList.BidListService;
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

import java.sql.SQLException;

@Controller
public class BidListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);
    private static final String LOG_ID = "[BidListController]";

    private final BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListService.getBids());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) throws SQLException {
//        throw new SQLException("Connection time out"); // for test
        return "bidList/add";
    }

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

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", bidListService.getBid(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(
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

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        BidList bid = bidListService.getBid(id);

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

package com.nnk.springboot.controllers;

import com.nnk.springboot.constantes.Messages;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
import java.util.List;

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
        // TODO: call service find all bids to show to the view
        List<BidList> bids = bidListService.getBids();
        model.addAttribute("bidLists", bids);

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) throws SQLException {
//        throw new SQLException("Connection time out"); // for test
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(Model model, @Valid BidList bid, BindingResult result) throws SQLException {
        // TODO: check data valid and save to db, after saving return bid list
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "bidList/add";
        }

        BidList savedBid = bidListService.save(bid);
        model.addAttribute("bidList", savedBid);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bid = bidListService.getBid(id);
        model.addAttribute("bidList", bid);

        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "bidList/update";
        }

        BidList updatedBid = bidListService.update(bidList);
        model.addAttribute("bidList", updatedBid);

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        BidList bid = bidListService.getBid(id);

        if (bid != null) {
            bidListService.delete(bid);
            LOGGER.info("Deleted BidList: {}", bid);
        } else {
            LOGGER.info("No BidList found with id: {}", id);
        }

        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_BID_DELETED);
        return "redirect:/bidList/list";
    }
}

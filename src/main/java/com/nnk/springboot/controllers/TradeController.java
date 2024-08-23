package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.trade.TradeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TradeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);
    private static final String LOG_ID = "[TradeController]";

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trades", tradeService.getTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(
            Model model,
            @Valid Trade trade,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "trade/add";
        }

        tradeService.save(trade);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_ADDED.formatted("trade"));

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("trade", tradeService.getTrade(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(
            @PathVariable("id") Integer id,
            Model model,
            @Valid Trade trade,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "trade/update";
        }

        tradeService.update(trade);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_UPDATED.formatted("trade"));

        return "redirect:/trade/list";
    }

    @DeleteMapping("/trade/delete/{id}")
    public String deleteTrade(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        Trade trade = tradeService.getTrade(id);

        if (trade != null) {
            tradeService.delete(trade);
            LOGGER.info("Deleted trade: {}", trade);
            redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_DELETED.formatted("trade"));
        } else {
            LOGGER.info("No trade found with id: {}", id);
            redirectAttributes.addFlashAttribute("failure", Messages.FAILURE_DELETE.formatted("trade"));
        }

        return "redirect:/trade/list";
    }
}

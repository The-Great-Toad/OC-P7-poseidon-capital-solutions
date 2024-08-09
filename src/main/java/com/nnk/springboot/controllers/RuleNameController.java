package com.nnk.springboot.controllers;

import com.nnk.springboot.constantes.Messages;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.ruleName.RuleNameService;
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
public class RuleNameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleNameController.class);
    private static final String LOG_ID = "[RuleNameController]";

    private final RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", ruleNameService.getRuleNames());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(
            Model model,
            @Valid RuleName ruleName,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "ruleName/add";
        }

        ruleNameService.save(ruleName);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_RULENAME_ADDED);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ruleName", ruleNameService.getRuleName(id));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(
            @PathVariable("id") Integer id,
            Model model,
            @Valid RuleName ruleName,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> LOGGER.error("{} - {}", LOG_ID, error));
            return "ruleName/update";
        }

        ruleNameService.update(ruleName);
        redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_RULENAME_UPDATED);

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(
            @PathVariable("id") Integer id,
            Model model,
            RedirectAttributes redirectAttributes)
    {
        RuleName ruleName = ruleNameService.getRuleName(id);

        if (ruleName != null) {
            ruleNameService.delete(ruleName);
            LOGGER.info("Deleted ruleName: {}", ruleName);
            redirectAttributes.addFlashAttribute("success", Messages.SUCCESS_RULENAME_DELETED);
        } else {
            LOGGER.info("No ruleName found with id: {}", id);
            redirectAttributes.addFlashAttribute("failure", Messages.FAILURE_RULENAME_DELETE);
        }

        return "redirect:/ruleName/list";
    }
}

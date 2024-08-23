package com.nnk.springboot.controllers;

import com.nnk.springboot.constantes.Messages;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@PreAuthorize("hasRole('ADMIN')")
@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);
    private static final String LOG_ID = "[TradeController]";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("validate")
    public String validate(
            Model model,
            @Valid User user,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            return "user/add";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userService.save(user);

        redirectAttributes.addFlashAttribute(
                "Success",
                Messages.SUCCESS_ADDED
                        .formatted("User :" + user.getUsername() + ","));

        return "redirect:/user/list";
    }

    @GetMapping("update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model)
    {
        User user = userService.getUser(id);

        user.setPassword("");
        model.addAttribute("user", user);

        return "user/update";
    }

    @PostMapping("update/{id}")
    public String updateUser(
            @PathVariable("id") Integer id,
            Model model,
            @Valid User user,
            BindingResult result,
            RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);

        userService.save(user);

        redirectAttributes.addFlashAttribute(
                "Success",
                Messages.SUCCESS_UPDATED
                        .formatted("User :" + user.getUsername() + ","));

        return "redirect:/user/list";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model)
    {
        User user = userService.getUser(id);

        userService.delete(user);

        return "redirect:/user/list";
    }
}

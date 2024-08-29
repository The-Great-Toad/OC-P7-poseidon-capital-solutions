package com.nnk.springboot.controllers;

import com.nnk.springboot.constants.Messages;
import com.nnk.springboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("app")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private static final String LOG_ID = "[LoginController]";

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.getEntities());
        mav.setViewName("user/list");
        return mav;
    }

    @PostMapping("logout")
    public String logout(RedirectAttributes redirectAttributes, Authentication authentication)
    {
        LOGGER.info("{} - login out user: {}", LOG_ID, authentication.getName());
        userService.logoutUser();
        redirectAttributes.addFlashAttribute("logoutSuccess", Messages.SUCCESS_LOGOUT);
        return "redirect:/login";
    }
}

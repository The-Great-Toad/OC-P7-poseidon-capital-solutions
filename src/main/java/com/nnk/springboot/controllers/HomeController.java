package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	private static final String LOG_ID = "[HomeController]";

	@RequestMapping("/")
	public String home(Model model, Authentication authentication)
	{
		LOGGER.info("{} - Authentication: {}", LOG_ID, authentication);
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/user/list";
	}


}

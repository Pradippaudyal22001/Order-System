package com.res.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.res.order.dao.Menu;

@Controller
public class pageController {
	@GetMapping("/")
	public String login() {
		return "index";
	}

	@GetMapping("/adminHome")
	public String adminHome() {
		return "admin_home";
	}

	@GetMapping("/addMenu")
	public String addMenu(Model model) {
		model.addAttribute("menuobj", new Menu());
		return "add_menu_item";
	}

	

	

};

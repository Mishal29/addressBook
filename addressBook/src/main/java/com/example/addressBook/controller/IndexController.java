package com.example.addressBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.addressBook.model.User;
import com.example.addressBook.service.UserService;

@Controller
public class IndexController {
	
	@Autowired
	UserService userService;

	@GetMapping("/")
	public String getIndex(
			@ModelAttribute User form,
			Model model
	) {
		if(!model.containsAttribute("retry")) {
			model.addAttribute("retry", false);
		}
		
		return "login";
	}
	
	@PostMapping("/")
	public String postIndex() {
		return "redirect:/";
	}
}

package com.excilys.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import com.excilys.binding.dto.UserDTO;

@Controller
public class UserController {
	
	
	@GetMapping({"/login"})
	public String showLoginForm(WebRequest request, Model model) {
	    UserDTO userDto = new UserDTO();
	    model.addAttribute("user", userDto);
	    return "login";
	}
	
	
}

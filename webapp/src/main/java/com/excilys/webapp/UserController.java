package com.excilys.webapp;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import com.excilys.binding.dto.UserDTO;

public class UserController {

	@GetMapping(value = "/user/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
	    UserDTO userDto = new UserDTO();
	    model.addAttribute("user", userDto);
	    return "registration";
	}
	
	
}

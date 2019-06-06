package com.excilys.binding.mapper;

import org.springframework.stereotype.Component;

import com.excilys.binding.dto.UserDTO;
import com.excilys.core.User;

@Component
public class UserMapper {

	public User dtoToModel(UserDTO userDto) {
		User user = new User();
		if(userDto!=null) {
			user.setPassword(userDto.getPassword());
			user.setUserName(userDto.getUserName());
		}
		return user;
	}

	public UserDTO modelToDto(User user) {
		UserDTO userDto = new UserDTO();
		if(user!=null) {
			userDto.setPassword(user.getPassword());
			userDto.setUserName(user.getUserName());
		}	
		return userDto;
	}
}

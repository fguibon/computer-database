package com.excilys.service;

import org.springframework.stereotype.Service;

import com.excilys.binding.exceptions.UserNotFoundException;
import com.excilys.core.User;
import com.excilys.persistence.dao.UserDAO;

@Service
public class UserService {

	private UserDAO userDAO;
	
	public UserService (UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public void verify(User user) throws UserNotFoundException {
		if(findUser(user.getUserName())){
			checkPassword(user.getPassword());
		} else {
			throw new UserNotFoundException("Can't find user of username: "+user.getUserName());
		}
	}
	
	public boolean findUser(String username) {
		return true;
	}
	
	public boolean checkPassword(String password) {
		return true;
	}
}

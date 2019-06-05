package com.excilys.service;

import org.springframework.stereotype.Service;

import com.excilys.persistence.dao.UserDAO;

@Service
public class UserService {

	private UserDAO userDAO;
	
	public UserService (UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public boolean findUser(String username) {
		return false;
	}
}

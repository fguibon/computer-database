package com.excilys.persistence.dao;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.excilys.binding.exceptions.DatabaseException;
import com.excilys.core.Computer;

@Repository
public class UserDAO {

	private static final String SELECT_ONE = 
			" FROM User u WHERE u.userName= :userName";
	
	private static final Logger LOGGER = 
			LogManager.getLogger(UserDAO.class);
	
	private SessionFactory sessionFactory;	

	public UserDAO(SessionFactory sessionFactory) {	
		this.sessionFactory = sessionFactory;
	}
	
	public boolean findUser(String userName) throws DatabaseException {
		boolean found = false;
		try (Session session = sessionFactory.openSession()){
			if(session.createQuery(SELECT_ONE,Computer.class)
					.setParameter("userName", userName).uniqueResult()!=null) {
				found = true;
			}
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not find the user of username: "+userName);
		}
		return found;
	}
}

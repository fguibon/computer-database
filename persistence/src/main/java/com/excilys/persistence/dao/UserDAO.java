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
			" FROM Computer c WHERE c.id= :id";
	
	private static final Logger LOGGER = 
			LogManager.getLogger(UserDAO.class);
	
	private SessionFactory sessionFactory;	

	public UserDAO(SessionFactory sessionFactory) {	
		this.sessionFactory = sessionFactory;
	}
	
	public Computer read(Long id) throws DatabaseException {
		Computer computer = new Computer();
		try (Session session = sessionFactory.openSession()){
			computer = session.createQuery(SELECT_ONE,Computer.class)
					.setParameter("id", id).getSingleResult();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not retrieve the computer of id: "+id);
		}
		return computer;
	}
}

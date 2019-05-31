package com.excilys.persistence.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.excilys.binding.exceptions.DatabaseException;
import com.excilys.core.Company;

@Repository
public class CompanyDAO {

	private static final Logger LOGGER = 
			LogManager.getLogger(CompanyDAO.class);

	private static final String SELECT_ONE = 
			"from Company WHERE id= :id";

	private static final String SELECT_ALL = 
			"from Company ";
	
	
	private SessionFactory sessionFactory;
	
	public CompanyDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	public List<Company> findAll() throws DatabaseException {
		
		List<Company> companies = new ArrayList<>();
		try (Session session = sessionFactory.openSession()){
			companies = session.createQuery(SELECT_ALL, Company.class).getResultList();
		} catch(PersistenceException e) {
			LOGGER.error(e.getMessage(),e);
			throw new DatabaseException("Cannot find companies") ;
		}
		return companies;
	}
	

	public Company read(Long id) throws DatabaseException {
		Company company = new Company();
		try(Session session = sessionFactory.openSession()) {
			company = session.createQuery(SELECT_ONE,Company.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DatabaseException("Cannot find the id provided : "+ id);
		} 
		return company;
	}

	
	public int delete(Long id) throws DatabaseException {
		try(Session session = sessionFactory.openSession()) {	
			Transaction tx = session.beginTransaction();
			Company toDelete = session.get(Company.class, id);
			session.delete(toDelete);
			tx.commit();
		} catch(PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not remove the company of id : ");
		}		
		return 1; 
	}

}

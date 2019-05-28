package com.excilys.persistence.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Company;
import com.excilys.model.Sorting;

/**
 * CompanyDAO class : makes requests to the company table
 * @author excilys
 *
 */

@Repository
public class CompanyDAO implements DataAccessObject<Company>{

	private static final Logger LOGGER = 
			LogManager.getLogger(CompanyDAO.class);
	
	

	private static final String SELECT_ONE = 
			"from Company WHERE id= :id";

	private static final String SELECT_ALL = 
			"from Company ";

	private static final String UPDATE= 
			"update Company set name= :name WHERE id= :id";
	
	private static final String DELETE_COMPANY=
			"delete Company WHERE id= :id";
	
	private static final String SELECT_ALL_PAGED =
			"from Company "
			+ " LIMIT :limit OFFSET :offset";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public CompanyDAO(EntityManager entityManager) {
		Objects.requireNonNull(entityManager);
		this.entityManager = entityManager;
	}

	/**
	 * Creates a company
	 * @return a boolean value to know if it is created
	 * @throws DatabaseException 
	 */
	@Override
	public int create(Company company) throws DatabaseException {
		int number = 0;
		try {
			entityManager.persist(company);
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage(),e);
			throw new DatabaseException("Cannot insert company : "+ company.toString());
		}
		return number;
	}

	/**
	 * Finds and returns all companies in the table
	 * @return a List of companies
	 * @throws DatabaseException 
	 */

	public List<Company> findAll() throws DatabaseException {
		
		List<Company> companies = new ArrayList<>();
		try {
			TypedQuery<Company> query = entityManager.createQuery(SELECT_ALL, Company.class);
			companies = query.getResultList();
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage(),e);
			throw new DatabaseException("Cannot find companies") ;
		}
		return companies;
	}
	
	/**
	 * Finds companies and limits the results
	 * @param limit
	 * @param currentPage
	 * @return
	 * @throws DatabaseException 
	 */
	public List<Company> findAllPaged(Sorting sorting) 
			throws DatabaseException {
		
		List<Company> companies = new ArrayList<>();
		int offset = ((sorting.getPage()-1) * sorting.getLimit());
		try {
			TypedQuery<Company> query = entityManager.createQuery(SELECT_ALL_PAGED,Company.class);
			query.setParameter("limit", sorting.getLimit());
			query.setParameter("offset", offset);
			companies=query.getResultList();
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage(),e);
			throw new DatabaseException("Cannot find companies with : "+sorting.toString());
		} 
		return companies;
	}


	/**
	 * Find a Company by its id
	 * @return a Company object
	 * @throws DatabaseException 
	 */
	@Override
	public Company findById(Long id) throws DatabaseException {
		Company company = new Company();
		try {
			TypedQuery<Company> query = entityManager.createQuery(SELECT_ONE,Company.class);
			query.setParameter("id", id);
			company = query.getSingleResult();
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DatabaseException("Cannot find the id provided : "+ id);
		} 
		return company;
	}

	/**
	 * Updates a Company information
	 * @return a Company object
	 * @throws DatabaseException 
	 */
	@Override
	public int update(Company company) throws DatabaseException {
		int number = 0;
		try {
			TypedQuery<Company> query = entityManager.createQuery(UPDATE, Company.class);
			query.setParameter("id", company.getId());
			query.setParameter("name", company.getName());
			number = query.executeUpdate();
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not update the company: "+company.toString());
		}
		return number;
	}

	/**
	 * Deletes a company record
	 * @throws DatabaseException 
	 * 
	 */
	@Override
	public int delete(Long id) throws DatabaseException {
		int number = 0;
		try {	
			TypedQuery<Company> query = entityManager.createQuery(DELETE_COMPANY, Company.class);
			query.setParameter("id", id);
			number = query.executeUpdate();
		} catch(DataAccessException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not remove the company of id : ");
		}		
		return number; 
	}

}

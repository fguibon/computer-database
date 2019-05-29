package com.excilys.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.exceptions.DatabaseException;
import com.excilys.model.Computer;
import com.excilys.model.Sorting;

/**
 * ComputerDAO class : makes requests to the computer table
 * @author excilys
 *
 */

@Repository
public class ComputerDAO {

	public enum Field { ID,NAME,INTRODUCED,DISCONTINUED,COMPANY_ID}

	private enum Order {ASC, DESC}

	private static final Logger LOGGER = 
			LogManager.getLogger(ComputerDAO.class);


	private static final String SELECT = 
			" FROM Computer c ";
	
	private static final String SELECT_ONE = 
			SELECT + "WHERE c.id= :id";


	private static final String SELECT_ORDER_BY =
			SELECT + "WHERE UPPER(c.name) LIKE UPPER(:filter) ORDER BY ";


	private static final String DELETE_COMPUTER_WHERE=
			"DELETE FROM Computer where company_id =:company_id ";

	private static final String COUNT = 
			"SELECT COUNT(c) FROM Computer c";
	
	private SessionFactory sessionFactory;	
	
	public ComputerDAO(SessionFactory sessionFactory) {	
		this.sessionFactory = sessionFactory;
	}


	/**
	 * Creates a computer
	 * @return a boolean value to know if it is created
	 * @throws Exception 
	 */
	public int create(Computer computer) throws DatabaseException {
		try (Session session = sessionFactory.openSession()){
			Transaction tx = session.beginTransaction();
			session.save(computer);
			tx.commit();
		} catch (PersistenceException e) {
			LOGGER.warn(e.getMessage());
			throw new DatabaseException("Cannot insert computer : "+computer.toString());
		}
		return 1;
				
	}

	/**
	 * Finds and return all computers in the table
	 * @return a List of computers
	 * @throws Exception 
	 */
	public List<Computer> findAll() throws DatabaseException {
		
		List<Computer> computers = new ArrayList<>();
		try (Session session = sessionFactory.openSession()){
			computers = session.createQuery(SELECT,Computer.class).getResultList();
		} catch(PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not retrieve the list of computers");
		}
		return computers;
	}

	/**
	 * Finds all computers and limits the results
	 * @param limit
	 * @param currentPage
	 * @return
	 * @throws Exception 
	 */
	public List<Computer> findAllPaged(Sorting sorting) throws DatabaseException {
		List<Computer> computers = new ArrayList<>();	
		int offset = ((sorting.getPage()-1) * sorting.getLimit());
		String sql = getSortingQuerySQL(sorting.getField(), sorting.getOrder());
		try(Session session = sessionFactory.openSession()) {
			Query<Computer> query = session.createQuery(sql, Computer.class);
			query.setParameter("filter", "%"+sorting.getFilter() +"%");
			query.setFirstResult(offset);
			query.setMaxResults(sorting.getLimit());
			computers = query.getResultList();
		} catch(PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not retrieve the computers");
		}
		return computers;
	}


	/**
	 * Find a Computer by its id
	 * @return a Computer object
	 * @throws Exception 
	 */
	
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


	/**
	 * Updates a Computer information
	 * @return the number of rows affected 
	 */

	public int update(Computer computer) throws DatabaseException {
		int number = 0;
		try (Session session = sessionFactory.openSession()){
			Transaction tx = session.beginTransaction();
			Computer tochange = read(computer.getId());
			
			tochange.setName(computer.getName());
			tochange.setIntroduced(computer.getIntroduced());
			tochange.setDiscontinued(computer.getDiscontinued());
			tochange.setCompany(computer.getCompany());
			session.update(tochange);
			tx.commit();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not update the computer: "+computer.toString());
		}
		return number;	
	}

	/**
	 * Deletes a computer from the database
	 * @return the number of rows affected
	 */

	public int delete(Long id) throws DatabaseException {
		try (Session session = sessionFactory.openSession()){
			Transaction tx = session.beginTransaction();
			Computer toDelete = session.get(Computer.class, id);
			session.delete(toDelete);
			tx.commit();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not delete the computer of id: "+id);
		}
		return 1;	
	}

	/**
	 * Delete all computers associated with the id given.
	 * @param id
	 * @return
	 * @throws DatabaseException
	 */
	public int deleteComputerWhere(Long id) throws DatabaseException {
		int number = 0; 
		try(Session session = sessionFactory.openSession()) {
			Query<Computer> query = session.createQuery(DELETE_COMPUTER_WHERE,Computer.class);
			query.setParameter(1, id);
			number = query.executeUpdate();
		} catch (PersistenceException e) {
			LOGGER.error("Query error : "+ e.getMessage());
			throw new DatabaseException("Could not remove the computer of id : "+id);
		}
		return number;
	}
	
	
	/**
	 * Get the computers total count
	 * @return the total number 
	 * @throws DatabaseException 
	 */
	public int count() throws DatabaseException  {
		int number = 0;
		try(Session session = sessionFactory.openSession()){
			Query<Long> query = session.createQuery(COUNT, Long.class);
			number = query.getSingleResult().intValue();
		} catch (PersistenceException e) {
			LOGGER.error(e.getMessage());
			throw new DatabaseException("Could not retrieve the total number of computers");
		}
		 return number;	
	}


	public String getField(String choice) {
		switch(choice) {
		case "name":
			return "c."+Field.NAME.toString().toLowerCase();
		case "intro":
			return "c."+Field.INTRODUCED.toString().toLowerCase();
		case "disco":
			return "c."+Field.DISCONTINUED.toString().toLowerCase();
		case "company":
			return "c."+Field.COMPANY_ID.toString().toLowerCase();
		default:
			return "c."+Field.ID.toString().toLowerCase();
		}
	}

	public String getOrder(String choice) {
		switch(choice) {
		case "asc":
			return Order.ASC.toString();
		case "desc":
			return Order.DESC.toString();
		default:
			return Order.ASC.toString();
		}
	}

	public String getSortingQuerySQL( String fieldParam, String orderParam ){
		String sql = SELECT_ORDER_BY + getField(fieldParam)+ 
				" " +getOrder(orderParam);
		LOGGER.debug(sql);
		return sql;
	}

}

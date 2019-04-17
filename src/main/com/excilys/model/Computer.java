package main.com.excilys.model;


import java.time.LocalDate;

import main.com.excilys.util.DataTransferObject;

public class Computer implements DataTransferObject {

	private Long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinueddate;
	private Long company_id;
	
	public Computer() {	}
	
	public Computer(Long id, String name, LocalDate introducedDate, 
			LocalDate discontinuedDate, Long company_id) {
		this.id=id;
		this.name=name;
		this.introducedDate=introducedDate;
		this.discontinueddate=discontinuedDate;
		this.company_id=company_id;
	}
	

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the introduced
	 */
	public LocalDate getIntroduced() {
		return introducedDate;
	}
	/**
	 * @param introducedDate the introduced to set
	 */
	public void setIntroduced(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}
	/**
	 * @return the discontinued
	 */
	public LocalDate getDiscontinued() {
		return discontinueddate;
	}
	/**
	 * @param discontinuedDate the discontinued to set
	 */
	public void setDiscontinued(LocalDate discontinuedDate) {
		this.discontinueddate = discontinuedDate;
	}
	/**
	 * @return the company_id
	 */
	public Long getCompanyId() {
		return company_id;
	}
	/**
	 * @param company_id the company_id to set
	 */
	public void setCompanyId(Long company_id) {
		this.company_id = company_id;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introducedDate + ", discontinued=" + discontinueddate
				+ ", id_company=" + company_id + "]";
	}
	
	
	
}

package model;

import java.sql.Timestamp;

import util.DataTransferObject;

public class Computer implements DataTransferObject {

	private Long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Long id_company;
	
	public Computer() {	}
	
	public Computer(Long id, String name, Timestamp introduced, 
			Timestamp discontinued, Long id_company) {
		this.id=id;
		this.name=name;
		this.introduced=introduced;
		this.discontinued=discontinued;
		this.id_company=id_company;
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
	public Timestamp getIntroduced() {
		return introduced;
	}
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}
	/**
	 * @return the discontinued
	 */
	public Timestamp getDiscontinued() {
		return discontinued;
	}
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}
	/**
	 * @return the id_company
	 */
	public Long getId_company() {
		return id_company;
	}
	/**
	 * @param id_company the id_company to set
	 */
	public void setId_company(Long id_company) {
		this.id_company = id_company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", id_company=" + id_company + "]";
	}
	
	
	
}

package main.com.excilys.mapper;

public class ComputerDTO {

	private String id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private String companyId;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	public String getIntroduced() {
		return introducedDate;
	}
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(String introduced) {
		this.introducedDate = introduced;
	}
	/**
	 * @return the discontinued
	 */
	public String getDiscontinued() {
		return discontinuedDate;
	}
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(String discontinued) {
		this.discontinuedDate = discontinued;
	}
	/**
	 * @return the company_id
	 */
	public String getcompany_id() {
		return companyId;
	}
	/**
	 * @param company_id the company_id to set
	 */
	public void setCompanyId(String company_id) {
		this.companyId = company_id;
	}
}

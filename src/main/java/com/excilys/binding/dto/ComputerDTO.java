package com.excilys.binding.dto;

public class ComputerDTO {

	private String id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private CompanyDTO companyDTO;
	
	public ComputerDTO() {
	}
	
	public ComputerDTO(String id, String name, String introducedDate, 
			String discontinuedDate, CompanyDTO companyDTO) {
		this.id = id;
		this.name=name;
		this.introducedDate = introducedDate;
		this.discontinuedDate = discontinuedDate;
		this.companyDTO = companyDTO;
	}
	
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
	 * @return the companyDTO
	 */
	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}
	/**
	 * @param companyDTO the companyDTO to set
	 */
	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}
	
	
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discontinuedDate="
				+ discontinuedDate + ", " + companyDTO + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyDTO == null) ? 0 : companyDTO.hashCode());
		result = prime * result + ((discontinuedDate == null) ? 0 : discontinuedDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introducedDate == null) ? 0 : introducedDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object o) {
		boolean eq =
				(o!=null) &&
				(this.getClass()==o.getClass()) &&
				(this.id == ((ComputerDTO) o).getId()) &&
				(this.name == ((ComputerDTO) o).getName()) &&
				(this.introducedDate == ((ComputerDTO) o).getIntroduced()) &&
				(this.discontinuedDate == ((ComputerDTO) o).getDiscontinued()) &&
				(this.companyDTO == ((ComputerDTO) o).getCompanyDTO())
				;
		return eq;
	}
	
}

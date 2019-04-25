package com.excilys.binding.dto;

import java.util.Objects;

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
		return Objects.hash(companyDTO, discontinuedDate, id, introducedDate, name);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComputerDTO other = (ComputerDTO) obj;
		return Objects.equals(companyDTO, other.companyDTO) && Objects.equals(discontinuedDate, other.discontinuedDate)
				&& Objects.equals(id, other.id) && Objects.equals(introducedDate, other.introducedDate)
				&& Objects.equals(name, other.name);
	}
	
}

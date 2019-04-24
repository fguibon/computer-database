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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComputerDTO other = (ComputerDTO) obj;
		if (companyDTO == null) {
			if (other.companyDTO != null)
				return false;
		} else if (!companyDTO.equals(other.companyDTO))
			return false;
		if (discontinuedDate == null) {
			if (other.discontinuedDate != null)
				return false;
		} else if (!discontinuedDate.equals(other.discontinuedDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introducedDate == null) {
			if (other.introducedDate != null)
				return false;
		} else if (!introducedDate.equals(other.introducedDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}

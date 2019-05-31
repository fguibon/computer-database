package com.excilys.binding.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.excilys.binding.validator.CompareDates;

@CompareDates(firstDate = "introducedDate", secondDate = "discontinuedDate")
public class ComputerDTO {

	
	private static final String DATE_PATTERN = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
		      + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
		      + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";
	private static final String NAME_PATTERN = "^[\\w-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:]{2,}$";
	

	private String id;
	
	@NotBlank( message = "Name must not null")
	@Pattern(regexp= NAME_PATTERN)
	private String name;
	
	@Pattern(regexp= DATE_PATTERN)
	private String introducedDate;
	
	@Pattern(regexp= DATE_PATTERN)
	private String discontinuedDate;
	
	private String companyId;
	
	private String companyName;


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
		if (introduced!= null && introduced.isEmpty()) {
			introduced = null;
		}
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
		if (discontinued!= null && discontinued.isEmpty()) {
			discontinued = null;
		}
		this.discontinuedDate = discontinued;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public static class Builder {
		private String id;
		private String name;
		private String introducedDate;
		private String discontinuedDate;
		private String companyId;
		private String companyName;


		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setIntroduced(String introduced) {
			this.introducedDate = introduced;
			return this;
		}

		public Builder setDiscontinued(String discontinued) {
			this.discontinuedDate = discontinued;
			return this;
		}
		
		public Builder setCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		public Builder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public ComputerDTO build() {
			ComputerDTO dto = new ComputerDTO();
			dto.setId(this.id);
			dto.setName(this.name);
			dto.setIntroduced(this.introducedDate);
			dto.setDiscontinued(this.discontinuedDate);
			dto.setCompanyId(this.companyId);
			dto.setCompanyName(this.companyName);
			return dto;
		}	
	}


	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introducedDate + ", discontinuedDate="
				+ discontinuedDate + ", company id=" + companyId + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(companyId, discontinuedDate, id, introducedDate, name);
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
		return Objects.equals(companyId, other.companyId) && Objects.equals(discontinuedDate, other.discontinuedDate)
				&& Objects.equals(id, other.id) && Objects.equals(introducedDate, other.introducedDate)
				&& Objects.equals(name, other.name);
	}

}

package com.excilys.binding.dto;

import java.util.Objects;

public class ComputerDTO {

	private String id;
	private String name;
	private String introducedDate;
	private String discontinuedDate;
	private String companyId;
	private String companyName;

	public ComputerDTO() {}


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

		public Builder() {}

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

package com.excilys.binding.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CompanyDTO {
	
	
	private static final String NAME_PATTERN = "^[\\w-,.0-9][^_!¡?÷?¿/\\+=@#$%ˆ&*(){}|~:]{2,}$";
	
	private String id;
	
	@NotBlank
	@Pattern(regexp= NAME_PATTERN)
	private String name;
	

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
	
	public static class Builder {
		private String id;
		private String name;


		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public CompanyDTO build() {
			CompanyDTO dto = new CompanyDTO();
			dto.setId(this.id);
			dto.setName(this.name);
			return dto;
		}	
	}
	
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyDTO other = (CompanyDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
}

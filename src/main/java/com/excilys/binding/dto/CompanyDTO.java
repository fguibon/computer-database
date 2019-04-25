package com.excilys.binding.dto;

import java.util.Objects;

public class CompanyDTO {
	
	private String id;
	private String name;
	
	public CompanyDTO() {
	}
	
	public CompanyDTO(String id, String name) {
		this.id= id;
		this.name=name;
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

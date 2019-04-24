package com.excilys.binding.dto;

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object o) {
		boolean eq =
				(o!=null) &&
				(this.getClass()==o.getClass()) &&
				(this.id == ((CompanyDTO) o).getId()) &&
				(this.name == ((CompanyDTO) o).getName()) 
				;
		return eq;
	}
	
}

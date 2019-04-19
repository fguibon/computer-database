package main.com.excilys.model;


import java.time.LocalDate;

public class Computer {

	private Long id;
	private String name;
	private LocalDate introducedDate;
	private LocalDate discontinueddate;
	private Company company;
	
	public Computer() {	}
	
	public Computer(Long id, String name, LocalDate introducedDate, 
			LocalDate discontinuedDate, Company company) {
		this.id=id;
		this.name=name;
		this.introducedDate=introducedDate;
		this.discontinueddate=discontinuedDate;
		this.company=company;
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
	public LocalDate getIntroduced() {
		return introducedDate;
	}
	/**
	 * @param introducedDate the introduced to set
	 */
	public void setIntroduced(LocalDate introducedDate) {
		this.introducedDate = introducedDate;
	}
	/**
	 * @return the discontinued
	 */
	public LocalDate getDiscontinued() {
		return discontinueddate;
	}
	/**
	 * @param discontinuedDate the discontinued to set
	 */
	public void setDiscontinued(LocalDate discontinuedDate) {
		this.discontinueddate = discontinuedDate;
	}
	/**
	 * @return the company_id
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introducedDate + ", discontinued=" + discontinueddate
				+ ", " + company + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinueddate == null) ? 0 : discontinueddate.hashCode());
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
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinueddate == null) {
			if (other.discontinueddate != null)
				return false;
		} else if (!discontinueddate.equals(other.discontinueddate))
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

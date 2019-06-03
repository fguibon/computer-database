package com.excilys.core;


import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="computer")
public class Computer {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "introduced")
	@Convert(converter = LocalDateAttributeConverter.class )
	private LocalDate introduced;
	
	@Column(name = "discontinued")
	@Convert(converter = LocalDateAttributeConverter.class )
	private LocalDate discontinued;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	public Computer() {
		
	}
	
	public Computer(Long id, String name, LocalDate discontinued, LocalDate introduced, Company company ) {
        this.discontinued = discontinued;
        this.id = id;
        this.introduced = introduced;
        this.company = company;
        this.name = name;
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
		return introduced;
	}
	/**
	 * @param introducedDate the introduced to set
	 */
	public void setIntroduced(LocalDate introducedDate) {
		this.introduced = introducedDate;
	}
	/**
	 * @return the discontinued
	 */
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	/**
	 * @param discontinuedDate the discontinued to set
	 */
	public void setDiscontinued(LocalDate discontinuedDate) {
		this.discontinued = discontinuedDate;
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
	
	public static class ComputerBuilder {
        private LocalDate discontinued;
        private Long id;
        private LocalDate introduced;
        private Company company;
        private String name;


        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public ComputerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ComputerBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }

        public ComputerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Computer build() {
            return new Computer(id,name,discontinued,  introduced, company );
        }
        
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introducedDate=" + introduced + ", discontinuedDate="
				+ discontinued + ", company=" + company + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, discontinued, id, introduced, name);
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
		return Objects.equals(company, other.company) && Objects.equals(discontinued, other.discontinued)
				&& Objects.equals(id, other.id) && Objects.equals(introduced, other.introduced)
				&& Objects.equals(name, other.name);
	}	
	
}

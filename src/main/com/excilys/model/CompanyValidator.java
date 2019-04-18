package main.com.excilys.model;

public class CompanyValidator implements Validator<Company> {

	@Override
	public void validate(Company company) {
		boolean valid=false;
		valid=( company.getName()!=null );
		if(!valid) throw new RuntimeException();
	}

}

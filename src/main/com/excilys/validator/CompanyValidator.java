package main.com.excilys.validator;

import main.com.excilys.model.Company;

public class CompanyValidator implements Validator<Company> {

	@Override
	public void validate(Company company) {
		boolean valid=false;
		valid=( company.getName()!=null );
		if(!valid) throw new RuntimeException();
	}

}

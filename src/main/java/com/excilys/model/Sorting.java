package com.excilys.model;

public class Sorting {

	private String field;
	private String order;
	
	public Sorting(String field, String order) {
		this.field=field;
		this.order=order;
	}


	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}


	@Override
	public String toString() {
		return "Sorting [field=" + field + ", order=" + order + "]";
	}
}

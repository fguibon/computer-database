package com.excilys.model;

public class Sorting {

	private String field;
	private String order;
	private String filter;
	private Page page;
	
	public Sorting() {}
	
	public Sorting(String field, String order, String filter, Page page) {
		this.field=field;
		this.order=order;
		this.setFilter(filter);
		this.setPage(page);
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


	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}


	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}


	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}


	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}


	@Override
	public String toString() {
		return "Sorting [field=" + field + ", order=" + order + ", filter=" + filter + ", page=" + page + "]";
	}
	
}

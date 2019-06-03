package com.excilys.core;

import java.util.ArrayList;
import java.util.List;

public class Sorting {

	private int page;
	private int limit;
	private String field;
	private String order;
	private String filter;
	
	public Sorting() {}
	
	public Sorting(int page, int limit, String field, String order, String filter) {
		this.page = page;
		this.limit = limit;
		this.field=field;
		this.order=order;
		this.filter = filter;
	}


	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
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
	 * Return the page list for the ui
	 * @param offset
	 * @return
	 */
	public List<Integer> getPageList(int offset){
		List<Integer> pages = new ArrayList<>();
		if(offset<=3) {
			for(int i=1;i<6;i++) {
				pages.add(i);
			}
		} else {
			for(int i=offset-2;i<offset+3;i++) {
				pages.add(i);
			}
		}
		return pages;
	}

	@Override
	public String toString() {
		return "Sorting [currentPage=" + page + ", limit=" + limit + ", field=" + field
				+ ", order=" + order + ", filter=" + filter + "]";
	}

	
}

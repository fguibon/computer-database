package com.excilys.model;

import java.util.ArrayList;
import java.util.List;

public class Page {

	private int currentPage;
	private int entriesPerPage;
	
	public Page(int currentPage, int entriesPerPage) {
		this.currentPage = currentPage;
		this.entriesPerPage = entriesPerPage;
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the entriesPerPage
	 */
	public int getEntriesPerPage() {
		return entriesPerPage;
	}

	/**
	 * @param entriesPerPage the entriesPerPage to set
	 */
	public void setEntriesPerPage(int entriesPerPage) {
		this.entriesPerPage = entriesPerPage;
	}
	
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
		return "Page [currentPage=" + currentPage + ", entriesPerPage=" + entriesPerPage + "]";
	}
	
	
}

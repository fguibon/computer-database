package com.excilys.model;

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

	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", entriesPerPage=" + entriesPerPage + "]";
	}
	
	
}

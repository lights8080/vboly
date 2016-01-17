package com.lihp.common.page;

import java.io.Serializable;

/**
 * 分页pageVO
 * 
 * @author lihp
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 5565237479954610505L;
	// 当前页码
	private int currentPage = 1;
	// 总页数
	private int totalPages;
	// 总记录数
	private int totalRecords;
	// 每页记录数
	private int everyPageSize;
	// 当前页查询开始索引值
	private int beginIndex;

	/**
     * 
     */
	public Page() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param currentPage
	 * @param totalPages
	 * @param totalRecords
	 * @param everyPageSize
	 * @param beginIndex
	 */
	public Page(int currentPage, int totalPages, int totalRecords,
			int everyPageSize, int beginIndex) {
		this.currentPage = currentPage;
		this.totalPages = totalPages;
		this.totalRecords = totalRecords;
		this.everyPageSize = everyPageSize;
		this.beginIndex = beginIndex;
	}

	/**
	 * @param everyPage
	 */
	public Page(int everyPageSize) {
		this.everyPageSize = everyPageSize;
	}

	/**
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the totalPages.
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages
	 *            The totalPages to set.
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return Returns the totalRecords.
	 */
	public int getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @param totalRecords
	 *            The totalRecords to set.
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return Returns the everyPageSize.
	 */
	public int getEveryPageSize() {
		return everyPageSize;
	}

	/**
	 * @param everyPageSize
	 *            The everyPageSize to set.
	 */
	public void setEveryPageSize(int everyPageSize) {
		this.everyPageSize = everyPageSize;
	}

	/**
	 * @return Returns the beginIndex.
	 */
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * @param beginIndex
	 *            The beginIndex to set.
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
}

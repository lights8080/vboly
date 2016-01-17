package com.lihp.common.page;

/**
 * 分页工具类
 * 
 * @author lihp
 */
public class PageUtil {

	private static final int DEFAULT_PAGE_SIZE = 100;

	/**
	 * 创建PageVo
	 * 
	 * @param page
	 * @param totalRecords
	 * @return
	 */
	public static Page createPage(Page page, int totalRecords) {
		return createPage(page.getCurrentPage(), page.getEveryPageSize(),
				totalRecords);
	}

	/**
	 * 创建PageVo
	 * 
	 * @param currentPage
	 * @param everyPageSize
	 * @param totalRecords
	 * @return
	 */
	public static Page createPage(int currentPage, int everyPageSize,
			int totalRecords) {
		everyPageSize = getEveryPageSize(everyPageSize);
		currentPage = getCurrentPage(currentPage);
		int totalPages = getTotalPages(everyPageSize, totalRecords);
		if (currentPage > totalPages) {
			currentPage = totalPages;
		}
		int beginIndex = getBeginIndex(everyPageSize, currentPage);
		return new Page(currentPage, totalPages, totalRecords, everyPageSize,
				beginIndex);
	}

	/**
	 * 取得每页记录数
	 * 
	 * @param everyPageSize
	 * @return
	 */
	private static int getEveryPageSize(int everyPageSize) {
		return everyPageSize <= 0 ? DEFAULT_PAGE_SIZE : everyPageSize;
	}

	/**
	 * 取得当前页码
	 * 
	 * @param currentPage
	 * @return
	 */
	private static int getCurrentPage(int currentPage) {
		return currentPage < 0 ? 1 : currentPage;
	}

	/**
	 * 取得当前页查询开始索引
	 * 
	 * @param everyPageSize
	 * @param currentPage
	 * @return
	 */
	private static int getBeginIndex(int everyPageSize, int currentPage) {
		return (currentPage - 1) * everyPageSize;
	}

	/**
	 * 取得总分页数
	 * 
	 * @param everyPageSize
	 * @param totalRecords
	 * @return
	 */
	private static int getTotalPages(int everyPageSize, int totalRecords) {
		int totalPages = 0;

		if (totalRecords % everyPageSize == 0) {
			totalPages = totalRecords / everyPageSize;
		} else {
			totalPages = totalRecords / everyPageSize + 1;
		}
		return totalPages;
	}
}

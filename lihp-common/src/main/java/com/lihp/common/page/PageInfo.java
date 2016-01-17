package com.lihp.common.page;

public class PageInfo implements java.io.Serializable {
	private static final long serialVersionUID = -2157930440243959361L;

	int currentPage = 1; // 当前页

	public int totalPages = 1; // 总页数

	int pageRecorders = 10; // 每页10条数据

	int pageRowBegin = 1; // 每页显示的记录的开始行号

	int totalRows = 0; // 总数据数

	private boolean hasNextPage = false;
	private boolean hasPreviousPage = false;

	private String path; // 分页提交的路径

	/**
	 * 增加,删除,修改操作成功与否的标志 0:默认值
	 * 
	 * 1:操作成功值
	 * 
	 * 2:失败值
	 * 
	 * 3:表示各种失败情况,现在不具体定义
	 **/

	protected int actionFlag = 0;

	public PageInfo() {

	}

	public PageInfo(int currentPage, int totalRows, int pageRecorders) {

		this.currentPage = currentPage;

		this.totalRows = totalRows;

		this.pageRecorders = pageRecorders;

		int totalPage = totalRows / pageRecorders;

		int yu = totalRows % pageRecorders;

		if (yu > 0) {
			totalPage = totalPage + 1;

		}
		if (totalRows == 0) {
			totalPage = 1;

		}
		this.setTotalPages(totalPage);

		try {

			if (totalPage > 0) {

				if (currentPage < 1 || currentPage > totalPage) {
					throw new Exception();
				}

			}

		} catch (Exception e) {

			// SysParam.businessLogger.info("");

			return;

		}

		// 设定本页开始的行号
		pageRowBegin = pageRecorders * (currentPage - 1) + 1;
	}

	public int getCurrentPage() {

		return currentPage;

	}

	public int getTotalPages() {

		return totalPages;

	}

	public int getTotalRows() {

		return totalRows;

	}

	public void setTotalRows(int totalRows) {
		totalPages = totalRows / pageRecorders;

		int yu = totalRows % pageRecorders;

		if (yu > 0) {
			totalPages = totalPages + 1;
		}

		this.totalRows = totalRows;

	}

	public void setTotalPages(int totalPages) {

		this.totalPages = totalPages;

	}

	public void setCurrentPage(int currentPage) {

		this.currentPage = currentPage;

	}

	public int getPageRecorders() {

		return pageRecorders;

	}

	public void setPageRecorders(int pageRecorders) {
		totalPages = totalRows / pageRecorders;

		int yu = totalRows % pageRecorders;

		if (yu > 0) {
			totalPages = totalPages + 1;
		}

		this.pageRecorders = pageRecorders;

	}

	public static void main(String args[]) {

		// 当前页，总行数，每页记录数
		PageInfo test = new PageInfo(2, 10, 3);
		System.out.println(test.pageRowBegin);

	}

	public String getPath() {

		return path;

	}

	public void setPath(String path) {

		this.path = path;

	}

	public boolean isHasNextPage() {

		return currentPage < totalPages;

	}

	public boolean isHasPreviousPage() {

		return currentPage > 1;

	}

	public int getActionFlag() {

		return actionFlag;

	}

	public void setActionFlag(int actionFlag) {

		this.actionFlag = actionFlag;

	}

	public String toString() {

		return new StringBuffer().append("[ [ actionFlag = " + actionFlag)

		.append(" ] [ currentPage = " + this.currentPage)

		.append(" ] [ hasNextPage = " + isHasNextPage())

		.append(" ] [ hasPreviousPage = " + isHasPreviousPage())

		.append(" ] [ path = " + this.path)

		.append(" ] [ pageRecorders = " + this.pageRecorders)

		.append(" ] [ totalPages = " + this.totalPages)

		.append(" ] [ totalRows = " + this.totalRows + " ] ]").toString();

	}

	public int getPageRowBegin() {
		return pageRowBegin;
	}

	public void setPageRowBegin(int pageRowBegin) {
		this.pageRowBegin = pageRowBegin;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean getHasNextPage() {
		return hasNextPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	/**
	 * 分页栏函数( path必须传进来)
	 * 
	 * @return String 分栏字符串
	 */
	public String getFooter() {
		StringBuffer str = new StringBuffer("");
		int next, prev;
		prev = currentPage - 1;
		next = currentPage + 1;
		str.append("第" + currentPage + "页");
		if (currentPage > 1) {
			str.append("&nbsp;<a href=\"javascript:goOtherPage(1);\">首页</a>&nbsp;");
		} else {
			str.append("&nbsp;首页&nbsp;");
		}
		if (currentPage > 1) {
			str.append("&nbsp;<a href=\"javascript:goOtherPage(" + prev
					+ ");\">上一页</a>&nbsp;");
		} else {
			str.append("&nbsp;上一页&nbsp;");
		}
		if (currentPage < totalPages) {
			str.append("&nbsp;<a href=\"javascript:goOtherPage(" + next
					+ ");\">下一页</a>&nbsp;");
		} else {
			str.append("&nbsp;下一页&nbsp;");
		}
		if (totalPages > 1 && currentPage != totalPages) {
			str.append("&nbsp;<a href=\"javascript:goOtherPage(" + totalPages
					+ ");\">末页</a>&nbsp;");
		} else {
			str.append("&nbsp;末页&nbsp;");
		}
		str.append("&nbsp;共" + totalPages + "页&nbsp;");
		str.append("&nbsp;转到&nbsp;");
		str.append("第<input id='pageIndex' name='pageIndex' size=2 maxlength=5>页<img style='cursor:hand' src='images/pagego.gif' onClick='gotoPage()'>");
		str.append("<form action='"
				+ path
				+ "' method='post' name=\"pageform\" id=\"pageform\"><input type=\"hidden\" name=\"page\" value=\"\"/></form>");
		str.append("<script type=\"text/javascript\">");
		str.append("function gotoPage() {");
		str.append("var pageIndex = document.getElementById(\"pageIndex\");");
		str.append("var pageIfTrue = pageIndex.value.search(\"[^0-9]\");");
		str.append("if (pageIndex.value ==''){");
		str.append("return;");
		str.append("}");
		str.append("if(pageIfTrue > -1){");
		str.append("alert(\"页数只能是数字！\");");
		str.append("return false;");
		str.append("}");
		str.append("if (pageIndex.value<1 || pageIndex.value >" + totalPages
				+ "){");
		str.append("alert(\"请输入合法页数！\");");
		str.append("return;");
		str.append("}");
		str.append("goOtherPage(pageIndex.value);");
		str.append("}");
		str.append("function goOtherPage(pageNum){");
		str.append("document.pageform.page.value=pageNum;");
		str.append("document.pageform.submit();");
		str.append("}");
		str.append("</script>");
		return str.toString();
	}

}

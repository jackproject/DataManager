package com.soho.model;

public class PageParam {

	// 总记录数量
	private Integer totalCount;

	// 一页的记录数量
	private Integer pageAmount;
	
	// 当前页面
	private Integer currentPage;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageAmount() {
		return pageAmount;
	}

	public void setPageAmount(Integer pageAmount) {
		this.pageAmount = pageAmount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return "PageParam [totalCount=" + totalCount + ", pageAmount="
				+ pageAmount + ", currentPage=" + currentPage + "]";
	}


}

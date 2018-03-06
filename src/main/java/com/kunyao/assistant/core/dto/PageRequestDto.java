package com.kunyao.assistant.core.dto;

public class PageRequestDto {

	private static final Integer DEFAULT_PAGESIZE = 12;

	private Integer currentPage;

	private Integer pageSize;

	public Integer getCurrentPage() {
		if (currentPage == null) {
			return 1;
		}
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			return DEFAULT_PAGESIZE;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}

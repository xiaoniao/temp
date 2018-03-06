package com.kunyao.assistant.core.entity;

import java.io.Serializable;

/**
 * 页码实体，只包含分页信息
 * 
 * @author GeNing
 * @since  2016.07.29
 * 
 */
public class PageInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5741003088887718458L;

	private Integer allRow;       // 总记录数
	
	private Integer totalPage;    // 总页数
	
	private Integer currentPage;  // 当前页
	
	private Integer pageSize;     // 每页记录数
	
	public PageInfo() {}
	
	public PageInfo(Integer allRow, Integer pageSize) {
		this.allRow = allRow;
		this.totalPage = countTotalPage(pageSize, allRow);
		this.pageSize = pageSize;
	}
	
	/**
	 * 计算总页数，静态方法，供外部直接通过类名调用 pageSize 每页记录数 allRow 总记录数
	 * @return 总页数
	 */
	public static int countTotalPage(final int pageSize, final int allRow) {
		int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow
				/ pageSize + 1;
		return totalPage;
	}
	
	/**
	 * 计算当前页第一条元素的下标
	 * pageSize 每页记录数 currentPage 当前第几页
	 * @return 当前页开始记录号
	 */
	public static int countOffset(final int pageSize, final int currentPage) {
		final int offset = pageSize * (currentPage - 1);
		return offset;
	}
	
	public Integer getAllRow() {
		return allRow;
	}
	public void setAllRow(Integer allRow) {
		this.allRow = allRow;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}

package com.kunyao.assistant.core.dto;

import com.kunyao.assistant.core.entity.PageData;

public class PageResult<T> extends JsonResult<PageData<T>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2357646036078720058L;

	public PageResult(String statusCode,  boolean success, String message, PageData<T> pageData) {
		// TODO Auto-generated constructor stub
		this.statusCode = statusCode;
		this.success = success;
		this.message = message;
		this.data = pageData;
	}
}

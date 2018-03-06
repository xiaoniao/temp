package com.kunyao.assistant.core.exception;

import com.kunyao.assistant.core.dto.ResultFactory;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	
	private String error;

	public ServiceException() {
        super();
    }
	
	public ServiceException(String error) {
		super(ResultFactory.getExceptionMsg(error));
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}

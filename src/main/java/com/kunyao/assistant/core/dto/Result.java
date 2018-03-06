package com.kunyao.assistant.core.dto;

import java.io.Serializable;

import com.kunyao.assistant.core.utils.JsonUtils;

/**
 * 结果集对象
 * @author GeNIng
 *
 */
public class Result implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6288374846131788743L;
    
    // 响应状态码
    protected String statusCode;
    
    // 是否操作成功
    protected boolean success;
    
    // 客户端返回消息
    protected String message;
    
    /**
     * 默认构造
     */
    public Result() {

    }
    
    public Result(String statusCode,  boolean success, String message) {
    	this.statusCode = statusCode;
    	this.success = success;
    	this.message = message;
    }
    
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toJson() {
		return JsonUtils.toJson(this);
	}
}

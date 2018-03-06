package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

public class OrderLog implements GenericModel{
	
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 2158963583348531141L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;
	
	private Integer orderId;
	
	private Date logTime;
	
	private String userName;
	
	private String logInfo;
	
	@ModelMeta(fieldType = ModelFieldType.VO)
	private String logDate;

	
	public OrderLog(){
		
	}
	
	public OrderLog(Integer orderId, String userName, String logInfo){
		this.orderId = orderId;
		this.userName = userName;
		this.logInfo = logInfo;
		this.logTime = new Date();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}


	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
	@Override
	public String getTablePrefixName() {
		return "o";
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = DateUtils.parseFullTime(logDate);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

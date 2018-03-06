package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

public enum AccountRecordEnum implements GenericEnum {

	CHARGE          (0, "充值", "CHARGE"), 
	SERVICE_COST    (1, "服务费", "SERVICE_COST"), 
	DELAY_COST      (2, "延时费", "DELAY_COST"), 
	REFUND          (3, "退款", "REFUND"),
	ORDER_COST		(4, "账单扣款", "ORDER_COST"),
	WITHDRAW		(5, "提现", "WITHDRAW");
	
	private int value;
	private String text;
	private String key;

	private AccountRecordEnum(int value, String text, String key) {
		this.value = value;
		this.text = text;
		this.key = key;
	}

	public int getValue() {
		return this.value;
	}

	public String getText() {
		return this.text;
	}
	
	public String getKey() {
		return key;
	}
}

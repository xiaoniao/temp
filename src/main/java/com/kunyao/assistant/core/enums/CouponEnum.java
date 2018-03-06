package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

/**
 * 优惠券状态
 */
public enum CouponEnum implements GenericEnum {

	UN_USER(0, "未使用"), 
	USERD(1, "已使用"), 
	OVER_DATE(2, "已失效[客户端展示]");

	private int value;
	private String text;

	private CouponEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return this.value;
	}

	public String getText() {
		return this.text;
	}
}

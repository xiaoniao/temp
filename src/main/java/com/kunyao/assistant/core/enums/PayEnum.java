package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

/**
 * 支付状态
 */
public enum PayEnum implements GenericEnum {

	UNPAY(0, "未支付"),
	FINISHPAY(1, "支付成功");

	private int value;
	private String text;

	private PayEnum(int value, String text) {
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
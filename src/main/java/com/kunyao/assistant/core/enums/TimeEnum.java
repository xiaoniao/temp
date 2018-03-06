package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

public enum TimeEnum implements GenericEnum {

	CAN_BOOK     (0, "可预约"),
	BOOKED       (1, "已预约"),
	CAN_NOT_BOOK (2, "请假");

	private int value;
	private String text;

	private TimeEnum(int value, String text) {
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

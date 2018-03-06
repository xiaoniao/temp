package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

/**
 * 发票类型
 */
public class InvoiceEnum {
	public enum Type implements GenericEnum {

		NORMAL(0, "普通发票"),
		VALUEADDED(1, "增值税发票");

		private int value;
		private String text;

		private Type(int value, String text) {
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

	public enum Status implements GenericEnum {

		WAIT_SEND(0, "待寄出 "),
		SENDED(1, "已寄出");

		private int value;
		private String text;

		private Status(int value, String text) {
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
}

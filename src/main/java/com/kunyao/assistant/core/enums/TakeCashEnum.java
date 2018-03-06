package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

/**
 * 提现
 */
public class TakeCashEnum {
	
	/**
	 * 提现方式
	 */
	public enum Method implements GenericEnum {

		WXPAY(0, "微信"),
		ALIPAY(1, "支付宝"),
		BANK(2, "银行卡");

		private int value;
		private String text;

		private Method(int value, String text) {
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
	
	/**
	 * 提现状态
	 */
	public enum Status implements GenericEnum {

		UNTAKE(0, "未提现"),
		FINISHTAKE(1, "提现成功");
		
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

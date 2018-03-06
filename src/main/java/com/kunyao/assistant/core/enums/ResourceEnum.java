package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

public class ResourceEnum {
	
	/**
	 * 审核状态 */
	public enum CheckStatus implements GenericEnum {

		WAIT        (0, "等待审核"),
		NO_PASS     (1, "未通过"), 
		PASS        (2, "通过");

		private int value;
		private String text;

		private CheckStatus(int value, String text) {
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

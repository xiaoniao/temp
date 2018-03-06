package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

public class MemberEnum {

	/**
	 *  身份证审核
	 */
	public enum idcardCheckPassStatus implements GenericEnum {
		
		NOT_AUDITED(0, "未审核"),
		AUDIT_PASS(1, "审核通过"),
		AUDIT_DOES_NOT_PASS(2, "审核不通过");

		private int value;
		private String text;

		private idcardCheckPassStatus(int value, String text) {
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

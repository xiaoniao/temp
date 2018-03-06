package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

public class CrossEnum {

	/**
	 *  预约状态
	 */
	public enum BookStatus implements GenericEnum {

		IDLE(0, "闲置"),
		SUBSCRIBE(1, "预约"),
		CLOSE(2, "无法被预约，例如金鹰请假");

		private int value;
		private String text;

		private BookStatus(int value, String text) {
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

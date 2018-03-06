package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

public class BaseEnum {
	
	/**
	 * 基础状态
	 * @author GeNing
	 * @since  2016.08.16
	 */
	public enum Status implements GenericEnum {
		
		BASE_STATUS_ENABLE  (1, "启用"),
		BASE_STATUS_DISABLED(0, "禁用");
	
		private int value;
		private String text;
		
		private Status(int value, String text) {
			this.value = value;
			this.text = text;
		}
		
		public int getValue() {
			// TODO Auto-generated method stub
			return this.value;
		}
		
		public String getText() {
			// TODO Auto-generated method stub
			return this.text;
		}
	}
}

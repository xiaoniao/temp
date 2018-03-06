package com.kunyao.assistant.core.enums;

import com.kunyao.assistant.core.generic.GenericEnum;

public class OrderEnum {

	/**
	 * 订单状态 */
	public enum Status implements GenericEnum {

		NEW              (0, "新建"),
		CANCEL           (1, "取消（用户未付款取消，不显示在订单中"), 
		WAIT_LOOP        (2, "待接单"),  // 支付成功，等待轮训
		CANCEL_AFTER_PAY (3, "取消（付款后取消）"),
		FAIL             (4, "预订失败（系统未选中服务） "), 
		WAIT_SERVICE     (5, "待服务"),
		SERVING          (6, "服务中"), // 1、用户确认行程之后变成服务中 2、线下金鹰输入编码后订单变为服务中
		WAIT_COMMENT     (7, "待评价"),
		FINISH           (8, "已完成"),
		EXCHANGE         (9, "订单交换"),
		CANCEL_BY_ADMIN  (10, "已取消，被管理员审核取消"),
		WAIT_CONFIRM     (11, "待确认行程"), // 1、金鹰提交行程给会员，等待会员待确认，确认之后成服务中 2、线下金鹰输入编码后订单变为服务中
		WAIT_SERVICE_CODE (12, "等待输入服务编码");

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
	
	/**
	 * 付款方式 */
	public enum ServicePayMethod implements GenericEnum {

		BALANCE        (0, "预存款支付"),
		WXPAY_JS       (1, "微信公众号支付"),
		WXPAY_APP      (2, "微信APP支付"),
		ALIPAY         (3, "支付宝支付"), 
		BANK           (4, "银行卡支付");

		private int value;
		private String text;

		private ServicePayMethod(int value, String text) {
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
	 * 联系方式  */
	public enum ContactMethod implements GenericEnum {

		CROSS          (0, "金鹰与我电话联系"),
		MEMBER         (1, "我有时间会主动电话联系金鹰"),
		ORTHER         (2, "用户自己填写");

		private int value;
		private String text;

		private ContactMethod(int value, String text) {
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
	 * 消费单付款方式  */
	public enum CostPayMethod implements GenericEnum {

		MEMBER         (0, "自己支付"),
		CROSS          (1, "金鹰代付[从余额中扣，如果没有余额金鹰会提示充值]");

		private int value;
		private String text;

		private CostPayMethod(int value, String text) {
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
	 * 订单操作对象
	 */
	public enum LogOperatUser {
		
		MEMBER(1, "会员"),
		CROSS(2, "金鹰"),
		MANAGER(3, "后台管理员"),
		SYSTEM(4, "系统");
		
		private int value;
		private String text;
		
		private LogOperatUser(int value, String text) {
			this.value = value;
			this.text  = text;
		}
		
		public int getValue() { return this.value; }
		
		public String getMText() { return text; }
	}
	
	public enum Travel implements GenericEnum {

		INIT          		(0, "等待确认，未开始"),// 金鹰添加行程
		CONFIRMED     		(1, "已确认"),	// 用户确认行程 或 订单处于服务中状态，添加当天之后行程
		SERVING		 		(2, "服务中"),	// 定时任务，当天改为服务中 或 订单处于服务中状态，添加当天新行程
		COST_WAIT_CONFIRM  	(3, "账单待确认"),// 金鹰点击今日下班
		FINISH        		(4, "服务完成"), // 用户确认账单
		REMOVE        		(5, "行程取消"); // 金鹰删除行程

		private int value;
		private String text;

		private Travel(int value, String text) {
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
	
	public enum Cost implements GenericEnum {

		INIT        (0, "初始化添加"),
		ADD         (1, "后续添加"),
		REMOVE      (2, "无效");

		private int value;
		private String text;

		private Cost(int value, String text) {
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
	
	public enum Doubt implements GenericEnum {
		
		INIT        (0, "未处理"),
		FINISH      (1, "已处理");
		
		private int value;
		private String text;
		
		private Doubt(int value, String text) {
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
	
public enum CancelType implements GenericEnum {
		
		CROSS        (0, "金鹰取消"),
		MEMBER      (1, "用户取消");
		
		private int value;
		private String text;
		
		private CancelType(int value, String text) {
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

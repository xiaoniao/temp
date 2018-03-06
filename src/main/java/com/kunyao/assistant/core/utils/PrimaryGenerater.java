package com.kunyao.assistant.core.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrimaryGenerater {

	private static final String SERIAL_NUMBER = "XXXXXX"; // 流水号格式

	private static final String PREFIX = "1";

	private static PrimaryGenerater primaryGenerater = new PrimaryGenerater();

	/** 项目启动和定时任务时会初始化此值 **/
	private volatile int currentOrderNum = 1;

	private PrimaryGenerater() {}

	/**
	 * 取得PrimaryGenerater的单例实现
	 */
	public static PrimaryGenerater getInstance() {
		return primaryGenerater;
	}

	/**
	 * 生成下一个订单编号
	 */
	public synchronized String generaterNextNumber() {
		String id = null;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		int count = SERIAL_NUMBER.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append("0");
		}
		DecimalFormat df = new DecimalFormat(sb.toString());
		id = PREFIX + formatter.format(date) + df.format(currentOrderNum++);
		return id;
	}

	/**
	 * 初始化订单数量
	 */
	public synchronized void initNum(int num) {
		currentOrderNum = num;
	}

	public static void main(String[] args) {
		System.out.println(PrimaryGenerater.getInstance().generaterNextNumber());
	}
}

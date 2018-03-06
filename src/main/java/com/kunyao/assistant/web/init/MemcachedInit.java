package com.kunyao.assistant.web.init;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kunyao.assistant.core.utils.PrimaryGenerater;
import com.kunyao.assistant.web.service.OrderService;

public class MemcachedInit {
	private static final Logger log = LoggerFactory.getLogger(MemcachedInit.class);

	@Resource
	private OrderService orderService;

	public void init() {
		// 初始化订单流水号
		int no = orderService.queryLastOrderNo();
		PrimaryGenerater.getInstance().initNum(no);
		log.info("初始化订单流水号成功，当前流水号为：" + no);
	}
}

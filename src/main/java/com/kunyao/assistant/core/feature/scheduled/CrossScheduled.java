package com.kunyao.assistant.core.feature.scheduled;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.utils.PrimaryGenerater;
import com.kunyao.assistant.web.service.CrossInfoService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.OrderTravelService;


public class CrossScheduled {
	private static final Logger log = LoggerFactory.getLogger(CrossScheduled.class);
	
	@Resource
	private CrossInfoService crossInfoService;
	
	@Resource
	private OrderService orderService;

	@Resource
	private OrderTravelService orderTravelService;
	
	/**
	 * 每月1号上午0点0分触发,工龄加一个月
	 */
	public void crossWorkAge() {
		CrossInfo crossinfo = new CrossInfo();
		try {
			List<CrossInfo> crossinfoList = crossInfoService.findList(null, null, crossinfo);
			for (CrossInfo cross : crossinfoList) {
				Integer workAge = cross.getWorkAge();
				workAge++;
				CrossInfo crossModel = new CrossInfo();
				crossModel.setId(cross.getId());
				crossModel.setWorkAge(workAge);
				crossInfoService.update(crossModel);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从0分钟开始，每5分钟触发一次
	 */
	public void submitOrder() {
		long time = System.currentTimeMillis();
		log.info("是否正在轮训订单:" + Constant.isLoop + " 开始轮训" + time);
		try {
			Constant.isLoop = true;
			orderService.loop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Constant.isLoop = false;
		log.info("结束轮训 耗时" + (System.currentTimeMillis() - time) + "'ms");
	}
	
	/**
	 * 每天 22:00:00 触发, 自动确认三天前账单, 结束前一天所有行程
	 */
	public void autoFinishTravel() {
		orderTravelService.updateAutoFinish();
	}
	
	/**
	 * 每天0点0分1秒点执行 生成订单编号
	 */
	public void orderNo() {
		Integer no = orderService.queryLastOrderNo();
		PrimaryGenerater.getInstance().initNum(no);
		log.info("每天0点0分1秒点执行 生成订单编号 no:" + no);
	}
	
	/**
	 * 金鹰是否下班
	 * 如晚上21:00金鹰仍未点击下班，推送用户是否延时，延时需支付延时费
	 */
	public void loopPayDelay() {
		try {
			orderTravelService.updateIsCrossOffWork();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 每天早晨6点 批量修改当天行程，把行程状态从'已确认'改为'服务中'
	 */
	/*public void travelService() {
		log.info("每天早晨6点 批量修改当天行程，把行程状态从'已确认'改为'服务中'");
		List<OrderTravel> list  = orderTravelService.queryConfirmedTravelListToday();
		log.info("orderTravel size: " + (list == null ? 0 : list.size()));
		if (list == null) {
			return;
		}
		for (OrderTravel orderTravel : list) {
			orderTravel.setStatus(Travel.SERVING.getValue());
			try {
				orderTravelService.update(orderTravel);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}*/
}

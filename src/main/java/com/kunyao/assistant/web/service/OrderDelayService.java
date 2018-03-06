package com.kunyao.assistant.web.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.OrderDelay;

public interface OrderDelayService extends GenericService<OrderDelay, Integer> {
	
	public OrderDelay createOrderDelay(Integer userId, Integer orderId, Integer hour, Integer payMethod);
	
	public Map<String, Object> payOrderDelay(HttpServletRequest request, OrderDelay orderDelay, String openId) throws ServiceException;
	
	public Map<String, Object> pay(HttpServletRequest request, Integer delayId, String openId) throws ServiceException;
	
	public OrderDelay finishPay(Integer delayId, String tradeNo);
	
	public OrderDelay findOrderDelayInfo(Integer delayId);

	public Map<String, Double> findDelayInfo(Integer userId) throws ServiceException;
}

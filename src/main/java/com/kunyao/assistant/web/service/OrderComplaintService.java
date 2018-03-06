package com.kunyao.assistant.web.service;

import org.apache.ibatis.annotations.Param;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.OrderComplaint;

public interface OrderComplaintService extends GenericService<OrderComplaint, Integer> {
	
	/**
	 * 添加投诉
	 * @param orderId
	 * @param userId
	 * @param remark
	 * @return
	 * @throws ServiceException
	 */
	OrderComplaint add(Integer userId, Integer orderId, String remark) throws ServiceException;
	
	
	
	OrderComplaint findOrderComplaintByOrderId(@Param("orderId") Integer orderId);
}

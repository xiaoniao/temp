package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.OrderCancel;

public interface OrderCancelService extends GenericService<OrderCancel, Integer> {
	
	OrderCancel add(Integer orderId, Integer userId, String remark, Integer type) throws ServiceException;
	
	OrderCancel findOrderCancelByOrderId(Integer orderId);

	/** 用户申请取消行程 */
	OrderCancel memberApplyCancel(Integer orderId, String remark) throws ServiceException ;

	OrderCancel crossApplyCancel(Integer orderId, Integer userId, String remark) throws ServiceException;
}

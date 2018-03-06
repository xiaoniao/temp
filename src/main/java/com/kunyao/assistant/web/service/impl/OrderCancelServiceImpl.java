package com.kunyao.assistant.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderCancel;
import com.kunyao.assistant.web.dao.OrderCancelMapper;
import com.kunyao.assistant.web.dao.OrderMapper;
import com.kunyao.assistant.web.service.OrderCancelService;

@Service
public class OrderCancelServiceImpl extends GenericServiceImpl<OrderCancel, Integer> implements OrderCancelService {
    @Resource
    private OrderCancelMapper orderCancelMapper;
    
    @Resource
    private OrderMapper orderMapper;

    public GenericDao<OrderCancel, Integer> getDao() {
        return orderCancelMapper;
    }

	@Override
	public OrderCancel add(Integer orderId, Integer userId, String remark, Integer type) throws ServiceException{
		OrderCancel orderCancel = new OrderCancel(orderId, userId, remark, type);
		orderCancelMapper.insert(orderCancel);
		return orderCancel;
	}

	@Override
	public OrderCancel findOrderCancelByOrderId(Integer orderId) {
		return orderCancelMapper.findOrderCancelByOrderId(orderId);
	}
	
	@Override
	public OrderCancel crossApplyCancel(Integer orderId, Integer userId, String remark) throws ServiceException {
		Order order = orderMapper.findOrderInfo(orderId);
		if (order == null) throw new ServiceException(ResultCode.NO_DATA);
		
		OrderCancel model = new OrderCancel();
		model.setOrderId(orderId);
		model.setUserId(userId);
		if (findOne(model) != null) throw new ServiceException(ResultCode.REPEATED_SUBMISSION);
		
		return add(orderId, userId, remark, OrderEnum.CancelType.CROSS.getValue());
	}

	@Override
	public OrderCancel memberApplyCancel(Integer orderId, String remark) throws ServiceException {
		Order order = orderMapper.findOrderInfo(orderId);
		if (order == null) throw new ServiceException(ResultCode.NO_DATA);
		
		Integer status = order.getStatus();
		if (status != OrderEnum.Status.WAIT_SERVICE.getValue() && status != OrderEnum.Status.SERVING.getValue() && status != OrderEnum.Status.WAIT_CONFIRM.getValue() && status != OrderEnum.Status.WAIT_SERVICE_CODE.getValue()) 
			throw new ServiceException(ResultCode.ORDER_STATUS_NEGATIVE);
		
		OrderCancel model = new OrderCancel();
		model.setOrderId(orderId);
		model.setUserId(order.getUserId());
		if (findOne(model) != null) throw new ServiceException(ResultCode.REPEATED_SUBMISSION);
		
		return add(orderId, order.getUserId(), remark, OrderEnum.CancelType.MEMBER.getValue());
	}
}

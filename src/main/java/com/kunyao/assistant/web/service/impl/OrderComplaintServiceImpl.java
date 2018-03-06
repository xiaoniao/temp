package com.kunyao.assistant.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.OrderComplaint;
import com.kunyao.assistant.web.dao.OrderComplaintMapper;
import com.kunyao.assistant.web.service.OrderComplaintService;

@Service
public class OrderComplaintServiceImpl extends GenericServiceImpl<OrderComplaint, Integer> implements OrderComplaintService {
    @Resource
    private OrderComplaintMapper orderComplaintMapper;

    public GenericDao<OrderComplaint, Integer> getDao() {
        return orderComplaintMapper;
    }

	@Override
	public OrderComplaint add(Integer userId, Integer orderId, String remark) throws ServiceException {
		OrderComplaint orderComplaint = new OrderComplaint(userId, orderId, remark);
		orderComplaintMapper.insert(orderComplaint);
		return orderComplaint;
	}

	@Override
	public OrderComplaint findOrderComplaintByOrderId(Integer orderId) {
		return orderComplaintMapper.findOrderComplaintByOrderId(orderId);
	}
}

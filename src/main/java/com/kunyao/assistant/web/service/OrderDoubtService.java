package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.OrderDoubt;

public interface OrderDoubtService extends GenericService<OrderDoubt, Integer>{

	List<OrderDoubt> findDoubtBytime(OrderDoubt orderDoubt);
	
	int placeOrder(Integer orderId, String date);
	
	void createDoubt(Integer userId, Integer orderId, String date) throws ServiceException;
}

package com.kunyao.assistant.web.service;

import java.util.Map;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Account;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderDelay;

public interface AccountService extends GenericService<Account, Integer> {
	
	public void order(Order order) throws ServiceException;
	
	public void updatePayDelay(OrderDelay orderDelay) throws ServiceException;

	public void freeze(Integer userId, Double money) throws ServiceException;
	
	public Map<String, Double> balance(Integer userId) throws ServiceException;
	
	public void updateRefund(Order order) throws ServiceException;
}

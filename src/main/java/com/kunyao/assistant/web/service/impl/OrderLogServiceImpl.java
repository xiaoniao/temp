package com.kunyao.assistant.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.OrderLog;
import com.kunyao.assistant.web.dao.OrderLogMapper;
import com.kunyao.assistant.web.service.OrderLogService;

@Service
public class OrderLogServiceImpl extends GenericServiceImpl<OrderLog, Integer> implements OrderLogService {
	
	@Resource
	private OrderLogMapper orderLogMapper;

	@Override
	public GenericDao<OrderLog, Integer> getDao() {
		return orderLogMapper;
	}

}

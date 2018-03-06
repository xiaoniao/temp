package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.OrderConfig;

import java.lang.Integer;

public interface OrderConfigService extends GenericService<OrderConfig, Integer> {
	
	/**
	 * 查询系统设置数据
	 * @return
	 */
	OrderConfig selectOrderConfig();
	
	boolean updateLooptime(Integer loopTime);
}

package com.kunyao.assistant.web.dao;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.OrderConfig;

import java.lang.Integer;

import org.apache.ibatis.annotations.Select;

public interface OrderConfigMapper extends GenericDao<OrderConfig, Integer> {

	final String FIND_ORDER_SERVICE_FEE = "SELECT * FROM kycom_o_order_config";

	@Select(FIND_ORDER_SERVICE_FEE)
	OrderConfig findOrderServiceFee();
}

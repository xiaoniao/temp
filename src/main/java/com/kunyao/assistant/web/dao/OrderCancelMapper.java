package com.kunyao.assistant.web.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.OrderCancel;

public interface OrderCancelMapper extends GenericDao<OrderCancel, Integer> {
	
	@Select("select * from kycom_o_order_cancel where order_id = #{orderId} order by create_time desc")
	OrderCancel findOrderCancelByOrderId(@Param("orderId") Integer orderId);
}

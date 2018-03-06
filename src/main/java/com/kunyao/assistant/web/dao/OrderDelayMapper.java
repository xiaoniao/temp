package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.OrderDelay;

public interface OrderDelayMapper extends GenericDao<OrderDelay, Integer> {
	
	final String FIND_ORDER_DELAY_INFO = "SELECT * FORM kycom_o_order_delay WHERE id = #{id}";
	
	final String FIND_LIST_BY_ORDER_ID_AND_DATE = "SELECT * FROM kycom_o_order_delay WHERE order_id = #{orderId} AND DATE_FORMAT(create_time, '%x/%m/%d') = #{date}";
	
	@Select(FIND_ORDER_DELAY_INFO)
	OrderDelay findOrderDelayInfo(@Param("id") Integer id);

	@Select(FIND_LIST_BY_ORDER_ID_AND_DATE)
	List<OrderDelay> findListByOrderIdAndDate(@Param("orderId") Integer orderId, @Param("date") String date);
}

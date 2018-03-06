package com.kunyao.assistant.web.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.OrderComplaint;

public interface OrderComplaintMapper extends GenericDao<OrderComplaint, Integer> {
	@Select("select * from kycom_o_order_complaint where order_id = #{orderId} order by create_time desc")
	OrderComplaint findOrderComplaintByOrderId(@Param("orderId") Integer orderId);
}

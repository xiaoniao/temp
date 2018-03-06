package com.kunyao.assistant.web.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;

public interface DataCheckMapper extends GenericDao<Void, Integer> {

	// 根据行程单id查询金鹰userid
	@Select("    SELECT                                                   " +
			"    	cross_user_id                                         " +
			"    FROM                                                     " +
			"    	kycom_o_order o                                       " +
			"    LEFT JOIN kycom_o_order_travel t ON t.order_id = o.id    " +
			"    WHERE                                                    " +
			"    	t.id = #{travelId};                                   ")
	Integer findCrossUserByOrderTravel(@Param("travelId") Integer travelId);
	
	// 根据账单id查询金鹰userid
	@Select("    SELECT                                                        " +
			"    	cross_user_id                                              " +
			"    FROM                                                          " +
			"    	kycom_o_order o                                            " +
			"    LEFT JOIN kycom_o_order_travel t ON t.order_id = o.id         " +
			"    LEFT JOIN kycom_o_order_cost c ON c.order_travel_id = t.id    " +
			"    WHERE                                                         " +
			"    	c.id = #{costId}                                           ")
	Integer findCrossUserByOrderCost(@Param("costId") Integer costId);
	
	// 根据订单id查询金鹰userid
	@Select("SELECT cross_user_id FROM kycom_o_order WHERE id = #{orderId}")
	Integer findCrossUserByOrder(@Param("orderId") Integer orderId);
}

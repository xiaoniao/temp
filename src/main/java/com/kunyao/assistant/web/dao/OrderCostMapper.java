package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.OrderCost;

public interface OrderCostMapper extends GenericDao<OrderCost, Integer> {
	
	final String FIND_COST_INFO = 
					"    SELECT                       " +
					"    	c.*, m.cost_name,         " +
					"    	t.title                   " +
					"    FROM                         " +
					"    	kycom_o_order_cost c,     " +
					"    	kycom_o_cost_item m,      " +
					"    	kycom_o_order_travel t    " +
					"    WHERE                        " +
					"    	c.order_travel_id = t.id  " +
					"    AND c.cost_item_id = m.id    " +
					"    AND c.id = #{id}             ";
	
	final String QUERY_ORDER_COST_LIST = 
					"    SELECT                                  " +
					"    	travel.id AS orderTravelId,          " +
					"    	travel.title,                        " +
					"    	travel.start_time,                   " +
					"    	cost.id AS id,                       " +
					"    	item.cost_name,                      " +
					"    	cost.remark,                         " +
					"    	cost.money                           " +
					"    FROM                                    " +
					"    	kycom_o_order_cost cost,             " +
					"    	kycom_o_cost_item item,              " +
					"    	kycom_o_order_travel travel          " +
					"    WHERE                                   " +
					"    	cost.order_travel_id IN(             " +
					"    		SELECT                           " +
					"    			travel.id                    " +
					"    		FROM                             " +
					"    			kycom_o_order_travel travel  " +
					"    		WHERE                            " +
					"    			travel.order_id = #{orderId} " +
					"    	)                                    " +
					"    AND cost.cost_item_id = item.id         " +
					"    AND cost.`status` != #{status}          " +
					"    AND travel.id = cost.order_travel_id    " +
					"    ORDER BY                                " +
					"    	travel.start_time                    ";
	
	final String QUERY_TRAVEL_COST_LIST =
					"    SELECT                             " +
					"    	*                               " +
					"    FROM                               " +
					"    	kycom_o_order_cost              " +
					"    WHERE                              " +
					"    	order_travel_id = #{travelId}   " +
					"    AND STATUS != #{status}            ";

	final String FIND_COST_LIST_BY_TRAVEL_ID = 
			 "SELECT "
			+"	cost.id id, "
			+"	cost.order_travel_id orderTravelId, "
			+ "	cost.cost_item_id costItemId, "
			+ "	cost.money money, "
			+ "	cost.remark remark, "
			+ "	cost.status status, "
			+ "	item.cost_name costName, "
			+ "	item.icon costIcon "
			+"FROM kycom_o_order_cost AS cost "
			+"LEFT JOIN kycom_o_cost_item AS item ON cost.cost_item_id = item.id "
			+"WHERE cost.order_travel_id = #{travelId} AND cost.status != ${status}";
	
	final String QUERY_ORDER_COST_LIST_BY_ORDER_ID = 
			"    SELECT                                  " +
			"    	travel.id AS orderTravelId,          " +
			"    	travel.title,                        " +
			"    	travel.start_time,                   " +
			"    	travel.end_time,                     " +
			"    	travel.order_id as orderId,          " +
			"    	cost.id AS id,                       " +
			"    	item.cost_name,                      " +
			"    	cost.remark,                         " +
			"    	cost.money, 						 " +
			"    	cost.cost_item_id, 				     " +
			"    	cost.status, 				         " +
			"		travel.status AS travelStatus        " +
			"    FROM                                    " +
			"    	kycom_o_order_cost cost,             " +
			"    	kycom_o_cost_item item,              " +
			"    	kycom_o_order_travel travel          " +
			"    WHERE                                   " +
			"    	cost.order_travel_id IN(             " +
			"    		SELECT                           " +
			"    			travel.id                    " +
			"    		FROM                             " +
			"    			kycom_o_order_travel travel  " +
			"    		WHERE                            " +
			"    			travel.order_id = #{orderId} " +
			"    	)                                    " +
			"    AND cost.cost_item_id = item.id         " +
			"    AND cost.`status` != #{status}          " +
			"    AND travel.id = cost.order_travel_id    " +
			"    ORDER BY                                " +
			"    	travel.start_time                    " ;
	
	
	final String QUERY_MONEY_LIST_BY_ORDER_ID = 
	
			 "  SELECT                                 		       " +                                                                           
			 "     cost.money                         		       " +
			 "  FROM                                    		   " +     
			 "     kycom_o_order_cost cost,             		   " +              
			 "     kycom_o_order_travel travel           		   " +
			 "  WHERE                                    		   " +
			 "     cost.order_travel_id IN(              		   " +
			 "        SELECT                            		   " +
			 "	          travel.id                     		   " +
			 "        FROM                               		   " +
			 "            kycom_o_order_travel travel    		   " +
			 "        WHERE                              		   " +
			 "            travel.order_id = #{orderId}   		   " +
			 "            and cost.`status` != 2                   " +
			 "            and travel.start_time >= #{startDate}    " +
			 "            and travel.end_time < #{endDate}         " +
			 "      )                                              " +
			 "      AND travel.id = cost.order_travel_id           " ;
	
	// 账单详情
	@Select(FIND_COST_INFO)
	OrderCost findInfo(@Param("id") Integer id);
		
	// 查询订单下账单列表
	@Select(QUERY_ORDER_COST_LIST)
	List<OrderCost> queryOrderCostList(@Param("orderId") Integer orderId, @Param("status") Integer status);
	
	// 查询行程单下账单列表
	@Select(QUERY_TRAVEL_COST_LIST)
	List<OrderCost> queryTravelCostList(@Param("travelId") Integer travelId, @Param("status") Integer status);
	
	@Select(FIND_COST_LIST_BY_TRAVEL_ID)
	List<OrderCost> findCostListByTravelId(@Param("travelId") Integer travelId, @Param("status") Integer status);
	
	// 根据账单id查询用户id
	@Select("    SELECT                                                        " +
			"    	user_id                                                    " +
			"    FROM                                                          " +
			"    	kycom_o_order o                                            " +
			"    LEFT JOIN kycom_o_order_travel t ON t.order_id = o.id         " +
			"    LEFT JOIN kycom_o_order_cost c ON c.order_travel_id = t.id    " +
			"    WHERE                                                         " +
			"    	c.id = #{costId}                                           ")
	Integer findUserByOrderCost(@Param("costId") Integer costId);
	
	/**
	 * 根据订单id查询行程数据
	 * @param orderId
	 * @return
	 */
	@Select(QUERY_ORDER_COST_LIST_BY_ORDER_ID)
	List<OrderCost> queryOrderCostListByOrderId(@Param("orderId") Integer orderId, @Param("status") Integer status);
	
	/**
	 * 根据订单id和日期查询当天行程所有价格
	 * @param orderId
	 * @return
	 */
	@Select(QUERY_MONEY_LIST_BY_ORDER_ID)
	List<Double> queryMoneyListByOrderId(@Param("orderId") Integer orderId, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	@Select("    SELECT                                                        " +
			"    	money                                                      " +
			"    FROM                                                          " +
			"    	kycom_o_order_cost                                         " +
			"    WHERE                                                         " +
			"    	order_travel_id = #{travelId}                              " +
			"    	and status != 2                                             ")
	List<Double> queryMoneyListByTravelId(@Param("travelId") Integer travelId);
	
	List<OrderCost> selectOrderCostList(@Param("startpos") Integer startpos, @Param("pagesize") Integer pagesize, @Param("orderCost") OrderCost orderCost);
	
	int selectOrderCostListCount(@Param("orderCost") OrderCost orderCost);
}

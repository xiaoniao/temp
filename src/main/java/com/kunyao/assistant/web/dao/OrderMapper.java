package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderCost;

public interface OrderMapper extends GenericDao<Order, Integer> {
	
	final String QUERY_ORDER_TODAY = 
			"	SELECT								"
			+ "		*								"
			+ "	FROM								"
			+ "		kycom_o_order					"
			+ "	WHERE								"
			+ "		start_time <= #{todayTime}		"
			+ "	AND									"
			+ "		end_time >= #{todayTime}		"
			+ "	AND "
			+ "		status IN (5,6,11,12)";
	
	final String FIND_ORDER_LIST_BY_MEMBERID = 
			"	SELECT *						"
			+ "	FROM 							"
			+ "		kycom_o_order				"
			+ "	WHERE							"
			+ "		user_id = #{memberUserId}			"
			+ " AND								"
			+ "		status != #{status}			";
	
	final String FIND_ORDER_INFO = "SELECT * FROM kycom_o_order where id = #{orderId}";
	
	final String FIND_ORDER_BY_ORDERCARD = 
			"    SELECT                        " +
			"    	*                          " +
			"    FROM                          " +
			"    	kycom_o_order              " +
			"    WHERE                         " +
			"    	order_card = #{orderCard}  " +
			"    LIMIT 1                       ";

	final String FIND_ORDER_DETAIL = 
			  "SELECT "
			+ "	oo.*, "
			+ "	city.name AS cityName, "
			+ "	ci.id AS crossId,"
			+ "	ci.pic AS crossPic,		"
			+ "	ci.cross_number AS crossNumber,	"
			+ "	ci.work_name AS workName,	"
			+ "	ci.user_id AS crossUserId,	"
			+ "	ci.contact_phone AS crossContactPhone "
			+ "FROM "
			+ "	kycom_o_order AS oo "
			+ "LEFT JOIN "
			+ "	kycom_t_city AS city ON oo.city_id = city.id "
			+ "LEFT JOIN "
			+ "	kycom_u_cross_info AS ci ON ci.user_id = oo.cross_user_id "
			+ "where "
			+ "	oo.id = #{orderId}";
	
	final String FIND_ORDER_COUNT_BY_MEMBER_USER_ID =
			"		SELECT COUNT(*) FROM kycom_o_order as oo							"+
			"		LEFT JOIN															"+
			"			kycom_u_cross_info AS ci ON ci.user_id = oo.cross_user_id		"+
			"		LEFT JOIN															"+
			"			kycom_t_city AS city ON city.id = oo.city_id					"+
			" 		WHERE oo.user_id = #{userId} 											"+
			"		AND 																"+
			"			oo.status NOT IN (0,1,9)										";
	
	final String FIND_ORDER_BY_CROSS_USER_ID = 

								"        select count(0)                                      "+
								"        FROM                                                 "+
								"		   kycom_o_order as oo                                "+
								"        LEFT JOIN                                            "+
								"		   kycom_u_user as uu ON oo.cross_user_id = uu.id     "+
								"        WHERE                                                "+
								"		   oo.`status`= 5                                     "+
								"		   and	uu.id = #{crossUserId}                        ";
 
	final String FIND_ORDER_LIST_BY_MEMBER_USER_ID =
			"		SELECT 																"+
			"			oo.*,															"+
			"			city.name AS cityName,											"+
			"			ci.id AS crossId,												"+
			"			ci.pic AS crossPic,												"+
			"			ci.cross_number AS crossNumber,									"+
			"			ci.work_name AS workName,										"+
			"			ci.user_id AS crossUserId,										"+
			"			ci.contact_phone AS crossContactPhone							"+
			"		FROM																"+
			"			kycom_o_order AS oo 											"+
			"		LEFT JOIN															"+
			"			kycom_u_cross_info AS ci ON ci.user_id = oo.cross_user_id		"+
			"		LEFT JOIN															"+
			"			kycom_t_city AS city ON city.id = oo.city_id					"+
			"		WHERE																"+
			"			oo.user_id = #{userId}											"+
			"		AND 																"+
			"			oo.status NOT IN (0,1,9)										"+
			"		ORDER BY oo.create_time DESC										"+
			"		LIMIT #{startPos}, #{pageSize}										";
	
	final String FIND_ORDER_LIST_FOR_TRAVEL =
			"		SELECT 																"+
			"			oo.*,															"+
			"			city.name AS cityName,											"+
			"			ci.id AS crossId,												"+
			"			ci.pic AS crossPic,												"+
			"			ci.cross_number AS crossNumber,									"+
			"			ci.work_name AS workName,										"+
			"			ci.user_id AS crossUserId,										"+
			"			ci.contact_phone AS crossContactPhone,							"+
			"			ci.banners AS crossBanners										"+
			"		FROM																"+
			"			kycom_o_order AS oo 											"+
			"		LEFT JOIN															"+
			"			kycom_u_cross_info AS ci ON ci.user_id = oo.cross_user_id		"+
			"		LEFT JOIN															"+
			"			kycom_t_city AS city ON city.id = oo.city_id					"+
			"		WHERE																"+
			"			oo.user_id = #{userId}											"+
			"		AND 																"+
			"			oo.status IN (2,5,6,7,11,12)											"+
			"		LIMIT #{startPos}, #{pageSize}										";
	
	
	final String FIND_ORDER_REQUIRE =
								"    SELECT                                      " +
								"    	o.id,                                    " +
								"    	o.travel_desc_id,                        " +
								"    	t.demand,                                " +
								"    	o.travel_require_text,                   " +
								"    	o.travel_require_voice                   " +
								"    FROM                                        " +
								"    	kycom_o_order o                          " +
								"    LEFT JOIN(                                  " +
								"    	SELECT                                   " +
								"    		travel.id,                           " +
								"    		group_concat(travel.demand) AS demand" +
								"    	FROM                                     " +
								"    		kycom_o_travel_desc travel           " +
								"    )AS t ON t.id IN(o.travel_desc_id)          " +
								"    WHERE                                       " +
								"    	o.id = #{orderId}                        ";

	final String FIND_ORDER_BY_CROSS_COUNT = 
								"    SELECT count(*)                                                                 " +
								"    FROM kycom_o_order o                                                            " +
								"                                                                                    " +
								"    LEFT JOIN kycom_u_member_info m                                                 " +
								"        ON m.user_id = o.user_id                                                    " +
								"                                                                                    " +
								"    LEFT JOIN kycom_a_account ac                                                    " +
								"        ON ac.user_id = o.user_id                                                   " +
								"                                                                                    " +
								"    WHERE o.cross_user_id = #{crossUserId} and o.status in (5,6,7,11,12)            " +
								"    ORDER BY o.start_time asc LIMIT #{startPos}, #{pageSize}                        ";
	
	final String FIND_ORDER_BY_CROSS = 
								"    SELECT o.id,                                                                    " +
								"             o.status,                                                              " +
								"             o.user_id,                                                             " +
								"             o.assign_time,                                                         " +
								"             o.start_time,                                                          " +
								"             o.end_time,                                                            " +
								"             o.contact_method,                                                      " +
								"             o.cost_pay_method,                                                     " +
								"             o.invoice_title,                                                       " +
								"             m.name,                                                                " +
								"             m.header,                                                              " +
								"             m.sex,                                                                 " +
								"             ac.balance                                                             " +
								"    FROM kycom_o_order o                                                            " +
								"                                                                                    " +
								"    LEFT JOIN kycom_u_member_info m                                                 " +
								"        ON m.user_id = o.user_id                                                    " +
								"                                                                                    " +
								"    LEFT JOIN kycom_a_account ac                                                    " +
								"        ON ac.user_id = o.user_id                                                   " +
								"                                                                                    " +
								"    WHERE o.cross_user_id = #{crossUserId} AND o.status in (5,6,7,11,12)            " +
								"    ORDER BY o.start_time asc LIMIT #{startPos}, #{pageSize}                        ";
	
	final String FIND_ORDER_BY_ID = 
								"    SELECT o.id,                                                                    " +
								"             o.status,                                                              " +
								"             o.user_id,                                                             " +
								"             o.assign_time,                                                         " +
								"             o.start_time,                                                          " +
								"             o.end_time,                                                            " +
								"             o.contact_method,                                                      " +
								"             o.cost_pay_method,                                                     " +
								"             o.invoice_title,                                                       " +
								"             m.name,                                                                " +
								"             m.header,                                                              " +
								"             ac.balance                                                             " +
								"    FROM kycom_o_order o                                                            " +
								"                                                                                    " +
								"    LEFT JOIN kycom_u_member_info m                                                 " +
								"        ON m.user_id = o.user_id                                                    " +
								"                                                                                    " +
								"    LEFT JOIN kycom_a_account ac                                                    " +
								"        ON ac.user_id = o.user_id                                                   " +
								"                                                                                    " +
								"    WHERE o.id = #{orderId}                                                         ";
	
	// 根据订单状态查询所有订单
	final String FIND_ALL_BY_STATUS =
								"    SELECT                  " +
								"    	*                    " +
								"    FROM                    " +
								"	     kycom_o_order       " +
								"    WHERE                   " +
								"	    `status` = #{status} " + 
								"    ORDER BY                " + 
								"        start_time          ";
	
	final String FIND_CROSS_HISTORY_ORDER_COUNT = 
								"    SELECT                                                      " +
								"    	 count(*)                                                " +
								"    FROM                                                        " +
								"    	kycom_o_order o                                          " +
								"    LEFT JOIN kycom_u_member_info m ON m.user_id = o.user_id    " +
								"    WHERE                                                       " +
								"    	o.cross_user_id = #{crossUserId}                         " +
								"       AND o.status >= #{orderStatus}                           ";
	
	final String FIND_CROSS_HISTORY_ORDER = 
								"    SELECT                                                      " +
								"    	o.id,                                                    " +
								"    	o.`status`,                                              " +
								"    	o.start_time,                                            " +
								"    	o.end_time,                                              " +
								"    	o.user_id,                                               " +
								"    	o.cost_pay_method,                                       " +
								"    	m.`name`,                                                " +
								"    	m.sex,                                                   " +
								"    	m.header                                                 " +
								"    FROM                                                        " +
								"    	kycom_o_order o                                          " +
								"    LEFT JOIN kycom_u_member_info m ON m.user_id = o.user_id    " +
								"    WHERE                                                       " +
								"    	o.cross_user_id = #{crossUserId}                         " +
								"       AND o.status >= #{orderStatus}                           " +
								"    ORDER BY o.id DESC                                          " +
								"    LIMIT #{startPos}, #{pageSize}                              ";
	
	final String FIND_CROSS_ORDER_INFO =
								"    SELECT                                                      " +
								"    	o.id,                                                    " +
								"    	o.`status`,                                              " +
								"    	o.order_card,                                            " +
								"    	o.start_time,                                            " +
								"    	o.end_time,                                              " +
								"    	o.user_id,                                               " +
								"    	m.header,                                                " +
								"    	m.`name`,                                                " +
								"    	m.sex,                                                   " +
								"    	m.bank_mobile,                                           " +
								"    	o.contact_method,                                        " +
								"    	o.pay_method,                                            " +
								"    	o.invoice_address,                                       " +
								"    	o.invoice_mobile,                                        " +
								"    	o.invoice_people,                                        " +
								"    	o.invoice_title                                          " +
								"    FROM                                                        " +
								"    	kycom_o_order o                                          " +
								"    LEFT JOIN kycom_u_member_info m ON m.user_id = o.user_id    " +
								"    WHERE                                                       " +
								"    	o.id = #{orderId}                                        ";
	
	final String FIND_LAST_ONE =
								"    SELECT                           " +
								"    	*                             " +
								"    FROM                             " +
								"    	kycom_o_order                 " +
								"    WHERE                            " +
								"    	create_time >= #{startTime}   " +
								"    AND create_time <= #{endTime}    " +
								"    ORDER BY                         " +
								"    	id DESC                       " +
								"    LIMIT 1;                         ";
	
	final String FIND_ORDER_COST_LIST =
								"    SELECT                                                                      " +
								"    	cost.id,                                                                 " +
								"    	travel.id AS order_travel_id,                                            " +
								"    	travel.order_id,                                                         " +
								"    	IFNULL(cost.money, 0) AS money                                           " +
								"    FROM                                                                        " +
								"    	kycom_o_order_travel AS travel                                           " +
								"    LEFT JOIN kycom_o_order_cost AS cost ON cost.order_travel_id = travel.id    " +
								"    WHERE                                                                       " +
								"    	travel.order_id = #{orderId}                                             " +
								"    AND travel.`status` != #{travelStatus}                                      " +
								"    AND cost. STATUS != #{costStatus};                                          ";
	
	final String FIND_COUNT_NOT_FINISH = "SELECT COUNT(*) FROM kycom_o_order WHERE user_id = #{userId} AND status IN (2,5,6,7,11,12)";

	final String FIND_ORDER_LIST_NOT_FINISHED = "SELECT id, user_id, cross_user_id, status FROM kycom_o_order WHERE user_id = #{userId} AND status IN (2,5,6,7,11,12)";

	final String FIND_LIST_TRAVEL_TODAY_SERVING = 
			"	SELECT DISTINCT(oo.id), oo.*	"
			+ "	FROM kycom_o_order AS oo	"
			+ "	LEFT JOIN kycom_o_order_travel AS ot ON oo.id = ot.order_id	"
			+ "	WHERE DATE_FORMAT(ot.start_time,'%x/%m/%d') = #{date}";
	
	@Select(FIND_ORDER_INFO)
	Order findOrderInfo(@Param("orderId") Integer orderId);
	
	@Select(FIND_ORDER_BY_ORDERCARD)
	Order findOrderByOrderCard(@Param("orderCard") String orderCard);
	
	@Select(FIND_ORDER_REQUIRE)
	Order findOrderRequire(@Param("orderId") Integer orderId);
	
	@Select(FIND_ORDER_BY_CROSS_COUNT)
	Integer findByCrossCount(@Param("crossUserId") Integer crossUserId, @Param("orderStatus") Integer orderStatus, @Param("endOrderStatus") Integer endOrderStatus,
			@Param("travelStatus") Integer travelStatus, @Param("costStatus") Integer costStatus, @Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	@Select(FIND_ORDER_BY_CROSS)
	List<Order> findByCross(@Param("crossUserId") Integer crossUserId, @Param("orderStatus") Integer orderStatus, @Param("endOrderStatus") Integer endOrderStatus, 
			@Param("travelStatus") Integer travelStatus, @Param("costStatus") Integer costStatus, @Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	@Select(FIND_ORDER_BY_ID)
	Order findByOrderId(@Param("orderId") Integer orderId);
	
	@Select(FIND_ORDER_COST_LIST)
	List<OrderCost> findOrderCostList(@Param("orderId") Integer orderId, @Param("travelStatus") Integer travelStatus, @Param("costStatus") Integer costStatus);
	
	@Select(FIND_ORDER_COUNT_BY_MEMBER_USER_ID)
	int findOrderCountByMemberUserId(@Param("userId") Integer userId);
	
	/**
	 * 根据金鹰用户id查询有无服务中的订单
	 * @param crossUserId
	 * @return
	 */
	@Select(FIND_ORDER_BY_CROSS_USER_ID)
	int findOrderByCrossUserId(@Param("crossUserId") Integer crossUserId);
	
	/**
	 * 用户查询订单
	 * @param userId
	 * @return
	 */
	@Select(FIND_ORDER_LIST_BY_MEMBER_USER_ID)
	List<Order> findOrderListByMemberUserId(@Param("userId") Integer userId, @Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	/**
	 * 用户查询行程单
	 * (待接单,服务中,待评价,待输入服务编码)
	 */
	@Select(FIND_ORDER_LIST_FOR_TRAVEL)
	List<Order> findOrderListForTravel(@Param("userId") Integer userId, @Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	/**
	 * 后台查询订单列表
	 * @param order
	 * @return
	 */
	List<Order> selectOrderList(@Param("startpos") Integer startpos, @Param("pagesize") Integer pagesize, @Param("order") Order order);
	
	/**
	 * 后台查询订单列表数量
	 * @param order
	 * @return
	 */
	int selectOrderCount(@Param("order") Order order);
	
	/**
	 * 查询某天的所有订单
	 */
	@Select(QUERY_ORDER_TODAY)
	List<Order> queryOrderToday(@Param("todayTime") String todayTime);
	
	/** 查询用户订单*/
	@Select(FIND_ORDER_LIST_BY_MEMBERID)
	List<Order> findOrderListByMemberId(@Param("memberUserId") Integer memberUserId, @Param("status") Integer status);
	
	/**
	 * 查询指定状态下所有订单
	 */
	@Select(FIND_ALL_BY_STATUS)
	List<Order> findAllByStatus(@Param("status") Integer status);
	
	/**
	 * 查询会员消费天数
	 */
	@Select(" SELECT                 " +
			"  id,                   " +
			"  start_time,           " +
			"  end_time              " +
			" FROM                   " +
			"	kycom_o_order        " +
			" WHERE                  " +
			"	user_id = #{userId}  " +
			" AND STATUS = #{status} ")
	List<Order> findOrderDays(@Param("userId") Integer userId, @Param("status") Integer status);
	
	@Select(FIND_ORDER_DETAIL)
	Order findOrderDetail(@Param("orderId") Integer orderId);

	@Select(FIND_COUNT_NOT_FINISH)
	int findCountNotFinish(@Param("userId") Integer userId);
	
	@Select(FIND_CROSS_HISTORY_ORDER_COUNT)
	Integer findCrossCountHistoryOrder(@Param("crossUserId") Integer crossUserId, @Param("orderStatus") Integer orderStatus);
	
	@Select(FIND_CROSS_HISTORY_ORDER)
	List<Order> findCrossHistoryOrder(@Param("crossUserId") Integer crossUserId, @Param("orderStatus") Integer orderStatus, 
			@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	@Select(FIND_CROSS_ORDER_INFO)
	Order findCrossOrderInfo(@Param("orderId") Integer orderId);
	
	@Select(FIND_LAST_ONE)
	Order findLastOne(@Param("startTime") String startTime, @Param("endTime") String endTime);

	@Select(FIND_ORDER_LIST_NOT_FINISHED)
	List<Order> findOrderListNotFinished(@Param("userId") Integer userId);
	
	@Select("SELECT * FROM kycom_o_order WHERE id = #{orderId}")
	Order findOrderById(@Param("orderId") Integer orderId);

	/**
	 * 查询未完成订单(行程页)
	 * @param userId
	 * @return
	 */
	@Select("SELECT COUNT(*) FROM kycom_o_order WHERE user_id = #{userId} AND status IN (2,5,6,11,12)")
	int findOrderCountServing(@Param("userId") Integer userId);

	@Select(FIND_LIST_TRAVEL_TODAY_SERVING)
	List<Order> findListTravelServing(@Param("date") String date);
}

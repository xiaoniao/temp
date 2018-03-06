package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.OrderTravel;

public interface OrderTravelMapper extends GenericDao<OrderTravel, Integer> {
	
	final String FIND_TRAVEL_INFO = 
					"   SELECT               " +
					"   	*                " +
					"   FROM                 " +
					"   kycom_o_order_travel " +
					"   WHERE                " +
					"   id = #{id}           ";
	
	final String QUERY_TRAVEL_LIST = 
					"	SELECT    " +
					"		*                      " +
					"	FROM                       " +
					"		kycom_o_order_travel   " +
					"	WHERE                      " +
					"		order_id = #{orderId}  " +
					"	AND `status` != #{status}  " +
					"	ORDER BY                   " +
					"		start_time             ";

	final String QUERY_TRAVEL_LIST_TODAY =
					"    SELECT                                     " +
					"    	*                                       " +
					"    FROM                                       " +
					"    	kycom_o_order_travel                    " +
					"    WHERE                                      " +
					"    	order_id = #{orderId}                   " +
					"    AND `status` != #{status}                  " +
					"    AND start_time >= #{startDate}             " +
					"    AND start_time <= #{endDate}               " +
					"    ORDER BY                                   " +
					"    	start_time                              " ;
	
	final String QUERY_WAIT_SERVICE_TRAVEL_COUNT=
					"    SELECT                      " +
					"    	count(*)                 " +
					"    FROM                        " +
					"    	kycom_o_order_travel     " +
					"    WHERE                       " +
					"    	order_id = #{orderId}    " +
					"    AND STATUS  != #{statusFinish} AND STATUS != #{statusRemove} ";
	
	final String QUERY_CONFIRM_TRAVEL_LIST_TODAY = 
					"    SELECT                         " +
					"    	*                           " +
					"    FROM                           " +
					"    	kycom_o_order_travel        " +
					"    WHERE                          " +
					"    	start_time >= #{startTime}  " +
					"    AND end_time <= #{endTime}     " +
					"    AND STATUS = #{status}         ";
	
	final String QUERY_SERVIING_TRAVEL_LIST_TODAY =
					"    SELECT                                       " +
					"    	DISTINCT order_id                         " +
					"    FROM                                         " +
					"    	kycom_o_order_travel                      " +
					"    WHERE                                        " +
					"    	order_id IN(                              " +
					"    		SELECT                                " +
					"    			id                                " +
					"    		FROM                                  " +
					"    			kycom_o_order                     " +
					"    		WHERE                                 " +
					"    			STATUS = #{orderStatus}           " +
					"    		AND start_time <= #{startTime}        " +
					"    		AND end_time >= #{endTime}            " +
					"    	)                                         " +
					"    AND start_time >= #{startTime}               " +
					"    AND end_time <= #{endTime}                   " +
					"    AND STATUS = #{travelStatus}                 ";
	
	final String QUERY_SERVIING_TRAVEL_LIST_TODAY_PAGE =
					"    SELECT                                       " +
					"    	DISTINCT order_id                         " +
					"    FROM                                         " +
					"    	kycom_o_order_travel                      " +
					"    WHERE                                        " +
					"    	order_id IN(                              " +
					"    		SELECT                                " +
					"    			id                                " +
					"    		FROM                                  " +
					"    			kycom_o_order                     " +
					"    		WHERE                                 " +
					"    			STATUS = #{orderStatus}           " +
					"    		AND start_time <= #{startTime}        " +
					"    		AND end_time >= #{endTime}            " +
					"    	)                                         " +
					"    AND start_time >= #{startTime}               " +
					"    AND end_time <= #{endTime}                   " +
					"    AND STATUS = #{travelStatus}                 " +
					"    ORDER BY                                     " +
					"    	order_id DESC                             " +
					"	 LIMIT #{startPos}, #{pageSize}				  ";
	
	// 查询指定日期的行程单数量
	final String QUERY_TRAVEL_COUNT_BY_DAY =
					"    SELECT                                                       " +
					"    	count(*)                                                  " +
					"    FROM                                                         " +
					"    	(                                                         " +
					"    		SELECT                                                " +
					"    			DATE_FORMAT(start_time, '%x/%m/%d')AS yyyymmdd    " +
					"    		FROM                                                  " +
					"    			kycom_o_order_travel                              " +
					"    		WHERE                                                 " +
					"    			order_id = #{orderId}                             " +
					"    		AND `status` != #{travelStatus}                       " +
					"    	)AS t                                                     " +
					"    WHERE                                                        " +
					"    	t.yyyymmdd = #{day}                                       ";
	
	// 查询指定日期
	final String FIND_COUNT_TRAVEL_SERVING = 
			"	SELECT count(*)												"
			+ "	FROM kycom_o_order_travel AS ot								"
			+ "	WHERE ot.order_id = #{orderId}								"
			+ "	AND DATE_FORMAT(ot.start_time, '%x/%m/%d') = #{date}		"
			+ "	AND ot.status < #{status}									";
	
	// 行程单详情
	@Select(FIND_TRAVEL_INFO)
	OrderTravel findInfo(@Param("id") Integer id);
	
	// 行程单列表
	@Select(QUERY_TRAVEL_LIST)
	List<OrderTravel> queryTravelList(@Param("orderId") Integer orderId, @Param("status") Integer status);
	
	// 查询当天行程单列表
	@Select(QUERY_TRAVEL_LIST_TODAY)
	List<OrderTravel> queryTodayTravelList(@Param("orderId") Integer orderId, @Param("status") Integer status, 
			@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	// 查询待服务行程数量
	@Select(QUERY_WAIT_SERVICE_TRAVEL_COUNT)
	Integer queryWaitServiceTravelCount(@Param("orderId") Integer orderId, @Param("statusFinish") Integer statusFinish, @Param("statusRemove") Integer statusRemove);
	
	// 根据行程单id查询用户id
	@Select("    SELECT                                                   " +
			"    	user_id                                               " +
			"    FROM                                                     " +
			"    	kycom_o_order o                                       " +
			"    LEFT JOIN kycom_o_order_travel t ON t.order_id = o.id    " +
			"    WHERE                                                    " +
			"    	t.id = #{travelId};                                   ")
	Integer findUserByOrderTravel(@Param("travelId") Integer travelId);
	
	@Select(" SELECT count(0) from kycom_o_order_travel where order_id = #{orderId}")
	int findTravelCountByOrderId(@Param("orderId") Integer orderId);
	
	// 查询今天已确认行程
	@Select(QUERY_CONFIRM_TRAVEL_LIST_TODAY)
	List<OrderTravel> queryConfirmedTravellistToday(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("status") Integer status);
	
	// 查询今天服务中订单id
	@Select(QUERY_SERVIING_TRAVEL_LIST_TODAY)
	List<Integer> queryServingTravelListToday(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("orderStatus") Integer orderStatus, @Param("travelStatus") Integer travelStatus);

	// 查询今天服务中订单id
	@Select(QUERY_SERVIING_TRAVEL_LIST_TODAY_PAGE)
	List<Integer> queryServingTravelListTodayPage(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("orderStatus") Integer orderStatus, @Param("travelStatus") Integer travelStatus,
			@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	// 查询指定日期的行程单数量
	@Select(QUERY_TRAVEL_COUNT_BY_DAY)
	Integer queryTravelCountByDay(@Param("orderId") Integer orderId, @Param("travelStatus") Integer travelStatus, @Param("day") String day);

	@Select(FIND_COUNT_TRAVEL_SERVING)
	int findCountTravelServing(@Param("orderId") Integer orderId, @Param("date") String date, @Param("status") Integer status);
	
}

package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.CrossTimes;

public interface CrossTimesMapper extends GenericDao<CrossTimes, Integer> {

	final String FIND_CROSS_TIME_BY_STATUS =
						"    SELECT time                        " +
						"    FROM kycom_u_cross_times           " +
						"    WHERE user_id = #{userId}          " +
						"            AND status = #{status}     " +
						"            AND time >= #{startDate}   " +
						"            AND time <= #{endDate}     ";
	
	final String FIND_CROSS_TIME_BY_DATE =
						"    SELECT *                           " +
						"    FROM kycom_u_cross_times           " +
						"    WHERE user_id = #{userId}          " +
						"            AND time >= #{startDate}   " +
						"            AND time <= #{endDate}     ";
	
	final String FIND_CROSS_TIME_BY_USERID =
						"    SELECT id, time, status                   " +
						"    FROM kycom_u_cross_times                  " +
						"    WHERE user_id = #{userId}                 " +
						"            AND time >= #{stayDate}           " +
						"            AND time <= #{endDate}            ";
	
	final String FIND_CROSS_TIME_BY_ORDER =
						"    SELECT distinct user_id                   " +
						"    FROM kycom_u_cross_times                  " +
						"	 WHERE	 status = #{status}                " +
						"            AND time >= #{stayDate}           " +
						"            AND time <= #{endDate}            ";

	@Select(FIND_CROSS_TIME_BY_STATUS)
	List<CrossTimes> findCrossTimeByStatus(@Param("userId") Integer userId, @Param("status") Integer status, 
			@Param("startDate") String startDate, @Param("endDate") String endDate);
	
	@Select(FIND_CROSS_TIME_BY_DATE)
	List<CrossTimes> findCrossTimeByDate(@Param("userId") Integer userId, @Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
	@Select(FIND_CROSS_TIME_BY_USERID)
	List<CrossTimes> findCrossTimeByUserId(@Param("userId") Integer userId, @Param("stayDate") String stayDate, 
			@Param("endDate") String endDate);
	
	@Select(FIND_CROSS_TIME_BY_ORDER)
	List<CrossTimes> findCrossTimeByOrder(@Param("status") Integer status, @Param("stayDate") String stayDate, 
			@Param("endDate") String endDate);
	
	@Update("    UPDATE kycom_u_cross_times         " +
			"    SET status = #{status}             " +
			"    WHERE                              " +
			"    	user_id = #{userId}             " +
			"    AND time = #{time}                 ")
	boolean updateCrossTime(@Param("status") Integer status, @Param("userId") Integer userId, @Param("time") String time);
}

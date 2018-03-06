package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.CrossInfo;

public interface CrossInfoMapper extends GenericDao<CrossInfo, Integer> {
	
	final String FIND_CROSS_LIST_COUNT = 
					"    SELECT count(*)                                                                           " +
					"                                                                                              " +
					"    FROM kycom_u_cross_info c                                                                 " +
					"                                                                                              " +
					"    LEFT JOIN (                                                                               " +
					"    	select user_id, count(*) AS times_count from kycom_u_cross_times AS ct                 " +
					"    	WHERE ct.time >= #{startDate}                                                          " +
					"    		AND ct.time <= #{endDate}                                                          " +
					"           AND ct.`status` = #{timeStatus}                                                    " +
					"    	GROUP BY user_id                                                                       " +
					"    ) AS t ON c.user_id = t.user_id                                                           " +
					"                                                                                              " +
					"    LEFT JOIN (                                                                               " +
					"    	select cm.cross_user_id, avg(cm.star) AS comment_avg from kycom_u_cross_comment AS cm  " +
					"    	GROUP BY cross_user_id                                                                 " +
					"    ) AS m ON c.user_id = m.cross_user_id                                                     " +
					"                                                                                              " +
					"    WHERE c.city_id = #{cityId} AND (c.cross_number LIKE #{search} OR c.name LIKE #{search})  ";
	
	final String FIND_CROSS_LIST = 
					"    SELECT c.id, c.user_id, c.pic, c.work_name, m.comment_avg, m.comment_amount, c.cross_number, t2.booked_day_count, t.fee_day_count    " +
					"           , c.education, c.work_age, c.year_birth, c.specialty                                                               " +
					"    FROM kycom_u_cross_info c                                                                                                 " +
					"                                                                                                                              " +
					"    LEFT JOIN (                                                                                                               " +
					"    	select user_id, count(*) AS fee_day_count from kycom_u_cross_times AS ct                                               " +
					"    	WHERE ct.time >= #{startDate}                                                                                          " +
					"    		AND ct.time <= #{endDate}                                                                                          " +
					"        AND ct.`status` = #{timeStatusIdel}                                                                                   " +
					"    	GROUP BY user_id                                                                                                       " +
					"    ) AS t ON c.user_id = t.user_id                                                                                           " +
					"                                                                                                                              " +
					"    LEFT JOIN (                                                                                                               " +
					"    	select user_id, count(*) AS booked_day_count from kycom_u_cross_times AS ct                                            " +
					"    	WHERE ct.`status` = #{timeStatusSub}                                                                                   " +
					"    	GROUP BY user_id                                                                                                       " +
					"    ) AS t2 ON c.user_id = t2.user_id                                                                                         " +
					"                                                                                                                              " +
					"    LEFT JOIN (                                                                                                               " +
					"    	select cm.cross_user_id, avg(cm.star) AS comment_avg , count(*) as comment_amount from kycom_u_cross_comment AS cm     " +
					"    	GROUP BY cross_user_id                                                                                                 " +
					"    ) AS m ON c.user_id = m.cross_user_id                                                                                     " +
					"                                                                                                                              " +
					"    LEFT JOIN kycom_u_user u ON u.id = c.user_id                                                                              " +
					"                                                                                                                              " +
					"    WHERE c.city_id = #{cityId} AND u. STATUS = 1 AND (c.cross_number LIKE #{search} OR c.work_name LIKE #{search})           " +
					"                                                                                                                              " +
					"    ORDER BY t.fee_day_count DESC, m.comment_avg DESC LIMIT #{startPos}, #{pageSize}                                          ";

	
	final String FIND_CROSS_INFO =
					"    SELECT *                                                             " +
					"    FROM kycom_u_cross_info c                                            " +
					"    LEFT JOIN                                                            " +
					"      (SELECT cm.cross_user_id,                                          " +
					"              avg(cm.star) AS comment_avg,                               " +
					"              avg(cm.star1) AS comment_star1,                            " +
					"              avg(cm.star2) AS comment_star2,                            " +
					"              avg(cm.star3) AS comment_star3,                            " +
					"              avg(cm.star4) AS comment_star4,                            " +
					"              count(*) AS comment_amount                                 " +
					"       FROM kycom_u_cross_comment AS cm                                  " +
					"       GROUP BY cm.cross_user_id) AS m ON c.user_id = m.cross_user_id    " +
					"    LEFT JOIN                                                            " +
					"      (SELECT user_id, count(*) AS booked_day_count                      " +
					"       FROM kycom_u_cross_times AS ct                                    " +
					"       WHERE ct.`status` = 1                                             " +
					"       GROUP BY user_id ) AS t2 ON c.user_id = t2.user_id                " +
					"    WHERE c.user_id = #{crossUserId}                                     ";
	
	final String FIND_CROSS_DETAIL = 
					"    SELECT *                                                             " +
					"    FROM kycom_u_cross_info c                                            " +
					"    LEFT JOIN (																			" +
					"    	select user_id, count(*) AS fee_day_count from kycom_u_cross_times AS ct			" +
					"    	WHERE ct.time >= #{startDate}														" +
					"    		AND ct.time <= #{endDate}														" +
					"        AND ct.status = 0																	" +
					"    	GROUP BY user_id ) AS t ON c.user_id = t.user_id									" +
					"    LEFT JOIN                                                            " +
					"      (SELECT cm.cross_user_id,                                          " +
					"              avg(cm.star) AS comment_avg,                               " +
					"              avg(cm.star1) AS comment_star1,                            " +
					"              avg(cm.star2) AS comment_star2,                            " +
					"              avg(cm.star3) AS comment_star3,                            " +
					"              avg(cm.star4) AS comment_star4,                            " +
					"              count(*) AS comment_amount                                 " +
					"       FROM kycom_u_cross_comment AS cm                                  " +
					"       GROUP BY cm.cross_user_id) AS m ON c.user_id = m.cross_user_id    " +
					"    LEFT JOIN                                                            " +
					"      (SELECT user_id, count(*) AS booked_day_count                      " +
					"       FROM kycom_u_cross_times AS ct                                    " +
					"       WHERE ct.status = 1                                             " +
					"       GROUP BY user_id ) AS t2 ON c.user_id = t2.user_id                " +
					"    WHERE c.user_id = #{crossUserId}                                     ";
	
	final String FIND_CROSS_AND_USER_LIST = 
			"    SELECT  ci.id, ci.cross_number AS crossNumber, ci.banners, tc.name AS cityName, ci.name, ci.work_name AS workName,   " +
			"    ci.contact_phone AS contactPhone, uu.mobile as mobile, uu.status as status                                     " +
			"    FROM kycom_u_cross_info ci                                                                  					" +     
			"    LEFT JOIN                                                                                  					" +
			"    	kycom_t_city tc ON ci.city_id = tc.id                                                  						" +
			"    LEFT JOIN                                                                                  					" +
			"       kycom_u_user uu ON ci.user_id = uu.id                                                    					" +
			"    LIMIT #{startPos}, #{pageSize}           					                                                    ";

	final String FIND_BY_USERNAME = 
							"    SELECT c.id ,                               " +
							"            c.cross_number ,                    " +
							"            c.name ,                            " +
							"            c.work_name ,                       " +
							"            c.contact_phone ,                   " +
							"            c.pic ,                             " +
							"            c.banners ,                         " +
							"            c.height ,                          " +
							"            c.native_place ,                    " +
							"            c.year_birth ,                      " +
							"            c.school ,                          " +
							"            c.education ,                       " +
							"            c.constellation ,                   " +
							"            c.work_age ,                        " +
							"            c.province_id ,                     " +
							"            c.city_id ,                         " +
							"            c.sex ,                             " +
							"            c.specialty ,                       " +
							"            c.native_place_city_id ,            " +
							"            c.native_place_province_id ,        " +
							"            c.user_id                           " +
							"    FROM kycom_u_user u                         " +
							"    LEFT JOIN kycom_u_cross_info c              " +
							"        ON u.userinfo_id = c.id                 " +
							"    WHERE u.username = #{username} limit 1     ";
	
	final String FIND_USER_BY_CITY_ID_COUNT = 
	         "   select count(0)                                                   " +
	         "   FROM                                                              " +
	         "	    kycom_u_user as uu                                             " +
	         "   LEFT JOIN                                                         " +
	         "	    kycom_u_cross_info as uci ON uu.userinfo_id = uci.id           " +
	         "   WHERE                                                             " +
	         "	   uu.`status`= 1                                                  " +
	         "	   and uci.city_id = #{cityId}                                     " ;
	
	
	
	final String FIND_USER_BY_USER_ID = 
			"	Select  uci.work_name as workName, IFNULL(avg(ucc.star),0) as avgStar, uu.id as userId  																	 " +
			"	FROM        													                                                                                                 " +
			"			 kycom_u_user uu 										  																								 " +						    				                   				
			"	LEFT JOIN                                                         																					     		 " +       					
			"		kycom_u_cross_comment ucc ON uu.id = ucc.cross_user_id    																									 " +
			"	LEFT JOIN                                                        																								 " +
			"	    (SELECT distinct user_id FROM kycom_u_cross_times WHERE	 status = #{status} AND time >= #{stayDate} AND time <= #{endDate}) AS TEMP on TEMP.user_id = uu.id  " +
			"   LEFT JOIN 																																						 " +
            "       kycom_u_cross_info uci ON uu.id = uci.user_id																												 " +
			"	GROUP BY TEMP.user_id           																																 " +   				                                           					  
			"	order by avgStar desc    																																		 " ;
	
	final String FIND_SIMPLE_CROSS_INFO = 
					"    SELECT                                          " +
					"    	r.pic,                                       " +
					"    	r.contact_phone,                             " +
					"    	r.work_name,                                 " +
					"    	c.`name` as cityName,                        " +
					"    	cm.commentAvg                                " +
					"    FROM                                            " +
					"    	kycom_u_cross_info r                         " +
					"    LEFT JOIN kycom_t_city c ON c.id = r.city_id    " +
					"    LEFT JOIN(                                      " +
					"    	SELECT                                       " +
					"    		cross_user_id,                           " +
					"    		AVG(star)AS commentAvg                   " +
					"    	FROM                                         " +
					"    		kycom_u_cross_comment                    " +
					"    )AS cm ON cm.cross_user_id = r.user_id          " +
					"    WHERE                                           " +
					"    	r.id = #{crossInfoId}                        " +
					"                                                    ";
	
	@Select(FIND_CROSS_LIST_COUNT)
    Integer findCrossListCount(@Param("startDate") String startDate, @Param("endDate") String endDate, 
    		@Param("cityId") Integer cityId, @Param("search") String search, @Param("timeStatus") Integer timeStatus,
    		@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	/**
	 * 查找符合 cityId 的金鹰，然后按金鹰匹配服务天数 和 金鹰平均评分排序
	 * 
	 * @param startDate 开始出行时间
	 * @param endDate 结束出行时间
	 * @param cityId 城市id
	 * @param timeStatus 是否预约
	 * @param startPos
	 * @param pageSize
	 * @return
	 */
	@Select(FIND_CROSS_LIST)
    List<CrossInfo> findCrossList(@Param("startDate") String startDate, @Param("endDate") String endDate, 
    		@Param("cityId") Integer cityId, @Param("search") String search, @Param("timeStatusIdel") Integer timeStatusIdel, @Param("timeStatusSub") Integer timeStatusSub,
    		@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	@Select(FIND_CROSS_INFO)
	CrossInfo findCrossInfo(@Param("crossUserId") Integer crossUserId);
	
	/**
	 * 查询金鹰列表
	 * @return
	 */
	List<CrossInfo> selectListByCondition(@Param(value = "startpos") Integer startpos, @Param(value = "pagesize") Integer pagesize, @Param(value = "crossInfo") CrossInfo crossInfo);
	
	/**
	 * 根据金鹰id查询金鹰信息
	 * @return
	 */
	CrossInfo findCrossById(@Param(value = "id") Integer id);
	
	/**
	 * 根据金鹰id更新金鹰数据
	 * @param crossInfo
	 * @return
	 */
	int updateCrossById(CrossInfo crossInfo);
	
	@Select(FIND_USER_BY_CITY_ID_COUNT)
	int findUserByCityIdCount(@Param("cityId") Integer cityId);

	@Select(FIND_BY_USERNAME)
	CrossInfo findByUsername(@Param(value = "username") String username);
	
	@Select(FIND_USER_BY_USER_ID)
	List<CrossInfo> findUserByUserId(@Param("status") Integer status, @Param("stayDate") String stayDate, @Param("endDate") String endDate);
	
	@Select(FIND_SIMPLE_CROSS_INFO)
	CrossInfo findSimpleCrossInfo(@Param("crossInfoId") Integer crossInfoId);
	
	@Select("select * from kycom_u_cross_info where user_id = #{userId}")
	CrossInfo findByUserId(@Param("userId") Integer userId);
	
	@Select("select * from kycom_u_cross_info where user_id = (select cross_user_id from kycom_o_order where id = #{orderId})")
	CrossInfo findByOrderId(@Param("orderId") Integer orderId);
	
	@Select(FIND_CROSS_DETAIL)
	CrossInfo findCrossDetail(@Param("crossUserId") Integer crossUserId, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	@Select("select * from kycom_u_cross_info")
	List<CrossInfo> findAll();
}

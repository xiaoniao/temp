package com.kunyao.assistant.web.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.MemberComment;

public interface MemberCommentMapper extends GenericDao<MemberComment, Integer> {

	
	@Select("    SELECT                    "+
			"	    count(*)               "+
			"    FROM                      "+
			"	    kycom_u_member_comment "+
			"    WHERE                     "+
			"	    user_id = #{userId}    ")
	Integer findCount(@Param("userId") Integer userId);
	
	@Select("    SELECT                    "+
			"	    count(*)               "+
			"    FROM                      "+
			"	    kycom_u_member_comment "+
			"    WHERE                     "+
			"	    order_id = #{orderId}    ")
	Integer findCountByOrderId(@Param("orderId") Integer orderId);
	
	@Select("    SELECT                    "+
			"	    AVG(star)AS avg        "+
			"    FROM                      "+
			"	    kycom_u_member_comment "+
			"    WHERE                     "+
			"	    user_id = #{userId}    ")
	Double findStarAvg(@Param("userId") Integer userId);
	
	@Select("    SELECT                    " +
			"    	count(*)               " +
			"    FROM                      " +
			"    	kycom_u_member_comment ")
	Integer findListCount();
	
	final String FIND_LIST_BY_WHERR = 
			"    SELECT                                                             " +
			"    	c.*,                                                            " +
			"       m.bank_mobile,                                                  " +
			"    	ci.work_name,                                                   " +
			"    	ci.city_id,                                                     " +
			"    	t.`name` as city_name                                           " +
			"    FROM                                                               " +
			"    	kycom_u_member_comment c                                        " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = c.user_id           " +
			"    LEFT JOIN kycom_u_cross_info ci ON ci.user_id = c.cross_user_id    " +
			"    LEFT JOIN kycom_t_city t ON t.id = ci.city_id                      " +
			"    #{WHERE}                                                           " +
			"    ORDER BY                                                           " +
			"    	c.create_time DESC                                              " +
			"	 LIMIT #{startPos}, #{pageSize}				                        ";
	
	final String FIND_LIST = 
			"    SELECT                                                             " +
			"    	c.*,                                                            " +
			"       m.bank_mobile,                                                  " +
			"    	ci.work_name,                                                   " +
			"    	ci.city_id,                                                     " +
			"    	t.`name` as city_name                                           " +
			"    FROM                                                               " +
			"    	kycom_u_member_comment c                                        " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = c.user_id           " +
			"    LEFT JOIN kycom_u_cross_info ci ON ci.user_id = c.cross_user_id    " +
			"    LEFT JOIN kycom_t_city t ON t.id = ci.city_id                      " +
			"    #{WHERE}                                                           ";
}
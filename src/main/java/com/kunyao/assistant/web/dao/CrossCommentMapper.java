package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.CrossComment;

public interface CrossCommentMapper extends GenericDao<CrossComment, Integer> {
	
	final String FIND_CROSSCOMMENT_BY_CROSSUSERID = 
			"	SELECT "
			+ "		cc.id,"
			+ "		cc.star, "
			+ "		cc.star1, "
			+ "		cc.star2, "
			+ "		cc.star3, "
			+ "		cc.star4, "
			+ "		cc.content, "
			+ "		cc.create_time,"
			+ "		cc.status,"
			+ "		cc.user_id,"
			+ "		mi.header AS memberHeader, "
			+ "		CONCAT(SUBSTR(mi.bank_mobile,1,3),'****',SUBSTR(mi.bank_mobile,9)) AS bankMobile "
			+ "	FROM `kycom_u_cross_comment` as cc "
			+ "	LEFT JOIN kycom_u_user as uu ON cc.user_id = uu.id "
			+ "	LEFT JOIN kycom_u_member_info as mi ON uu.userinfo_id = mi.id "
			+ "	WHERE cc.cross_user_id = #{crossUserId}";
	
	
	final String FIND_LIST_COUNT = 
					"    SELECT                                                      " +
					"    	count(*)                                                 " +
					"    FROM                                                        " +
					"    	kycom_u_cross_comment                                    ";
	
	final String FIND_LIST_BY_WHERR = 
					"    SELECT                                                             " +
					"    	c.*,                                                            " +
					"       m.bank_mobile,                                                  " +
					"    	ci.work_name,                                                   " +
					"    	ci.city_id,                                                     " +
					"    	t.`name` as city_name                                           " +
					"    FROM                                                               " +
					"    	kycom_u_cross_comment c                                         " +
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
			"    	kycom_u_cross_comment c                                         " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = c.user_id           " +
			"    LEFT JOIN kycom_u_cross_info ci ON ci.user_id = c.cross_user_id    " +
			"    LEFT JOIN kycom_t_city t ON t.id = ci.city_id                      " +
			"    #{WHERE}                                                           ";
	
	/**
     * 根据金鹰id获取评价列表
     * 
     * @param crossUserId
     * @return
     */
	
	@Select(FIND_CROSSCOMMENT_BY_CROSSUSERID)
    List<CrossComment> findCrossCommentByCrossUserId(@Param("crossUserId") Integer crossUserId);
	
	@Select(FIND_LIST_COUNT)
	Integer findListCount();
}

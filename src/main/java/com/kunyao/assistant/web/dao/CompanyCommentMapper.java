package com.kunyao.assistant.web.dao;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.CompanyComment;

import java.lang.Integer;

import org.apache.ibatis.annotations.Select;

public interface CompanyCommentMapper extends GenericDao<CompanyComment, Integer> {
	
	@Select("    SELECT                     " +
			"    	count(*)                " +
			"    FROM                       " +
			"    	kycom_u_company_comment ")
	Integer findListCount();
	
	final String FIND_LIST_BY_WHERR = 
			"    SELECT                                                      " +
			"    	c.*, m.bank_mobile,                                      " +
			"    	o.start_time AS order_start_time,                        " +
			"    	o.city_id,                                               " +
			"    	t.`name` AS city_name                                    " +
			"    FROM                                                        " +
			"    	kycom_u_company_comment c                                " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = c.user_id    " +
			"    LEFT JOIN kycom_o_order o ON o.id = c.order_id              " +
			"    LEFT JOIN kycom_t_city t ON t.id = o.city_id                " +
			"    #{WHERE}                                                    " +
			"    order by c.create_time desc                                 " +
			"	 LIMIT #{startPos}, #{pageSize}				                 ";
	
	final String FIND_LIST = 
			"    SELECT                                                      " +
			"    	c.*, m.bank_mobile,                                      " +
			"    	o.start_time AS order_start_time,                        " +
			"    	o.city_id,                                               " +
			"    	t.`name` AS city_name                                    " +
			"    FROM                                                        " +
			"    	kycom_u_company_comment c                                " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = c.user_id    " +
			"    LEFT JOIN kycom_o_order o ON o.id = c.order_id              " +
			"    LEFT JOIN kycom_t_city t ON t.id = o.city_id                " +
			"    #{WHERE}                                                    ";
}
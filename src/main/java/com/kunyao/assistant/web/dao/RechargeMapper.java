package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Recharge;

public interface RechargeMapper extends GenericDao<Recharge, Integer> {
	
	final String FIND_RECHARGE_INFO_BY_ID = "SELECT * FROM kycom_u_recharge WHERE id = #{id}";
	
	final String FIND_LIST_COUNT = 
				"    SELECT                                                      " +
				"    	count(*)                                                 " +
				"    FROM                                                        " +
				"    	kycom_u_recharge r                                       ";
	
	final String FIND_LIST_BY_WHERR = 
				"    SELECT                                                      " +
				"    	r.*, m.bank_mobile                                       " +
				"    FROM                                                        " +
				"    	kycom_u_recharge r                                       " +
				"    LEFT JOIN kycom_u_member_info m ON r.user_id = m.user_id    " +
				"    #{WHERE}                                                    " +
				"    ORDER BY                                                    " +
				"    	r.create_time DESC                                       " +
				"	 LIMIT #{startPos}, #{pageSize}				                 ";
	
	final String FIND_LIST = 
			"    SELECT                                                      " +
			"    	r.*, m.bank_mobile                                       " +
			"    FROM                                                        " +
			"    	kycom_u_recharge r                                       " +
			"    LEFT JOIN kycom_u_member_info m ON r.user_id = m.user_id    " +
			"    #{WHERE}                                                    ";
	
	@Select(FIND_RECHARGE_INFO_BY_ID)
	Recharge findRechargeInfoById(@Param("id") Integer id);
	
	@Select(FIND_LIST_COUNT)
	Integer findListCount();
	
	@Select("    SELECT                                                      " +
			"    	r.*, m.bank_mobile                                       " +
			"    FROM                                                        " +
			"    	kycom_u_recharge r                                       " +
			"    LEFT JOIN kycom_u_member_info m ON r.user_id = m.user_id    " +
			"    ORDER BY                                                    " +
			"    	r.create_time DESC                                       ")
	List<Recharge> findAll();
}

package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.TakeCash;

public interface TakeCashMapper extends GenericDao<TakeCash, Integer> {
	
	final String FIND_LIST_COUNT = 
			"    SELECT                                                      " +
			"    	count(*)                                                 " +
			"    FROM                                                        " +
			"    	kycom_a_take_cash                                        ";

	final String FIND_LIST_BY_WHERR = 
				"    SELECT                                                      " +
				"    	t.*, m.bank_mobile                                       " +
				"    FROM                                                        " +
				"    	kycom_a_take_cash t                                      " +
				"    LEFT JOIN kycom_u_member_info m ON m.user_id = t.user_id    " +
				"    #{WHERE}                                                    " +
				"    ORDER BY                                                    " +
				"    	t.create_time DESC                                       " +
				"	 LIMIT #{startPos}, #{pageSize}				                 ";
	
	final String FIND_LIST = 
			"    SELECT                                                      " +
			"    	t.*, m.bank_mobile                                       " +
			"    FROM                                                        " +
			"    	kycom_a_take_cash t                                      " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = t.user_id    " +
			"    #{WHERE}                                                    ";
	
	@Select(FIND_LIST_COUNT)
	Integer findListCount();
	
	@Select("    SELECT                                                      " +
			"    	t.*, m.bank_mobile                                       " +
			"    FROM                                                        " +
			"    	kycom_a_take_cash t                                      " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = t.user_id    " +
			"    ORDER BY                                                    " +
			"    	t.create_time DESC                                       ")
	List<TakeCash> findAll();
}

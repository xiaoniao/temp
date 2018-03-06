package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.AccountRecord;

public interface AccountRecordMapper extends GenericDao<AccountRecord, Integer> {
	
	final String FIND_BY_STATUS =
					"    SELECT                                            " +
					"    	a.*, o.order_card                              " +
					"    FROM                                              " +
					"    	kycom_a_account_record a                       " +
					"    LEFT JOIN kycom_o_order o ON o.id = a.order_id    " +
					"    WHERE                                             " +
					"    	a.user_id = #{userId}                          " +
					"    AND a.type = #{type}                              " +
					"	 ORDER BY operate_time DESC						   ";
	
	final String FIND_BY_STATUS_IN =
					"    SELECT                                            " +
					"    	a.*, o.order_card                              " +
					"    FROM                                              " +
					"    	kycom_a_account_record a                       " +
					"    LEFT JOIN kycom_o_order o ON o.id = a.order_id    " +
					"    WHERE                                             " +
					"    	a.user_id = #{userId}                          " +
					"    AND a.type IN(1, 2, 3, 4)                         " +
					"	 ORDER BY operate_time DESC						   ";
	
	final String FIND_BY_MONTH_COUNT = 
					"    SELECT                                                      " +
					"    	a.id                                                     " +
					"    FROM                                                        " +
					"    	kycom_a_account_record a                                 " +
					"    LEFT JOIN kycom_o_order o ON o.id = a.order_id              " +
					"    LEFT JOIN kycom_u_member_info m ON m.user_id = a.user_id    " +
					"    	#{WHERE}                                                 ";
	
	final String FIND_BY_MONTH = 
					"    SELECT                                                      " +
					"    	a.*, o.order_card, m.bank_mobile                         " +
					"    FROM                                                        " +
					"    	kycom_a_account_record a                                 " +
					"    LEFT JOIN kycom_o_order o ON o.id = a.order_id              " +
					"    LEFT JOIN kycom_u_member_info m ON m.user_id = a.user_id    " +
					"    	#{WHERE}                                                 " +
					"    ORDER BY                                                    " +
					"    	a.operate_time DESC                                      " +
					"	 LIMIT #{startPos}, #{pageSize}                              ";
	
	final String FIND_HISTORY_COUNT = 
					"    SELECT                                                      " +
					"    	a.id                                                     " +
					"    FROM                                                        " +
					"    	kycom_a_account_record a                                 " +
					"    LEFT JOIN kycom_o_order o ON o.id = a.order_id              " +
					"    LEFT JOIN kycom_u_member_info m ON m.user_id = a.user_id    " +
					"    	#{WHERE}                                                 ";
	
	final String FIND_HISTORY = 
					"    SELECT                                                      " +
					"    	a.*, o.order_card, m.bank_mobile                         " +
					"    FROM                                                        " +
					"    	kycom_a_account_record a                                 " +
					"    LEFT JOIN kycom_o_order o ON o.id = a.order_id              " +
					"    LEFT JOIN kycom_u_member_info m ON m.user_id = a.user_id    " +
					"    	#{WHERE}                                                 " +
					"    ORDER BY                                                    " +
					"    	a.operate_time DESC                                      " +
					"	 LIMIT #{startPos}, #{pageSize}                              ";
	
	public static final String FIND_LIST_BY_WHERR = 
					"    SELECT                                                      " +
					"    	r.*, m.bank_mobile                                       " +
					"    FROM                                                        " +
					"    	kycom_u_recharge r                                       " +
					"    LEFT JOIN kycom_u_member_info m ON r.user_id = m.user_id    " +
					"    WHERE  #{WHERE}                                             " +
					"    ORDER BY                                                    " +
					"    	r.create_time DESC;                                      ";
	
	public static final String SUM_MONEY_RECORD = " SELECT IFNULL(SUM(money), 0) FROM kycom_a_account_record WHERE user_id = #{userId} AND type = #{type}";
	
	// 查询指定类型资金明细
	@Select(FIND_BY_STATUS)
	List<AccountRecord> findByStatus(@Param("userId") Integer userId, @Param("type") Integer type);
	
	// 查询指定类型资金明细 写死
	@Select(FIND_BY_STATUS_IN)
	List<AccountRecord> findByStatusIn(@Param("userId") Integer userId);

	@Select(SUM_MONEY_RECORD)
	Double sumMoneyRecord(@Param("userId") Integer userId, @Param("type") Integer type);
}

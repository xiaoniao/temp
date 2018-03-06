package com.kunyao.assistant.web.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Account;

public interface AccountMapper extends GenericDao<Account, Integer> {
	
	final String FIND_ACCOUNT = "SELECT * FROM kycom_a_account where user_id = #{userId}";
	
	@Select(FIND_ACCOUNT)
	Account findAccount(@Param("userId") Integer userId);
}

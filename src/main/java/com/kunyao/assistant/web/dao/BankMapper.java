package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Bank;

public interface BankMapper extends GenericDao<Bank, Integer> {
	
	final String LIST =
			"    SELECT                  " +
			"    	*                    " +
			"    FROM                    " +
			"    	kycom_t_bank         " +
			"    WHERE                   " +
			"    	user_id = #{userId}  " +
			"    AND status = #{status}  ";
			
	final String INFO =
				"    SELECT                  " +
				"    	*                    " +
				"    FROM                    " +
				"    	kycom_t_bank         " +
				"    WHERE                   " +
				"    	id = #{id}           ";
	
	@Select(LIST)
	List<Bank> list(@Param("userId") Integer userId, @Param("status") Integer status);
	
	@Select(INFO)
	Bank info(@Param("id") Integer id);
}

package com.kunyao.assistant.web.dao;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.CommonInvoice;

import java.lang.Integer;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CommonInvoiceMapper extends GenericDao<CommonInvoice, Integer> {
	
	@Select("    SELECT                                                                        " +
			"    	*                                                                          " +
			"    FROM                                                                          " +
			"    	kycom_u_common_invoice                                                     " +
			"    WHERE                                                                         " +
			"    	user_id = #{userId}                                                        " +
			"    AND(title = #{titleOrCompanyName} OR company_name = #{titleOrCompanyName})    " +
			"    LIMIT 1                                                                       ")
	CommonInvoice findByTitleOrCompanyName(@Param("userId") Integer userId, @Param("titleOrCompanyName") String titleOrCompanyName);
}

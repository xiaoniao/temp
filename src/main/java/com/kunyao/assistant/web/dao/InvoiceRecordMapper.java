package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.InvoiceRecord;

public interface InvoiceRecordMapper extends GenericDao<InvoiceRecord, Integer> {
	
	final String LIST = 
					"    SELECT                     " +
					"    	*                       " +
					"    FROM                       " +
					"    	kycom_u_invoice_record  " +
					"    WHERE                      " +
					"    	user_id = #{userId}     ";
	
	final String LIST_PAGE_COUNT = 
					"    SELECT                     " +
					"    	count(*)                " +
					"    FROM                       " +
					"    	kycom_u_invoice_record  ";
	
	final String LIST_PAGE = 
					"    SELECT                         " +
					"    	*                           " +
					"    FROM                           " +
					"    	kycom_u_invoice_record      " +
					"    ORDER BY create_time desc      " +
					"    LIMIT #{startPos}, #{pageSize} ";
	
	final String INFO = 
					"    SELECT                     " +
					"    	*                       " +
					"    FROM                       " +
					"    	kycom_u_invoice_record  " +
					"    WHERE                      " +
					"    	id = #{id}              ";
	
	@Select(LIST)
	List<InvoiceRecord> list(@Param("userId") Integer userId);
	
	@Select(LIST_PAGE)
	List<InvoiceRecord> listPage(@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize);
	
	@Select(LIST_PAGE_COUNT)
	Integer listPageCount();
	
	@Select(INFO)
	InvoiceRecord info(@Param("id") Integer id);
}

package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.OrderDoubt;

public interface OrderDoubtMapper extends GenericDao<OrderDoubt, Integer> {
	
	final String FIND_DOUBT_BY_DATE = 
			"	SELECT * "
			+ "	FROM kycom_o_order_doubt "
			+ "	WHERE order_id = #{orderId} "
			+ "	AND status = #{status} "
			+ "	AND date_of_doubt_time = #{date}";
	
	@Select(
			"    SELECT                                                        " +
			"    	*                                                          " +
			"    FROM                                                          " +
			"    	kycom_o_order_doubt                                        " +
			"    WHERE                                                         " +
			"    	order_id = #{id}										   " +
			"    	and status = #{status}									   " +
			"    	and date_of_doubt_time >= #{stayDate}					   " +
			"    	and date_of_doubt_time < #{endDate}						   " )
	List<OrderDoubt> findDoubtBytime(@Param("id") Integer id , @Param("status") Integer status, @Param("stayDate") String stayDate, @Param("endDate") String endDate);

	@Select(FIND_DOUBT_BY_DATE)
	OrderDoubt findDoubtByDate(@Param("orderId") Integer orderId, @Param("date") String date, @Param("status") Integer status);
		
}


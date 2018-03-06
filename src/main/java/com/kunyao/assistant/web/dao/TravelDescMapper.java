package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.TravelDesc;

public interface TravelDescMapper extends GenericDao<TravelDesc, Integer> {
	
	final String QUERY_LIST = "SELECT * FROM kycom_o_travel_desc";
	
	@Select(QUERY_LIST)
	List<TravelDesc> queryList();
	
}

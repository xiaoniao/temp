package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.CostItem;

public interface CostItemMapper extends GenericDao<CostItem, Integer> {
	
	@Select("SELECT * FROM kycom_o_cost_item")
	List<CostItem> findAll();
}

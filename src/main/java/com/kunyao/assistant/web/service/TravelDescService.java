package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.TravelDesc;

import java.lang.Integer;
import java.util.List;

public interface TravelDescService extends GenericService<TravelDesc, Integer> {
	
	public List<TravelDesc> queryList();
	
}

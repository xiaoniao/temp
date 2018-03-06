package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Province;

public interface ProvinceService extends GenericService<Province, Integer> {
	
	Province findProvinceByIdAndStatus(Integer id, Integer status);

	List<Province> findAll();

	Province findByCityId(Integer cityId);
	
}

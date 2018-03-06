package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Brand;

public interface BrandService extends GenericService<Brand, Integer> {
	
	List<Brand> selectBrandList(Integer startpos, Integer pagesize, Brand brand);
	
	PageInfo selectBrandListCount(Integer pageSize, Brand brand);
}

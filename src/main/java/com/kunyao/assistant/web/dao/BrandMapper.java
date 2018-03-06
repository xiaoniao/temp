package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Brand;

public interface BrandMapper extends GenericDao<Brand, Integer> {
	
	List<Brand> selectBrandList(@Param("startpos") Integer startpos, @Param("pagesize") Integer pagesize, @Param("brand") Brand brand);
	
	int selectBrandCount(@Param("brand") Brand brand);
}

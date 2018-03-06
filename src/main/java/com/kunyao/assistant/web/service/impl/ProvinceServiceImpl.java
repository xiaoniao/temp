package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.web.dao.ProvinceMapper;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.ProvinceService;

@Service
public class ProvinceServiceImpl extends GenericServiceImpl<Province, Integer> implements ProvinceService {

	@Resource
	private ProvinceMapper provinceMapper;

	@Resource
	private OrderService orderService;

	public GenericDao<Province, Integer> getDao() {
		return provinceMapper;
	}

	@Override
	public Province findProvinceByIdAndStatus(Integer id, Integer status) {
		return provinceMapper.findProvinceByIdAndStatus(id, status);
	}

	@Override
	public List<Province> findAll() {
		return provinceMapper.findAll();
	}

	@Override
	public Province findByCityId(Integer cityId) {
		return provinceMapper.findProvinceByCityId(cityId);
	}

	
}

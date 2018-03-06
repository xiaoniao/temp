package com.kunyao.assistant.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Resource;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.ResourceMapper;
import com.kunyao.assistant.web.service.ResourceService;

@Service
public class ResourceServiceImpl extends GenericServiceImpl<Resource, Integer> implements ResourceService {
	
	@javax.annotation.Resource
	private ResourceMapper resourceListMapper;

	public GenericDao<Resource, Integer> getDao() {
		return resourceListMapper;
	}

	@Override
	public PageInfo listCount(Integer pageSize, Resource resource) {
		String sql = "SELECT COUNT(*) FROM kycom_r_resource r LEFT JOIN kycom_r_resource_type rt ON rt.id = r.type_id";
		StringBuffer sqlBuffer = new StringBuffer();
		appendBefore(sqlBuffer);
		sqlBuffer.append("rt.status = 1");
		
		if (resource.getProvinceId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.province_id = " + resource.getProvinceId());
		}
		if (resource.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.city_id = " + resource.getCityId());
		}
		if (resource.getTypeId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.type_id = " + resource.getTypeId());
		}
		if (!StringUtils.isNull(resource.getTitle())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.title like '%" + resource.getTitle() + "%'");
		}
		PageInfo pageInfo = null;
		try {
			pageInfo = findPageInfo(pageSize, sql + sqlBuffer.toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return pageInfo;
	}

	@Override
	public List<Resource> list(Integer currentPage, Integer pageSize, Resource resource) {
		String sql = "SELECT r.* FROM kycom_r_resource r LEFT JOIN kycom_r_resource_type rt ON rt.id = r.type_id";
		StringBuffer sqlBuffer = new StringBuffer();
		appendBefore(sqlBuffer);
		sqlBuffer.append("rt.status = 1");
		
		if (resource.getProvinceId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.province_id = " + resource.getProvinceId());
		}
		if (resource.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.city_id = " + resource.getCityId());
		}
		if (resource.getTypeId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.type_id = " + resource.getTypeId());
		}
		if (!StringUtils.isNull(resource.getTitle())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("(");
			sqlBuffer.append("r.title like '%" + resource.getTitle() + "%'");
			sqlBuffer.append(" OR ");
			sqlBuffer.append("r.content like '%" + resource.getTitle() + "%'");
			sqlBuffer.append(")");
		}
		if (currentPage != null && pageSize != null) {
			sqlBuffer.append(" limit " + PageInfo.countOffset(pageSize, currentPage) + ", " + pageSize);
		}
		List<Resource> resources = null;
		try {
			resources = findList(sql + sqlBuffer.toString(), resource);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return resources;
	}

	private void appendBefore(StringBuffer sqlBuffer) {
		if (sqlBuffer.length() == 0) {
			sqlBuffer.append(" WHERE ");
		} else {
			sqlBuffer.append(" AND ");
		}
	}
}

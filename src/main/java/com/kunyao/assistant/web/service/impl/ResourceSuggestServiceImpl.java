package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.ResourceSuggest;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.ResourceSuggestMapper;
import com.kunyao.assistant.web.service.ResourceSuggestService;

@Service
public class ResourceSuggestServiceImpl extends GenericServiceImpl<ResourceSuggest, Integer> implements ResourceSuggestService {
    @Resource
    private ResourceSuggestMapper resourceSuggestMapper;

    public GenericDao<ResourceSuggest, Integer> getDao() {
        return resourceSuggestMapper;
    }

	@Override
	public PageInfo listCount(Integer pageSize, ResourceSuggest resourceSuggest) {
		String sql = "SELECT COUNT(*) FROM kycom_r_resource_suggest r LEFT JOIN kycom_r_resource_type rt ON rt.id = r.type_id";
		StringBuffer sqlBuffer = new StringBuffer();
		appendBefore(sqlBuffer);
		sqlBuffer.append("rt.status = 1");
		
		if (resourceSuggest.getProvinceId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.province_id = " + resourceSuggest.getProvinceId());
		}
		if (resourceSuggest.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.city_id = " + resourceSuggest.getCityId());
		}
		if (resourceSuggest.getTypeId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.type_id = " + resourceSuggest.getTypeId());
		}
		if (!StringUtils.isNull(resourceSuggest.getTitle())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.title like '%" + resourceSuggest.getTitle() + "%'");
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
	public List<ResourceSuggest> list(Integer currentPage, Integer pageSize, ResourceSuggest resourceSuggest) {
		String sql = "SELECT r.* FROM kycom_r_resource_suggest r LEFT JOIN kycom_r_resource_type rt ON rt.id = r.type_id";
		StringBuffer sqlBuffer = new StringBuffer();
		appendBefore(sqlBuffer);
		sqlBuffer.append("rt.status = 1");
		
		if (resourceSuggest.getProvinceId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.province_id = " + resourceSuggest.getProvinceId());
		}
		if (resourceSuggest.getResourceId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.resource_id = " + resourceSuggest.getResourceId());
		}
		if (resourceSuggest.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.city_id = " + resourceSuggest.getCityId());
		}
		if (resourceSuggest.getTypeId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.type_id = " + resourceSuggest.getTypeId());
		}
		if (!StringUtils.isNull(resourceSuggest.getTitle())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.title like '%" + resourceSuggest.getTitle() + "%'");
		}
		if (resourceSuggest.getCheckStatus() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append("r.check_status =" + resourceSuggest.getCheckStatus());
		}
		sqlBuffer.append(" ORDER BY r.create_time DESC");
		List<ResourceSuggest> resources = null;
		try {
			resources = findList(sql + sqlBuffer.toString(), resourceSuggest);
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

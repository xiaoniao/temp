package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Resource;

public interface ResourceService extends GenericService<Resource, Integer> {

	PageInfo listCount(Integer pageSize, Resource resource);
	
	List<Resource> list(Integer currentPage, Integer pageSize, Resource resource);
}

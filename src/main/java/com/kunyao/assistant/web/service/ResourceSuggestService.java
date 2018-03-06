package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.ResourceSuggest;

public interface ResourceSuggestService extends GenericService<ResourceSuggest, Integer> {

	PageInfo listCount(Integer pageSize, ResourceSuggest resourceSuggest);

	List<ResourceSuggest> list(Integer currentPage, Integer pageSize, ResourceSuggest resourceSuggest);
}

package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.dto.GroupCrossInfoDto;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Collection;

public interface CollectionService extends GenericService<Collection, Integer> {

	List<GroupCrossInfoDto> findList(Integer userId);
	
	Collection addCollection(Integer userId, Integer crossInfoId);
	
	Boolean isCollection(Integer userId, Integer crossInfoId);
	
	void removeCollection(Integer userId, Integer crossInfoId);
}

package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.ActivityLinked;
import java.lang.Integer;

public interface ActivityLinkedService extends GenericService<ActivityLinked, Integer> {
    
	ActivityLinked create(Integer activityId, Integer userId, String couponIds) throws ServiceException;
}

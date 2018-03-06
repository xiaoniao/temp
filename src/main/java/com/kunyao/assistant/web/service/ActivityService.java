package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Activity;
import com.kunyao.assistant.core.model.ActivityLinked;

import java.lang.Integer;

public interface ActivityService extends GenericService<Activity, Integer> {
	
	/**
	 * 参加活动
	 * @param userId
	 * @param activityId
	 * @param token 参见用户标识
	 * @return
	 * @throws ServiceException
	 */
	ActivityLinked joinActivity(Integer userId, Integer activityId, String token) throws ServiceException;
}

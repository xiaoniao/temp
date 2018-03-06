package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.ActivityLinked;
import com.kunyao.assistant.web.dao.ActivityLinkedMapper;
import com.kunyao.assistant.web.service.ActivityLinkedService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ActivityLinkedServiceImpl extends GenericServiceImpl<ActivityLinked, Integer> implements ActivityLinkedService {
	@Resource
	private ActivityLinkedMapper activityLinkedMapper;

	public GenericDao<ActivityLinked, Integer> getDao() {
		return activityLinkedMapper;
	}

	@Override
	public ActivityLinked create(Integer activityId, Integer userId, String couponIds) throws ServiceException {
		ActivityLinked activityLinked = new ActivityLinked(activityId, userId, couponIds);
		insert(activityLinked);
		return activityLinked;
	}
}

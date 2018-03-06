package com.kunyao.assistant.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.ManagerInfo;
import com.kunyao.assistant.web.dao.ManagerInfoMapper;
import com.kunyao.assistant.web.service.ManagerService;

@Service
public class ManagerInfoServiceImpl extends GenericServiceImpl<ManagerInfo, Integer> implements ManagerService {
    @Resource
    private ManagerInfoMapper managerMapper;

    public GenericDao<ManagerInfo, Integer> getDao() {
        return managerMapper;
    }

	@Override
	public ManagerInfo findByUserId(Integer userId) {
		return managerMapper.findByUserId(userId);
	}
}

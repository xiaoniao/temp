package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.ManagerInfo;

import java.lang.Integer;

public interface ManagerService extends GenericService<ManagerInfo, Integer> {

	ManagerInfo findByUserId(Integer userId);
}

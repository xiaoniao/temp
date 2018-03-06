package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.model.UserRole;

public interface UserRoleService extends GenericService<UserRole, Integer> {

	/**
	 * 查询子管理员帐号
	 */
	List<User> findSubManage() throws ServiceException;
}

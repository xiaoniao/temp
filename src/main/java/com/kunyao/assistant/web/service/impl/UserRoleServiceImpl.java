package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.model.UserRole;
import com.kunyao.assistant.web.dao.UserRoleMapper;
import com.kunyao.assistant.web.service.UserRoleService;
import com.kunyao.assistant.web.service.UserService;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends GenericServiceImpl<UserRole, Integer> implements UserRoleService {
	@Resource
	private UserRoleMapper userRoleMapper;

	@Resource
	private UserService userService;

	public GenericDao<UserRole, Integer> getDao() {
		return userRoleMapper;
	}

	@Override
	public List<User> findSubManage() throws ServiceException {
		List<User> result = new ArrayList<>();
		List<UserRole> userRoles = userRoleMapper.findSubManage();
		for (UserRole userRole : userRoles) {
			User user = userService.findByID(userRole.getUserId());
			if (user.getUsername().equals("admin")) {
				continue;
			}
			result.add(user);
		}
		return result;
	}
}

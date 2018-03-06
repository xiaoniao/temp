package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Permission;

public interface PermissionService extends GenericService<Permission, Integer> {
	
	/**
	 * 通过角色id 查询角色 拥有的权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<Permission> findPermissionsByRoleId(Integer roleId);
}

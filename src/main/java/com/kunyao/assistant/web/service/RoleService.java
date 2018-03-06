package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Role;

public interface RoleService extends GenericService<Role, Integer> {

	/**
	 * 添加角色
	 */
	public void addRole();
	
	/**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    List<Role> findRolesByUserId(Integer userId);
}

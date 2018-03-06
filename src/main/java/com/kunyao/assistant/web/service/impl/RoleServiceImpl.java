package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Role;
import com.kunyao.assistant.web.dao.RoleMapper;
import com.kunyao.assistant.web.service.RoleService;
import java.lang.Integer;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Integer> implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    public GenericDao<Role, Integer> getDao() {
        return roleMapper;
    }

    /**
     * 添加角色
     */
    @Override
    public void addRole() {
    	Role role = new Role("管理员", "后台管理员", "admin", 0);
    	roleMapper.insert(role);
    }

	@Override
	public List<Role> findRolesByUserId(Integer userId) {
		return roleMapper.findRolesByUserId(userId);
	}
}

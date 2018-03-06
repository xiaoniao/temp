package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Permission;
import com.kunyao.assistant.web.dao.PermissionMapper;
import com.kunyao.assistant.web.service.PermissionService;
import java.lang.Integer;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends GenericServiceImpl<Permission, Integer> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    public GenericDao<Permission, Integer> getDao() {
        return permissionMapper;
    }

	@Override
	public List<Permission> findPermissionsByRoleId(Integer roleId) {
		return permissionMapper.findPermissionsByRoleId(roleId);
	}
}

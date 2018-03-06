package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.RolePermission;
import com.kunyao.assistant.web.dao.RolePermissionMapper;
import com.kunyao.assistant.web.service.RolePermissionService;
import java.lang.Integer;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends GenericServiceImpl<RolePermission, Integer> implements RolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    public GenericDao<RolePermission, Integer> getDao() {
        return rolePermissionMapper;
    }
}

package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Permission;

public interface PermissionMapper extends GenericDao<Permission, Integer> {
	
	final String FIND_PERMISSIONS_BY_ROLEID = 
			"SELECT * FROM kycom_u_permission p " + 
            "LEFT JOIN kycom_u_role_permission rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}";
	
	/**
     * 通过角色id 查询角色 拥有的权限
     * 
     * @param roleId
     * @return
     */
	@Select(FIND_PERMISSIONS_BY_ROLEID)
    List<Permission> findPermissionsByRoleId(@Param("roleId") Integer roleId);
}

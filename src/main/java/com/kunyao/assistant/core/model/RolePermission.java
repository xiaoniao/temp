package com.kunyao.assistant.core.model;

import java.lang.Integer;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
/**
 * 角色权限
 */
public class RolePermission implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -8194947669916565477L;

	/**
     * 角色id */
    private Integer roleId;
    
    /**
     * 权限id */
    private Integer permission_id;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

	public Integer getPermission_id() {
		return permission_id;
	}

	public void setPermission_id(Integer permission_id) {
		this.permission_id = permission_id;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}
}

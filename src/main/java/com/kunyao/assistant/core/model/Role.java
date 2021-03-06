package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.lang.String;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 角色
 */
public class Role implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 5027678344676309701L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 权限描述 */
    private String description;

    /**
     * 权限名称 */
    private String name;

    /**
     * 权限标识 */
    private String sign;

    /**
     * 权限状态 */
    private Integer status;
    
    public Role() {
    	
	}
    
    public Role(String name, String description, String sign, Integer status) {
    	this.name = name;
		this.description = description;
		this.sign = sign;
		this.status = status;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String getTablePrefixName() {
		return "u";
	}
}

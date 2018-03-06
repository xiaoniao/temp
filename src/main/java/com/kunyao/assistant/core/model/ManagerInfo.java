package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.lang.String;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 后台管理员/客服
 */
public class ManagerInfo implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 8658332828542186577L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 姓名 */
    private String name;

    /**
     * 职位 */
    private Integer positionId;
    
    /**
     * 所属省份 为空代表全国 */
    private Integer provinceId;
    
    /**
     * 所属城市 为空代表某省下面全部城市 */
    private Integer cityId;
    
    /**
     * 所属资源分类id */
    private String resourceTypeIds;
    
    /**
     * userid */
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getResourceTypeIds() {
		return resourceTypeIds;
	}

	public void setResourceTypeIds(String resourceTypeIds) {
		this.resourceTypeIds = resourceTypeIds;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}
}

package com.kunyao.assistant.core.model;

import javax.validation.constraints.NotNull;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 资源分类
 */
public class ResourceType implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 1903741718179890816L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 分类名称 */
	@NotNull
    private String name;

    /**
     * 状态 */
    private Integer status;
    
    /**
     * VO
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
	private Integer isShowNumber; // 是否显示分类下数量
    @ModelMeta(fieldType = ModelFieldType.VO)
   	private Integer cityId; // 城市id
    @ModelMeta(fieldType = ModelFieldType.VO)
	private Integer number; // 分类下数量
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String title;
    
    public ResourceType() {
    	
	}
    
    public ResourceType(String name) {
		this.name = name;
		this.status = BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
	}

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Integer getIsShowNumber() {
		return isShowNumber;
	}

	public void setIsShowNumber(Integer isShowNumber) {
		this.isShowNumber = isShowNumber;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getTablePrefixName() {
		return "r";
	}
}

package com.kunyao.assistant.core.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 资源建议评论
 * 资源素材
 */
public class ResourceSuggest implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 161320210950456919L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

	/**
     * 省份id */
	@NotNull
    private Integer provinceId;
	
    /**
     * 城市id */
	@NotNull
    private Integer cityId;
	
	/**
     * 资源分类id */
	private Integer typeId;
	
	/**
     * 资源文章id */
    private Integer resourceId;
	
    /**
     * 标题 */
	@NotEmpty
    private String title;

    /**
     * 内容 */
    private String content;

    /**
     * 多图 */
    private String images;

    /**
     * 创建时间 */
    private Date createTime;

    /**
     * 提交人 */
    @NotNull
    private Integer submitUserId;

    /**
     * 审核状态 0等待审核 1未通过 2通过 */
    private Integer checkStatus;

    /**
     * VO
     */
    @ModelMeta(fieldType = ModelFieldType.ID)
    private String cityName;

    @ModelMeta(fieldType = ModelFieldType.ID)
    private String provinceName;
    
    @ModelMeta(fieldType = ModelFieldType.ID)
    private String provinceAndcityName;
    
    @ModelMeta(fieldType = ModelFieldType.ID)
    private String typeName;
    
    @ModelMeta(fieldType = ModelFieldType.ID)
    private String submitUserName;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate; 
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String[] imageList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String[] getImageList() {
		return imageList;
	}

	public void setImageList(String[] imageList) {
		this.imageList = imageList;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(Integer submitUserId) {
        this.submitUserId = submitUserId;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceAndcityName() {
		return provinceAndcityName;
	}

	public void setProvinceAndcityName(String provinceAndcityName) {
		this.provinceAndcityName = provinceAndcityName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSubmitUserName() {
		return submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseYMDHMTime(createDate);
	}
	
	@Override
	public String getTablePrefixName() {
		return "r";
	}
}

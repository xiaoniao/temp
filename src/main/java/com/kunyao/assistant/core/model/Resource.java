package com.kunyao.assistant.core.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;

/**
 * 资源
 */
public class Resource implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 1889338151071232932L;
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
     * 分类id */
	@NotNull
    private Integer typeId;

    /**
     * 标题 */
	@NotEmpty
    private String title;

    /**
     * 内容 */
	@NotEmpty
    private String content;
	
	/**
     * 多图 */
    private String images;

    /**
     * 状态 1显示 0不显示 */
    private Integer status;

    /**
     * 创建时间 */
    private Date createTime;

    /**
     * 更新时间 */
    private Date updateTime;
    
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
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;

    @ModelMeta(fieldType = ModelFieldType.VO)
    private String updateDate;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private List<String> imageList;

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

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseYMDHMTime(createDate);
	}
	
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = DateUtils.parseYMDHMTime(updateDate);
	}
	
	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String getTablePrefixName() {
		return "r";
	}
	
	/**
	 * 修改资源
	 */
	public void copyFromNotNullValue(Resource copyParam) {
		if (copyParam.getProvinceId() != null) {
			setCityId(copyParam.getProvinceId());
		}
		if (copyParam.getCityId() != null) {
			setCityId(copyParam.getCityId());
		}
		if (copyParam.getTypeId() != null) {
			setTypeId(copyParam.getTypeId());
		}
		if (!StringUtils.isNull(copyParam.getTitle())) {
			setTitle(copyParam.getTitle());
		}
		if (!StringUtils.isNull(copyParam.getContent())) {
			setContent(copyParam.getContent());
		}
	}
}

package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 收藏金鹰
 */
public class Collection implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 3656471200506885183L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 用户id */
    private Integer userId;

    /**
     * 金鹰id crossInfo表id */
    private Integer crossInfoId;

    /**
     * 收藏时间 */
    private Date collectionTime;

    public Collection() {
    	
	}
    
    public Collection(Integer userId, Integer crossInfoId) {
    	this.userId = userId;
    	this.crossInfoId = crossInfoId;
    	this.collectionTime = new Date();
	}
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCrossInfoId() {
		return crossInfoId;
	}

	public void setCrossInfoId(Integer crossInfoId) {
		this.crossInfoId = crossInfoId;
	}

	public Date getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(Date collectionTime) {
        this.collectionTime = collectionTime;
    }

	@Override
	public String getTablePrefixName() {
		return "u";
	}
}

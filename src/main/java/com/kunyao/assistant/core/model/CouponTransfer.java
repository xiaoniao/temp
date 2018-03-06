package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 优惠券转移赠送
 */
public class CouponTransfer implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 3917983877856353196L;

	private Integer id;

    /**
     * 优惠券id */
    private Integer couponId;

    /**
     * 原始会员userid */
    private Integer originalUserId;

    /**
     * 新会员userid */
    private Integer newUserId;

    /**
     * 创建时间 */
    private Date createTime;
    
    /**
     * VO
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getOriginalUserId() {
        return originalUserId;
    }

    public void setOriginalUserId(Integer originalUserId) {
        this.originalUserId = originalUserId;
    }

    public Integer getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(Integer newUserId) {
        this.newUserId = newUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseFullTime(createDate);
	}
	
	@Override
	public String getTablePrefixName() {
		return "u";
	}
}

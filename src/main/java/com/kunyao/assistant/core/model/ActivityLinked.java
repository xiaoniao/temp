package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 用户参与活动
 */
public class ActivityLinked implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -2458119925613973188L;

	private Integer id;

    /**
     * 活动id */
    private Integer activityId;

    /**
     * 会员id */
    private Integer userId;

    /**
     * 优惠券id以逗号分割，参加活动时设置，代码硬编码写入优惠券 */
    private String couponIds;
    
    /**
     * 创建时间 */
    private Date createTime;
    
    /**
     * VO
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;

    public ActivityLinked() {
    	
	}
    
    public ActivityLinked(Integer activityId, Integer userId, String couponIds) {
		this.activityId = activityId;
		this.userId = userId;
		this.couponIds = couponIds;
		this.createTime = new Date();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(String couponIds) {
        this.couponIds = couponIds;
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

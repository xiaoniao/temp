package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 公司评价
 */
public class CompanyComment implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -7472285429349710644L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 订单id */
    private Integer orderId;

    /**
     * 会员id */
    private Integer userId;

    /**
     * 评价内容 一般、良好、优秀、卓越 */
    private String content;
    
    /**
     * 创建时间 */
    private Date createTime;

    /*************VO***********/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer cityId;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String cityName;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String starDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Date orderStartTime;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String orderStartDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String bankMobile;
    
    public CompanyComment() {
	}

	public CompanyComment(Integer orderId, Integer userId, String content) {
		this.orderId = orderId;
		this.userId = userId;
		this.content = content;
		this.createTime = new Date();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
	
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStarDate() {
		return starDate;
	}

	public void setStarDate(String starDate) {
		this.starDate = starDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Date getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
		
	}

	public String getOrderStartDate() {
		return orderStartDate;
	}

	public void setOrderStartDate(Date orderStartDate) {
		this.orderStartDate = DateUtils.parseFullTime(orderStartDate);
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}
}

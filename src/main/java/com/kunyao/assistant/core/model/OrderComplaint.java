package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 订单投诉
 */
public class OrderComplaint implements GenericModel{
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 2590165582347553213L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 用户id */
    private Integer userId;

    /**
     * 订单 */
    private Integer orderId;

    /**
     * 备注 */
    private String remark;

    /**
     * 0 未处理 1已处理 */
    private Integer status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**************VO**************/
    @ModelMeta(fieldType = ModelFieldType.ID)
    private String createDate;
    
    public OrderComplaint(){
    	
    }
    
    public OrderComplaint(Integer userId, Integer orderId, String remark){
    	this.userId = userId;
    	this.orderId = orderId;
    	this.remark = remark;
    	this.status = 0;
    	this.createTime = new Date();
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String getTablePrefixName() {
		return "o";
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
		this.createDate = DateUtils.parseYMDHMTime(createDate);
	}
}

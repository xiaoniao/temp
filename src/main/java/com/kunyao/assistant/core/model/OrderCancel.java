package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 订单取消申请
 */
public class OrderCancel implements GenericModel{
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 1L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * userid 金鹰或用户userid */
    private Integer userId;
    
    /**
     * 订单id */
    private Integer orderId;

    /**
     * 0 未处理 1已处理 */
    private Integer status;

    /**
     * 备注 */
    private String remark;
    
    /**
     * 取消对象 0 金鹰取消  1 用户取消 */
    private Integer type;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**************VO**************/
    @ModelMeta(fieldType = ModelFieldType.ID)
    private String createDate;
    
    /**
     * with a new Date()
     * @param orderId
     * @param userId
     * @param remark
     * @param type
     */
    public OrderCancel(Integer orderId, Integer userId, String remark, Integer type){
    	this.userId = userId;
    	this.orderId = orderId;
    	this.remark = remark;
    	this.status = 0;            // 0是未处理
    	this.type = type;
    	this.createTime = new Date();
    }
    
    public OrderCancel(){
    	
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

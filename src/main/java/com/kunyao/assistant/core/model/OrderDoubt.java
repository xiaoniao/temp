package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 账单疑义
 * 对订单某一天的账单有疑义
 */
public class OrderDoubt implements GenericModel{
	
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 9048528259034831021L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

	/**
     * userid / 金鹰或者用户 */
    private Integer userId;

    /**
     * 订单id */
    private Integer orderId;
    
    /**
     * 账单
     * 某一天
     */
    private Date dateOfDoubtTime;
    
    /**
     * 0 未处理 1已处理 */
    private Integer status;

    /**
     * 备注 */
    private String remark;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**************VO**************/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String dateOfDoubtDate;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String stayDate;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    
    public OrderDoubt() {
	}

	public OrderDoubt(Integer userId, Integer orderId, Date dateOfDoubtTime, Integer status, Date createTime) {
		this.userId = userId;
		this.orderId = orderId;
		this.dateOfDoubtTime = dateOfDoubtTime;
		this.status = status;
		this.createTime = createTime;
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

	public Date getDateOfDoubtTime() {
		return dateOfDoubtTime;
	}

	public void setDateOfDoubtTime(Date dateOfDoubtTime) {
		this.dateOfDoubtTime = dateOfDoubtTime;
	}

	public String getDateOfDoubtDate() {
		return dateOfDoubtDate;
	}

	public void setDateOfDoubtDate(Date dateOfDoubtDate) {
		this.dateOfDoubtDate = DateUtils.parseYMDTime(dateOfDoubtDate);
	}

	public String getStayDate() {
		return stayDate;
	}

	public void setStayDate(Date stayDate) {
		this.stayDate = DateUtils.parseYMDTime(stayDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = DateUtils.parseYMDTime(endDate);
	}
}

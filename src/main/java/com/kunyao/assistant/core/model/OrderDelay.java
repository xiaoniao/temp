package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 订单延迟
 */
public class OrderDelay implements GenericModel{
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 6994494499562086137L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 用户id */
    private Integer userId;

    /**
     * 订单id */
    private Integer orderId;
    
    /**
     * 延迟时间 */
    private Integer hour;
    
    /**
     * 支付金额 */
    private Double money;

    /**
     * 支付时间 */
    private Date createTime;

    /**
     * 第三方交易号 */
    private String tradeNo;

    /**
     * 支付方式 */
    private Integer payMethod;

    /**
     * 0 未支付 1 支付成功 */
    private Integer status;
    
    /*******************VO*******************/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    
    public OrderDelay() {
		super();
	}

	public OrderDelay(Integer userId, Integer orderId, Integer hour, Double money, Integer payMethod, Integer status) {
		super();
		this.userId = userId;
		this.orderId = orderId;
		this.hour = hour;
		this.money = money;
		this.payMethod = payMethod;
		this.status = status;
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

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseFullTime(createDate);
	}
}

package com.kunyao.assistant.core.model;

import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 订单支付记录
 */
public class OrderPayRecord implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 2582850079260490297L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 订单id */
    private Integer orderId;

    /**
     * 支付方式 */
    private Integer payMethod;

    /**
     * 金额 */
    private Double price;

    /**
     * 第三方交易号 例如支付宝交易号 */
    private String tradeNo;

    /**
     * 通知时间 */
    private Date notifyTime;

    public OrderPayRecord(Integer orderId, Integer payMethod, Double price, String tradeNo, Date notifyTime) {
		this.orderId = orderId;
		this.payMethod = payMethod;
		this.price = price;
		this.tradeNo = tradeNo;
		this.notifyTime = notifyTime;
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

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

	@Override
	public String getTablePrefixName() {
		return "o";
	}
}

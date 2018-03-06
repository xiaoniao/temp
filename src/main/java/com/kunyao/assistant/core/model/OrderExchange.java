package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 订单交换记录 用户中途更换金鹰
 */
public class OrderExchange implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 7114203368159170726L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 旧订单id */
    private Integer oldOrderId;

    /**
     * 新订单id */
    private Integer newOrderId;

    /**
     * 交换时间 */
    private Date exchangeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOldOrderId() {
        return oldOrderId;
    }

    public void setOldOrderId(Integer oldOrderId) {
        this.oldOrderId = oldOrderId;
    }

    public Integer getNewOrderId() {
        return newOrderId;
    }

    public void setNewOrderId(Integer newOrderId) {
        this.newOrderId = newOrderId;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

	@Override
	public String getTablePrefixName() {
		return "o";
	}
}

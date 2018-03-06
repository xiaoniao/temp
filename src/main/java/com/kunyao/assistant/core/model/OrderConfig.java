package com.kunyao.assistant.core.model;

import java.lang.Double;
import java.lang.Integer;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 订单配置
 */
public class OrderConfig implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -4969745004703220679L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

	/**
	 * 单天服务费用 */
	private Double serviceMoney;

	/**
	 * 延时定价 */
	private Double delayMoney;

	/**
	 * 订单轮训时间 */
	private Integer loopTime;
	
	/**
	 * 单天服务费使用优惠券最大可抵扣金额 */
	private Double maxDiscount;
	
	/**
	 * 能量柱计分：代支付满分金额 */
	private Double fullPayment;
	
	/**
	 * 能量柱评分：余额满分金额 */
	private Double fullBalance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(Double serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public Double getDelayMoney() {
		return delayMoney;
	}

	public void setDelayMoney(Double delayMoney) {
		this.delayMoney = delayMoney;
	}

	public Integer getLoopTime() {
		return loopTime;
	}

	public void setLoopTime(Integer loopTime) {
		this.loopTime = loopTime;
	}

	public Double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public Double getFullPayment() {
		return fullPayment;
	}

	public void setFullPayment(Double fullPayment) {
		this.fullPayment = fullPayment;
	}

	public Double getFullBalance() {
		return fullBalance;
	}

	public void setFullBalance(Double fullBalance) {
		this.fullBalance = fullBalance;
	}

	@Override
	public String getTablePrefixName() {
		return "o";
	}
}

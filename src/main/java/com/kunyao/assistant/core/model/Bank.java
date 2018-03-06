package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 绑定银行卡
 */
public class Bank implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -6298476437171754673L;
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 用户id */
    private Integer userId;

    /**
     * 持卡人 */
    private String userName;

    /**
     * 银行名称 */
    private String bankName;
    
    /**
     * 银行卡类型 */
    private String cardType;
    
    /**
     * 银行卡号 */
    private String bankCard;

    /**
     * 创建时间 */
    private Date createTime;
    
    /**
     * 状态 */
    private Integer status;
    
	public Bank() {
		
	}
	
    public Bank(Integer userId, String userName, String banCard, String banName, String cardType) {
		this.userId = userId;
		this.userName = userName;
		this.bankCard = banCard;
		this.status = BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
		this.createTime = new Date();
		this.bankName = banName;
		this.cardType = cardType;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String getTablePrefixName() {
		return "t";
	}
}

package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.AccountRecordEnum;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 余额变动记录
 */
public class AccountRecord implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -2565851126477607036L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 会员id */
    private Integer userId;

    /**
     * 订单id */
    private Integer orderId;

    /**
     * 金额 */
    private Double money;

    /**
     * 类型 0充值 1服务费 2延时费 3退款 */
    private Integer type;

    /**
     * 操作时间 */
    private Date operateTime;

    /*** VO **/
    /**
     * 订单编号 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String orderCard;
    
    /**
     * 会员帐号 等同于会员帐号 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String bankMobile;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String typeStr;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String operateTimeStr;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String operateDate;
    
    public AccountRecord(){
    	
    }
    
    public AccountRecord(Integer userId, Integer orderId, Double money, Integer type, Date operateTime) {
		this.userId = userId;
		this.orderId = orderId;
		this.money = money;
		this.type = type;
		this.operateTime = operateTime;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

	public String getOrderCard() {
		return orderCard;
	}

	public void setOrderCard(String orderCard) {
		this.orderCard = orderCard;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getOperateTimeStr() {
		return operateTimeStr;
	}

	public void setOperateTimeStr(String operateTimeStr) {
		this.operateTimeStr = operateTimeStr;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = DateUtils.parseFullTime(operateDate);
	}

	@Override
	public String getTablePrefixName() {
		return "a";
	}
	
	public void dto() {
		if (this.type.equals(AccountRecordEnum.CHARGE.getValue())) {
			this.typeStr = "充值";
		} else if (this.type.equals(AccountRecordEnum.SERVICE_COST.getValue())) {
			this.typeStr = "服务费";
		} else if (this.type.equals(AccountRecordEnum.DELAY_COST.getValue())) {
			this.typeStr = "延时费";
		} else if (this.type.equals(AccountRecordEnum.REFUND.getValue())) {
			this.typeStr = "退款";
		} else if (this.type.equals(AccountRecordEnum.ORDER_COST.getValue())) {
			this.typeStr = "账单扣款";
		} else if (this.type.equals(AccountRecordEnum.WITHDRAW.getValue())) {
			this.typeStr = "提现";
		}
		this.operateTimeStr = DateUtils.parseFullTime(this.operateTime);
	}
}

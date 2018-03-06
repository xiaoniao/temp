package com.kunyao.assistant.core.model;

import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 提现
 */
public class TakeCash implements GenericModel{
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -7811124239484454805L;

	@ModelMeta(fieldType = ModelFieldType.ID)
    private Integer id;

    /**
     * 用户id */
    private Integer userId;

    /**
     * 提现金额 */
    private Double money;

    /**
     * 第三方交易号 */
    private String tradeNo;

    /**
     * 提现状态 0提现中 1提现成功 */
    private Integer status;

    /**
     * 创建时间 */
    private Date createTime;
    
    /** VO **/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String bankMobile;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String methodStr;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String statusStr;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String starDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;

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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getMethodStr() {
		return methodStr;
	}

	public void setMethodStr(String methodStr) {
		this.methodStr = methodStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseFullTime(createDate);
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

	@Override
	public String getTablePrefixName() {
		return "a";
	}
	
	public void dto() {
		if (this.status == 0) {
			this.statusStr = "提现中";
		} else if (this.status == 1) {
			this.statusStr = "提现成功";
		}
		if (this.createTime != null) {
			this.createDate = DateUtils.parseFullTime(this.createTime);
		}
	}
}

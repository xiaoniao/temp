package com.kunyao.assistant.core.model;

import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.enums.PayEnum;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 充值记录
 */
public class Recharge implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -467426930397260908L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 用户id */
    private Integer userId;

    /**
     * 充值金额 */
    private Double money;

    /**
     * 充值时间 */
    private Date createTime;
    
    /**
     * 充值方式 */
    private Integer payMethod;

    /**
     * 第三方交易号 */
    private String tradeNo;
    
    /**
     * 是否需要发票 */
    private Integer isNeedInvoice;

    /**
     * 发票抬头 */
    private String invoiceTitle;

    /**
     * 邮寄地址 */
    private String invoiceAddress;

    /**
     * 邮寄收件人 */
    private String invoicePeople;

    /**
     * 邮寄联系方式 */
    private String invoiceMobile;
    
    /**
     * 0 未支付 1 支付成功 */
    private Integer status;
    
    /** VO **/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String bankMobile;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createTimeStr;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String payMethodStr;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String statusStr;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String starDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    
    public Recharge() {
	}

	/** 生成充值订单 */
	public Recharge(Integer userId, Double money, Date createTime, Integer payMethod, Integer isNeedInvoice,
			String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile, Integer status) {
		this.userId = userId;
		this.money = money;
		this.createTime = createTime;
		this.payMethod = payMethod;
		this.isNeedInvoice = isNeedInvoice;
		this.invoiceTitle = invoiceTitle;
		this.invoiceAddress = invoiceAddress;
		this.invoicePeople = invoicePeople;
		this.invoiceMobile = invoiceMobile;
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

    public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
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

	public Integer getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(Integer isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getInvoicePeople() {
		return invoicePeople;
	}

	public void setInvoicePeople(String invoicePeople) {
		this.invoicePeople = invoicePeople;
	}

	public String getInvoiceMobile() {
		return invoiceMobile;
	}

	public void setInvoiceMobile(String invoiceMobile) {
		this.invoiceMobile = invoiceMobile;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getPayMethodStr() {
		return payMethodStr;
	}

	public void setPayMethodStr(String payMethodStr) {
		this.payMethodStr = payMethodStr;
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

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}
	
	public void dto() {
		if (this.createTime != null) {
			this.createTimeStr = DateUtils.parseFullTime(this.createTime);
		}
		if (this.status.equals(PayEnum.FINISHPAY.getValue())) {
			this.statusStr = PayEnum.FINISHPAY.getText();
		} else if (this.status.equals(PayEnum.UNPAY.getValue())) {
			this.statusStr = PayEnum.UNPAY.getText();
		}
		if (this.payMethod.equals(OrderEnum.ServicePayMethod.BALANCE.getValue())) {
			this.payMethodStr = OrderEnum.ServicePayMethod.BALANCE.getText();
		} else if (this.payMethod.equals(OrderEnum.ServicePayMethod.WXPAY_JS.getValue())) {
			this.payMethodStr = OrderEnum.ServicePayMethod.WXPAY_JS.getText();
		} else if (this.payMethod.equals(OrderEnum.ServicePayMethod.WXPAY_APP.getValue())) {
			this.payMethodStr = OrderEnum.ServicePayMethod.WXPAY_APP.getText();
		} else if (this.payMethod.equals(OrderEnum.ServicePayMethod.ALIPAY.getValue())) {
			this.payMethodStr = OrderEnum.ServicePayMethod.ALIPAY.getText();
		} else if (this.payMethod.equals(OrderEnum.ServicePayMethod.BANK.getValue())) {
			this.payMethodStr = OrderEnum.ServicePayMethod.BANK.getText();
		}
	}
}

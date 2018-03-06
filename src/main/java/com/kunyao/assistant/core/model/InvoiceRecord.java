package com.kunyao.assistant.core.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.InvoiceEnum;
import com.kunyao.assistant.core.generic.GenericModel;

public class InvoiceRecord implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -6988183758692834858L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

	/**
	 * 发票抬头
	 */
	private String title;

	/**
	 * 发票类型 0普通发票 1增值税发票
	 */
	private Integer type;

	/**
	 * 企业名称
	 */
	private String companyName;

	/**
	 * 识别号
	 */
	private String identification;

	/**
	 * 信用代码
	 */
	private String code;

	/**
	 * 注册地址
	 */
	private String address;

	/**
	 * 联系电话
	 */
	private String mobile;

	/**
	 * 开户行
	 */
	private String bankName;

	/**
	 * 开户行帐号
	 */
	private String bankCard;

	/**
	 * 收件人姓名
	 */
	private String receiverName;

	/**
	 * 收件人手机号
	 */
	private String receiverMobile;

	/**
	 * 收件人地址
	 */
	private String receiverAddress;

	/**
	 * 会员id
	 */
	private Integer userId;

	/**
	 * 快递公司
	 */
	private String expressName;

	/**
	 * 快递单号
	 */
	private String expressCode;

	/**
	 * 开票金额
	 */
	private Double money;

	/**
	 * 开票时间
	 */
	private Date createTime;

	/**
	 * order|订单id
	 * recharge|充值id
	 * 
	 * 目前只存订单id，不加前缀
	 */
	private String referenceId;
	
	/**
	 * 开票状态 0待寄出 1已寄出
	 */
	private Integer status;
	
	/*** VO ****/
	@ModelMeta(fieldType = ModelFieldType.VO)
	private String typeStr;
	@ModelMeta(fieldType = ModelFieldType.VO)
	private String statusStr;
	@ModelMeta(fieldType = ModelFieldType.VO)
	private String createTimeStr;

	public InvoiceRecord() {

	}

	/*
	 * 普通发票
	 */
	public InvoiceRecord(String title, String receiverName, String receiverMobile, String receiverAddress,
			Integer userId, String referenceId) {
		this.title = title;
		this.receiverName = receiverName;
		this.receiverMobile = receiverMobile;
		this.receiverAddress = receiverAddress;
		this.userId = userId;
		this.type = InvoiceEnum.Type.NORMAL.getValue();
		this.money = 0.0;
		this.createTime = new Date();
		this.status = InvoiceEnum.Status.WAIT_SEND.getValue();
		this.referenceId = referenceId;
	}

	/**
	 * 增值税发票
	 */
	public InvoiceRecord(String companyName, String identification, String code, String address, String mobile,
			String bankName, String bankCard, String receiverName, String receiverMobile, String receiverAddress,
			Integer userId, String referenceId) {
		this.type = InvoiceEnum.Type.VALUEADDED.getValue();
		this.companyName = companyName;
		this.identification = identification;
		this.code = code;
		this.address = address;
		this.mobile = mobile;
		this.bankName = bankName;
		this.bankCard = bankCard;
		
		this.receiverName = receiverName;
		this.receiverMobile = receiverMobile;
		this.receiverAddress = receiverAddress;
		
		this.userId = userId;
		this.money = 0.0;
		this.createTime = new Date();
		this.status = InvoiceEnum.Status.WAIT_SEND.getValue();
		this.referenceId = referenceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
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

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}
	
	public void dto() {
		if (this.type.intValue() == InvoiceEnum.Type.NORMAL.getValue()) {
			typeStr = InvoiceEnum.Type.NORMAL.getText();
		} else {
			typeStr = InvoiceEnum.Type.VALUEADDED.getText();
			this.title = this.companyName;
		}
		if (this.status.intValue() == InvoiceEnum.Status.WAIT_SEND.getValue()) {
			statusStr = InvoiceEnum.Status.WAIT_SEND.getText();
		} else {
			statusStr = InvoiceEnum.Status.SENDED.getText();
		}
		SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		createTimeStr = FULL_FORMAT.format(this.createTime);
	}

	@Override
	public String toString() {
		return "InvoiceRecord [id=" + id + ", title=" + title + ", type=" + type + ", companyName=" + companyName
				+ ", identification=" + identification + ", code=" + code + ", address=" + address + ", mobile="
				+ mobile + ", bankName=" + bankName + ", bankCard=" + bankCard + ", receiverName=" + receiverName
				+ ", receiverMobile=" + receiverMobile + ", receiverAddress=" + receiverAddress + ", userId=" + userId
				+ ", expressName=" + expressName + ", expressCode=" + expressCode + ", money=" + money + ", createTime="
				+ createTime + ", status=" + status + ", typeStr=" + typeStr + ", statusStr=" + statusStr
				+ ", createTimeStr=" + createTimeStr + "]";
	}
}

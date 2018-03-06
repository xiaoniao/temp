package com.kunyao.assistant.core.entity.wx;

import com.kunyao.assistant.core.feature.weixin.WxUtils;

public class WXOrder {

	private Integer orderId;
	private String orderCard;
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String packageValue;
	private String signType;
	private String paySign;
	private String sendUrl;
	private String agent;

	public WXOrder() {

	}

	public WXOrder(Integer orderId, String appId, String timeStamp, String nonceStr, String packageValue,
			String signType, String sendUrl, String userAgent) {
		this.orderId = orderId;
		this.appId = appId;
		this.timeStamp = timeStamp;
		this.nonceStr = nonceStr;
		this.packageValue = packageValue;
		this.signType = signType;
		this.sendUrl = sendUrl;

		char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
		this.agent = new String(new char[] { agent });

		String charEncoding = "UTF-8";
		this.paySign = WxUtils.createWXPaySign(charEncoding, appId, timeStamp, nonceStr, packageValue, signType);
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderCard() {
		return orderCard;
	}

	public void setOrderCard(String orderCard) {
		this.orderCard = orderCard;
	}
}

package com.kunyao.assistant.core.model;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.kunyao.assistant.core.feature.weixin.WxSettings;
import com.kunyao.assistant.core.feature.weixin.WxUtils;

public class WXOrderApp {

	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String result_code;
	private String trade_type;
	private String prepay_id;
	private String timestamp;
	private String preResultSign;

	public WXOrderApp(String appid, String mch_id, String device_info, String nonce_str, String sign,
			String result_code, String trade_type, String prepay_id) {
		this.appid = appid;
		this.mch_id = mch_id;
		this.device_info = device_info;
		this.nonce_str = nonce_str;
		this.result_code = result_code;
		this.trade_type = trade_type;
		this.prepay_id = prepay_id;
		this.timestamp = Long.toString(new Date().getTime() / 1000);
		this.sign = sign;
		
		// 对结果进行编码
		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("appid", WxSettings.APP_ID);
		map.put("partnerid", this.mch_id);
		map.put("prepayid", this.prepay_id);
		map.put("package", "Sign=WXPay");
		map.put("noncestr", this.nonce_str);
		map.put("timestamp", this.timestamp);
		this.preResultSign = WxUtils.createWXOrderPaySign("UTF-8", map);
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPreResultSign() {
		return preResultSign;
	}

	public void setPreResultSign(String preResultSign) {
		this.preResultSign = preResultSign;
	}

	@Override
	public String toString() {
		return "WXOrderApp [appid=" + appid + ", mch_id=" + mch_id + ", device_info=" + device_info + ", nonce_str="
				+ nonce_str + ", sign=" + sign + ", result_code=" + result_code + ", trade_type=" + trade_type
				+ ", prepay_id=" + prepay_id + ", timestamp=" + timestamp + ", preResultSign=" + preResultSign + "]";
	}
}

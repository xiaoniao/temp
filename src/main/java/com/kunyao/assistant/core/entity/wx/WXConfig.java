package com.kunyao.assistant.core.entity.wx;

/**
 * 公众号配置
 */
public class WXConfig {

	private String appId = "";         // 必填，公众号的唯一标识
	private long timestamp = 0L;       // 必填，生成签名的时间戳
	private String noncestr = "";      // 必填，生成签名的随机串
	private String signature = "";     // 必填，签名，见附录1

	public WXConfig() {

	}

	public WXConfig(String appId, long timestamp, String noncestr, String signature) {
		super();
		this.appId = appId;
		this.timestamp = timestamp;
		this.noncestr = noncestr;
		this.signature = signature;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "WXConfig [appId=" + appId + ", timestamp=" + timestamp + ", noncestr=" + noncestr + ", signature="
				+ signature + "]";
	}
}

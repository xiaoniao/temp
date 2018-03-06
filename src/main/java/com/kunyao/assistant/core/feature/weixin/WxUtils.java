package com.kunyao.assistant.core.feature.weixin;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.kunyao.assistant.core.entity.wx.WXConfig;
import com.kunyao.assistant.core.utils.HttpUtils;
import com.kunyao.assistant.core.utils.MD5Utils;
import com.kunyao.assistant.core.utils.StringUtils;

import net.sf.json.JSONObject;

/**
 * 微信常用方法
 * 
 * @author GeNing
 * @since 2016.08.23
 */
public class WxUtils {

	/**
	 * 刷新微信Token
	 */
	public static void refereshWXToken() {
		long nowTime = new Date().getTime();
		if (nowTime - WxSettings.GET_TIME < WxSettings.VALID_TIME) {
			return;
		}
		
		// 先获取微信基础Token
		String result = HttpUtils.getByUrlConnection(WxSettings.GET_TOKEN_URL);
		JSONObject jsonObject = JSONObject.fromObject(result);
		WxSettings.ACCESS_TOKEN = jsonObject.getString("access_token");

		// 再通过基础Token获取 JSAPI_TICKET
		String ticketUrl = WxSettings.GET_JSAPI_TICKET.replace("{1}", WxSettings.ACCESS_TOKEN);
		result = HttpUtils.getByUrlConnection(ticketUrl);
		jsonObject = JSONObject.fromObject(result);
		WxSettings.JSAPI_TICKET = jsonObject.getString("ticket");

		// 重置获取时间
		WxSettings.GET_TIME = nowTime;
	}
	
	/**
	 * 如果当前URL为空时，重新获取当前签名URL
	 * @param request
	 * @return
	 */
	public static String wxUrl(HttpServletRequest request) {
		return request.getQueryString() == null ? request.getRequestURL().toString() : request.getRequestURL().toString()+ "?" + request.getQueryString(); 
	}
	
	/**
	 * 微信 JSAPI config签名
	 * @param parameters
	 * @return
	 */
	public static String createWXConfigSign(SortedMap<String, String> parameters) {
		StringBuffer sBuffer = new StringBuffer();

		Set<String> keySet = parameters.keySet();
		Iterator<String> it = keySet.iterator();

		int i = 0;
		while (it.hasNext()) {
			String key = it.next();
			String value = parameters.get(key);
			if (i != 0) {
				sBuffer.append("&");
			}
			if (!StringUtils.isNull(value) && !"sign".equals(key) && !"key".equals(key)) {
				sBuffer.append(key + "=" + value);
			}
			i++;
		}

		String sign = StringUtils.getSHA1(String.valueOf(sBuffer));
		return sign;
	}

	/**
	 * 获取微信 JSAPI Config
	 * @param url
	 * @return
	 */
	public static WXConfig takeWXConfig(String url) {

		// 刷新微信Token和JSAPI_TICKET
		refereshWXToken();

		long timestamp = System.currentTimeMillis() / 1000; // 签名时间戳
		String nonceStr = StringUtils.getRandomString(16); // 签名随机字符

		// 创建Config签名
		SortedMap<String, String> paramsMap = new TreeMap<String, String>();
		paramsMap.put("jsapi_ticket", WxSettings.JSAPI_TICKET);
		paramsMap.put("noncestr", nonceStr);
		paramsMap.put("timestamp", String.valueOf(timestamp));
		paramsMap.put("url", url);
		String configSign = createWXConfigSign(paramsMap);

		// 实例 WXConfig 对象返回
		WXConfig config = new WXConfig(WxSettings.APP_ID, timestamp, nonceStr, configSign);
		return config;
	}

	/**
	 * 创建统一支付后回调给客户端的支付签名
	 * 
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	public static String createWXPaySign(String characterEncoding, String appId, String timeStamp, String nonceStr, String packageValue, String signType) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("appId=" + appId + "&");
		sBuffer.append("nonceStr=" + nonceStr + "&");
		sBuffer.append("package=" + packageValue + "&");
		sBuffer.append("signType=" + signType + "&");
		sBuffer.append("timeStamp=" + timeStamp + "&");
		sBuffer.append("key=" + WxSettings.BUSINESS_KEY);
		String sign = MD5Utils.MD5Encode(sBuffer.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	/**
	 * 创建微信统一支付签名
	 * 
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	public static String createWXOrderPaySign(String characterEncoding, SortedMap<String, String> parameters) {
		StringBuffer sBuffer = new StringBuffer();
		Set<String> keySet = parameters.keySet();
		Iterator<String> it = keySet.iterator();

		while (it.hasNext()) {
			String key = it.next();
			String value = parameters.get(key);

			if (!StringUtils.isNull(value) && !"sign".equals(key) && !"key".equals(key)) {
				sBuffer.append(key + "=" + value + "&");
			}
		}
		sBuffer.append("key=" + WxSettings.BUSINESS_KEY);
		String sign = MD5Utils.MD5Encode(sBuffer.toString(), characterEncoding).toUpperCase();
		return sign;
	}
}

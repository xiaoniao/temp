package com.kunyao.assistant.core.feature.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.kunyao.assistant.core.entity.wx.WXMember;
import com.kunyao.assistant.core.entity.wx.WXOrder;
import com.kunyao.assistant.core.entity.wx.WxTemplate;
import com.kunyao.assistant.core.model.WXOrderApp;
import com.kunyao.assistant.core.utils.HttpUtils;
import com.kunyao.assistant.core.utils.HttpsUtils;
import com.kunyao.assistant.core.utils.JsonUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.core.utils.XMLUtils;

import net.sf.json.JSONObject;

public class WxService {
	private static final Logger log = LoggerFactory.getLogger(WxService.class);

	/**
	 * 获取微信会员基本信息
	 * 
	 * @param code
	 * @return
	 */
	public static WXMember getWXMember(String code) {
		WXMember member = null;
		if (StringUtils.isNull(code)) {
			return null;
		}

		// 获取Token
		String url = WxSettings.GET_OPEN_ID.replace("{0}", WxSettings.APP_ID).replace("{1}", WxSettings.APP_SECRET).replace("{2}", code);
		String result = HttpUtils.getByUrlConnection(url);
		JSONObject jsonObj = JSONObject.fromObject(result);
		if (!jsonObj.has("access_token")) {
			return null;
		}

		String openId = jsonObj.getString("openid");
		String accessToken = jsonObj.getString("access_token");

		if (StringUtils.isNull(openId) || StringUtils.isNull(accessToken)) {
			return null;
		}

		// 获取用户信息
		String userInfoUrl = WxSettings.GET_USERINFO.replace("{0}", accessToken).replace("{1}", openId);
		String userInfoResult = HttpUtils.getByUrlConnection(userInfoUrl);
		JSONObject userInfoJson = JSONObject.fromObject(userInfoResult);

		if (!userInfoJson.has("openid"))
			return null;

		int sex = userInfoJson.getInt("sex");
		String nickname = userInfoJson.getString("nickname");
		String province = userInfoJson.getString("province");
		String city = userInfoJson.getString("city");
		String country = userInfoJson.getString("country");
		String headimgurl = userInfoJson.getString("headimgurl");

		member = new WXMember(sex, nickname, province, city, country, headimgurl, openId);
		return member;
	}


	/**
	 * 微信公众号统一支付接口，生成预支付订单
	 * @return
	 */
	public static WXOrder unifiedOrder(Integer orderId, String openId, String outTradeNo, Double totalAmount, String tradeType, HttpServletRequest request) {
		WXOrder wxOrder = null;

		// 开启微信统一支付
		String spbillCreateIp = request.getRemoteAddr();                      // 获取ip
		String userAgent = request.getHeader("user-agent");                   // 获取请求的头部信息
		int totalFee = (int) (totalAmount * 100.0);
		String body = null;
		totalFee = 1;

		SortedMap<String, String> paramsMap = new TreeMap<String, String>();
		paramsMap.put("appid", WxSettings.APP_ID);
		paramsMap.put("mch_id", WxSettings.BUSINESS_ID);
		paramsMap.put("nonce_str", StringUtils.getRandomString(30));
		paramsMap.put("body", body);
		paramsMap.put("out_trade_no", outTradeNo);                           // 订单编号
		paramsMap.put("total_fee", String.valueOf(totalFee));                // 订单金额
		paramsMap.put("spbill_create_ip", spbillCreateIp);                   // ip地址
		paramsMap.put("notify_url", WxSettings.WEIXIN_PAY_NOTIFY_URL);       // 成功后回调地址
		paramsMap.put("trade_type", tradeType);                              // 使用的SDK平台
		paramsMap.put("openid", openId);                    // 微信OpenID
		String sign = WxUtils.createWXOrderPaySign("UTF-8", paramsMap);
		paramsMap.put("sign", sign);

		String requestXML = XMLUtils.mapParseXml(paramsMap);
		String result = null;
		try {
			result = new String(HttpsUtils.requestByUrlConnection(WxSettings.UNIFIED_ORDER, "POST", requestXML).getBytes("GB2312"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		Map<String, String> resultMap = null;
		try {
			resultMap = XMLUtils.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		if ("SUCCESS".equals(resultMap.get("result_code"))) {
			String appId = WxSettings.APP_ID;
			String timeStamp = Long.toString(new Date().getTime() / 1000);
			String nonceStr = StringUtils.getRandomString(16);
			String packageValue = "prepay_id=" + resultMap.get("prepay_id");
			String signType = "MD5";
			String sendUrl = WxSettings.WEIXIN_PAY_NOTIFY_URL;

			wxOrder = new WXOrder(orderId, appId, timeStamp, nonceStr, packageValue, signType, sendUrl, userAgent);
		}
		return wxOrder;
	}
	
	/**
	 * 微信App统一支付接口，生成预支付订单
	 * @return
	 */
	public static WXOrderApp unifiedOrderApp(Integer orderId, String openId, String outTradeNo, Double totalAmount, String tradeType, HttpServletRequest request) {
		WXOrderApp wxOrder = null;

		// 开启微信统一支付
		String spbillCreateIp = request.getRemoteAddr();                      // 获取ip
		int totalFee = (int) (totalAmount * 100.0);
		String body = "充值";

		SortedMap<String, String> paramsMap = new TreeMap<String, String>();
		paramsMap.put("appid", WxSettings.APP_ID);							 // 应用ID
		paramsMap.put("body", body);										 // 商品描述
		paramsMap.put("mch_id", WxSettings.BUSINESS_ID);					 // 商户号
		paramsMap.put("nonce_str", StringUtils.getRandomString(30));		 // 随机字符串
		paramsMap.put("notify_url", WxSettings.WEIXIN_PAY_NOTIFY_URL);       // 成功后回调地址
		paramsMap.put("out_trade_no", outTradeNo);                           // 订单编号
		paramsMap.put("spbill_create_ip", spbillCreateIp);                   // ip地址
		paramsMap.put("total_fee", String.valueOf(totalFee));                // 订单金额
		paramsMap.put("trade_type", tradeType);                              // 使用的SDK平台
		// APP支付不需要此参数 paramsMap.put("openid", openId);                   // 微信OpenID
		String sign = WxUtils.createWXOrderPaySign("UTF-8", paramsMap);
		paramsMap.put("sign", sign);
		
		for (String key : paramsMap.keySet()) {
			log.info("key:" + key + " value:" + paramsMap.get(key));
		}

		String requestXML = XMLUtils.mapParseXml(paramsMap);
		String result = null;
		try {
			result = new String(HttpsUtils.requestByUrlConnection(WxSettings.UNIFIED_ORDER, "POST", requestXML).getBytes("GB2312"), "GBK");
			log.info(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		Map<String, String> resultMap = null;
		try {
			resultMap = XMLUtils.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		if ("SUCCESS".equals(resultMap.get("result_code"))) {
			String appid = resultMap.get("appid");
			String mch_id = resultMap.get("mch_id");
			String device_info = resultMap.get("device_info");
			String nonce_str = resultMap.get("nonce_str");
			String msign = resultMap.get("sign");
			String result_code = resultMap.get("result_code");
			String trade_type = "";
			String prepay_id = "";
			if (result_code.equals("SUCCESS")) {
				trade_type = resultMap.get("trade_type");
				prepay_id = resultMap.get("prepay_id");
			}
			wxOrder = new WXOrderApp(appid, mch_id, device_info, nonce_str, msign, result_code, trade_type, prepay_id);
		}
		return wxOrder;
	}

	/**
	 * 查询 微信订单状态
	 */
	public static Map<String, String> queryOrderStatus(String orderCard) {
		SortedMap<String, String> paramsMap = new TreeMap<String, String>();
		paramsMap.put("appid", WxSettings.APP_ID);
		paramsMap.put("mch_id", WxSettings.BUSINESS_ID);
		paramsMap.put("out_trade_no", orderCard);
		paramsMap.put("nonce_str", StringUtils.getRandomString(30));
		String sign = WxUtils.createWXOrderPaySign("UTF-8", paramsMap);
		paramsMap.put("sign", sign);

		String requestXML = XMLUtils.mapParseXml(paramsMap);
		String result = null;
		try {
			result = new String(HttpsUtils.requestByUrlConnection(WxSettings.QUERY_ORDER, "POST", requestXML).getBytes("GB2312"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		Map<String, String> resultMap = null;
		try {
			resultMap = XMLUtils.doXMLParse(result);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 发送微信模板消息
	 */
	public static boolean sendWxTemplate(WxTemplate template) {
		String requestJSON = JsonUtils.toJson(template);
		String templateUrl = WxSettings.WX_TEMPLATE_MESG.replace("{0}", WxSettings.ACCESS_TOKEN);
		
		String result = null;
		try {
			result = new String(HttpsUtils.requestByUrlConnection(templateUrl, "POST", requestJSON).getBytes("GB2312"), "GBK");
			Map<String, String> resultMap = JsonUtils.fromJson(result, new TypeToken<Map<String, String>>() { }.getType());
			if ("0".equals(resultMap.get("errcode"))) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
}

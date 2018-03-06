package com.kunyao.assistant.core.feature.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.kunyao.assistant.core.utils.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 云片短信服务
 * @author GeNing
 * @since  2016.07.29
 */
public class YunPianSMS {
	
	// 智能匹配模版发送接口的http地址                       
	private static final String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
	
	// 发送语音验证码接口的http地址
	private static final String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";
	
	// 隐私通话绑定
	private static final String URI_ANONYMOUS_BIND = "https://call.yunpian.com/v2/call/bind.json";
	
	// 隐私通话解绑
	private static final String URI_ANONYMOUS_UNBIND = "https://call.yunpian.com/v2/call/unbind.json";

	// 编码格式。发送编码格式统一用UTF-8
	public static String ENCODING = "UTF-8";
	
	// 云片后台APIKey
	private static String YUNPIAN_SERVER_APIKEY = "75a1c63e8a293a48c633a9ebf4b56dfe";
	
	public static String TPL_ID_1630644 = "1630644";
	public static String TPL_ID_1677570 = "1677570";
	public static String TPL_ID_1677590 = "1677590";
	public static String TPL_ID_1677538 = "1677538";
	public static String TPL_ID_1677518 = "1677518";
	public static String TPL_ID_1677504 = "1677504";
	public static String TPL_ID_1677500 = "1677500";
	public static String TPL_ID_1677516 = "1677516";
	
	public static final String SMS_SERVER_RETURN_RESULT_SUCCESS_KEY = "code";
	public static final String SMS_SERVER_RETURN_RESULT_SUCCESS_VALUE = "0";
	public static final String VOICE_SERVER_RETURN_RESULT_SUCCESS_KEY = "count";
	public static final String VOICE_SERVER_RETURN_RESULT_SUCCESS_VALUE = "1";
	public static final String SMS_SEND_VALUE_CODE = "22";          // 一小时内只能发送三次
	public static final String SMS_TIME_OVER = "17";				// 一天内只能发送10次
	private static final String ANON_CALL_DURATION = "600";		// 隐私电话绑定有效时常(秒)
	public static Map<String, String> PHONE_CODE_MAP = new HashMap<String, String>();
	
	public static int sendLoginText(String mobile, String templateContent) {
		String tpl_value = null;
		try {
			
			tpl_value = URLEncoder.encode("#code#",ENCODING) +"="
					+ URLEncoder.encode(templateContent, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return YunPianSMS.sendText(mobile, templateContent, TPL_ID_1630644, tpl_value);
	}
	
	/**
	 * 智能匹配模版接口发短信
	 * 
	 * @param mobile 接受的手机号
	 * @param templateContent 短信内容
	 * @return
	 * 
	 **/
	public static int sendText(String mobile, String templateContent, String tplId, String tplValue) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", YUNPIAN_SERVER_APIKEY);
		params.put("mobile", mobile);
		params.put("tpl_id", tplId);
		params.put("tpl_value", tplValue);
		
		JSONArray resultJson = JSONArray.fromObject("["+HttpUtils.postByHttpClient(URI_SEND_SMS, params, ENCODING)+"]");
		
		if (SMS_SERVER_RETURN_RESULT_SUCCESS_VALUE.equals(
				resultJson.getJSONObject(0).getString(SMS_SERVER_RETURN_RESULT_SUCCESS_KEY)))
			return 1;
		else if (SMS_SEND_VALUE_CODE.equals(
				resultJson.getJSONObject(0).getString(SMS_SERVER_RETURN_RESULT_SUCCESS_KEY)))
			return -1;
		else
			return -2;
	}
	
	/**
	 * 通过接口发送语音验证码
	 * 
	 * @param mobile 接收的手机号
	 * @param code 验证码
	 * @return 
	 * 
	 */
	public static int sendVoice(String mobile, String templateContent) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", YUNPIAN_SERVER_APIKEY);
		params.put("code", templateContent);
		params.put("mobile", mobile);
		
		JSONArray resultJson = JSONArray.fromObject("["+HttpUtils.postByHttpClient(URI_SEND_VOICE, params, ENCODING)+"]");
		JSONObject jsonObject = resultJson.getJSONObject(0);
		
		if (jsonObject.get(VOICE_SERVER_RETURN_RESULT_SUCCESS_KEY) != null && VOICE_SERVER_RETURN_RESULT_SUCCESS_VALUE.equals(jsonObject.getString(VOICE_SERVER_RETURN_RESULT_SUCCESS_KEY)))
			return 1;
		else if (SMS_TIME_OVER.equals(jsonObject.getString(SMS_SERVER_RETURN_RESULT_SUCCESS_KEY))) {
			return -1;
		} else
			return -2;
	}
	
	/**
	 * 隐私电话绑定
	 * @param memberMobile
	 * @param crossMobile
	 * @return
	 */
	public static JSONObject anonymousBind(String memberMobile, String crossMobile) {
		Map<String, String> params = new HashMap<>();
		params.put("apikey", YUNPIAN_SERVER_APIKEY);
		params.put("from", memberMobile);
		params.put("to", crossMobile);
		params.put("duration", ANON_CALL_DURATION);
		return JSONObject.fromObject(HttpUtils.postByHttpClient(URI_ANONYMOUS_BIND, params, ENCODING));
	}
	
	/**
	 * 隐私电话解绑
	 * @param memberMobile
	 * @param crossMobile
	 * @return
	 */
	public static JSONObject anonymousUnbind(String memberMobile, String crossMobile) {
		Map<String, String> params = new HashMap<>();
		params.put("apikey", YUNPIAN_SERVER_APIKEY);
		params.put("from", memberMobile);
		params.put("to", crossMobile);
		params.put("duration", "0");	// 解绑延时，0表示立即解绑
		return JSONObject.fromObject(HttpUtils.postByHttpClient(URI_ANONYMOUS_UNBIND, params, ENCODING));
	}
}

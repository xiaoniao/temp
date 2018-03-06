package com.kunyao.assistant.core.feature.juhe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * 实名认证接口
 *   1.验证手机号
 *   2.验证银行卡
 *   3.验证身份证
 *   4.判断银行卡类型及真伪
 */
public class JuheUtils {
	private static Logger logger = LoggerFactory.getLogger(JuheUtils.class);

	public static final String URL_PHONE = "http://v.juhe.cn/telecom2/query"; // 手机
	public static final String URL_BANK = "http://v.juhe.cn/verifybankcard/query"; // 银行卡
	public static final String URL_IDCARD = "http://op.juhe.cn/idcard/query"; // 身份证
	public static final String URL_BANKID = "http://detectionBankCard.api.juhe.cn/bankCard"; // 判断银行卡类型及真伪
	
	public static final String APPKEY_PHONE = "f2416b7b662aaebba6a5d090a806e34d"; // 手机二元素校验KEY
	public static final String APPKEY_BANK = "5dc6c3300af5dcf8143cde15106011c8"; // 银行卡二元素检测KEY
	public static final String APPKEY_IDCARD = "e4adf0f7448b416b2cdbc7d13f4366a9"; // 身份证实名认证KEY
	public static final String APPKEY_BANKID = "808603d744228ab1c7d84257dbe20ddf"; // 判断银行卡类型及真伪KEY
	
	
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	/**
	 * 1.验证手机号
	 * 
	 * @param mobile 手机号码
	 * @param realname 姓名
	 */
	public static boolean verifyPhone(String mobile, String realname) {
		String result = null;
		String url = URL_PHONE;
		Map<String, String> params = new HashMap<>();
		params.put("mobile", mobile);
		params.put("realname", realname);
		params.put("key", APPKEY_PHONE);

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				if (object.getJSONObject("result").getInt("res") == 1) {
					return true;
				}
			} else {
				logger.info(object.get("error_code") + ":" + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 2.验证银行卡
	 * 
	 * @param bankcard 银行卡卡号
	 * @param realname 姓名
	 */
	public static boolean verifyBankcard(String bankcard, String realname) {
		String result = null;
		String url = URL_BANK; // 请求接口地址
		Map<String, String> params = new HashMap<>();// 请求参数
		params.put("bankcard", bankcard);
		params.put("realname", realname);
		params.put("key", APPKEY_BANK);
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				if (object.getJSONObject("result").getInt("res") == 1) {
					return true;
				}
			} else {
				logger.info(object.get("error_code") + ":" + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 3.验证身份证
	 * 
	 * @param idcard 身份证号码
	 * @param realname 姓名
	 */
	public static boolean verifyIdcard(String idcard, String realname) {
		String result = null;
		String url = URL_IDCARD;
		Map<String, String> params = new HashMap<>();
		params.put("idcard", idcard);
		params.put("realname", realname);
		params.put("key", APPKEY_IDCARD);

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				if (object.getJSONObject("result").getInt("res") == 1) {
					return true;
				}
			} else {
				logger.info(object.get("error_code") + ":" + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 4.判断银行卡类型及真伪
	 * 
	 * @param cardid 银行卡号
	 */
	public static JSONObject verifyBankCardType(String cardid) {
		String result = null;
		String url = URL_BANKID;
		Map<String, String> params = new HashMap<>();
		params.put("cardid", cardid);
		params.put("key", APPKEY_BANKID);
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 网络请求
	 * 
	 * @param strUrl 请求地址
	 * @param params 请求参数
	 * @param method 请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map<String, String> params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	public static String urlencode(Map<String, String> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}

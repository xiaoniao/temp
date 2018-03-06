package com.kunyao.assistant.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

public class AliPayUtils {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AliPayUtils.class);
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016121404245032";
	
	/** 商户私钥，pkcs8格式 */
	public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCVmnpcLuk1gCACotrkOgk6Y+g4LmBFtDkpu85EZntvqQAp4G8fwcywpPKaILxWHLzm7tJaZnAPScCA+FZON+JZ1G4GBWC48VZDHc0VDIoh0enfb2wHOcDkyPMxDDhWFZ88pvn8dvFUo6xoTPsaUGk9K60mm2JmcCyAn80xZKDvAT6hNSIvKvsMXjRpVZUk64mr+7BDx/R90KYt37Oy3qpsxQjnH/kxWgFzmNbQ29eoYfPS3uJWldD3fG9I536ud5UBk76Ck1oQJQ2NOhv2BuqNwO3znyIlhX4QxL1CBaR4LR1WqxdoTa+J/CybwbwV1lAnwbiQ8b7mCm16TS6LndlDAgMBAAECggEAOrR8sXHDF7hoV0B6rrZLartLo8gnBRM8nzDQNy0T4PQrWUN1t7t/zA1eGbcg0JXEVsZ+ivTJomYpgTJyA42QZ/mhFwHDO6+QTxQBpvHdvnm10XTSql8yX1SvgZ6u+LDRZKaNUFGIT9NOUztqCaBmO5fJAW5WBaac//g1N92p/T8hMk2GX9/yb72RWCnCzt8e5tvEaRaEZA+9VZw81LK7L8CSOLh0Yn011KDnw4wgXuxFpxQ6pxO7sVCZFP8NCbnDk/whG944Id+4Q2Or/2EotkLTmej/AFx19ZQFUI85qHhkr0mREgHRS1hcssSrRZNa8RENkFjVD4DAsnsAyrQwAQKBgQDEcwDkhSRfWgVjYPASKoU0GLgmBk9WxNGVZXbqhWuIwIoBJbKifznoZxe3SwEMOrBdTziPuX/mVwbSaDKfON6c2X2RKGq+yO5CD0M46Oz72KtjHeymOKrL3LyXGLIK9NmoAGYoq9CaV35lgCzBm0iCY1P5WKL1MAjv2nxVEAkxQwKBgQDC9BzC68/a9e2TaDo9XCdYLG/lBEX2ezACBtuyPjUgRjqsFOYbDIlGImgsWYdB21ODHAO38oqLuFZfIk4uj2G1eU7v0stDXngf/BvIz4LfNsaqCOg9Qg9sA35t96sJsX6W9N80M/vM9znCGW2ZF2QPNDpQ0g0YFuKKSLoSKBo4AQKBgBUFjXHmwXampm70/5uiCzEA+Cuxfyn008yd1/TOpCw1Fk97Hjt/S84PwhzuHMWXPiouAQp4OjSG2YcIeWpKZp/4MhSnc4zI6Z4ODg539mwYynZHzHwZEkXQcCnnDZ5YhzYQHN57TE5H0JHB/ogVOtQhFupWztFX+4zXH3jmx2ULAoGBALDKPHbK74ApupsNTpssQYWlLodpO6TMi3mbDInz/atZ5IT+orjuwmG2e++T9KNVKMNhmUGrkdYogTLeYjW40quF6X50Et3yAk9HCU8uVwjinH+/ehNrH58c//7rNNCihCCSyQKHDZYOr/MDqi7FVnivLR7zjOpGfoNWdl5WvYgBAoGBAKuWAkY5F+vvUM3EndS/t2b8UP//x8H20zMx1+pQ+oMYRATz2U5kismq/Gb4Yu59+5tzNLJw8yl7BjKZbVZISiRGjtMbujWK3bhGy3yHFtGKG2nb+iol/bxcdiKwbcSmsHNoYGL78o6pSWH682ApbtmKlxasmTa2mLi/JWj+2Ie6";
	
	public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzP0G3iAJDJBTyQlyZ1Va59agxrBqMdMzrtr6ECP/C0JR1qs3E7ZocEFdx8bbQYXcCtyYKDtw1PbxEwjhxgFCVerJkMUcEDNWNAWwaeZsgor3FK7ZVFkEcT+qO/h4UP6VyeVsBKNLiM4ssoFEyHKBqqc4X9L7otCNVs7xAOswri17dah7cVB/q+C4ISNiPgbk1tw5yO88tE7CcygrZz1KChadTGrHDFzlWmGxNEZ+6NdllPBpjVcPEtSIzi/+ilOB4/j5uLfpUKp93MfmVB1HJJNMQQZXlVE0bXN2YMjbzx154E4z81rSQuwIsxifSA6VkkFi71c8itgn50fqibAh5QIDAQAB";
	
	/**
	 * 构造支付订单参数
	 */
	private static Map<String, String> buildOrderParamMap(String tradeNo, double totalAmount, String subject, String body) {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", APPID);

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("charset", "utf-8");
		
		keyValues.put("sign_type", "RSA2");
		
		SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		keyValues.put("timestamp", FULL_FORMAT.format(new Date()));
		
		keyValues.put("version", "1.0");
		
		keyValues.put("notify_url", "http://app.cityjinying.com/rest/callback/alipay");
		
		keyValues.put("biz_content", "{" + 
									    "\"timeout_express\":\"30m\"," + 
									    "\"product_code\":\"QUICK_MSECURITY_PAY\"," + 
									    "\"total_amount\":\"" + new DecimalFormat("#.##").format(totalAmount) + "\"," + 
									    "\"subject\":\"" + subject + "\"," + 
									    "\"body\":\"" + body + "\"," + 
									    "\"out_trade_no\":\"" + tradeNo +  "\""+ 
									"}");
		return keyValues;
	}

	/**
	 * 对支付参数信息进行签名
	 * @param map 待签名授权信息
	 * @return
	 */
	private static String getSign(Map<String, String> map) {
		String oriSign = null;
		try {
			String signContent = AlipaySignature.getSignContent(map);
			// log.info("signContent:" + signContent);
			
			oriSign = AlipaySignature.rsaSign(signContent, RSA_PRIVATE, "UTF-8", "RSA2");
			// log.info("sign:" + oriSign);
		} catch (AlipayApiException e1) {
			e1.printStackTrace();
		}
		
		String encodedSign = "";
		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
			// log.info("encodedSign:" + encodedSign);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}
	
	/**
	 * 生成签名后的订单信息
	 */
	public static String createOrder(String tradeNo, double totalAmount, String subject, String body) {
		// 构造键值对
		Map<String, String> params = buildOrderParamMap(tradeNo, totalAmount, subject, body);
		// 加密
		String sign = getSign(params);
		// 解析成字符串
		String orderParam = buildOrderParam(params);
		// 订单字符串
		String orderInfo = orderParam + "&" + sign;
		// log.info("orderInfo:" + orderInfo);
		return orderInfo;
	}
	
	/**
	 * 构造支付订单参数信息
	 * @param map 支付订单参数
	 * @return
	 */
	private static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));
		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	public static boolean rsaCheckV2(Map<String, String> params) {
		try {
			String sign = params.get("sign");
	        String content = AlipaySignature.getSignCheckContentV1(params);
	        return AlipaySignature.rsaCheck(content, sign, ALIPAY_PUBLIC_KEY, "UTF-8", "RSA2");
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		return false;
	}
}

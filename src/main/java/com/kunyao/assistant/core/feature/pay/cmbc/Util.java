package com.kunyao.assistant.core.feature.pay.cmbc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Util {
	
	// 商户密钥
	public static final String SECRET_KEY = "Changyiwangluo88";
	public static final String CHARSET = "GBK";

	public static final String HOST = "http://121.15.180.66:801/";

	// 支付+签约
	public static final String URL_PREPAYEUSERP_D = "http://121.15.180.66:801/NetPayment/BaseHttp.dll?MB_EUserPay";

	// 按商户日期查询订单
	public static final String URL_QUERYORDERBYMERCHANTDATE = HOST + "NetPayment/BaseHttp.dll?QuerySettledOrderByMerchantDate";
	// 查询入账明细
	public static final String URL_QUERYACCOUNTEDORDER = HOST + "NetPayment/BaseHttp.dll?QueryAccountedList";
	// 查询单笔订单
	public static final String URL_QUERYSINGLEORDER = HOST + "NetPayment/BaseHttp.dll?QuerySingleOrder";
	// 退款
	public static final String URL_DOREFUND = HOST + "NetPayment/BaseHttp.dll?DoRefund";
	
	// 支付+签约
	//public static final String URL_PUBLIC_KEY = "https://b2b.cmbchina.com/CmbBank_B2B/UI/NetPay/DoBusiness.ashx";//生产环境
	public static final String URL_PUBLIC_KEY = "http://121.15.180.72/CmbBank_B2B/UI/NetPay/DoBusiness.ashx";//测试环境
	
	/**
	 * 发送POST请求
	 */
	public static String uploadParam(String jsonParam, String url, String charset) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL httpUrl = new URL(url);
			HttpURLConnection urlCon = (HttpURLConnection) httpUrl.openConnection();
			urlCon.setRequestMethod("POST");
			urlCon.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlCon.setDoOutput(true);
			urlCon.setDoInput(true);
			urlCon.setReadTimeout(5 * 1000);
			out = new OutputStreamWriter(urlCon.getOutputStream(), charset);// 指定编码格式
			out.write("jsonRequestData=" + jsonParam);
			out.flush();

			in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), charset));
			String str = null;
			while ((str = in.readLine()) != null) {
				result.append(str);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 获取当前时间戳
	 */
	public static String getNowTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(System.currentTimeMillis());
	}
	
	/**
	 * 获取订单日期
	 */
	public static String getOrderDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(System.currentTimeMillis());
	}

	/**
	 * DES加密
	 */
	public static String DesEncrypt(byte[] plain, byte[] key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKeySpec = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKeySpec);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");// DES/ECB/PKCS5Padding
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			byte[] byteBuffer = cipher.doFinal(plain);
			// 將 byte转换为16进制string
			StringBuffer strHexString = new StringBuffer();
			for (int i = 0; i < byteBuffer.length; i++) {
				String hex = Integer.toHexString(0xff & byteBuffer[i]);
				if (hex.length() == 1) {
					strHexString.append('0');
				}
				strHexString.append(hex);
			}
			return strHexString.toString();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RC4加密
	 */
	public static String RC4Encrypt(byte[] plain, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, "RC4");
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("RC4");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			// 现在，获取数据并加密
			// 正式执行加密操作
			byte[] byteBuffer = cipher.doFinal(plain);
			// 將 byte转换为16进制string
			StringBuffer strHexString = new StringBuffer();
			for (int i = 0; i < byteBuffer.length; i++) {
				String hex = Integer.toHexString(0xff & byteBuffer[i]);
				if (hex.length() == 1) {
					strHexString.append('0');
				}
				strHexString.append(hex);
			}
			return strHexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}

package com.kunyao.assistant.core.feature.pay.cmbc;


import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.utils.Base64;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;

public class NetPay {
	
	// 支付商户开户分行号，4位，请咨询开户的招商银行分支机构；
	private static String BranchID = "0571";
	// 支付商户号，6位长数字，由银行在商户开户时确定
	private static String CoNo = "001268";
	// 协议商户企业编号 由银行在商户开户时确定。 即8位的企业网银编号。
	// private static String MchNo = "20160099";

	public static String pay(String orderCard, Double payMoney, Integer userId, String clientIp) {
		int type = 1;
		String result = "";
		switch (type) {
		case 1:// 支付+签约
			result = prePay(orderCard, payMoney, userId, clientIp);
			break;
		case 2:// 查询入账明细
			result = Util.uploadParam(queryAccountedOrder(), Util.URL_QUERYACCOUNTEDORDER, Util.CHARSET);
			break;
		case 3:// 查询单笔订单明细
			result = Util.uploadParam(querySingleOrder(), Util.URL_QUERYSINGLEORDER, Util.CHARSET);
			break;
		case 4:// 退款
			result = Util.uploadParam(doRefund(), Util.URL_DOREFUND, Util.CHARSET);
			break;
		case 5:// 查询已处理订单（按处理日期查询）
			result = Util.uploadParam(queryByMerchantDate(), Util.URL_QUERYORDERBYMERCHANTDATE, Util.CHARSET);
			break;
		case 0:
			System.exit(0);
			break;
		default:
			break;
		}
		return result;
	}
	
	public static void main(String[] args) {
		initPublicKey();
	}
	
	/**
	 * 查询招行公钥
	 * 招行公钥会定期更换，更换频率较低。商户可以通过API每天取一次招行公钥后存在本地，每次验签时使用即可，建议每天凌晨2:15发起查询招行公钥请求更新公钥。
	 */
	public static void initPublicKey() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		if (!StringUtils.isNull(Constant.CMBC_PUBLIC_KEY) && !StringUtils.isNull(Constant.CMBC_PUBLIC_KEY_TIME)) {
			try {
				Date keyTime = format.parse(Constant.CMBC_PUBLIC_KEY_TIME);
				if (new Date().getTime() - keyTime.getTime() < DateUtils.DAY) {
					return;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", Util.getNowTime());
		reqData.put("txCode", "FBPK");
		reqData.put("branchNo", String.valueOf(BranchID));
		reqData.put("merchantNo", String.valueOf(CoNo));
		String result = Util.uploadParam(buildParam(reqData), Util.URL_PUBLIC_KEY, "UTF-8");
		if (!StringUtils.isNull(result)) {
			try {
				JSONObject rspData = new JSONObject(result).getJSONObject("rspData");
				Constant.CMBC_PUBLIC_KEY_TIME = rspData.getString("dateTime");
				Constant.CMBC_PUBLIC_KEY = rspData.getString("fbPubKey");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 签约+支付
	 */
	public static String prePay(String orderCard, Double payMoney, Integer userId, String clientIp) {
		DecimalFormat df = new DecimalFormat("#.00");
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", Util.getNowTime());
		reqData.put("branchNo", BranchID);
		reqData.put("merchantNo", CoNo);
		reqData.put("date", Util.getOrderDate());
		reqData.put("orderNo", orderCard);
		reqData.put("amount", df.format(payMoney));
		reqData.put("expireTimeSpan", "30");
		reqData.put("payNoticeUrl", "http://116.62.6.249/rest/mc/cmbc/payNotice");
		reqData.put("payNoticePara", "");
		reqData.put("returnUrl", "http://116.62.6.249/rest/mc/cmbc/returnPage");
		reqData.put("clientIP", clientIp);
		reqData.put("agrNo", String.valueOf(userId));
		reqData.put("merchantSerialNo", orderCard); //协议开通请求流水号，开通协议时必填。
		reqData.put("userID", String.valueOf(userId));
		reqData.put("signNoticeUrl", "http://116.62.6.249/rest/mc/cmbc/agreement");
		return buildParam(reqData);
	}

	/**
	 * 查询已处理订单（按商户日期查询）
	 */
	public static String queryByMerchantDate() {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", Util.getNowTime());
		reqData.put("branchNo", "0755");
		reqData.put("merchantNo", "000054");
		reqData.put("beginDate", "20160502");
		reqData.put("endDate", "20160503");
		reqData.put("operatorNo", "9999");
		reqData.put("nextKeyValue", "");

		return buildParam(reqData);
	}

	/**
	 * 给请求数据增加签名字段
	 */
	public static String addSignParam(String reqJSON) {
		try {
			JSONObject param = new JSONObject(reqJSON);
			String reqData = param.getString("reqData");
			String sign = sign(reqData, Util.SECRET_KEY, param.getString("charset"));
			param.put("sign", sign);
			return param.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查询入账明细
	 */
	public static String queryAccountedOrder() {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", Util.getNowTime());
		reqData.put("branchNo", "0755");
		reqData.put("merchantNo", "000054");
		reqData.put("date", "20160625");
		reqData.put("operatorNo", "9999");
		reqData.put("nextKeyValue", "");

		return buildParam(reqData);
	}

	/**
	 * 查询单笔订单明细
	 */
	public static String querySingleOrder() {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", Util.getNowTime());
		reqData.put("branchNo", "0755");
		reqData.put("merchantNo", "000054");
		reqData.put("type", "A");
		reqData.put("bankSerialNo", "16250323300000000010");
		reqData.put("date", "20160625");
		reqData.put("orderNo", "9999000001");
		reqData.put("orderRefNo", "");
		reqData.put("operatorNo", "9999");

		return buildParam(reqData);
	}

	/**
	 * 退款
	 */
	public static String doRefund() {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("dateTime", Util.getNowTime());
		reqData.put("branchNo", "0755");
		reqData.put("merchantNo", "000054");
		reqData.put("date", "20160503");
		reqData.put("orderNo", "9999000015");
		reqData.put("amount", "0.01");
		reqData.put("desc", "取消订单");
		reqData.put("refundSerialNo", "16250323300000000010");
		reqData.put("operatorNo", "9998");
		reqData.put("encrypType", "");
		reqData.put("pwd", "123456");

		return buildParam(reqData);
	}

	public static String buildParam(Map<String, String> reqDataMap) {
		JSONObject jsonParam = new JSONObject();
		try {
			jsonParam.put("version", "1.0");
			jsonParam.put("charset", Util.CHARSET);// 支持GBK和UTF-8两种编码
			jsonParam.put("sign", sign(reqDataMap, Util.SECRET_KEY));
			jsonParam.put("signType", "SHA-256");
			jsonParam.put("reqData", reqDataMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonParam.toString();
	}

	/**
	 * 对参数签名：
	 * 对reqData所有请求参数按从a到z的字典顺序排列，如果首字母相同，按第二个字母排列，以此类推。排序完成后按将所有键值对以“&”符号拼接。
	 * 拼接完成后再加上商户密钥。示例：param1=value1&param2=value2&...&paramN=valueN&secretKey
	 * 
	 * @param reqDataMap
	 *            请求参数
	 * @param secretKey
	 *            商户密钥
	 */
	public static String sign(Map<String, String> reqDataMap, String secretKey) {
		StringBuffer buffer = new StringBuffer();
		List<String> keyList = sortParams(reqDataMap);
		for (String key : keyList) {
			buffer.append(key).append("=").append(reqDataMap.get(key)).append("&");
		}
		buffer.append(secretKey);// 商户密钥

		try {
			// 创建加密对象
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			// 传入要加密的字符串,按指定的字符集将字符串转换为字节流
			messageDigest.update(buffer.toString().getBytes(Util.CHARSET));
			byte byteBuffer[] = messageDigest.digest();

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
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 对参数签名
	 */
	public static String sign(String reqDataJSON, String secretKey, String charset) {
		StringBuffer buffer = new StringBuffer();

		try {
			JSONObject json = new JSONObject(reqDataJSON);
			List<String> keyList = sortParams(json);
			for (String key : keyList) {
				buffer.append(key).append("=").append(json.get(key)).append("&");
			}
			buffer.append(secretKey);// 商户密钥
			// 创建加密对象
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			// 传入要加密的字符串,按指定的字符集将字符串转换为字节流
			messageDigest.update(buffer.toString().getBytes(charset));
			byte byteBuffer[] = messageDigest.digest();

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
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 对参数按字典顺序排序，不区分大小写
	 */
	public static List<String> sortParams(Map<String, String> reqDataMap) {
		List<String> list = new ArrayList<String>(reqDataMap.keySet());
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				String[] temp = { o1.toLowerCase(), o2.toLowerCase() };
				Arrays.sort(temp);
				if (o1.equalsIgnoreCase(temp[0])) {
					return -1;
				} else if (temp[0].equalsIgnoreCase(temp[1])) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		return list;
	}

	/**
	 * 对参数排序
	 */
	public static List<String> sortParams(JSONObject json) {
		List<String> list = new ArrayList<String>();
		Iterator<String> it = json.keys();
		while (it.hasNext()) {
			list.add((String) it.next());
		}
		Collections.sort(list, new Comparator<String>() {
			public int compare(String o1, String o2) {
				String[] temp = { o1.toLowerCase(), o2.toLowerCase() };
				Arrays.sort(temp);
				if (o1.equalsIgnoreCase(temp[0])) {
					return -1;
				} else if (temp[0].equalsIgnoreCase(temp[1])) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		return list;
	}
	
	public static String sortStr(String reqDataJSON) {
		StringBuffer buffer = new StringBuffer();
		JSONObject json = new JSONObject(reqDataJSON);
		List<String> keyList = sortParams(json);
		for (String key : keyList) {
			buffer.append(key).append("=").append(json.get(key)).append("&");
		}
		return buffer.substring(0, buffer.length() - 1);
	}
	
	public static boolean valid(String jsonRequestData) {
		JSONObject requestData = new JSONObject(jsonRequestData);
		String sign = requestData.getString("sign");
		
		String strNoticeData = requestData.getJSONObject("noticeData").toString();
		
		NetPay.initPublicKey();
		String strToSign = sortStr(strNoticeData);
		boolean valid = NetPay.isValidSignature(strToSign, sign, Constant.CMBC_PUBLIC_KEY);
		return valid;
	}
	
	public static boolean isValidSignature(String strToSign, String strSign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            signature.update(strToSign.getBytes("UTF-8"));
            boolean bverify = signature.verify(Base64.decode(strSign));
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;  
	}
}

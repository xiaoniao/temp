package com.kunyao.assistant.core.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

public class SignUtils {

	private static final String ALGORITHM = "RSA2";

	private static final String SIGN_ALGORITHMS = "SHA256WithRSA";

	private static final String DEFAULT_CHARSET = "UTF-8";

	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 生成Url签名
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	public static String createUrlSign(String characterEncoding, SortedMap<String, String> parameters) {
		
		StringBuffer sBuffer = new StringBuffer();
		
		Set<String> keySet = parameters.keySet();
		Iterator<String> it = keySet.iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			String value = parameters.get(key);
			
			if (StringUtils.isNull(value))
				continue;
			
			// 微信签名时，商家key不参与签名
			if ("sign".equals(key) || "key".equals(key))
				continue;
			
			sBuffer.append(key + "=" + value + "&");
		}
		
		String sign = MD5Utils.MD5Encode(sBuffer.toString(), characterEncoding).toUpperCase();
		return sign;
	}

}

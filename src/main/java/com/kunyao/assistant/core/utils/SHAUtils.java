package com.kunyao.assistant.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtils {
	
	public static String SHAEncode(String strSrc) {
		MessageDigest md = null;
		String strDes = null;
		String encName = "SHA-256";

		byte[] bt = strSrc.getBytes();
		
		try {
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	public static void main(String args[]) {
		
	}

}

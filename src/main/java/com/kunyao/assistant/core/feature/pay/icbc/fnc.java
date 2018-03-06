package com.kunyao.assistant.core.feature.pay.icbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;

import cn.com.infosec.icbc.ReturnValue;

public class fnc {
	public static void main(String[] args) throws UnsupportedEncodingException {

		String keyPath = "C:\\Users\\Administrator\\Desktop\\zhoufangwei\\";
		String ip = "83.36.2.123"; // 219.143.240.25
		String port = "80";
		String trustStore = keyPath + "mytrust";
		String tmpxml = "";

		//签名要使用的参数
		String signature = null;
		String certificate = null;
		String pk_path = keyPath + "201702.key";
		String pk_pass = "12345678";
		String publickey_path = keyPath + "201702.cer";

		try {
			System.out.println("trustStore:" + trustStore);
			System.setProperty("javax.net.ssl.trustStore", trustStore);
			Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);
			HttpConnection myconn = new HttpConnection(ip, Integer.parseInt(port), myhttps);
			myconn.open();
			if (!myconn.isOpen()) {
				System.err.println("HttpConnection open  error.");
			}

			PostMethod mypost = new PostMethod();
			try {
				mypost.setRequestHeader("contentType", "application/x-www-form-urlencoded");
				//添加包头
				mypost.setRequestHeader("pageEncoding", "GBK");
				mypost.addParameter("Version", "0.0.1.0");
				mypost.addParameter("TransCode", "QACCBAL");
				mypost.addParameter("BankCode", "102");
				mypost.addParameter("GroupCIS", "120290000034634");
				mypost.addParameter("ID", "201702.y.1202");
				mypost.addParameter("PackageID", "123456");

				FileInputStream in3 = new FileInputStream(publickey_path);
				byte[] pubcert = new byte[in3.available()];
				in3.read(pubcert);
				in3.close();
				mypost.addParameter("Cert", new String(ReturnValue.base64enc(pubcert)));
				System.out.println("cert=" + new String(ReturnValue.base64enc(pubcert)));

				String sContent =
					"<?xml version=\"1.0\" encoding = \"GBK\"?>"
						+ "<CMS>"
						+ "<eb>"
						+ "<pub>"
						+ "<TransCode>QACCBAL</TransCode>"
						+ "<CIS>120290000034634</CIS>"
						+ "<BankCode>102</BankCode>"
						+ "<ID>201702.y.1202</ID>"
						+ "<TranDate>20170225</TranDate>"
						+ "<TranTime>144700000</TranTime>"
						+ "<fSeqno>123456</fSeqno>"
						+ "</pub>"
						+ "<in>"
						+ "<TotalNum>1</TotalNum>"
						+ "<ReqReserved1></ReqReserved1>"
						+ "<ReqReserved2></ReqReserved2>"
						+ "<rd>"
						+ "<iSeqno>1</iSeqno>"
						+ "<AccNo>9558881202000133284</AccNo>"
						+ "<CurrType>001</CurrType>"
						+ "<ReqReserved3></ReqReserved3>"
						+ "<ReqReserved4></ReqReserved4>"
						+ "</rd>"
						+ "</in>"
						+ "</eb>"
						+ "</CMS>";

				String signStr = String.valueOf(sContent.length());
				String lenStr = signStr;
				for (int i = 0; i < 10 - signStr.length(); i++) {
					lenStr = "0" + lenStr;
				}

				//byteSrc是明文字节数组
				byte[] byteSrc = sContent.getBytes();
				//证书私钥保护口令字符数组
				char[] keyPass = pk_pass.toCharArray();

				/*FileInputStream in1 = new FileInputStream(retcertPath);
				byte[] bcert = new byte[in1.available()];
				in1.read(bcert);
				in1.close();*/

				FileInputStream in2 = new FileInputStream(pk_path);
				//证书私钥字节数组
				byte[] bkey = new byte[in2.available()];
				in2.read(bkey);
				in2.close();

				//使用证书私钥对明文字节数组进行签名     byteSrc.length---明文字节数组长度
				byte[] sign = ReturnValue.sign(byteSrc, byteSrc.length, bkey, keyPass);

				String tmptt = (lenStr + sContent + "ICBCCMP" + new String(ReturnValue.base64enc(sign)));
				System.out.println("senddata=" + tmptt);

				tmpxml = new String(ReturnValue.base64enc((tmptt.getBytes())));
				mypost.addParameter("reqData", tmpxml);
				System.out.println("reqData=" + tmpxml);

				int returnFlag = mypost.execute(new HttpState(), myconn);
				try {
					String postResult = mypost.getResponseBodyAsString();
					System.out.println(postResult);
					postResult = postResult.substring(postResult.indexOf("reqData=") + 8);
					System.out.println("获取解码前的返回信息:" + postResult);
					//postResult = postResult.substring(8);
					byte[] decodeResult = ReturnValue.base64dec(postResult.getBytes());
					String sysout = new String(decodeResult);
					System.out.println("获取解码后的返回信息:" + sysout);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mypost.releaseConnection();
			myconn.close();
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error:" + e.getMessage());
		}
	}

}

package com.kunyao.assistant.core.feature.pay.icbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import cn.com.infosec.icbc.ReturnValue;

public class Demo {
	public static void main(String[] args) throws UnsupportedEncodingException {

		String str = "<?xml version=\"1.0\" encoding = \"GBK\"?>" + "<CMS>" + "<eb>" + "<pub>"
				+ "<TransCode>QACCBAL</TransCode>" + "<CIS>120290000439613</CIS>" + "<BankCode>102</BankCode>"
				+ "<ID>GMJT3t.y.1202</ID>" + "<TranDate>20120525</TranDate>" + "<TranTime>16140000000</TranTime>"
				+ "<fSeqno>20090306183200000000</fSeqno>" + "</pub>" + "<in>" + "<TotalNum>1</TotalNum>"
				+ "<ReqReserved1></ReqReserved1>" + "<ReqReserved2></ReqReserved2>" + "<rd>" + "<iSeqno>1</iSeqno>"
				+ "<AccNo>1202021109900004613</AccNo>" + "<CurrType>001</CurrType>" + "<ReqReserved3></ReqReserved3>"
				+ "<ReqReserved4></ReqReserved4>" + "</rd>" + "</in>" + "</eb>" + "</CMS>";
		str = "Version=0.0.0.1&TransCode=QACCBAL&BankCode=102&GroupCIS=120290000171230&ID=GMJT3t.y.1202&PackageID=20090306183200000000&Cert=&reqData="
				+ str;
		postserver(str);
	}

	public static void postserver(String str) {
		URL url;
		try {
			url = new URL("http://mybank3.dccnet.com.cn/servlet/ICBCCMPAPIReqServlet?userID=GMJT3t.y.1202&PackageID=20090306183200000000&SendTime=200120319154900");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");

			java.io.OutputStream os = connection.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(str);
			osw.flush();

			BufferedReader inss = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			StringBuffer content = new StringBuffer();
			while ((line = inss.readLine()) != null) {
				content.append(line);
			}
			inss.close();
			System.out.println(content.toString());
			String signMsg = content.toString();
			signMsg = signMsg.substring(8, signMsg.length());
			byte[] signB = signMsg.getBytes();
			signB = ReturnValue.base64dec(signMsg.getBytes());
			System.out.println(" ");
			System.out.println("返回结果：" + new String(signB, "GB2312"));
			System.out.println("运行结束！");
			inss = null;
			url = null;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.kunyao.assistant.core.feature.pay.icbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.http.HttpConnection;

import com.kunyao.assistant.core.utils.HttpsUtils;
import com.kunyao.assistant.core.utils.XMLUtils;

import cn.com.infosec.icbc.ReturnValue;

public class ICBCUtils {

	private String domain = "https://mybank3.dccnet.com.cn/servlet/ICBCINBSEBusinessServlet";
	private String keyPath = "";

	/**
	 * 证书密钥
	 */
	private String getCert() {
		try {
			FileInputStream in1 = new FileInputStream("C:\\Users\\Administrator\\Desktop\\网银支付\\20170109.crt");
			byte[] bcert = new byte[in1.available()];
			in1.read(bcert);
			in1.close();

			byte[] EncCert = ReturnValue.base64enc(bcert);
			byte[] DecCert = ReturnValue.base64dec(EncCert);
			if (DecCert != null) {
				return new String(ReturnValue.base64enc(DecCert)); // Base64编码
			} else {
				System.out.println("证书BASE64解码失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		ICBCUtils c = new ICBCUtils();
		c.apply(111, 10000);
	}
	
	/**
	 * 支付申请接口
	 */
	public void apply(Integer id, Integer amount) {
		Map<String, String> form = new HashMap<>();
		form.put("Version", "0.0.1.0");
		form.put("TransCode", "EPAYAPPLY");
		form.put("BankCode", "");
		form.put("GroupCIS", "");
		form.put("ID", "201701.y.1202 ");
		form.put("PackageID", new SimpleDateFormat("yyyyMMdd").format(new Date()) + new DecimalFormat("0000000").format(id)); // 产生规则为当前日期（北京时间，格式为yyyyMMdd）＋7位序列号（例如200212230000001，为2002年12月23日发送的一个交易请求包的包序列ID）
		form.put("Cert", getCert()); // 证书
		form.put("reqData", XMLUtils.icbcApply(String.valueOf(id), amount)); // 交易数据
		
		StringBuffer stringBuffer = new StringBuffer();
		for (String key : form.keySet()) {
			stringBuffer.append("&").append(key).append("=").append(form.get(key));
		}
		String result = HttpsUtils.requestByUrlConnection(domain, "POST", "?" + stringBuffer.substring(1));
		System.out.println(result);
	}

	/**
	 * 支付提交接口
	 */
	public void commit() {

	}
}

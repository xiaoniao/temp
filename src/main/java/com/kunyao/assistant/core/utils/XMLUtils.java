package com.kunyao.assistant.core.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Xml 工具类
 * 
 * @author GeNing
 * @since  2016.07.29
 * 
 */
public class XMLUtils {
	
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String, String> m = new HashMap<String, String>();
		
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		
		@SuppressWarnings("unchecked")
		Iterator<Element> it = root.getChildren().iterator();
		
		while(it.hasNext()) {
			
			Element e = it.next();
			String k = e.getName();
			String v = "";
			
			@SuppressWarnings("unchecked")
			List<Element> children = e.getChildren();
			
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtils.getChildrenText(children);
			}
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getChildrenText(List<Element> children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator<Element> it = children.iterator();
			while(it.hasNext()) {
				Element e = it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List<Element> list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(XMLUtils.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Map转xml
	 * @param parameters
	 * @return
	 */
	public static String mapParseXml(SortedMap<String, String> parameters) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<xml>");
		
		Set<String> keySet = parameters.keySet();
		Iterator<String> it = keySet.iterator();
		
		while(it.hasNext()) {
			String key = it.next();
			String value = parameters.get(key);
			
			if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
				sBuffer.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
			}else {
				sBuffer.append("<" + key + ">" + value + "</" + key +">");
			}
		}
		
		sBuffer.append("</xml>");
		return sBuffer.toString();
	}

	private static final String CIS = "120290001066991";
	private static final String ShopAcctNo = "1202022719927388888";
	
	/**
	 * 高级无界面支付申请接口
	 */
	public static String icbcApply(String orderId, int orderAmt) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding = \"GBK\"?>");
		stringBuffer.append("<CMS>");
		stringBuffer.append("	<eb>");
		stringBuffer.append("		<pub>");
		stringBuffer.append("			<TransCode>EPAYAPPLY</TransCode>");
		stringBuffer.append("			<CIS>").append(CIS).append("</CIS>");
		stringBuffer.append("			<BankCode>").append(CIS).append("</BankCode>");
		stringBuffer.append("			<ID>").append("201701.y.1202 ").append("</ID>");
		stringBuffer.append("			<TranDate>").append(new SimpleDateFormat("yyyyMMdd").format(new Date())).append("</TranDate>");
		stringBuffer.append("			<TranTime>").append(new SimpleDateFormat("HHmmssSSS").format(new Date())).append("</TranTime>");
		stringBuffer.append("			<fSeqno>").append(orderId).append("</fSeqno>");
		stringBuffer.append("		</pub>");
		stringBuffer.append("		<in>");
		stringBuffer.append("			<SignTime>").append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append("</SignTime>");
		stringBuffer.append("			<PayChannel>").append("1").append("</PayChannel>");
		stringBuffer.append("			<SubmitTime>").append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).append("</SubmitTime>");
		stringBuffer.append("			<OrderNo>").append(orderId).append("</OrderNo>");
		stringBuffer.append("			<OrderAmt>").append(orderAmt).append("</OrderAmt>");
		stringBuffer.append("			<PayStage>").append("1").append("</PayStage>");
		stringBuffer.append("			<PayStageFlag>").append("0").append("</PayStageFlag>");
		stringBuffer.append("			<ShopAcctNo>").append(ShopAcctNo).append("</ShopAcctNo>");
		stringBuffer.append("			<GoodsNo>").append("001").append("</GoodsNo>");
		stringBuffer.append("			<GoodsName>").append("支付").append("</GoodsName>");
		stringBuffer.append("			<GoodsNumber>").append("1").append("</GoodsNumber>");
		stringBuffer.append("			<TransferAmt>").append("0").append("</TransferAmt>");
		stringBuffer.append("			<JUnionFlag>").append("0").append("</JUnionFlag>");
		stringBuffer.append("			<Language1>").append("ZH_CN").append("</Language1>");
		stringBuffer.append("			<CurrType>").append("001").append("</CurrType>");
		stringBuffer.append("			<ShopCode>").append("###################").append("</ShopCode>");
		stringBuffer.append("			<CardFlag>").append("2").append("</CardFlag>");
		stringBuffer.append("			<NotifyType>").append("HS").append("</NotifyType>");
		stringBuffer.append("			<RSendType>").append("1").append("</RSendType>");
		stringBuffer.append("			<GoodsType>").append("0").append("</GoodsType>");
		stringBuffer.append("			<Phone>").append("###############").append("</Phone>");
		stringBuffer.append("			<CusAcctNo>").append("########").append("</CusAcctNo>");
		stringBuffer.append("			<CusAlias>").append("CusAlias").append("</CusAlias>");
		stringBuffer.append("			<ThirdFlag>").append("0").append("</ThirdFlag>");
		stringBuffer.append("			<AgentShopCode>").append("").append("</AgentShopCode>");
		stringBuffer.append("			<AgentShopName>").append("").append("</AgentShopName>");
		stringBuffer.append("			<AgentOrderFlag>").append("").append("</AgentOrderFlag>");
		stringBuffer.append("			<BuyerID>").append("").append("</BuyerID>");
		stringBuffer.append("			<Recer>").append("").append("</Recer>");
		stringBuffer.append("			<RecAddr>").append("").append("</RecAddr>");
		stringBuffer.append("			<RecPhone>").append("").append("</RecPhone>");
		stringBuffer.append("			<ShopAcctSeq>").append("").append("</ShopAcctSeq>");
		stringBuffer.append("		</in>");
		stringBuffer.append("	</eb>");
		stringBuffer.append("</CMS>");
		return stringBuffer.toString();
	}
	
	/**
	 * 高级无界面提交接口
	 */
	public static String icbcSubmit() {
		return "";
	}
}

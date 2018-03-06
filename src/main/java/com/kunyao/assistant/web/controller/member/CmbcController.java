package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.exception.ResourceNotFoundException;
import com.kunyao.assistant.core.feature.pay.cmbc.NetPay;
import com.kunyao.assistant.core.model.Recharge;
import com.kunyao.assistant.web.service.RechargeService;

@Controller
@RequestMapping(value = "/mc/cmbc")
public class CmbcController {
	private static final Logger log = LoggerFactory.getLogger(CmbcController.class);

	@Resource
	private RechargeService rechargeService;
	
	/**
	 * 签约结果回调
	 */
	@RequestMapping(value = "agreement")
	@ResponseBody
	public String agreement(String jsonRequestData) {
		log.info("jsonRequestData:" + jsonRequestData);
		return "";
	}
	
	public static void main(String[] args) {
		//CmbcController c = new CmbcController();
		//c.payNotice("{\"noticeData\":{\"branchNo\":\"0571\",\"merchantPara\":\"\",\"httpMethod\":\"POST\",\"merchantNo\":\"001268\",\"cardType\":\"02\",\"bankDate\":\"20170511\",\"discountFlag\":\"N\",\"noticeSerialNo\":\"0571001268201705110000000339\",\"date\":\"20170511\",\"orderNo\":\"0000000339\",\"bankSerialNo\":\"17251138900000000020\",\"noticeType\":\"BKPAYRTN\",\"noticeUrl\":\"http://116.62.6.249/rest/mc/cmbc/payNotice\",\"amount\":\"0.01\",\"discountAmount\":\"0.00\",\"dateTime\":\"20170511165059\"},\"signType\":\"RSA\",\"version\":\"1.0\",\"charset\":\"UTF-8\",\"sign\":\"x6Adl7UN3YKvsRJjNFvC75h1+yOv5J4YBGgVkwCK1bYMjYa6BKBhg5/tIpTcWQkKQgfc97OzTPMXYEQvXgg5Jdfu10/cg9BrUUewfVpDN0bTTxAf61sgBoD8jfxTEEq7ehQOeBPfazx6Yq0aYloRbubATdCN0xG6NTLR0v5lFlM=\"}");
	}

	/**
	 * 支付成功回调
	 */
	@RequestMapping(value = "payNotice")
	@ResponseBody
	public String payNotice(String jsonRequestData) {
		log.info(jsonRequestData);
		if (!NetPay.valid(jsonRequestData)) {
			log.info("验签失败");
			throw new ResourceNotFoundException();//抛出404状态码
		}
		log.info("验签成功");
		JSONObject noticeData = new JSONObject(jsonRequestData).getJSONObject("noticeData");
		String orderNo = noticeData.getString("orderNo");
		String bankSerialNo = noticeData.getString("bankSerialNo");
		
		Integer rechargeId = Integer.valueOf(orderNo);
		Recharge r = rechargeService.finishPay(rechargeId, bankSerialNo);
		if (r != null && r.getId() > 0) {
			return "success";
		}
		throw new ResourceNotFoundException();//抛出404状态码
	}

	/**
	 * 支付成功跳转页面
	 */
	@RequestMapping(value = "returnPage")
	@ResponseBody
	public String returnPage() {
		log.info("returnPage");
		StringBuffer result = new StringBuffer();
		result.append("<html>")
				.append("<head>")
				.append("</head>")
				.append("<body onload=\"load()\">")
				.append("</body>")
				.append("<script>")
					.append("function load() {")
					.append("window.webview.closeActivity()")
				.append("	}")
				.append("</script>")
				.append("</html>");
		return result.toString();
	}

}

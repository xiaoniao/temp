package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.web.service.OrderDelayService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.RechargeService;

@Controller
@RequestMapping(value = "/mc/icbc")
public class ICBCController {
	private Logger log = LoggerFactory.getLogger(ICBCController.class);

	@Resource
	private OrderService orderService;

	@Resource
	private RechargeService rechargeService;

	@Resource
	private OrderDelayService orderDelayService;

	/**
	 * 
	 * @param merVAR
	 *            返回商户变量，商户提交接口中merVAR字段当返回银行结果时，作为一个隐藏域变量，商户可以用此变量维护session等等。
	 *            由客户端浏览器支付完成后提交通知结果时是明文传输，建议商户对此变量使用额外安全防范措施，如签名、base64，
	 *            银行端将此字段原样返回
	 * @param notifyData
	 *            通知结果数据 xml格式，提交商户时对xml明文串进行了base64编码
	 * @param signMsg
	 *            银行对通知结果的签名数据，银行使用自己证书对商户通知消息notifyData字段的xml格式明文串进行的签名，
	 *            然后进行BASE64编码后的字符串。
	 * @return
	 */
	@RequestMapping(value = "payResult", method = RequestMethod.POST)
	@ResponseBody
	public String payResult(String merVAR, String notifyData, String signMsg) {
//		ICBCUtils icbcUtils = new ICBCUtils();
//		boolean verifyResult = icbcUtils.verifySign(signMsg);
//		log.info("verifyResult:" + verifyResult);
//		if (verifyResult) {
//			ICBCResult icbcResult = XMLUtils.parseICBC(icbcUtils.base64desc(notifyData));
//			Integer rechargeId = Integer.valueOf(icbcResult.getOrderid().replace("recharge_", ""));
//			System.out.println("rechargeId:" + rechargeId);
//			Recharge recharge = rechargeService.findRechargeInfo(rechargeId);
//			Double rechargeMoney = recharge.getMoney() * 100;
//			if (!(recharge != null && recharge.getId() > 0
//					&& icbcResult.getAmount().equals(rechargeMoney.intValue()))) {
//				log.info("error");
//				return "";
//			}
//			Recharge r = rechargeService.finishPay(rechargeId, icbcResult.getTranSerialNo());
//			if (r != null && r.getId() > 0) {
//				return "success";
//			}
//		}
		return "";
	}
}

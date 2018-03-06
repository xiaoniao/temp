package com.kunyao.assistant.web.controller.member;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.wx.WXConfig;
import com.kunyao.assistant.core.entity.wx.WXMember;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.weixin.WxService;
import com.kunyao.assistant.core.feature.weixin.WxUtils;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderDelay;
import com.kunyao.assistant.core.model.Recharge;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.core.utils.XMLUtils;
import com.kunyao.assistant.web.service.MemberInfoService;
import com.kunyao.assistant.web.service.OrderDelayService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.RechargeService;

@Controller
@RequestMapping(value = "/mc/wx")
@ResponseBody
public class WXController {
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private RechargeService rechargeService;
	
	@Resource
	private OrderDelayService orderDelayService;

	/**
	 * 获取微信JSAPI Config对象
	 * 
	 * @param request
	 * @param cUrl
	 * @return
	 */
	@RequestMapping(value = "/take_config/")
	public Result wxConfig(HttpServletRequest request, String cUrl) {
		if (StringUtils.isNull(cUrl)) cUrl = WxUtils.wxUrl(request);
		WXConfig wxConfig = WxUtils.takeWXConfig(cUrl);
		if (wxConfig == null) {
			return ResultFactory.createError("wxConfig == null");
		}
		return ResultFactory.createJsonSuccess(wxConfig);
	}

	/**
	 * 获取用户基本信息
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/get_userinfo/")
	public Result wxUserInfo(@RequestParam String code) {
		WXMember wxMember = WxService.getWXMember(code);
		return ResultFactory.createJsonSuccess(wxMember);
	}

	/**
	 * 微信支付回调
	 */
	@RequestMapping(value = "/pay_success/")
	public String weixinPaySuccess(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {

		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();

		String result = new String(outSteam.toByteArray(), "utf-8"); // 获取微信调用我们notify_url的返回信息

		Map<String, String> map = XMLUtils.doXMLParse(result);

		if (!map.get("result_code").toString().equalsIgnoreCase("SUCCESS"))
			return "failed";

		String orderCard = String.valueOf(map.get("out_trade_no"));
		String payTicket = String.valueOf(map.get("transaction_id"));
		
		boolean flag = false;
		if ("order".equals(orderCard.substring(0, orderCard.indexOf("_")))) {
			Integer orderId = null;
			try {
				Order order = orderService.queryOrderByOrderCard(orderCard.replace("order_", ""));
				orderId = order.getId();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			if (orderId != null) {
				Order order = orderService.finishPay(orderId, payTicket);
				if (order != null && order.getId() > 0)
					flag = true;
			}
		} else if ("recharge".equals(orderCard.substring(0, orderCard.indexOf("_")))) {
			Recharge recharge = rechargeService.finishPay(Integer.valueOf(orderCard.replace("recharge_", "")), payTicket);
			if (recharge != null && recharge.getId() > 0)
				flag = true;
		} else if("delayed".equals(orderCard.substring(0, orderCard.indexOf("_")))) {
			OrderDelay orderDelay = orderDelayService.finishPay(Integer.valueOf(orderCard.replace("delay_", "")), payTicket);
			if (orderDelay != null && orderDelay.getId() > 0)
				flag = true;
		}

		if (flag)
			return "success";
		else
			return "failed";
	}
}

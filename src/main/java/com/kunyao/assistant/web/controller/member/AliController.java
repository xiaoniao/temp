package com.kunyao.assistant.web.controller.member;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderDelay;
import com.kunyao.assistant.core.model.Recharge;
import com.kunyao.assistant.core.utils.AliPayUtils;
import com.kunyao.assistant.web.service.OrderDelayService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.RechargeService;

@Controller
@RequestMapping(value = "/callback")
public class AliController {
	private Logger log = LoggerFactory.getLogger(AliController.class);
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private RechargeService rechargeService;
	
	@Resource
	private OrderDelayService orderDelayService;
	
	/**
	 * 支付宝支付回调
	 * @param request
	 * @return 校验成功返回success，校验失败返回failure
	 */
	@RequestMapping(value = "alipay", method = RequestMethod.POST)
	@ResponseBody
	public String alipay(HttpServletRequest request) {
		
		Map<String, String> params = new HashMap<String, String>();
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}

		boolean signVerified = AliPayUtils.rsaCheckV2(params);
		log.info("signVerified:" + signVerified);
		if (signVerified) {
			// 验签成功后
			// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
			// 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）
			// 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
			// 4、验证app_id是否为该商户本身
			
			String trade_no = params.get("trade_no"); // 支付宝交易凭证号
			String out_trade_no = params.get("out_trade_no"); // 原支付请求的商户订单号
			String total_amount = params.get("total_amount"); // 本次交易支付的订单金额，单位为人民币（元）
			String seller_id = params.get("seller_id"); // 卖家支付宝用户号
			String app_id = params.get("app_id"); // 支付宝分配给开发者的应用Id
			String trade_status = params.get("trade_status");
			
			log.info("trade_no" + trade_no + 
					 "out_trade_no" + out_trade_no +
					 "total_amount" + total_amount +
					 "seller_id" + seller_id +
					 "app_id" + app_id +
					 "trade_status" + trade_status);
			
			if (trade_status.equals("TRADE_SUCCESS")) {
				boolean flag = false;
				if ("order".equals(out_trade_no.substring(0, out_trade_no.indexOf("_")))) {
					String orderCard = out_trade_no.replace("order_", "");
					Order order = null;
					try {
						order = orderService.queryOrderByOrderCard(orderCard);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					if (!(order != null && order.getId() != null && total_amount.equals(new DecimalFormat("#.##").format(order.getServiceMoney())))) {
						return "failure";
					}
					
					Order o = orderService.finishPay(order.getId(), trade_no);
					if (o != null && o.getId() > 0) {
						flag = true;
					}
				} else if ("recharge".equals(out_trade_no.substring(0, out_trade_no.indexOf("_")))) {
					Integer rechargeId = Integer.valueOf(out_trade_no.replace("recharge_", ""));
					Recharge recharge = rechargeService.findRechargeInfo(rechargeId);
					if (!(recharge != null && recharge.getId() > 0 && total_amount.equals(new DecimalFormat("#.##").format(recharge.getMoney())))) {
						return "failure";
					}
					Recharge r = rechargeService.finishPay(rechargeId, trade_no);
					if (r != null && r.getId() > 0) {
						flag = true;
					} 
				} else if ("delay".equals(out_trade_no.substring(0, out_trade_no.indexOf("_")))) {
					Integer delayId = Integer.valueOf(out_trade_no.replace("delay_", ""));
					OrderDelay delay = orderDelayService.findOrderDelayInfo(delayId);
					if (!(delay != null && delay.getId() > 0 && total_amount.equals(new DecimalFormat("#.##").format(delay.getMoney())))) {
						return "failure";
					}
					OrderDelay d = orderDelayService.finishPay(delayId, out_trade_no);
					if (d != null && d.getId() > 0)
						flag = true;
				}
				
				if (flag) {
					return "success";
				} else {
					// 验签失败则记录异常日志
				}
			}
		} else {
			// 验签失败则记录异常日志
		}
		return "failure";
	}

}

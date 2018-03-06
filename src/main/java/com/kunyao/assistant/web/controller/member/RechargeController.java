package com.kunyao.assistant.web.controller.member;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.MemberInfo;
import com.kunyao.assistant.core.model.Recharge;
import com.kunyao.assistant.web.service.MemberInfoService;
import com.kunyao.assistant.web.service.RechargeService;

@Controller
@RequestMapping("/mc/recharge")
public class RechargeController {

	@Resource
	private RechargeService rechargeService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	/**
	 * 生成充值订单
	 */
	@ResponseBody
	@RequestMapping(value = "/create", produces = {"application/json;charset=UTF-8"})
	public Result createRecharge(@RequestParam Integer userId, @RequestParam Double money, @RequestParam Integer payMethod, @RequestParam Integer isNeedInvoice, String invoiceTitle,
			String invoiceAddress, String invoicePeople, String invoiceMobile, HttpServletRequest request) {
		MemberInfo memberInfo = memberInfoService.findMemberInfo(userId);
		Recharge recharge = rechargeService.createRecharge(userId, money, payMethod, isNeedInvoice, invoiceTitle, invoiceAddress, invoicePeople, invoiceMobile);
		Map<String, Object> map = rechargeService.payRecharge(request, recharge, memberInfo.getOpenId());
		return ResultFactory.createJsonSuccess(map);
	}
	
	/**
	 * 支付
	 */
	@ResponseBody
	@RequestMapping(value = "/pay", produces = {"application/json;charset=UTF-8"})
	public Result payRecharge(@RequestParam Integer rechargeId, HttpServletRequest request) {
		MemberInfo memberInfo = null;;
		try {
			memberInfo = memberInfoService.findMemberInfo(rechargeService.findByID(rechargeId).getUserId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		Map<String, Object> map;
		map = rechargeService.pay(request, rechargeId, memberInfo.getOpenId());
		return ResultFactory.createJsonSuccess(map);
	}
}

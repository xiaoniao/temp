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
import com.kunyao.assistant.core.model.OrderDelay;
import com.kunyao.assistant.web.service.MemberInfoService;
import com.kunyao.assistant.web.service.OrderDelayService;

@Controller
@RequestMapping("/mc/delay")
public class OrderDelayController {

	@Resource
	private OrderDelayService orderDelayService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	/**
	 * 生成延时费支付订单
	 */
	@ResponseBody
	@RequestMapping(value = "/create", produces ={"application/json;charset=UTF-8"})
	public Result createDelay(@RequestParam Integer userId, @RequestParam Integer orderId, @RequestParam Integer hour, @RequestParam Integer payMethod, HttpServletRequest request) {
		MemberInfo memberInfo = memberInfoService.findMemberInfo(userId);
		OrderDelay orderDelay = orderDelayService.createOrderDelay(userId, orderId, hour, payMethod);
		try {
			Map<String, Object> map = orderDelayService.payOrderDelay(request, orderDelay, memberInfo.getOpenId());
			return ResultFactory.createJsonSuccess(map);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 支付
	 */
	@ResponseBody
	@RequestMapping(value = "/pay", produces ={"application/json;charset=UTF-8"})
	public Result payOrderDelay(@RequestParam Integer delayId, HttpServletRequest request) {
		MemberInfo memberInfo = null;
		try {
			memberInfo = memberInfoService.findMemberInfo(orderDelayService.findByID(delayId).getUserId());
			Map<String, Object> map = orderDelayService.pay(request, delayId, memberInfo.getOpenId());
			return ResultFactory.createJsonSuccess(map);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 查询账户余额、延时费标准
	 */
	@ResponseBody
	@RequestMapping(value = "/delayInfo")
	public Result delayInfo(Integer userId) {
		Map<String, Double> map = null;
		try {
			map = orderDelayService.findDelayInfo(userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(map);
	}
}

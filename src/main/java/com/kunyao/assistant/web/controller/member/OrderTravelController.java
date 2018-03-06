package com.kunyao.assistant.web.controller.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.web.service.OrderTravelService;
import com.kunyao.assistant.web.service.TravelDescService;

@Controller
@RequestMapping(value = "/mc/travel")
@ResponseBody
public class OrderTravelController {

	@Resource
	private OrderTravelService orderTravelService;
	
	@Resource
	private TravelDescService travelDescService;

	/**
	 * 会员结束单天服务
	 */
	@RequestMapping(value = "/finish")
	public Result finish(Integer orderId, String date) {
		int result;
		try {
			result = orderTravelService.updateMemberFinish(orderId, date);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(result);
	}
	
	/**
	 * 延时费页面结束服务
	 */
	@ResponseBody
	@RequestMapping(value = "/delay_finish")
	public Result finish(Integer orderId) {
		String date = DateUtils.parseYMDTime(new Date());
		Integer orderStatus;
		try {
			orderStatus = orderTravelService.updateFinishTodayTravel(orderId, date);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(orderStatus);
	}
	
	/**
	 * 账单(行程单)列表
	 * @param orderId
	 * @param costPayMethod
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
	public Result travelList(@RequestParam Integer orderId, @RequestParam Integer costPayMethod) {
		List<Map<String, Object>> travelList = orderTravelService.selectTravelListWithCost(orderId, costPayMethod);
		return ResultFactory.createJsonSuccess(travelList);
	}
	
	/**
	 * 确认行程
	 * @param orderId
	 * @param costPayMethod
	 * @param userId
	 * @param dayNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/confirm", produces = {"application/json;charset=UTF-8"})
	public Result confirmTravel(@RequestParam Integer orderId, @RequestParam Integer costPayMethod, Integer dayNum) {
		try {
			orderTravelService.updateConfirmTravel(orderId, costPayMethod, dayNum);
			return ResultFactory.createSuccess();
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 行程要求服务列表
	 */
	@ResponseBody
	@RequestMapping(value = "/desc_list", produces = {"application/json;charset=UTF-8"})
	public Result descList() {
		return ResultFactory.createJsonSuccess(travelDescService.queryList());
	}

}

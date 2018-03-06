package com.kunyao.assistant.web.controller.cross;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.web.service.OrderTravelService;

/**
 * 行程单
 */
@Controller
@RequestMapping(value = "/cc/travel")
@ResponseBody
public class CrossTravelController {
	
	@Resource
	private OrderTravelService orderTravelService;
	
	/**
	 * 行程单详情
	 */
	@RequestMapping(value = "/info")
	public Result info(@RequestParam Integer travelId) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderTravelService.queryInfo(travelId));
	}
	
	/**
	 * 行程单列表
	 */
	@RequestMapping(value = "/list")
	public Result traveList(@RequestParam Integer orderId) {
		return ResultFactory.createJsonSuccess(orderTravelService.queryTravelList(orderId));
	}
	
	/**
	 * 添加行程单
	 */
	@RequestMapping(value = "/add")
	public Result add(@RequestParam Integer orderId, @RequestParam Date startTime, @RequestParam Date endTime, @RequestParam String title) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderTravelService.insertOrderTravel(orderId, startTime, endTime, title));
	}
	
	/**
	 * 删除行程单
	 */
	@RequestMapping(value = "/remove")
	public Result remove(@RequestParam Integer travelId) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderTravelService.updateRemove(travelId));
	}
	
	/**
	 * 编辑行程单
	 */
	@RequestMapping(value = "/edit")
	public Result edit(@RequestParam Integer travelId, @RequestParam Date startTime, @RequestParam Date endTime, @RequestParam String title) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderTravelService.updateEdit(travelId, startTime, endTime, title));
	}
	
	/**
	 * 提交行程，推送给会员 。只允许推送给待服务订单
	 */
	@RequestMapping(value = "/push")
	public Result push(@RequestParam Integer orderId) throws ServiceException {
		orderTravelService.updatePush(orderId);
		return ResultFactory.createJsonSuccess(true);
	}
	
	/**
	 * 开始上班
	 */
	@RequestMapping(value = "/start")
	public Result start(@RequestParam Integer orderId, @RequestParam String date) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderTravelService.updateCrossStart(orderId, date));
	}
	
	/**
	 * 当日下班（结束单天行程）
	 */
	@RequestMapping(value = "/finish")
	public Result finish(@RequestParam Integer orderId, @RequestParam String date) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderTravelService.updateCrossFinish(orderId, date));
	}
	
	/**
	 * 选择行程单
	 */
	@RequestMapping(value = "/select")
	public Result finish(@RequestParam Integer orderId) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderTravelService.queryTravelByOrderId(orderId));
	}
}

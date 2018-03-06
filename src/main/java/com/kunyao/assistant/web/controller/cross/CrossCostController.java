package com.kunyao.assistant.web.controller.cross;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.web.service.OrderCostService;

/**
 * 账单
 */
@Controller
@RequestMapping(value = "/cc/cost")
@ResponseBody
public class CrossCostController {

	@Resource
	private OrderCostService orderCostService;

	/**
	 * 账单详情
	 */
	@RequestMapping(value = "/info")
	public Result info(@RequestParam Integer costId) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderCostService.queryById(costId));
	}
	
	/**
	 * 账单列表
	 */
	@RequestMapping(value = "/list")
	public Result costList(@RequestParam Integer orderId) {
		return ResultFactory.createJsonSuccess(orderCostService.queryCostList(orderId));
	}

	/**
	 * 添加账单
	 */
	@RequestMapping(value = "/add")
	public Result add(@RequestParam Integer orderId, @RequestParam Integer travelId, @RequestParam Integer costItemId, @RequestParam Double money, String remark) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderCostService.createAdd(orderId, travelId, costItemId, money, remark));
	}

	/**
	 * 删除账单
	 */
	@RequestMapping(value = "/remove")
	public Result remove(@RequestParam Integer costId) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderCostService.updateRemove(costId));
	}

	/**
	 * 编辑账单
	 */
	@RequestMapping(value = "/edit")
	public Result edit(@RequestParam Integer orderId, @RequestParam Integer costId, Double money, String remark) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderCostService.updateEdit(orderId, costId, money, remark));
	}
	
	/**
	 * 消费类目
	 */
	@RequestMapping(value = "/costItem")
	public Result costItem() throws ServiceException {
		return ResultFactory.createJsonSuccess(orderCostService.queryAllCostItem());
	}
}

package com.kunyao.assistant.web.controller.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.model.OrderCost;
import com.kunyao.assistant.web.service.OrderCostService;

@Controller("umOrderCostTroller")
@RequestMapping("/um/orderCost")
public class OrderCostTroller {

	@Resource
	private OrderCostService orderCostService;

	private final String LIST_PAGE_URL = "um/order-cost-list";
	
	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		return LIST_PAGE_URL;
	}
	
	@ResponseBody
	@RequestMapping(value = "/lscount")
	public Result count(Integer pageSize, OrderCost orderCost) {
		PageInfo page = orderCostService.selectOrderCostListCount(pageSize, orderCost);
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Result list(Integer currentPage, Integer pageSize, OrderCost orderCost) {
		try {
			return ResultFactory.createJsonSuccess(orderCostService.selectOrderCostList(currentPage, pageSize, orderCost));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}
}

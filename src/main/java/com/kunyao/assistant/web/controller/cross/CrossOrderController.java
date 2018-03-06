package com.kunyao.assistant.web.controller.cross;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.PageRequestDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.web.service.OrderCancelService;
import com.kunyao.assistant.web.service.OrderService;

@Controller
@RequestMapping(value = "/cc/order")
@ResponseBody
public class CrossOrderController {

	@Resource
	private OrderService orderService;
	
	@Resource
	private OrderCancelService orderCancelService;

	/**
	 * 强制轮训订单
	 */
	@RequestMapping(value = "/loop")
	public void loop() throws ServiceException {
		orderService.loop();
	}
	
	/**
	 * 订单列表
	 */
	@RequestMapping(value = "/list")
	public Result list(@RequestParam Integer crossUserId, String search, PageRequestDto dto) {
		PageData<Order> pageData = null;
		try {
			pageData = orderService.queryByCrossUserId(crossUserId, search, dto.getCurrentPage(), dto.getPageSize());
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(pageData);
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping(value = "/detail")
	public Result detail(Integer orderId) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderService.queryOrderDetail(orderId));
	}

	/**
	 * 输入服务编码，输入成功订单状态更改为服务中
	 */
	@RequestMapping(value = "/serviceCode")
	public Result serviceCode(@RequestParam Integer orderId, String serviceCode) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderService.serviceCode(orderId, serviceCode));
	}
	
	/**
	 * 修改订单联系方式和付款方式，限制:只允许修改待服务订单
	 */
	@RequestMapping(value = "/edt")
	public Result edt(@RequestParam Integer orderId, Integer contactMethod, Integer costPayMethod) throws ServiceException {
		return ResultFactory.createJsonSuccess(orderService.edit(orderId, contactMethod, costPayMethod));
	}

	/**
	 * 订单行程要求
	 */
	@RequestMapping(value = "/require")
	public Result require(@RequestParam Integer orderId) {
		return ResultFactory.createJsonSuccess(orderService.queryOrderRequire(orderId));
	}
	
	/**
	 * 申请取消订单
	 */
	@RequestMapping(value = "/aheadOfTheEnd")
	public Result aheadOfTheEnd(@RequestParam Integer orderId, @RequestParam Integer userId, String remark) {
		try {
			return ResultFactory.createJsonSuccess(orderCancelService.crossApplyCancel(orderId, userId, remark));
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 历史订单
	 */
	@RequestMapping(value = "/history")
	public Result history(@RequestParam Integer crossUserId, String search, PageRequestDto dto) {
		PageData<Order> pageData = orderService.queryCrossHistoryOrder(crossUserId, dto.getCurrentPage(), dto.getPageSize());
		return ResultFactory.createJsonSuccess(pageData);
	}
	
	/**
	 * 历史订单详情
	 */
	@RequestMapping(value = "/info")
	public Result info(@RequestParam Integer orderId) {
		return ResultFactory.createJsonSuccess(orderService.queryCrossOrderInfo(orderId));
	}
}

package com.kunyao.assistant.web.controller.member;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.PageRequestDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.socket.SocketServer;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderCancel;
import com.kunyao.assistant.web.service.OrderCancelService;
import com.kunyao.assistant.web.service.OrderService;

@Controller
@RequestMapping(value = "/mc/order")
@ResponseBody
public class OrderController {

	@Resource
	private OrderService orderService;
	
	@Resource
	private OrderCancelService orderCancelService;
	
	/**
	 * 查询支付配置信息和用户信息
	 */
	@RequestMapping(value = "/payInfo")
	public Result payInfo(Integer userId, Integer day) {
		Map<String, Object> map;
		try {
			map = orderService.queryPayInfo(userId, day);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(map);
	}
	
	/**
	 * 生成订单
	 */
	@RequestMapping(value = "/create")
	public Result createOrder(HttpServletRequest request, Integer userId, Integer crossUserId, String startTime, String endTime, 
			String couponId, Integer payMethod, Integer cityId, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile) {
		try {
			Order order = orderService.createOrder(userId, crossUserId, startTime, endTime, couponId, payMethod, cityId, isNeedInvoice, invoiceTitle, invoiceAddress, invoicePeople, invoiceMobile);
			Map<String, Object> map = orderService.payOrder(request, order);
			SocketServer.sendNotify("通知", "您有一条新订单");
			return ResultFactory.createJsonSuccess(map);
		} catch (ParseException e) {
			return ResultFactory.createError(ResultCode.DATE_FORMAT_ERROR);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 支付订单
	 */
	@RequestMapping(value = "/pay")
	public Result payOrder(HttpServletRequest request, Integer orderId) {
		Map<String, Object> map;
		try {
			map = orderService.payOrder(request, orderId);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(map);
	}

	/**
	 * 完善订单
	 */
	@RequestMapping(value = "/fill")
	public Result fillOrder(Integer orderId, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile, 
			Integer contactMethod, Integer costPayMethod, String travelDescId, String travelRequireText, String travelRequireVoice) {

		try {
			orderService.fillOrder(orderId, isNeedInvoice, invoiceTitle, invoiceAddress, invoicePeople, invoiceMobile, contactMethod, costPayMethod, travelDescId, 
					travelRequireText, travelRequireVoice);
			
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(true);
	}
	
	/**
	 * 订单列表
	 */
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
	public Result orderList(@RequestParam Integer userId, @RequestParam Integer currentPage, @RequestParam Integer pageSize) {
		PageData<Order> pageData = orderService.queryOrderList(userId, currentPage, pageSize);
		return ResultFactory.createJsonSuccess(pageData);
	}
	
	/**
	 * 订单详情
	 */
	@RequestMapping(value = "/detail", produces = {"application/json;charset=UTF-8"})
	public Result orderInfo(@RequestParam Integer orderId) {
		Map<String, Object> map = new HashMap<>();
		try {
			map = orderService.findOrderTotalInfo(orderId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(map);
	}
	
	/**
	 * 查询待评价订单
	 */
	@ResponseBody
	@RequestMapping(value = "/not_finished_order", produces = {"application/json;charset=UTF-8"})
	public Result orderNotFinished(@RequestParam Integer userId) {
		Order order;
		try {
			order = orderService.selectOrderNotFinished(userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(order);
	}
	
	/**
	 * 取消订单(未接单)
	 */
	@RequestMapping(value = "/cancel", produces = {"application/json;charset=UTF-8"})
	public Result cancelOrder(@RequestParam Integer orderId) {
		Order order = null;
		try {
			order = orderService.updateCancelOrder(orderId);
			SocketServer.sendNotify("通知", "有一条取消订单");
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(order);
	}
	
	/**
	 * 查询行程要求
	 */
	@ResponseBody
	@RequestMapping(value = "/require", produces = {"application/json;charset=UTF-8"})
	public Result queryRequire(@RequestParam Integer orderId) {
		try {
			return ResultFactory.createJsonSuccess(orderService.findOrderRequireInfo(orderId));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 行程列表
	 */
	@ResponseBody
	@RequestMapping(value = "/travel_list", produces = {"application/json;charset=UTF-8"})
	public Result travelList(@RequestParam Integer userId, PageRequestDto pageRequst) {
		PageData<Order> pageData = orderService.findOrderListWithTravel(userId, pageRequst.getCurrentPage(), pageRequst.getPageSize());
		return ResultFactory.createJsonSuccess(pageData);
	}
	
	/**
	 * 用户申请取消订单
	 */
	@ResponseBody
	@RequestMapping(value = "/apply_cancel", produces = {"application/json;charset=UTF-8"})
	public Result applyCancel(@RequestParam Integer orderId, String remark) {
		try {
			OrderCancel orderCancel = orderCancelService.memberApplyCancel(orderId, remark);
			SocketServer.sendNotify("通知", "有一条申请取消订单");
			return ResultFactory.createJsonSuccess(orderCancel);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 查询用户服务中订单数量
	 */
	@ResponseBody
	@RequestMapping(value = "/serving_count", produces = {"application/json;charset=UTF-8"})
	public Result servingOrderCount(Integer userId) {
		int count = 0;
		try {
			count = orderService.findCountServing(userId);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(count);
	}
}

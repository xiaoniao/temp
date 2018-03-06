package com.kunyao.assistant.web.controller.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.model.OrderConfig;
import com.kunyao.assistant.web.service.OrderConfigService;
import com.kunyao.assistant.web.service.OrderService;

@Controller
@RequestMapping("/um/orderConfig")
public class OrderConfigController {
	
	@Resource
	private OrderConfigService orderConfigService;
	
	@Resource
	private OrderService orderService;
	
	private final String LIST_PAGE_URL = "um/order-config-list";
	
	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		
		OrderConfig	orderConfigInfo = orderConfigService.selectOrderConfig();
		model.addAttribute("orderConfigInfo", orderConfigInfo);
		return LIST_PAGE_URL;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateServiceMoney", produces = { "application/json;charset=UTF-8" })
	public Result updateServiceMoney(@RequestParam Double serviceMoney, @RequestParam Integer id) {
		if (serviceMoney == null && id == null){
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		
		OrderConfig orderConfig = new OrderConfig();
		orderConfig.setServiceMoney(serviceMoney);
		try {
			if (id == null || id < 1){
				orderConfigService.insert(orderConfig);
			} else {
				orderConfig.setId(id);
				orderConfigService.update(orderConfig);
			}
		} catch (Exception e) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateDelayMoney", produces = { "application/json;charset=UTF-8" })
	public Result updateDelayMoney(@RequestParam Double delayMoney, @RequestParam Integer id) {
		if (delayMoney == null && id == null){
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		OrderConfig orderConfig = new OrderConfig();
		orderConfig.setDelayMoney(delayMoney);
		try {
			if (id == null || id < 1){
				orderConfigService.insert(orderConfig);
			} else {
				orderConfig.setId(id);
				orderConfigService.update(orderConfig);
			}
		} catch (Exception e) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateLoopTime", produces = { "application/json;charset=UTF-8" })
	public Result updateLoopTime(@RequestParam Integer loopTime, @RequestParam Integer id) {
		if (loopTime == null && id == null){
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		
		// 限制轮训时间在1-59分钟内
		if (loopTime < 1 || loopTime > 59) {
			return ResultFactory.createError(ResultCode.LOOP_TIME_OVER_SCOPE);
		}
	
		// 修改轮巡时间是判断是否有订单正在等待轮巡，如有则提示管理员“当前订单正在轮巡，请在周期结束后再提交”，未完成
		if (Constant.isLoop == true){
			return ResultFactory.createError(ResultCode.ORDERS_ARE_BEING_WHEEL_GUARD);
		}

		OrderConfig orderConfig = new OrderConfig();
		orderConfig.setLoopTime(loopTime);
		
		try {
			if (id == null || id < 1){
				orderConfigService.insert(orderConfig);
			} else {
				orderConfig.setId(id);
				orderConfigService.update(orderConfig);
			}
			boolean result = orderConfigService.updateLooptime(loopTime);
			if (result) {
				return ResultFactory.createJsonSuccess(ResultCode.LOOP_TIME_CHANGE_SUCCESS);
			}
		} catch (Exception e) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMaxDiscount", produces = { "application/json;charset=UTF-8" })
	public Result updateMaxDiscount(@RequestParam Double maxDiscount, @RequestParam Integer id) {
		if (maxDiscount == null && id == null){
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		OrderConfig orderConfig = new OrderConfig();
		orderConfig.setMaxDiscount(maxDiscount);
		try {
			if (id == null || id < 1){
				orderConfigService.insert(orderConfig);
			} else {
				orderConfig.setId(id);
				orderConfigService.update(orderConfig);
			}
		} catch (Exception e) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateFullPayment", produces = { "application/json;charset=UTF-8" })
	public Result updateFullPayment(@RequestParam Double fullPayment, @RequestParam Integer id) {
		if (fullPayment == null && id == null){
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		OrderConfig orderConfig = new OrderConfig();
		orderConfig.setFullPayment(fullPayment);
		try {
			if (id == null || id < 1){
				orderConfigService.insert(orderConfig);
			} else {
				orderConfig.setId(id);
				orderConfigService.update(orderConfig);
			}
		} catch (Exception e) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateFullBalance", produces = { "application/json;charset=UTF-8" })
	public Result updateFullBalance(@RequestParam Double fullBalance, @RequestParam Integer id) {
		if (fullBalance == null && id == null){
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		OrderConfig orderConfig = new OrderConfig();
		orderConfig.setFullBalance(fullBalance);
		try {
			if (id == null || id < 1){
				orderConfigService.insert(orderConfig);
			} else {
				orderConfig.setId(id);
				orderConfigService.update(orderConfig);
			}
		} catch (Exception e) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
}

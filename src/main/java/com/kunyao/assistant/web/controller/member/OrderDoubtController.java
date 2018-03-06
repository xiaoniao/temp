package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderDoubt;
import com.kunyao.assistant.web.service.OrderDoubtService;
import com.kunyao.assistant.web.service.OrderService;

@Controller("mcDoubt")
@RequestMapping("/mc/doubt")
public class OrderDoubtController {

	@Resource
	private OrderDoubtService orderDoubtService;
	
	 @Resource
		private OrderService orderService;
	 
	@ResponseBody
	@RequestMapping(value = "/create")
	public Result createDoubt(Integer userId, Integer orderId, String date) {
		try {
			OrderDoubt orderDoubt = new OrderDoubt();
			orderDoubt.setUserId(userId);
			orderDoubt.setOrderId(orderId);
			OrderDoubt orderDoubtInfo = orderDoubtService.findOne(orderDoubt);
			if(orderDoubtInfo != null){
				return ResultFactory.createError(ResultCode.REPEATED_SUBMISSION);
			}
			
			Order order = orderService.findByID(orderId);
			if (order.getStatus() != OrderEnum.Status.SERVING.getValue()){
				throw new ServiceException(ResultCode.NOT_ORDER_IN_SERVICE);
			}
			
			orderDoubtService.createDoubt(userId, orderId, date);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createSuccess();
	}
}

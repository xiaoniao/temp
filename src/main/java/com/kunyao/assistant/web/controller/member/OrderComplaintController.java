package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.OrderComplaint;
import com.kunyao.assistant.web.service.OrderComplaintService;

@Controller
@RequestMapping(value = "/mc/orderComplaint")
@ResponseBody
public class OrderComplaintController {
	
	@Resource
	private OrderComplaintService orderComplaintService;
	
	/**
	 * 订单投诉
	 */
	@RequestMapping(value = "/aheadOfTheEnd")
	public Result aheadOfTheEnd(Integer userId, Integer orderId, String remark) throws ServiceException {
		OrderComplaint orderComplaint = new OrderComplaint();
		orderComplaint.setUserId(userId);
		orderComplaint.setOrderId(orderId);
		OrderComplaint orderComplaintInfo = orderComplaintService.findOne(orderComplaint);
		if(orderComplaintInfo != null){
			return ResultFactory.createError(ResultCode.REPEATED_SUBMISSION);
		}
		return ResultFactory.createJsonSuccess(orderComplaintService.add(userId, orderId, remark));
	}
}

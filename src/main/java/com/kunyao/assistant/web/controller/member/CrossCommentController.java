package com.kunyao.assistant.web.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.CrossComment;
import com.kunyao.assistant.web.service.CrossCommentService;
import com.kunyao.assistant.web.service.InvoiceRecordService;

@Controller
@RequestMapping("/mc/crossComment")
public class CrossCommentController {
	
	@Resource
	private CrossCommentService crossCommentService;
	
	@Resource
	private InvoiceRecordService invoiceRecordService;
	
	/**
	 * 根据金鹰id获取评价列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findCrossCommentByCrossUserId/", produces = {"application/json;charset=UTF-8"})
	public Result findCrossCommentByCrossUserId(Integer userId, Integer crossUserId) {
		List<CrossComment> crossCommentList = crossCommentService.findCrossCommentByCrossUserId(userId, crossUserId);
		return ResultFactory.createJsonSuccess(crossCommentList);
	}
	
	/**
	 * 评价金鹰
	 */
	@ResponseBody
	@RequestMapping(value = "/commentCross", produces = {"application/json;charset=UTF-8"})
	public Result commentCross(@Valid CrossComment comment, Integer orderId, String companyContent) {
		try {
			crossCommentService.updateCommentCross(comment, orderId, companyContent);
			return ResultFactory.createSuccess();
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
	}

	/**
	 * 返回金鹰所有平均值接口数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findStarAvg/", produces = {"application/json;charset=UTF-8"})
	public Result findStarAvg(Integer crossUserId) {
		Map<String, Double> starMap = crossCommentService.findStarAvg(crossUserId);
		return ResultFactory.createJsonSuccess(starMap);
	}
	
	/**
	 * 评价金鹰页面展示账单扣款和余额 */
	@ResponseBody
	@RequestMapping(value = "/findCostInfo", produces = {"application/json;charset=UTF-8"})
	public Result findCostInfo(Integer orderId, Integer userId) {
		try {
			Map<String, Double> costInfo = crossCommentService.findCostInfo(orderId, userId);
			return ResultFactory.createJsonSuccess(costInfo);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
	}
}

package com.kunyao.assistant.web.controller.member;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.PageRequestDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.web.service.CrossInfoService;

@Controller
@RequestMapping(value = "/mc/cross")
@ResponseBody
public class CrossController {

	@Resource
	private CrossInfoService crossInfoService;
	
	/**
	 * 金鹰列表
	 */
	@RequestMapping(value = "/list")
	public Result crossList(Integer cityId, String startDate, String endDate, String search, PageRequestDto dto) {
		PageData<CrossInfo> pageData = crossInfoService.queryCrossList(startDate, endDate, cityId, search, dto.getCurrentPage(), dto.getPageSize());
		return ResultFactory.createJsonSuccess(pageData);
	}
	
	/**
	 * 金鹰详情
	 */
	@RequestMapping(value = "/info")
	public Result crossInfo(Integer crossUserId, String startDate, String endDate) {
		CrossInfo crossInfo = crossInfoService.queryCrossInfo(crossUserId, startDate, endDate);
		return ResultFactory.createJsonSuccess(crossInfo);
	}
	
	/**
	 * 金鹰排班表
	 */
	@RequestMapping(value = "/time")
	public Result timeList(Integer userId) {
		List<List<Map<String, String>>> list = crossInfoService.queryCrossTimeList(userId);
		return ResultFactory.createJsonSuccess(list);
	}
	
	/**
	 * 查询是否可以被预约
	 */
	@RequestMapping(value = "/canBook")
	public Result canBook(Integer userId, String startDate, String endDate) {
		try {
			return ResultFactory.createJsonSuccess(crossInfoService.canBook(userId, startDate, endDate));
		} catch (ParseException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.DATE_FORMAT_ERROR);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
}

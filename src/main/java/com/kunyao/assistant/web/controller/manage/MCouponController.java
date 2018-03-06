package com.kunyao.assistant.web.controller.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.PageRequestDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Coupon;
import com.kunyao.assistant.web.service.CouponService;

@Controller
@RequestMapping("/um/coupon")
public class MCouponController {

	// 优惠券管理
	private final String COUPON_LIST_URL = "um/coupon-list";

	@Resource
	private CouponService couponService;

	@RequestMapping(value = "/toCouponList")
	public String toCouponList() {
		return COUPON_LIST_URL;
	}

	@RequestMapping(value = "/listPageCount")
	@ResponseBody
	public Result couponListPageCount(Coupon coupon) {
		PageInfo page;
		try {
			page = new PageInfo(couponService.queryListCount(coupon), 12);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/listPage")
	@ResponseBody
	public Result couponListPage(Coupon coupon, PageRequestDto pageRequestDto) {
		try {
			return ResultFactory.createJsonSuccess(couponService.queryList(pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize(), coupon));
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
}

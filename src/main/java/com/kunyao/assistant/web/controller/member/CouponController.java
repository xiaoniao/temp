package com.kunyao.assistant.web.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.model.Coupon;
import com.kunyao.assistant.web.service.CouponService;

@Controller("memberCoupon")
@RequestMapping("/mc/coupon")
public class CouponController {

	@Resource
	private CouponService couponService;
	
	/**
	 * 用户端查询优惠券(包含已失效)
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
	public Result list(Integer userId) {
		Map<String, List<Coupon>> map = couponService.selectCouponsMap(userId);
		return ResultFactory.createJsonSuccess(map);
	}
	
	/**
	 * 优惠券使用上限
	 * @param stayDays
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/max_discount", produces = {"application/json;charset=UTF-8"})
	public Result maxDiscount(Integer stayDays) {
		Double maxDiscount = couponService.selectLimitInfo(stayDays);
		return ResultFactory.createJsonSuccess(maxDiscount);
	}
	
	/**
	 * 查询分享记录
	 */
	@ResponseBody
	@RequestMapping(value = "/share_list", produces = {"application/json;charset=UTF-8"})
	public Result shareList(Integer userId) {
		List<Coupon> coupons = couponService.selectListWithShared(userId);
		return ResultFactory.createJsonSuccess(coupons);
	}
}

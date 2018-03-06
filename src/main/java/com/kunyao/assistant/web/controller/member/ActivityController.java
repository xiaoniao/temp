package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.web.service.ActivityService;
import com.kunyao.assistant.web.service.CouponService;

@Controller("activityController")
@RequestMapping("/mc/activity")
public class ActivityController {

	@Resource
	private ActivityService activityService;

	@Resource
	private CouponService couponService;
	
	static final String activity_170318 = "activities/20170318/index.jsp";

	/**
	 * 参加活动
	 */
	@ResponseBody
	@RequestMapping(value = "/join")
	public Result join(Integer userId, Integer activityId, String token) throws ServiceException {
		return ResultFactory.createJsonSuccess(activityService.joinActivity(userId, activityId, token));
	}
	
	/**
	 * 2017/03/18 扫码注册送代金券活动
	 */
	@RequestMapping(value = "/activity_170318")
	public String act170318(String token) {
		return activity_170318 + "?token=" + token;
	}
}

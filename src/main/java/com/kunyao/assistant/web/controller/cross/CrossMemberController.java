package com.kunyao.assistant.web.controller.cross;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.web.service.MemberCommentService;
import com.kunyao.assistant.web.service.MemberInfoService;

@Controller
@RequestMapping(value = "/cc/member")
@ResponseBody
public class CrossMemberController {

	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private MemberCommentService memberCommentService;
	
	/**
	 * 会员信息
	 */
	@RequestMapping(value = "/info")
	public Result list(@RequestParam Integer userId) {
		return ResultFactory.createJsonSuccess(memberInfoService.findMemberInfo(userId));
	}
	
	/**
	 * 金鹰评价会员
	 */
	@RequestMapping(value = "/comment")
	public Result comment(@RequestParam Integer orderId, @RequestParam Integer crossUserId, @RequestParam Integer userId, 
			Double star1, Double star2, Double star3, Double star4, String content) throws ServiceException {
		return ResultFactory.createJsonSuccess(memberCommentService.add(orderId, crossUserId, userId, star1, star2, star3, star4, content));
	}
}

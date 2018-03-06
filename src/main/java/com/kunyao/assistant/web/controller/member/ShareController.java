package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Share;
import com.kunyao.assistant.web.service.ShareService;

@Controller("memberShare")
@RequestMapping("/mc/share")
public class ShareController {

	@Resource
	private ShareService shareService;
	
	@ResponseBody
	@RequestMapping(value = "/source")
	public Result sourceInfo(String code) {
		Share source = shareService.sourceInfo(code);
		return ResultFactory.createJsonSuccess(source);
	}
	
	@ResponseBody
	@RequestMapping(value = "/member_share")
	public Result memberShare(Integer userId) {
		try {
			String code = shareService.memberShare(userId);
			return ResultFactory.createJsonSuccess(code);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
	}
}

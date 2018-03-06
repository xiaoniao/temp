package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.web.service.MessagesService;

@Controller
@RequestMapping("/mc/message")
@ResponseBody
public class MessageController {

	@Resource
	private MessagesService messagesService;

	@RequestMapping(value = "/unreadCount")
	public Result unreadCount(Integer userId) {
		return ResultFactory.createJsonSuccess(messagesService.unreadCount(userId));
	}

	@RequestMapping(value = "/list")
	public Result list(Integer userId) {
		return ResultFactory.createJsonSuccess(messagesService.list(userId));
	}

	@RequestMapping(value = "/info")
	public Result info(Integer messageId) {
		return ResultFactory.createJsonSuccess(messagesService.info(messageId));
	}
}

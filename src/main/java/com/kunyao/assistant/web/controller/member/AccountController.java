package com.kunyao.assistant.web.controller.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.web.service.AccountRecordService;
import com.kunyao.assistant.web.service.AccountService;

@Controller
@RequestMapping("/mc/account")
@ResponseBody
public class AccountController {
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private AccountRecordService accountRecordService;
	
	/**
	 * 查询预存金
	 */
	@RequestMapping(value = "/balance")
	public Result balance(Integer userId) throws ServiceException{
		return ResultFactory.createJsonSuccess(accountService.balance(userId));
	}
	
	/**
	 * 资金明细
	 */
	@RequestMapping(value = "/record")
	public Result record(Integer userId) throws ServiceException{
		return ResultFactory.createJsonSuccess(accountRecordService.queryAccountRecord(userId));
	}
}

package com.kunyao.assistant.web.controller.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.PageRequestDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.model.AccountRecord;
import com.kunyao.assistant.web.service.AccountRecordService;

@Controller
@RequestMapping("/um/accountRecord")
public class MAccountRecordController {

	// 帐户明细
	private final String ACCOUNT_RECORD_LIST_URL = "um/account-record-list";

	// 历史帐户明细
	private final String ACCOUNT_RECORD_HISTORY_LIST_URL = "um/account-record-history-list";

	@Resource
	private AccountRecordService accountRecordService;

	// 帐户明细
	@RequestMapping(value = "/toAccountRecordList")
	public String toAccountRecordList() {
		return ACCOUNT_RECORD_LIST_URL;
	}

	// 历史帐户明细
	@RequestMapping(value = "/toAccountRecordHistoryList")
	public String toAccountRecordHistoryList() {
		return ACCOUNT_RECORD_HISTORY_LIST_URL;
	}

	@RequestMapping(value = "/listPageCount")
	@ResponseBody
	public Result listPageCount(String pageType, AccountRecord search) {
		PageInfo page = new PageInfo(accountRecordService.queryListCount(pageType, search), 12);
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/listPage")
	@ResponseBody
	public Result listPage(String pageType, PageRequestDto pageRequestDto, AccountRecord search) {
		return ResultFactory.createJsonSuccess(accountRecordService.queryList(pageType, pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize(), search));
	}
}

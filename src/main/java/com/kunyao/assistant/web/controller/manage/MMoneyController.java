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
import com.kunyao.assistant.core.model.Recharge;
import com.kunyao.assistant.core.model.TakeCash;
import com.kunyao.assistant.web.service.RechargeService;
import com.kunyao.assistant.web.service.TakeCashService;

@Controller
@RequestMapping("/um/money")
public class MMoneyController {

	// 充值记录
	private final String RECHARGE_LIST_URL = "um/recharge-list";

	// 提现记录
	private final String TAKE_CRSH_LIST_URL = "um/take-cash-list";

	@Resource
	private RechargeService rechargeService;

	@Resource
	private TakeCashService takeCashService;

	// 充值记录
	@RequestMapping(value = "/toRechargeList")
	public String toRechargeList() {
		return RECHARGE_LIST_URL;
	}

	@RequestMapping(value = "/recharge/listPageCount")
	@ResponseBody
	public Result rechargeListPageCount(Recharge recharge) {
		PageInfo page;
		try {
			page = new PageInfo(rechargeService.queryListCount(recharge), 12);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/recharge/listPage")
	@ResponseBody
	public Result rechargeListPage(Recharge recharge, PageRequestDto pageRequestDto) {
		try {
			return ResultFactory.createJsonSuccess(rechargeService.queryList(pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize(), recharge));
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	@RequestMapping(value = "/recharge/listAll")
	@ResponseBody
	public Result rechargeListAll() {
		return ResultFactory.createJsonSuccess(rechargeService.queryAll());
	}

	// 提现记录
	@RequestMapping(value = "/toTakeCashList")
	public String toTakeCashList() {
		return TAKE_CRSH_LIST_URL;
	}

	@RequestMapping(value = "/takeCash/listPageCount")
	@ResponseBody
	public Result takeCashListPageCount(TakeCash takeCash) {
		PageInfo page;
		try {
			page = new PageInfo(takeCashService.queryListCount(takeCash), 12);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/takeCash/listPage")
	@ResponseBody
	public Result takeCashListPage(TakeCash takeCash, PageRequestDto pageRequestDto) {
		try {
			return ResultFactory.createJsonSuccess(takeCashService.queryList(pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize(), takeCash));
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	@RequestMapping(value = "/takeCash/listAll")
	@ResponseBody
	public Result takeCashListAll() {
		return ResultFactory.createJsonSuccess(takeCashService.queryAll());
	}
}

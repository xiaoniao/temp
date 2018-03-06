package com.kunyao.assistant.web.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.CommonInvoice;
import com.kunyao.assistant.web.service.CommonInvoiceService;
import com.kunyao.assistant.web.service.InvoiceRecordService;

@Controller
@RequestMapping("/mc/invoice")
@ResponseBody
public class CommonInvoiceController {

	@Resource
	private CommonInvoiceService commonInvoiceService;
	
	@Resource
	private InvoiceRecordService invoiceRecordService;
	
	/** 查询发票 */
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
	public Result findInvoiceInfo(Integer userId) {
		CommonInvoice model = new CommonInvoice();
		model.setUserId(userId);
		model.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		try {
			List<CommonInvoice> invoices = commonInvoiceService.findList(null, null, model);
			return ResultFactory.createJsonSuccess(invoices);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 添加发票
	 */
	@RequestMapping(value = "/add")
	public Result add(CommonInvoice commonInvoice) throws ServiceException {
		return ResultFactory.createJsonSuccess(commonInvoiceService.add(commonInvoice));
	}
	
	/**
	 * 删除发票
	 */
	@RequestMapping(value = "/remove")
	public Result remove(CommonInvoice commonInvoice) throws ServiceException {
		return ResultFactory.createJsonSuccess(commonInvoiceService.remove(commonInvoice));
	}
	
	/**
	 * 编辑发票
	 */
	@RequestMapping(value = "/edit")
	public Result edit(CommonInvoice commonInvoice) throws ServiceException {
		return ResultFactory.createJsonSuccess(commonInvoiceService.edit(commonInvoice));
	}
	
	/**
	 * 查询开票记录
	 */
	@RequestMapping(value = "/recodeList")
	public Result recodeList(Integer userId) throws ServiceException {
		return ResultFactory.createJsonSuccess(invoiceRecordService.list(userId));
	}
	
	/**
	 * 查询开票详情
	 */
	@RequestMapping(value = "/recordInfo")
	public Result recordInfo(Integer id) throws ServiceException {
		return ResultFactory.createJsonSuccess(invoiceRecordService.info(id));
	}
}

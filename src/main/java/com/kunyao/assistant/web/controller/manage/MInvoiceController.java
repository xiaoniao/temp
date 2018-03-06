package com.kunyao.assistant.web.controller.manage;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.PageRequestDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.InvoiceEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.InvoiceRecord;
import com.kunyao.assistant.web.service.InvoiceRecordService;

@Controller
@RequestMapping("/um/invoice")
public class MInvoiceController {

	// 开票记录列表
	private final String INVOICE_RECORD_LIST_URL = "um/invoice-record-list";

	// 开票记录详情
	private final String INVOICE_RECORD_DETAIL_URL = "um/invoice-record-detail";

	@Resource
	private InvoiceRecordService invoiceRecordService;

	// 开票记录列表
	@RequestMapping(value = "/toInvoiceRecordList")
	public String toInvoiceRecordList() {
		return INVOICE_RECORD_LIST_URL;
	}

	// 开票记录详情
	@RequestMapping(value = "/toInvoiceRecordDetail")
	public String toInvoiceRecordDetail(Integer id, Model model) {
		InvoiceRecord invoiceRecord = invoiceRecordService.info(id);
		invoiceRecord.dto();
		model.addAttribute("invoiceRecord", invoiceRecord);
		return INVOICE_RECORD_DETAIL_URL;
	}

	@RequestMapping(value = "/listPageCount")
	@ResponseBody
	public Result listPageCount() {
		PageInfo page = new PageInfo(invoiceRecordService.listPageCount(), 12);
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/listPage")
	@ResponseBody
	public Result listPage(PageRequestDto pageRequestDto) {
		return ResultFactory.createJsonSuccess(invoiceRecordService.listPage(pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize()));
	}
	
	@RequestMapping(value = "/confrimSend")
	@ResponseBody
	public Result confrimSend(Integer id, Double money) {
		InvoiceRecord invoiceRecord = invoiceRecordService.info(id);
		invoiceRecord.setStatus(InvoiceEnum.Status.SENDED.getValue());
		invoiceRecord.setMoney(money);
		try {
			invoiceRecordService.update(invoiceRecord);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
		}
		return ResultFactory.createSuccess();
	}
}

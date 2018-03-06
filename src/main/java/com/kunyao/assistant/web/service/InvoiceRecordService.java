package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.InvoiceRecord;

public interface InvoiceRecordService extends GenericService<InvoiceRecord, Integer> {

	List<InvoiceRecord> list(Integer userId);

	List<InvoiceRecord> listPage(Integer currentPage, Integer pagesize);
	
	Integer listPageCount();

	InvoiceRecord info(Integer id);

	void addOrderInvoice(Integer orderId) throws ServiceException;

	void addRechargeInvoice(Integer rechargeId) throws ServiceException;
}

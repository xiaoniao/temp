package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.CommonInvoice;

import java.lang.Integer;

public interface CommonInvoiceService extends GenericService<CommonInvoice, Integer> {

	CommonInvoice add(CommonInvoice commonInvoice) throws ServiceException ;

	CommonInvoice remove(CommonInvoice commonInvoice) throws ServiceException ;

	CommonInvoice edit(CommonInvoice commonInvoice) throws ServiceException ;
}

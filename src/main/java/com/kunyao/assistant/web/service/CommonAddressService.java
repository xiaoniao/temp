package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.CommonAddress;

public interface CommonAddressService extends GenericService<CommonAddress, Integer> {

	CommonAddress add(CommonAddress commonInvoice) throws ServiceException;

	CommonAddress remove(CommonAddress commonInvoice) throws ServiceException;

	CommonAddress edit(CommonAddress commonInvoice) throws ServiceException;
}

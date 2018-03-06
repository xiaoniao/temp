package com.kunyao.assistant.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CommonInvoice;
import com.kunyao.assistant.web.dao.CommonInvoiceMapper;
import com.kunyao.assistant.web.service.CommonInvoiceService;

@Service
public class CommonInvoiceServiceImpl extends GenericServiceImpl<CommonInvoice, Integer> implements CommonInvoiceService {
    @Resource
    private CommonInvoiceMapper commonInvoiceMapper;

    public GenericDao<CommonInvoice, Integer> getDao() {
        return commonInvoiceMapper;
    }

	@Override
	public CommonInvoice add(CommonInvoice commonInvoice) throws ServiceException {
		filter(commonInvoice);
		commonInvoice.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		insert(commonInvoice);
		return commonInvoice;
	}

	@Override
	public CommonInvoice remove(CommonInvoice commonInvoice) throws ServiceException {
		filter(commonInvoice);
		commonInvoice.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		update(commonInvoice);
		return commonInvoice;
	}

	@Override
	public CommonInvoice edit(CommonInvoice commonInvoice) throws ServiceException {
		filter(commonInvoice);
		update(commonInvoice);
		return commonInvoice;
	}
	
	private void filter(CommonInvoice commonInvoice) {
		if (commonInvoice.getTitle() != null && commonInvoice.getTitle().equals("undefined")) {
			commonInvoice.setTitle(null);
		}
		if (commonInvoice.getCompanyName() != null && commonInvoice.getCompanyName().equals("undefined")) {
			commonInvoice.setCompanyName(null);
		}
		if (commonInvoice.getIdentification() != null && commonInvoice.getIdentification().equals("undefined")) {
			commonInvoice.setIdentification(null);
		}
		if (commonInvoice.getCode() != null && commonInvoice.getCode().equals("undefined")) {
			commonInvoice.setCode(null);
		}
		if (commonInvoice.getAddress() != null && commonInvoice.getAddress().equals("undefined")) {
			commonInvoice.setAddress(null);
		}
		if (commonInvoice.getMobile() != null && commonInvoice.getMobile().equals("undefined")) {
			commonInvoice.setMobile(null);
		}
		if (commonInvoice.getBankName() != null && commonInvoice.getBankName().equals("undefined")) {
			commonInvoice.setBankName(null);
		}
		if (commonInvoice.getBankCard() != null && commonInvoice.getBankCard().equals("undefined")) {
			commonInvoice.setBankCard(null);
		}
	}
}

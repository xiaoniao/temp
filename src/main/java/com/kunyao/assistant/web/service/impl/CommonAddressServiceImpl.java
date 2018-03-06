package com.kunyao.assistant.web.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CommonAddress;
import com.kunyao.assistant.web.dao.CommonAddressMapper;
import com.kunyao.assistant.web.service.CommonAddressService;

@Service
public class CommonAddressServiceImpl extends GenericServiceImpl<CommonAddress, Integer> implements CommonAddressService {
    @Resource
    private CommonAddressMapper commonAddressMapper;

    public GenericDao<CommonAddress, Integer> getDao() {
        return commonAddressMapper;
    }

	@Override
	public CommonAddress add(CommonAddress model) throws ServiceException {
		filter(model);
		model.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		insert(model);
		return model;
	}

	@Override
	public CommonAddress remove(CommonAddress model) throws ServiceException {
		filter(model);
		model.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		update(model);
		return model;
	}

	@Override
	public CommonAddress edit(CommonAddress model) throws ServiceException {
		filter(model);
		update(model);
		return model;
	}
	
	private void filter(CommonAddress commonAddress) {
		if (commonAddress.getAddress() != null && commonAddress.getAddress().equals("undefined")) {
			commonAddress.setAddress(null);
		}
		if (commonAddress.getMobile() != null && commonAddress.getMobile().equals("undefined")) {
			commonAddress.setMobile(null);
		}
		if (commonAddress.getName() != null && commonAddress.getName().equals("undefined")) {
			commonAddress.setName(null);
		}
		if (commonAddress.getZipCode() != null && commonAddress.getZipCode().equals("undefined")) {
			commonAddress.setZipCode(null);
		}
	}
}

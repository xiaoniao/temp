package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.juhe.JuheUtils;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Bank;
import com.kunyao.assistant.web.dao.BankMapper;
import com.kunyao.assistant.web.service.BankService;

@Service
public class BankServiceImpl extends GenericServiceImpl<Bank, Integer> implements BankService {
	@Resource
	private BankMapper bankMapper;

	public GenericDao<Bank, Integer> getDao() {
		return bankMapper;
	}

	@Override
	public List<Bank> list(Integer userId) throws ServiceException {
		return bankMapper.list(userId, BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
	}

	@Override
	public Bank add(Integer userId, String userName, String banCard) throws ServiceException {
		JSONObject jsonObject = JuheUtils.verifyBankCardType(banCard);
		if (jsonObject == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		if (jsonObject.optInt("error_code") != 0) {
			throw new ServiceException(ResultCode.BANK_WRONG);
		}
		JSONObject object = jsonObject.optJSONObject("result");
		Bank bank = new Bank(userId, userName, banCard, object.optString("bank"), object.optString("type"));
		insert(bank);
		return bank;
	}

	@Override
	public Bank remove(Integer userId, Integer id) throws ServiceException {
		Bank bank = bankMapper.info(id);
		bank.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		update(bank);
		return bank;
	}
}

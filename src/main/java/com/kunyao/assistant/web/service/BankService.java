package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Bank;

public interface BankService extends GenericService<Bank, Integer> {

	List<Bank> list(Integer userId) throws ServiceException;

	Bank add(Integer userId, String userName, String banCard) throws ServiceException;

	Bank remove(Integer userId, Integer id) throws ServiceException;
}

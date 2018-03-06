package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.TakeCash;

public interface TakeCashService extends GenericService<TakeCash, Integer> {
	
	Integer queryListCount(TakeCash takeCash) throws ServiceException;
	
	List<TakeCash> queryList(Integer currentPage, Integer pageSize, TakeCash takeCash) throws ServiceException;
	
	List<TakeCash> queryAll();
}

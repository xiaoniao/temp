package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.CompanyComment;

public interface CompanyCommentService extends GenericService<CompanyComment, Integer> {
	
	public Integer queryListCount(CompanyComment companyComment) throws ServiceException;
	
	public List<CompanyComment> queryList(Integer currentPage, Integer pageSize, CompanyComment companyComment) throws ServiceException;
}

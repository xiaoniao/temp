package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.MemberComment;

public interface MemberCommentService extends GenericService<MemberComment, Integer> {
	
	MemberComment add(Integer orderId, Integer crossUserId, Integer userId, Double star1, Double star2, Double star3, Double star4,
			String content) throws ServiceException;
	
	double calculateScore(Integer userId);
	
	
	
	public Integer queryListCount(MemberComment memberComment) throws ServiceException;
	
	public List<MemberComment> queryList(Integer currentPage, Integer pageSize, MemberComment memberComment) throws ServiceException;
}

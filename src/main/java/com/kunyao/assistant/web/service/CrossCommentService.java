package com.kunyao.assistant.web.service;

import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.CrossComment;

public interface CrossCommentService extends GenericService<CrossComment, Integer> {
	
	
	/**
     * 根据金鹰id获取评价列表
     * 
     * @param crossUserId
     * @return
     */
	public List<CrossComment> findCrossCommentByCrossUserId(Integer userId, Integer crossUserId);
	
	/**
	 * 评价金鹰
	 */
	public void updateCommentCross(CrossComment comment, Integer orderId, String companyContent) throws ServiceException;
	
	/**
     * 根据金鹰id返回所有评价平均值
     * 
     * @param crossUserId
     * @return
     */
	public Map<String, Double> findStarAvg(Integer crossUserId);
	
	
	public Integer queryListCount(CrossComment crossComment) throws ServiceException;
	
	public List<CrossComment> queryList(Integer currentPage, Integer pageSize, CrossComment crossComment) throws ServiceException;

	/**
	 * 评价金鹰页面展示账单扣款和余额
	 * @param orderId
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 */
	public Map<String, Double> findCostInfo(Integer orderId, Integer userId) throws ServiceException;
}

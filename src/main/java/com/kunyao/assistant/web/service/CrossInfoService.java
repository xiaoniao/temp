package com.kunyao.assistant.web.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.CrossInfo;

public interface CrossInfoService extends GenericService<CrossInfo, Integer> {
	
	CrossInfo login(String username, String password) throws ServiceException;

	PageData<CrossInfo> queryCrossList(String startDate, String endDate, Integer cityId, String search, Integer currentPage, Integer pagesize);

	CrossInfo queryCrossInfo(Integer crossUserId, String startDate, String endDate);
	
	CrossInfo querySimpleCrossInfo(Integer crossInfoId);
	
	List<List<Map<String, String>>> queryCrossTimeList(Integer userId);
	
	int createCrossInfo(CrossInfo crossinfo) throws ServiceException;
	
	List<CrossInfo> selectCrossInfoList(Integer currentPage, Integer pageSize, CrossInfo crossInfo);
	
	/**
	 * 批量删除多图图片
	 * @param hotel
	 * @return
	 */
	int deleteBanners(Integer crossId, String banners);
	
	/**
	 * 根据金鹰id获取数据
	 * @param crossId
	 * @return
	 */
	CrossInfo findCrossById(Integer crossId);
	
	CrossInfo findByOrderId(Integer orderId);
	
	int updateCrossInfo(CrossInfo crossInfo) throws ServiceException;
	
	boolean canBook(Integer crossUserId, String startDate, String endDate) throws ParseException, ServiceException ;
	
	/**
	 * 根据城市id获取到金鹰的有效用户
	 * @param cityId
	 * @return
	 */
	int findUserByCityIdCount(Integer cityId);
	
	/**
	 * 获取综合均分
	 * @param userId
	 * @return
	 */
	List<CrossInfo> findUserByUserId(Integer status, String stayDate, String endDate);

	/**
	 * 更新密码
	 */
	void updatePassword(Integer crossUserId, String oldPassword, String newPassword) throws ServiceException;
	
	/**
	 * 更新联系电话
	 */
	void updateContactPhone(Integer crossUserId, String phone) throws ServiceException;
	
	/**
	 * 上传个推id
	 */
	void uploadGetuiId(Integer crossUserId, String getuiId) throws ServiceException;
	
	/**
	 * 上传坐标
	 */
	void uploadLocation(Integer crossUserId, String lat, String lng) throws ServiceException;
}

package com.kunyao.assistant.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.OrderTravel;

public interface OrderTravelService extends GenericService<OrderTravel, Integer> {

	List<Map<String, Object>> queryTravelList(Integer orderId);
	
	List<Map<String, Object>> selectTravelListWithCost(Integer orderId, Integer costPayMethod);
	
	List<Map<String, Object>> queryTravelByOrderId(Integer orderId);
	
	void updateConfirmTravel(Integer orderId, Integer costPayMethod, Integer dayNum) throws ServiceException;
	
	OrderTravel insertOrderTravel(Integer orderId, Date startTime, Date endTime, String title) throws ServiceException;

	OrderTravel updateRemove(Integer travelId) throws ServiceException;

	OrderTravel updateEdit(Integer travelId, Date startTime, Date endTime, String title) throws ServiceException;
	
	OrderTravel queryInfo(Integer travelId) throws ServiceException;
	
	void updatePush(Integer orderId) throws ServiceException;
	
	boolean updateCrossFinish(Integer orderId, String date) throws ServiceException;
	
	boolean updateCrossStart(Integer orderId, String date) throws ServiceException;
	
	int updateMemberFinish(Integer orderId, String date) throws ServiceException;
	
	void updateAutoFinish();

	Integer updateFinishTodayTravel(Integer orderId, String date) throws ServiceException;
	
	List<OrderTravel> queryConfirmedTravelListToday();
	
	void updateIsCrossOffWork() throws ServiceException;

	void updateAutoFinish(String date) throws ServiceException;
	
	List<Map<String, Object>> queryOrderTravelList(Integer orderId);

}
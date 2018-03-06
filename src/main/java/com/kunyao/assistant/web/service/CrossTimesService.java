package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.CrossTimes;

public interface CrossTimesService extends GenericService<CrossTimes, Integer> {

	List<CrossTimes> findCrossTimeByUserId(Integer userId, String stayDate, String endDate);
	
	/**
	 * 开启请假状态
	 * @return
	 */
	boolean openCrossTimesDate(Integer crossTimesId);
	
	/**
	 * 解除请假状态
	 */
	boolean closeCrossTimesDate(Integer crossTimesId);
	
	List<CrossTimes> selectCrossList(Integer crossTimesId);
	
	/**
	 * 更换金鹰查询有效的金鹰
	 * @param status
	 * @param stayDate
	 * @param endDate
	 * @return
	 */
	List<CrossTimes> findCrossTimeByOrder(Integer status, String stayDate, String endDate);
	

	/**
	 * 更新金鹰服务时间状态
	 * @param userId
	 * @param status
	 * @param time
	 * @return
	 */
	boolean updateCrossTimeStatus(Integer userId, Integer status, String time);
	
	void generateTime(String startDate);
}

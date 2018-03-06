package com.kunyao.assistant.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.enums.CrossEnum;
import com.kunyao.assistant.core.enums.TimeEnum;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.CrossTimes;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.web.dao.CrossInfoMapper;
import com.kunyao.assistant.web.dao.CrossTimesMapper;
import com.kunyao.assistant.web.service.CrossTimesService;

@Service
public class CrossTimesServiceImpl extends GenericServiceImpl<CrossTimes, Integer> implements CrossTimesService {
    @Resource
    private CrossTimesMapper crossTimesMapper;
    
    @Resource
    private CrossInfoMapper crossInfoMapper;
    
    public GenericDao<CrossTimes, Integer> getDao() {
        return crossTimesMapper;
    }
    
    @Override
	public List<CrossTimes> findCrossTimeByUserId(Integer userId, String stayDate, String endDate) {
		return crossTimesMapper.findCrossTimeByUserId(userId, stayDate, endDate);
	}

	@Override
	public boolean openCrossTimesDate(Integer crossTimesId) {
		CrossTimes crossTimes = new CrossTimes();
		crossTimes.setId(crossTimesId);
		crossTimes.setStatus(CrossEnum.BookStatus.IDLE.getValue());
		
		try {
			crossTimesMapper.updateByID(crossTimes);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


	@Override
	public boolean closeCrossTimesDate(Integer crossTimesId) {
		CrossTimes crossTimes = new CrossTimes();
		crossTimes.setId(crossTimesId);
		crossTimes.setStatus(CrossEnum.BookStatus.CLOSE.getValue());
		
		try {
			crossTimesMapper.updateByID(crossTimes);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


	@Override
	public List<CrossTimes> selectCrossList(Integer crossTimesId) {
		CrossInfo cross = crossInfoMapper.findCrossById(crossTimesId);
		
		Date today = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, 60);
		Date torrowDate = calendar.getTime();
		String stayDate = DateUtils.parseYMDTime(today);
		String endDate = DateUtils.parseYMDTime(torrowDate);
		String nextMonth = "";
		List<CrossTimes> crossTimesList = crossTimesMapper.findCrossTimeByUserId(cross.getUserId(), stayDate, endDate);
		boolean flge = false;
		List<CrossTimes> newCrossTimesList = new ArrayList<CrossTimes>();
		
		/**
		 * 先获取第一天周几，循环再第一天前加入空，入周日不加 周一加一个 周二加两个.........（一次循环可搞定）
		 */
		Integer week = DateUtils.getWeekOfDate(crossTimesList.get(0).getTime());
		if (week != 6) {
			for (int i = 0; i < week; i++) {
				CrossTimes CrossTimes = new CrossTimes();
				CrossTimes.setSpecialCharacter("kk");                  // kk表示换行
				newCrossTimesList.add(CrossTimes);
			}
		}

		/**
		 * 循环30天，判断每一天是否是当月月底，如果是判断当天是周几，根据周几在后追加 空/换行/空
		 */
		for (int i = 0; i < crossTimesList.size(); i++) {
			// 先加入
			CrossTimes CrossTimes = new CrossTimes();
			CrossTimes.setId(crossTimesList.get(i).getId());
			CrossTimes.setStatus(crossTimesList.get(i).getStatus());
			CrossTimes.setUserId(crossTimesList.get(i).getUserId());
			CrossTimes.setTimeDate(crossTimesList.get(i).getTime());
			newCrossTimesList.add(CrossTimes);
			Integer weeks = DateUtils.getWeekOfDate(crossTimesList.get(i).getTime());
			// 判断是否是月末, i判断如果从1号开始到最后一天正好是周6，则不走这判断
			if (DateUtils.getMonthLastDay().equals(DateUtils.parseYMDTime(crossTimesList.get(i).getTime())) && flge == false) {
				if (weeks != 6) {
					for (int j = 0; j < 7; j++) {
						CrossTimes crossTime = new CrossTimes();
						crossTime.setSpecialCharacter("kk");
						newCrossTimesList.add(crossTime);
					}
				} 
				
				flge = true;
				nextMonth = DateUtils.getNextMonthLastDay();
			}
			
			if (nextMonth.equals(DateUtils.parseYMDTime(crossTimesList.get(i).getTime())) && flge == true) {
				if (weeks != 6) {
					for (int j = 0; j < 7; j++) {
						CrossTimes crossTime = new CrossTimes();
						crossTime.setSpecialCharacter("kk");
						newCrossTimesList.add(crossTime);
					}
				} 
			}
			
			if (i == 59) { // 判断最后一天是周几
				if (weeks != 6 && weeks != 7) {
					for (int j = 6; j > weeks; j--) {
						CrossTimes crossTime = new CrossTimes();
						crossTime.setSpecialCharacter("kk");
						newCrossTimesList.add(crossTime);   
					}
				}
				if (weeks == 7){
					for (int j = 1; j < weeks; j++) {
						CrossTimes crossTime = new CrossTimes();
						crossTime.setSpecialCharacter("kk");
						newCrossTimesList.add(crossTime);   
					}
				}
			}
		}
		List<CrossTimes> crossTimesLists = new ArrayList<CrossTimes>();
		
		for(int i = 0; i < newCrossTimesList.size(); i++){
			crossTimesLists.add(newCrossTimesList.get(i));
			
			if(i == 6 || i == 13 || i == 20 || i == 27 || i == 34 || i == 41 || i == 48 || i == 55 || i == 62 || i == 69 || i == 76 || i == 83 ){
				CrossTimes crossTimes = new CrossTimes();
				crossTimes.setSpecialCharacter("ww");
				crossTimesLists.add(crossTimes);
			}
		}
		return crossTimesLists;
	}

	@Override
	public List<CrossTimes> findCrossTimeByOrder(Integer status, String stayDate, String endDate) {
		return crossTimesMapper.findCrossTimeByOrder(status, stayDate, endDate);
	}

	@Override
	public boolean updateCrossTimeStatus(Integer userId, Integer status, String time) {
		return crossTimesMapper.updateCrossTime(status, userId, time);
	}
	
	@Override
	public void generateTime(String startDate) {
		List<CrossInfo> crossInfos = crossInfoMapper.findAll();
		Date startTime = DateUtils.parseYMDDate(startDate);
		for (CrossInfo crossInfo : crossInfos) {
			Date date = startTime;
			for (int i = 0; i < 366; i++) {
				CrossTimes c = new CrossTimes();
				c.setUserId(crossInfo.getUserId());
				c.setTime(date);
				if (crossTimesMapper.findCountByCondition(c) == 0) {
					CrossTimes crossTimes = new CrossTimes();
					crossTimes.setUserId(crossInfo.getUserId());
					crossTimes.setStatus(TimeEnum.CAN_BOOK.getValue());
					crossTimes.setTime(date);
					crossTimesMapper.insert(crossTimes);
				}
				date = new Date(date.getTime() + DateUtils.DAY);
			}
		}
	}
}

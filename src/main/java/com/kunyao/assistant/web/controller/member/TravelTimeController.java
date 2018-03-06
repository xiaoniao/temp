package com.kunyao.assistant.web.controller.member;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.utils.DateUtils;

/***
 * 出行时间接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/mc/travelTime")
public class TravelTimeController {
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getTraveTime", produces = { "application/json;charset=UTF-8" })
	public Result getTraveTime() {

		Date today = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, 30);
		Date torrowDate = calendar.getTime();
		List<String> travelTimeList = new ArrayList<String>();
		List<Date> listDate = DateUtils.getDatesBetweenTwoDate(today, torrowDate);

		/**
		 * 先获取第一天周几，循环再第一天前加入空，入周日不加 周一加一个 周二加两个.........（一次循环可搞定）
		 */
		Integer week = DateUtils.getWeekOfDate(listDate.get(0));
		if (week != 7) {
			for (int i = 0; i < week; i++) {
				travelTimeList.add("");
			}
		}

		/**
		 * 循环30天，判断每一天是否是当月月底，如果是判断当天是周几，根据周几在后追加 空/换行/空
		 */
		for (int i = 0; i < listDate.size(); i++) {
			// 先加入
			if (i == 0 && listDate.get(i).getTime() >= DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date()) + " 19:00:00").getTime()) {
				// 如果第一天时间超过19:00, 当天不可选
				travelTimeList.add("");
			} else {
				travelTimeList.add(DateUtils.parseYMDTime(listDate.get(i)));
			}
			// 判断是否是月末, i判断如果从1号开始到最后一天正好是周6，则不走这判断
			if (DateUtils.getMonthLastDay().equals(DateUtils.parseYMDTime(listDate.get(i))) && i != 30) {

				Integer weeks = DateUtils.getWeekOfDate(listDate.get(i));
				if (weeks != 6) {
					for (int j = 0; j < 8; j++) {

						if (weeks == 1 && j == 5) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 2 && j == 4) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 3 && j == 3) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 4 && j == 2) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 5 && j == 1) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 7 && j == 6) {
							travelTimeList.add("hh");
							continue;
						}
						travelTimeList.add("");
					}
				} else {
					travelTimeList.add("hh");
				}
			}

			if (i == 30) { // 判断最后一天是周几
				Integer weeks = DateUtils.getWeekOfDate(listDate.get(i));
				if (weeks != 6) {
					for (int j = 0; j < 8; j++) {
						if (weeks == 1 && j == 5) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 2 && j == 4) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 3 && j == 3) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 4 && j == 2) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 5 && j == 1) {
							travelTimeList.add("hh");
							continue;
						} else if (weeks == 7 && j == 6) {
							travelTimeList.add("hh");
							continue;
						}
						travelTimeList.add("");
					}
				} else {
					travelTimeList.add("hh");
				}
			}
		}

		// List<TravelTime> travelTimeList = new ArrayList<TravelTime>();

		/*
		 * for (int i = 0; i < listDate.size(); i++) { Calendar ymd = new
		 * GregorianCalendar(); ymd.setTime(listDate.get(i)); TravelTime
		 * travelTime = new TravelTime();
		 * travelTime.setYear(ymd.get(Calendar.YEAR));
		 * travelTime.setMonth(ymd.get(Calendar.MONTH) + 1);
		 * travelTime.setDay(ymd.get(Calendar.DAY_OF_MONTH));
		 * 
		 * travelTimeList.add(travelTime); }
		 */

		// List<String> travelTimeList = new ArrayList<String>();
		// for (int i = 0; i < listDate.size(); i++) {
		// travelTimeList.add(DateUtils.parseYMDTime(listDate.get(i)));
		// }
		return ResultFactory.createJsonSuccess(travelTimeList);
	}

}

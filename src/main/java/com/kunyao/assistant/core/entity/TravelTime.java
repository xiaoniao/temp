package com.kunyao.assistant.core.entity;

import java.io.Serializable;


/**
 * 封装年月日实体类
 * @author Administrator
 *
 */

public class TravelTime  implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3487436191137616588L;

	private Integer year;        // 年
    
    private Integer month;	     // 月
    
    private Integer day;		 // 日

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

}

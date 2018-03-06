package com.kunyao.assistant.web.service;

import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.Province;

public interface CityService extends GenericService<City, Integer> {
	
	/**
	 * 根据状态查有效的城市列表(按字母分组排序)
	 * 
	 * @param status
	 * @return
	 */
	List<Map<String, Object>> selectCity(Integer status);
	
	/**
	 * 根据会员下过单获取城市列表
	 * 
	 * @param status
	 * @return
	 */
	List<City> findCityByMemberId(Integer memberId);
	
	/**
	 * 根据坐标获取当前城市
	 * @param latLngs
	 * @return
	 */
	City getLocationCity(String latLngs);
	
	/**
	 * 查询省列表并读取所有城市
	 * @return
	 */
	 List<Province> selectAllInfo(Province province, Integer currentPage, Integer pagesize);
	 
	 /**
	  * 开通城市
	  * @return
	  */
	boolean openCityStatus(Integer cityId);
		
	/**
	 * 关闭城市
	 */
	boolean closeCityStatus(Integer cityId);
	
	City findCityById(Integer cityId, Integer status);
	
	List<City> findListByProvinceId(Integer provinceId);
	
	City findInfo(Integer cityId) throws ServiceException;

	Map<String, Object> selectAddressInfo(Integer cityId);

	/** 查询已开通城市 */
	List<City> findCityList(Integer status);
}

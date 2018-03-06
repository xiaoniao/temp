package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.City;

public interface CityMapper extends GenericDao<City, Integer> {
	
	final String FIND_CITY_BY_STATUS = 
			"SELECT id, name FROM `kycom_t_city` " + 
            "WHERE status = #{status} " +
            "ORDER BY CONVERT(name USING gbk) ASC";
	
	final String FIND_CITY_BY_ID = 
			"SELECT id, name FROM `kycom_t_city` " + 
            "WHERE id = #{cityId} AND status = #{status}";
	
	final String FIND_CITY = 
			"SELECT id, name FROM `kycom_t_city` " + 
            "WHERE id = #{cityId}";
	
	final String FIND_CITY_BY_PROVINCEID = 
			"SELECT id, name, status, province_id FROM `kycom_t_city` " + 
            "WHERE province_id = #{provinceId}           ";
	
	/**
     * 查询已开通的城市列表
     * (按拼音首字母排序(勿改))
     * @param city
     * @return
     */
	@Select(FIND_CITY_BY_STATUS)
    List<City> selectCity(@Param("status") Integer status);
	
	/**
     * 根据id查询城市名和id
     * 
     * @param cityId
     * @return
     */
	@Select(FIND_CITY_BY_ID)
    City findCityById(@Param("cityId") Integer cityId, @Param("status") Integer status);
	
	@Select(FIND_CITY)
    City findCity(@Param("cityId") Integer cityId);
	
	/**
	 * 根据省id查询城市列表
	 * @param provinceId
	 * @return
	 */
	@Select(FIND_CITY_BY_PROVINCEID)
	List<City> findCityByProvinceId(@Param("provinceId") Integer provinceId);
}

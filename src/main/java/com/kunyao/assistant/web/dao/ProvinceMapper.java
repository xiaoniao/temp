package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Province;

public interface ProvinceMapper extends GenericDao<Province, Integer> {
	
	final String FIND_PROVINCE_BY_CITY_ID = " SELECT p.* FROM kycom_t_province AS p LEFT JOIN kycom_t_city AS c ON c.province_id = p.id WHERE c.id = #{cityId} ";
	
	@Select(" select * from kycom_t_province where id = #{id} and status = #{status}")
    Province findProvinceByIdAndStatus(@Param("id") Integer id, @Param("status") Integer status);

	@Select("SELECT * FROM kycom_t_province")
	List<Province> findAll();

	@Select(FIND_PROVINCE_BY_CITY_ID)
	Province findProvinceByCityId(@Param("cityId") Integer cityId);
	
}

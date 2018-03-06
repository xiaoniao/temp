package com.kunyao.assistant.web.dao;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.ManagerInfo;

import java.lang.Integer;

import org.apache.ibatis.annotations.Select;

public interface ManagerInfoMapper extends GenericDao<ManagerInfo, Integer> {

	@Select("SELECT * FROM kycom_u_manager_info WHERE user_id = #{userId}")
	ManagerInfo findByUserId(Integer userId);
}

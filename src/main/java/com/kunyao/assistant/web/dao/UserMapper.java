package com.kunyao.assistant.web.dao;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.User;

import java.lang.Integer;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends GenericDao<User, Integer> {
	
	@Select("select * from kycom_u_user where username = #{userName}")
	User selectByUsername(@Param("userName") String userName);
}

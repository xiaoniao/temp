package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.UserRole;

public interface UserRoleMapper extends GenericDao<UserRole, Integer> {
	
	@Select(
			" SELECT " +
			" 	* " +
			" FROM " +
			"	kycom_u_user_role ur " +
			" WHERE " +
			"	ur.role_id IN( " +
			"		SELECT " +
			"			id " +
			"		FROM " +
			"			kycom_u_role r " +
			"		WHERE " +
			"			r.sign IN( " +
			"				'admin', " +
			"				'res_manage_city', " +
			"				'res_manage_all', " +
			"				'res_dev_practice', " +
			"				'res_dev_full', " +
			"				'normal_manage' " +
			"			) " +
			"	) " +
			" ORDER BY " +
			"	ur.user_id")
	List<UserRole> findSubManage();
}

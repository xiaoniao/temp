package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Role;

public interface RoleMapper extends GenericDao<Role, Integer> {
	
	final String FIND_ROLES_BY_USERID = 
				" SELECT                                                 " +
				" 	*                                                    " +
				" FROM                                                   " +
				" 	kycom_u_role AS r                                    " +
				" LEFT JOIN kycom_u_user_role AS ur ON r.id = ur.role_id " +
				" WHERE                                                  " +
				" 	ur.user_id = #{userId}                               ";
	
	/**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
	@Select(FIND_ROLES_BY_USERID)
	List<Role> findRolesByUserId(@Param("userId") Integer userId);
}

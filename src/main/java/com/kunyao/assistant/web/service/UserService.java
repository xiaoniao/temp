package com.kunyao.assistant.web.service;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.User;

public interface UserService extends GenericService<User, Integer> {
	
	/**
	 * 根据用户用查询用户信息
	 * @param userName
	 * @return
	 */
	User selectByUsername(String userName);
	
	/**
	 * 更新密码
	 */
	void updatePassword(Integer userId, String oldPwd, String newPwd) throws ServiceException;

	/**
	 * 禁用帐户
	 */
	void disable(Integer userId) throws ServiceException;
	
	/**
	 * 启用 帐户
	 */
	void enable(Integer userId) throws ServiceException;
	
	/**
	 * 添加管理员
	 */
	User addManager(String type, String username, Integer provinceId, Integer cityId, String resourceTypeIds, String encryptionPassword) throws ServiceException;
}

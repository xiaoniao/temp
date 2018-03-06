package com.kunyao.assistant.web.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.ManagerInfo;
import com.kunyao.assistant.core.model.Role;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.model.UserRole;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.UserMapper;
import com.kunyao.assistant.web.dao.UserRoleMapper;
import com.kunyao.assistant.web.service.ManagerService;
import com.kunyao.assistant.web.service.RoleService;
import com.kunyao.assistant.web.service.UserService;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements UserService {
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private ManagerService managerService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleMapper userRoleMapper;

	public GenericDao<User, Integer> getDao() {
		return userMapper;
	}
	
	@Override
	public User addManager(String type, String username, Integer provinceId, Integer cityId, String resourceTypeIds, String encryptionPassword) throws ServiceException {
		if (selectByUsername(username) != null) {
			throw new ServiceException(ResultCode.USERNAME_EXIST);
		}
		
		String password = StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + encryptionPassword);
		User user = new User(username, username, password, new Date(), type);
		userMapper.insert(user);
		
		ManagerInfo managerInfo = new ManagerInfo();
		managerInfo.setName(username);
		managerInfo.setUserId(user.getId());
		if (type.equals(Constant.ROLE_SIGN_MANAGE_NORMAL)) {
			
		} else if (type.equals(Constant.ROLE_SIGN_RES_DEV_PRACTICE)) {
			managerInfo.setProvinceId(provinceId);
			managerInfo.setCityId(cityId);
			managerInfo.setResourceTypeIds(resourceTypeIds);
		} else if (type.equals(Constant.ROLE_SIGN_RES_DEV_FULL)) {
			
		} else if (type.equals(Constant.ROLE_SIGN_RES_MANAGE_CITY)) {
			managerInfo.setProvinceId(provinceId);
			managerInfo.setCityId(cityId);
		} else if (type.equals(Constant.ROLE_SIGN_RES_MANAGE_ALL)) {
			
		}
		managerService.insert(managerInfo);
		user.setUserinfoId(managerInfo.getId());
		userMapper.updateByID(user);
		
		Role rModel = new Role();
		rModel.setSign(type);
		Role role = roleService.findOne(rModel);
		UserRole userRole = new UserRole();
		userRole.setRoleId(role.getId());
		userRole.setUserId(user.getId());
		userRoleMapper.insert(userRole);     
		
		return user;
	}

	@Override
	public User selectByUsername(String userName) {
		return userMapper.selectByUsername(userName);
	}
	
	@Override
	public void updatePassword(Integer userId, String oldPwd, String newPwd) throws ServiceException {
		User user = findByID(userId);
		if (user == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		
		if (!user.getPassword().equals(StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + oldPwd))) {
			throw new ServiceException(ResultCode.PASSWORD_WRONG);
		}
		user.setPassword(StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + newPwd));
		update(user);
	}
	
	@Override
	public void disable(Integer userId) throws ServiceException {
		User user = findByID(userId);
		if (user == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		user.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		update(user);
	}
	
	@Override
	public void enable(Integer userId) throws ServiceException {
		User user = findByID(userId);
		if (user == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		user.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		update(user);
	}
}

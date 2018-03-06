package com.kunyao.assistant.web.security;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Permission;
import com.kunyao.assistant.core.model.Role;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.web.service.PermissionService;
import com.kunyao.assistant.web.service.RoleService;
import com.kunyao.assistant.web.service.UserService;

/**
 * 用户身份验证,授权 Realm 组件
 **/
@Component(value = "securityRealm")
public class SecurityRealm extends AuthorizingRealm {
	private static Logger logger = Logger.getLogger(SecurityRealm.class);

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService;

	@Resource
	private PermissionService permissionService;

	/**
	 * 权限检查
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		final String username = String.valueOf(principals.getPrimaryPrincipal());
		User userModel = new User();
		userModel.setUsername(username);

		User user = null;
		try {
			user = userService.findOne(userModel);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			return authorizationInfo;
		}

		final List<Role> roleInfos = roleService.findRolesByUserId(user.getId());
		for (Role role : roleInfos) {

			// 添加角色
			authorizationInfo.addRole(role.getSign());

			final List<Permission> permissions = permissionService.findPermissionsByRoleId(role.getId());
			for (Permission permission : permissions) {

				// 添加权限
				authorizationInfo.addStringPermission(permission.getSign());
			}
		}
		return authorizationInfo;
	}

	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = String.valueOf(token.getPrincipal());
		String password = new String((char[]) token.getCredentials());

		try {

			User userModel = new User(username, password);

			// 通过数据库进行验证
			final User authentication = userService.findOne(userModel);

			if (authentication == null) {
				throw new AuthenticationException("用户名或密码错误.");
			}
			if (authentication.getStatus().intValue() == BaseEnum.Status.BASE_STATUS_DISABLED.getValue()) {
				throw new AuthenticationException("帐号被禁用.");
			}
			
			authentication.setLastLoginTime(new Date());
			userService.update(authentication);

			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());

			// 移除缓存，为解决 管理员修改某角色权限后需重启或等session超时才生效（临时解决方案）
			super.clearCachedAuthorizationInfo(authenticationInfo.getPrincipals());

			return authenticationInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getSerialVersionUID() {
		return "";
	}

}

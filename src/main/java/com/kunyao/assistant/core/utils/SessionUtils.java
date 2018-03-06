package com.kunyao.assistant.core.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SessionUtils {

	public static Object getAttribute(String key) {
		Subject currentUser = SecurityUtils.getSubject();
		Object obj = currentUser.getSession().getAttribute(key);
		return obj;
	}

	public static void setAttribute(String key, Object obj) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.getSession().setAttribute(key, obj);
	}
}

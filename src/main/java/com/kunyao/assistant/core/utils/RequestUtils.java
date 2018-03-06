package com.kunyao.assistant.core.utils;

import java.util.Enumeration;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	
	/**
     * 获取访问IP
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
    	String ip = request.getHeader("X-Forwarded-For");
    	if (!StringUtils.isNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
    		//多次反向代理后会有多个ip值，第一个ip才是真实ip
    		int index = ip.indexOf(",");
    		if(index != -1)
    			return ip.substring(0,index);
    		else
    			return ip;
    	}
    	
    	ip = request.getHeader("X-Real-IP");
    	if (!StringUtils.isNull(ip) && !"unKnown".equalsIgnoreCase(ip))
    		return ip;
    	
    	return request.getRemoteAddr();
    }
    

    /**
     * 获取所有请求参数
     * @param request
     * @return
     */
    public static SortedMap<String, String> getRequestAllParams(HttpServletRequest request) {
    	
    	SortedMap<String, String> map = null;
    	
    	@SuppressWarnings("rawtypes")
		Enumeration paramNames = request.getParameterNames();
    	
    	if (paramNames == null) 
    		return null;
    	else
    		map =  new TreeMap<String, String>();
    	
    	while (paramNames.hasMoreElements()) {
    		
    		String paramName = (String) paramNames.nextElement();
    		String paramValue = request.getParameter(paramName);
    		
    		if (!StringUtils.isNull(paramValue))
    			map.put(paramName, paramValue);
    	}
    	
    	return map;
    }
}

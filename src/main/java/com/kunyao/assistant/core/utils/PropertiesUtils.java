package com.kunyao.assistant.core.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Properties 工具类
 *
 * @author GeNing
 * @since  2016.07.28
 *
 */
public class PropertiesUtils {
	
	/**
	 * 加载 porperties文件
	 * @return
	 */
	public static Properties loadPropertyInstance(String messagePropertiesFilePath) {
		
		Properties props = null;
		InputStream is = null;
		
		try {
			
			is = PropertiesUtils.class.getResourceAsStream(messagePropertiesFilePath);
			props = new Properties();
	        props.load(new InputStreamReader(is, "UTF-8"));
	        is.close();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} 
		
        return props;
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

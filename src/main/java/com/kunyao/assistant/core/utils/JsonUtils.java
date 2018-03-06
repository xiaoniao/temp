package com.kunyao.assistant.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/** 
 * Java对象和JSON字符串相互转化工具类 
 * 
 * @author GeNing
 * @since  2016-04-25
 *  
 */  
public class JsonUtils {
	
	private JsonUtils() { }  
    
    /** 
     * 对象转换成json字符串 
     * @param obj  
     * @return  
     */  
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);  
    }
  
    /** 
     * json字符串转成对象 
     * @param str   
     * @param type 
     * @return  
     */  
    public static <T> T fromJson(String str, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }
  
    /** 
     * json字符串转成对象 
     * @param str   
     * @param type  
     * @return  
     */  
    public static <T> T fromJson(String str, Class<T> type) {  
        Gson gson = new Gson();  
        return gson.fromJson(str, type);  
    }  
    
    /**
     * json字符串转为 模型数组
     * @param str
     * @return
     */
    public static <T> List<T> fromJson(String str) {
    	
    	List<T> resultList = null;
    	
    	if (!StringUtils.isNull(str)) {
    		
    		// 验证字符串是否是Json格式的字符串
    		try {
    			new JsonParser().parse(str);
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
    		
    		Gson gson = new Gson();  
    		
    		// 将JSON字符串转为 指定类型的数组
    		try {
    			resultList = gson.fromJson(str, new TypeToken<List<T>>() {}.getType());
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
    	}
    	
    	return resultList;
    } 
}

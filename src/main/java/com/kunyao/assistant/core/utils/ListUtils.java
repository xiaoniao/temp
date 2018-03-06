package com.kunyao.assistant.core.utils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListUtils<T> {
	
	/** 
     * @param targetList 目标排序List 
     * @param sortField 排序字段(实体类属性名) 
     * @param sortMode 排序方式（asc or  desc） 
     */  
	public void sort(List<T> targetList, final String sortField, final String sortMode) {

		Collections.sort(targetList, new Comparator<T>() {
			
			public int compare(T t1, T t2) {
				int retVal = 0;
				try {
					// 首字母转大写
					String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;

					Method method1 = t1.getClass().getMethod(methodStr);
					Method method2 = t2.getClass().getMethod(methodStr);
					
					if (sortMode != null && "desc".equals(sortMode)) {
						retVal = method2.invoke(t2).toString().compareTo(method1.invoke(t1).toString()); // 倒序
					} else {
						retVal = method1.invoke(t1).toString().compareTo(method2.invoke(t2).toString()); // 正序
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				return retVal;
			}
		});
	}
}

package com.kunyao.assistant.core.feature.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.utils.StringUtils;

public class SQLHelper {
	
	// 数据库表前缀
	public static final String DATABASE_TABLE_PREFIX = "kycom";  
	
	// 数据库表名包前缀
	public static final String GET_TABLE_PREFIX_NAME_BY_MODEL = "getTablePrefixName";

	/**
	 * 获取 模型对象对应的数据库表名
	 * @param model
	 * @return
	 */
	public static String getTableName(final Object model) {
		
		StringBuffer tableName = null;      // 数据库完整表名
		String tablePackageFixName = null;  // 数据库表名 '包' 前缀
		
		// 获取当前 Model 对应的数据表名 '包' 前缀
		try {
			
			Method method = model.getClass().getMethod(GET_TABLE_PREFIX_NAME_BY_MODEL);
			tablePackageFixName = String.valueOf(method.invoke(model));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 获取当前 Model 类名，如：User
		String modelClassName = model.getClass().getSimpleName();
		
        if (!StringUtils.isNull(modelClassName)) {
        	
        	// 先为表名追加 数据库前缀 和 包前缀
        	tableName = new StringBuffer();
        	tableName.append(DATABASE_TABLE_PREFIX).append("_").append(tablePackageFixName);
        	
        	// 循环模型类型，当遇到大写字母时就追加 下划线及转换小写，当遇到小写字母时则直接追加
        	for (int i = 0; i < modelClassName.length(); i++) {
    			
        		char[] chars = new char[1];  
            	chars[0] = modelClassName.charAt(i);
            	String temp = new String(chars);  
            	
            	if (chars[0]>='A'  &&  chars[0]<='Z')
            		tableName.append("_" + temp.toLowerCase());
            	else
            		tableName.append(temp);
    		}
        }
        
        return String.valueOf(tableName);
	}
	
	/**
	 * 创建 新增或修改的 内容条件
	 * @param model
	 * @return
	 */
	public static List<SQLContent> createInsertOrUpdateSQLContent(final Object model) {
		
		if (model == null) 
			return null;
		
		List<SQLContent> contentList = null;
		
		// 通过泛型获取 Model所有字段集合
		Field[] fields = model.getClass().getDeclaredFields();
		
		if (fields != null && fields.length > 0) {
			
			contentList = new ArrayList<SQLContent>();
			
			/*
			 * 循环字段集合
			 */
			for (int i = 0; i < fields.length; i++) {
				
				// 通过属性注解值 判断当前字段是否是 序列化，VO或主键ID，如果是则不做操作
				ModelMeta meta = fields[i].getAnnotation(ModelMeta.class);

				if (meta != null && (
						meta.fieldType() == ModelFieldType.SERIALVERSION || 
						meta.fieldType() == ModelFieldType.ID || 
						meta.fieldType() == ModelFieldType.VO))
					continue;
				
				
				// 获取字段值
				String fieldValue = getModelFieldValueByName(model, fields[i].getName());
				
				// 如果当前字段值为空，则直接循环下一个字段
				if (StringUtils.isNull(fieldValue))
					continue;
				
				// 如果不为空，则封装成 SQLContent对象
				String dbField = getDBFieldByModelFieldName(fields[i].getName());
				String modelField = "#{" + fields[i].getName() + "}";
				
	        	SQLContent sqlContent = new SQLContent(String.valueOf(dbField), modelField);
	        	contentList.add(sqlContent);
			}
		}
		
		return contentList;
	}
	
	/**
	 * 创建 查询 内容条件
	 * @param model
	 * @return
	 */
	public static List<SQLContent> createSelectSQLContent(final Object model) {
		
		if (model == null) 
			return null;
		
		List<SQLContent> contentList = null;
		
		Field[] fields = model.getClass().getDeclaredFields();
		
		if (fields != null && fields.length > 0) {
			
			contentList = new ArrayList<SQLContent>();
			
			for (int i = 0; i < fields.length; i++) {
				
				// 通过属性注解值 判断当前字段是否是 序列化，VO或主键ID，如果是则不做操作
				ModelMeta meta = fields[i].getAnnotation(ModelMeta.class);
				if (meta != null && (
						meta.fieldType() == ModelFieldType.SERIALVERSION ||
						meta.fieldType() == ModelFieldType.ID ||
						meta.fieldType() == ModelFieldType.VO))
					continue;
				
				String fieldValue = getModelFieldValueByName(model, fields[i].getName());
				
				if (StringUtils.isNull(fieldValue))
					continue;
				
				String dbField = getDBFieldByModelFieldName(fields[i].getName());
				String modelField = "#{" + fields[i].getName() + "}";
				
	        	SQLContent sqlContent = new SQLContent(String.valueOf(dbField), modelField);
	        	contentList.add(sqlContent);
			}
		}
		
		return contentList;
	}
	
	/**
	 * 根据对象属性名称获取对应属性值
	 * @param model
	 * @param fieldName
	 * @return
	 */
	public static String getModelFieldValueByName(Object model, String fieldName) {
		
		String fieldMethodName = "get" + StringUtils.captureName(fieldName);
		String fieldValue = null;
		
		// 获取当前 Model 对应的数据表名 '包' 前缀
		try {
			
			Method method = model.getClass().getMethod(fieldMethodName);
			fieldValue = String.valueOf(method.invoke(model));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return fieldValue;
	}
	
	/**
	 * 根据数据字段名称获取对应属性名称
	 * @param model
	 * @param fieldName
	 * @return
	 */
	public static String getModelFieldNameByDBFieldName(Object model, String fieldName) {
		
		if (StringUtils.isNull(fieldName))
			return null;
		
		StringBuffer modelFieldName = new StringBuffer();
		
		boolean isUp = false;
		
		for (int i = 0; i < fieldName.length(); i ++) {
			
    		char[] chars = new char[1];
        	chars[0] = fieldName.charAt(i);
        	String temp = new String(chars);  
        	
        	if (chars[0] == '_') {
        		isUp = true;
        		continue;
        	}
        	
        	if (isUp) {
        		modelFieldName.append(temp.toUpperCase());
        		isUp = false;
        	} else {
        		modelFieldName.append(temp);
        	}
		}
		
		return String.valueOf(modelFieldName);
	}
	
	/**
	 * 根据数据字段名称获取对应属性值的Get方法
	 * @param model
	 * @param fieldName
	 * @return
	 */
	public static String getModelFieldValueByDBFieldName(Object model, String fieldName) {
		
		if (StringUtils.isNull(fieldName))
			return null;
		
		StringBuffer modelFieldName = new StringBuffer("set");
		
		boolean isUp = false;
		
		for (int i = 0; i < fieldName.length(); i ++) {
			
    		char[] chars = new char[1];
        	chars[0] = fieldName.charAt(i);
        	String temp = new String(chars);  
        	
        	if (i == 0) {
        		modelFieldName.append(temp.toUpperCase());
        		continue;
        	}
        	
        	if (chars[0] == '_') {
        		isUp = true;
        		continue;
        	}
        	
        	if (isUp) {
        		modelFieldName.append(temp.toUpperCase());
        		isUp = false;
        	} else {
        		modelFieldName.append(temp);
        	}
		}
		
		String methodName = String.valueOf(modelFieldName);
		
		if (methodName.length() <= 3)
			return null;
		
//		if (methodName.contains("Time")) {
//			methodName = methodName.replace("Time", "Date");
//		}
		
		return methodName;
	}
	
	/**
	 * 根据属性名称获取数据库字段名称
	 * @param modelFieldName
	 * @return
	 */
	public static String getDBFieldByModelFieldName(String modelFieldName) {
		
		StringBuffer dbField = null;
		
		if (!StringUtils.isNull(modelFieldName)) {
		
			dbField = new StringBuffer();
			
			// 循环模型字段，当遇到大写字母时就追加 下划线及转换小写，当遇到小写字母时则直接追加
	    	for (int i = 0; i < modelFieldName.length(); i ++) {
				
	    		char[] chars = new char[1];  
	        	chars[0] = modelFieldName.charAt(i);
	        	String temp = new String(chars);  
	        	
	        	if (chars[0]>='A'  &&  chars[0]<='Z')
	        		dbField.append("_" + temp.toLowerCase());
	        	else
	        		dbField.append(temp);
			}
		}
    	
    	return dbField != null ? String.valueOf(dbField) : null;
	}
	
	/**
	 * 查询条件对象
	 */
	public static class SQLContent {
		
		private String field;      // 字段
		private String fieldKey;   // 获取字段值的Map键
		
		public SQLContent() {
			
		}
		
		public SQLContent(String field, String fieldKey) {
			this.field = field;
			this.fieldKey = fieldKey;
		}
		
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getFieldKey() {
			return fieldKey;
		}
		public void setFieldKey(String fieldKey) {
			this.fieldKey = fieldKey;
		}
	}
	
	/**
	 * OrderBy
	 */
	public static class SQLOrderbyModel {

		private String orderbyField;
		
		private String orderbyRule;
		
		public SQLOrderbyModel() {
			
		}
		
		public SQLOrderbyModel(String orderbyField, String orderbyRule) {
			this.orderbyField = SQLHelper.getDBFieldByModelFieldName(orderbyField);
			this.orderbyRule = orderbyRule;
		}

		public String getOrderbyField() {
			return orderbyField;
		}

		public void setOrderbyField(String orderbyField) {
			this.orderbyField = orderbyField;
		}

		public String getOrderbyRule() {
			return orderbyRule;
		}

		public void setOrderbyRule(String orderbyRule) {
			this.orderbyRule = orderbyRule;
		}
	}
}

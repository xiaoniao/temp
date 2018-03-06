package com.kunyao.assistant.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelMeta {
	
	public enum ModelFieldType {
		
		// 序列化
		SERIALVERSION,
		
		// 主键
		ID,  
		
		// 数据库字段
		PO,  
		
		// 业务字段
		VO;  
	}
	
	ModelFieldType fieldType() default ModelFieldType.PO;
}

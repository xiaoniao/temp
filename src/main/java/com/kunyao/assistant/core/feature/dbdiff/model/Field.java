package com.kunyao.assistant.core.feature.dbdiff.model;

/*
 * 字段属性
 */
public class Field {

	/**
     * 字段名 */
	private String name;
	
	/**
     * 是否允许为空 */
	private String isNullable;
	
	/**
     * auto_increment */
	private String extra;
	
	/**
     * 注释 */
	private String comments;
	
	/**
     * 字段类型 */
	private Class<?> type;
	
	/**
     * 字段类型（包含长度） */
	private String columnType;
	
	/**
     * 是否是主键 */
	private boolean isPrimaryKey;
	
	/**
     * 样式 */
	private String style = Table.STYLE_DIFFERENT;

	public String getName() {
		return name;
	}

	public void setName(String fieldName) {
		this.name = fieldName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> fieldType) {
		this.type = fieldType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return "Field [fieldName=" + name + ", isNullable=" + isNullable + ", extra=" + extra + ", comments="
				+ comments + ", fieldType=" + type + ", columnType=" + columnType + ", isPrimaryKey="
				+ isPrimaryKey + ", style=" + style + "]";
	}
}

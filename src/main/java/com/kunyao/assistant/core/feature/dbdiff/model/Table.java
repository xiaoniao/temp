package com.kunyao.assistant.core.feature.dbdiff.model;

import java.util.List;

/**
 * 表信息
 */
public class Table {
	
	public static final String STYLE_SAME = "default";
	public static final String STYLE_DIFFERENT = "red";
	public static final String STYLE_NO_EXIST = "no_exist";
	
	/**
     * 区分线上线下 remote/local */
	private String type;
	
	/**
     * 表名 */
	private String tableName;
	
	private String className;
	
	/**
     * 注释 */
	private String comments;
	
	/**
     * 字段列表 */
	private List<Field> fields;
	
	/**
     * 样式  default无差异  red有差异*/
	private String style = STYLE_DIFFERENT;
	
	/**
     * sql语句 */
	private String sql;

	
	public Table() {
		
	}

	public Table(String tableName, String style) {
		setTableName(tableName);
		this.style = style;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
		
		tableName = tableName.replace("kycom_u_", "");
		tableName = tableName.replace("kycom_a_", "");
		tableName = tableName.replace("kycom_t_", "");
		tableName = tableName.replace("kycom_r_", "");
		tableName = tableName.replace("kycom_o_", "");
		StringBuffer className = new StringBuffer();
		boolean is_ = false;
		for (int i = 0; i < tableName.length(); i++) {
			char c = tableName.charAt(i);
			if (i == 0) {
				c = (char) (c - (char) 32);
			}
			if (c == '_') {
				is_ = true;
			} else {
				if (is_) {
					className.append((char) (c - (char) 32));
				} else {
					className.append(c);
				}
				is_ = false;
			}
		}
		this.className = className.toString();
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "TableInfo [tableName=" + tableName + ", fields=" + fields + "]";
	}
}

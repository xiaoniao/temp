package com.kunyao.assistant.core.feature.sql;

import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.feature.sql.SQLHelper.SQLContent;
import com.kunyao.assistant.core.feature.sql.SQLHelper.SQLOrderbyModel;
import com.kunyao.assistant.core.utils.StringUtils;

/**
 * 生成SQL 工具类
 * @author GeNing
 * @since  2016.11.21
 */
public class SQLWriter {
	
	private boolean insert = false;
	
	private String insertTable;
	
	private String insertValues;
	
	private boolean update = false;
	
	private String updateTable;
	
	private String setValues;
	
	private String selectColumns;
	
	private String tableName;
	
	private String whereConditions;
	
	private String orderByColumns;
	
	private String limit;
	
	public void INSERT_INTO(final String tableName) {
		insert = true;
		this.insertTable = tableName;
	}
	
	public void VALUES(final List<SQLContent> contentList) {
		
		StringBuffer insertBuffer = null;
		
		if (contentList != null && contentList.size() > 0) {
			insertBuffer = new StringBuffer();
			
			insertBuffer.append(" (");
			for (int i = 0; i < contentList.size(); i ++) {
				if (i != 0)
					insertBuffer.append(", ");
				insertBuffer.append(contentList.get(i).getField());
			}
			insertBuffer.append(") ");
			
			insertBuffer.append("VALUES");
			
			insertBuffer.append(" (");
			for (int i = 0; i < contentList.size(); i ++) {
				if (i != 0)
					insertBuffer.append(", ");
				insertBuffer.append(contentList.get(i).getFieldKey());
			}
			insertBuffer.append(") ");
		}
		
		this.insertValues = String.valueOf(insertBuffer);
	}
	
	public void UPDATE(final String tableName) {
		update = true;
		this.updateTable = tableName;
	}
	
	public void SET(final List<SQLContent> contentList) {
		StringBuffer setBuffer = null;
		
		if (contentList != null && contentList.size() > 0) {
			setBuffer = new StringBuffer();
			
//			setBuffer.append("(");
			for (int i = 0; i < contentList.size(); i ++) {
				if (i != 0)
					setBuffer.append(", ");
				setBuffer.append(contentList.get(i).getField() + " = " + contentList.get(i).getFieldKey());
			}
//			setBuffer.append(")");
		}
		
		this.setValues = String.valueOf(setBuffer);
	}
	
	public void SELECT(final String selectColumns) {
		this.selectColumns = selectColumns;
	}
	
	public void FROM(final String tableName) {
		this.tableName = tableName;
	}
	
	public void WHERE(final List<SQLContent> contentList) {
		StringBuffer conditionsBuffer = null;
		
		if (contentList != null && contentList.size() > 0) {
			conditionsBuffer = new StringBuffer();
			
			for (int i = 0; i < contentList.size(); i ++) {
				if (i != 0)
					conditionsBuffer.append(" AND ");
				conditionsBuffer.append(contentList.get(i).getField() + " = " + contentList.get(i).getFieldKey().replaceAll("\\#\\{", "#{model."));
			}
		}
		
		this.whereConditions = String.valueOf(conditionsBuffer);
	}
	
	public void WHERE(final String where) {
		this.whereConditions = where;
	}
	
	public void ORDERBY(final List<SQLOrderbyModel> contentList) {
		StringBuffer orderByBuffer = null;
		
		if (contentList != null && contentList.size() > 0) {
			orderByBuffer = new StringBuffer();
			
			for (int i = 0; i < contentList.size(); i ++) {
				if (i != 0)
					orderByBuffer.append(",");
				orderByBuffer.append(contentList.get(i).getOrderbyField() + " " + contentList.get(i).getOrderbyRule());
			}
		}
		
		this.orderByColumns = String.valueOf(orderByBuffer);
	}
	
	public void LIMIT(final Map<String, Integer> limitMap) {
		if (limitMap != null && limitMap.size() > 0) {
			if (limitMap.get("startPos") > -1 && limitMap.get("pageSize") > -1)
				this.limit = limitMap.get("startPos") + "," + limitMap.get("pageSize");
		}
	}
	
	public String createSQL() {
		// TODO Auto-generated method stub\
		StringBuffer sqlBuffer = new StringBuffer();
		
		if (insert && !StringUtils.isNull(insertTable)) 
			sqlBuffer.append("INSERT INTO " + insertTable);
		
		if (!StringUtils.isNull(insertValues)) 
			sqlBuffer.append(insertValues);
		
		if (update && !StringUtils.isNull(updateTable))
			sqlBuffer.append("UPDATE " + updateTable + " ");
		
		if (!StringUtils.isNull(setValues)) 
			sqlBuffer.append("SET " + setValues + " ");
		
		if (!StringUtils.isNull(selectColumns)) 
			sqlBuffer.append("SELECT " + this.selectColumns + " ");
		
		if (!StringUtils.isNull(tableName)) 
			sqlBuffer.append("FROM " + this.tableName + " ");
		
		if (!StringUtils.isNull(whereConditions))
			sqlBuffer.append("WHERE (" + this.whereConditions + ")" + " ");
		
		if (!StringUtils.isNull(orderByColumns))
			sqlBuffer.append("ORDER BY " + this.orderByColumns + " ");
		
		if (!StringUtils.isNull(limit))
			sqlBuffer.append("LIMIT " + this.limit + " ");
		
		return String.valueOf(sqlBuffer);
	}
}

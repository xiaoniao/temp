package com.kunyao.assistant.core.feature.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kunyao.assistant.core.feature.sql.SQLHelper.SQLContent;
import com.kunyao.assistant.core.feature.sql.SQLHelper.SQLOrderbyModel;

public class SQLProvider<Model, PK> {
	
	private final static Logger logger = Logger.getLogger(SQLProvider.class);
	
	/**
	 * 创建新增语句
	 * @param model
	 * @return
	 */
	public String createInsertSQL(final Model model) {
		
		final String tableName = SQLHelper.getTableName(model);
		final List<SQLContent> contentList = SQLHelper.createInsertOrUpdateSQLContent(model);
		
		String sql = new SQLWriter() {{
			
			INSERT_INTO(tableName);
			VALUES(contentList);
			
		}}.createSQL();
		
		logger.info(sql);
		
		return sql;
	}
	
	/**
	 * 创建修改语句
	 * @param model
	 * @return
	 */
	public String createUpdateSQL(final Model model) {
		
		final String tableName = SQLHelper.getTableName(model);
		final List<SQLContent> contentList = SQLHelper.createInsertOrUpdateSQLContent(model);
		
		String sql = new SQLWriter() {{
			
			UPDATE(tableName);
			SET(contentList);
			WHERE("id = #{id}");
			
		}}.createSQL();
		
		return sql;
	}
	
	/**
	 * 创建根据ID查询语句
	 * @param model
	 * @return
	 */
	public String createFindByIdSQL(final Model model) {
		
		final String tableName = SQLHelper.getTableName(model);

		String sql = new SQLWriter() {{
			
			SELECT("*");
			FROM(tableName);
			WHERE("id = #{id}");
			
		}}.createSQL();
		
		logger.info(sql);
		
		return sql;
	}
	
	/**
     * 动态查询单个对象（加排序）
     * @return 
     */
	@SuppressWarnings({ "unchecked", "serial" })
	public String createFindOneSQL(Map<String, Object> params) {
		
		final Model model = (Model) params.get("model");
		
		final String tableName = SQLHelper.getTableName(model);
		final List<SQLContent> contentList = SQLHelper.createSelectSQLContent(model);
		final Map<String, Integer> limitMap = new HashMap<String, Integer>() {{ put("startPos", 0); put("pageSize", 1); }};
		List<SQLOrderbyModel> orderbyList = null;
		
		if (params.get("orderBy") != null) 
			orderbyList = (List<SQLOrderbyModel>) params.get("orderBy");
		
		
		SQLWriter sqlWriter = new SQLWriter();
		
		sqlWriter.SELECT("*");
		sqlWriter.FROM(tableName);
		
		if (contentList != null && contentList.size() > 0) 
			sqlWriter.WHERE(contentList);
		
		if (orderbyList != null && orderbyList.size() > 0) 
			sqlWriter.ORDERBY(orderbyList);
		
		sqlWriter.LIMIT(limitMap);

		String sql = sqlWriter.createSQL();
		
		logger.info(sql);
		
		return sql;
		
	}
	
	/**
	 * 创建根据条件动态查询结果数量
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public String createFindCountByConditionSQL(Map<String, Object> params) {
		
		final Model model = (Model) params.get("model");
		final String tableName = SQLHelper.getTableName(model);
		final List<SQLContent> contentList = SQLHelper.createSelectSQLContent(model);
		
		String sql = new SQLWriter() {{
			
			SELECT("COUNT(*)");
			FROM(tableName);
			
			if (contentList != null && contentList.size() > 0) 
				WHERE(contentList);
			
		}}.createSQL();
		
		logger.info(sql);
		
		return sql;
	}
	
	/**
	 * 创建根据条件动态查询列表集合
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "serial" })
	public String createFindListByConditionSQL(final Map<String, Object> params) {
		
		final Model model = (Model) params.get("model");
		
		final String tableName = SQLHelper.getTableName(model);
		final List<SQLContent> contentList = SQLHelper.createSelectSQLContent(model);
		
		Map<String, Integer> limitMap = null;
		List<SQLOrderbyModel> orderbyList = null;
		
		if (params.get("startPos") != null && params.get("pageSize") != null) 
			limitMap = new HashMap<String, Integer>() {{ put("startPos", Integer.parseInt(String.valueOf(params.get("startPos")))); put("pageSize", Integer.parseInt(String.valueOf(params.get("pageSize")))); }};
		
		if (params.get("orderBy") != null) 
			orderbyList = (List<SQLOrderbyModel>) params.get("orderBy");
			
		
		SQLWriter sqlWriter = new SQLWriter();
		
		sqlWriter.SELECT("*");
		sqlWriter.FROM(tableName);
		
		if (contentList != null && contentList.size() > 0) 
			sqlWriter.WHERE(contentList);
		
		if (orderbyList != null && orderbyList.size() > 0) 
			sqlWriter.ORDERBY(orderbyList);
		
		if (limitMap != null && limitMap.size() > 0)
			sqlWriter.LIMIT(limitMap);

		String sql = sqlWriter.createSQL();
		
		logger.info(sql);
		
		return sql;
	}
	
	public String createSQL(final Map<String, Object> params) {
		return String.valueOf(params.get("sql"));
	}
}

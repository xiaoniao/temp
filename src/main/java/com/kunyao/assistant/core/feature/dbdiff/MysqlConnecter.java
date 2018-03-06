package com.kunyao.assistant.core.feature.dbdiff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.feature.dbdiff.model.Field;
import com.kunyao.assistant.core.feature.dbdiff.model.Table;

public class MysqlConnecter {

	private static Map<String, Class<?>> map = new HashMap<>();
	static {
		map.put("tinyint", Integer.class);
		map.put("smallint", Integer.class);
		map.put("mediumint", Integer.class);
		map.put("Integer", Integer.class);
		map.put("bigint", Long.class);
		map.put("int", Integer.class);

		map.put("float", Double.class);
		map.put("double", Double.class);
		map.put("decimal", Double.class);

		map.put("char", String.class);
		map.put("varchar", String.class);
		map.put("tinyblob", String.class);
		map.put("tinytext", String.class);
		map.put("blob", String.class);
		map.put("text", String.class);
		map.put("mediumblob", String.class);
		map.put("mediumtext", String.class);
		map.put("logngblob", String.class);
		map.put("longtext", String.class);
		map.put("enum", String.class);
		map.put("set", String.class);

		map.put("date", Date.class);
		map.put("Date", Date.class);
		map.put("time", Date.class);
		map.put("year", Date.class);
		map.put("datetime", Date.class);
		map.put("timestamp", Date.class);
	}

	public static List<Table> connect(String mysql, String dbname, String username, String password, String type) {
		List<Table> tableInfos = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://" + mysql + "/" + dbname, username, password);

			// 读取数据库表名
			PreparedStatement pstmt = connection.prepareStatement(
					"select table_name from information_schema.`TABLES` where TABLE_SCHEMA = '" + dbname + "'");
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				String tableName = resultSet.getString(1);
				Table tableInfo = new Table();
				tableInfo.setType(type);
				tableInfo.setTableName(tableName);

				// 读取字段名
				PreparedStatement fieldPstmt = connection.prepareStatement(
						"select column_name, is_nullable, data_type, column_type, column_key, extra, column_comment from information_schema.`COLUMNS` where TABLE_SCHEMA = '"
								+ dbname + "' and table_name = '" + tableName + "'");
				ResultSet fieldResultSet = fieldPstmt.executeQuery();
				List<Field> fields = new ArrayList<>();
				while (fieldResultSet.next()) {
					Field field = new Field();
					String columnName = fieldResultSet.getString(1);
					String isNullable = fieldResultSet.getString(2);
					String dataType = fieldResultSet.getString(3);
					String columnType = fieldResultSet.getString(4);
					String columnKey = fieldResultSet.getString(5);
					String extra = fieldResultSet.getString(6);
					String columnComment = fieldResultSet.getString(7);
					field.setName(columnName);
					field.setIsNullable(isNullable);
					field.setType(map.get(dataType));
					field.setColumnType(columnType);
					field.setExtra(extra);
					field.setComments(columnComment);
					field.setPrimaryKey(columnKey.equals("PRI") ? true : false);
					fields.add(field);
				}
				tableInfo.setFields(fields);
				tableInfos.add(tableInfo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return tableInfos;
	}
}

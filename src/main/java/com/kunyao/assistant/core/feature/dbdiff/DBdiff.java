package com.kunyao.assistant.core.feature.dbdiff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kunyao.assistant.core.feature.dbdiff.model.Config;
import com.kunyao.assistant.core.feature.dbdiff.model.Field;
import com.kunyao.assistant.core.feature.dbdiff.model.Table;

/**
 * 数据库差异
 */
public class DBdiff {

	public static void main(String[] args) {
		String localMysql = "127.0.0.1:3306";
		String localDbName = "xy-test";
		String localUsername = "root";
		String localPassword = "b9xcc4z2"; 
		String remoteMysql = "116.62.6.249:3306"; 
		String remoteDbName = "xy-test";
		String remoteUsername = "root";
		String remotePassword = "kunyao2017."; 
		boolean isHidenSameValue= true;
		
		Config config = new Config(localMysql, localDbName, localUsername, localPassword, remoteMysql, remoteDbName, remoteUsername, remotePassword, isHidenSameValue);
		
		Map<String, List<Table>> map = DBdiff.diff(config);
	}
	
	/**
	 * 比较数据库差异
	 * @param dbConfig 数据库配置信息
	 * @return
	 */
	public static Map<String, List<Table>> diff(Config dbConfig) {
		// 本地数据库
		List<Table> localTables = MysqlConnecter.connect(dbConfig.localMysql, dbConfig.localDbName, dbConfig.localUsername, dbConfig.localPassword, "local");
		// 远程数据库
		List<Table> remoteTables = MysqlConnecter.connect(dbConfig.remoteMysql, dbConfig.remoteDbName, dbConfig.remoteUsername, dbConfig.remotePassword, "remote");
//
//		compare(localTables, remoteTables);
//		compare(remoteTables, localTables);

		// 补全另一个表中没有的表
		Set<String> keys = new HashSet<>();
		for (Table tableInfo : localTables) {
			if ("red".equals(tableInfo.getStyle())) {
				Table t = new Table();
				t.setTableName(tableInfo.getTableName());
				t.setStyle("none");
				remoteTables.add(t);
			}
			keys.add(tableInfo.getTableName());
		}
		for (Table tableInfo : remoteTables) {
			if ("red".equals(tableInfo.getStyle())) {
				Table t = new Table();
				t.setTableName(tableInfo.getTableName());
				t.setStyle("none");
				localTables.add(t);
			}
			keys.add(tableInfo.getTableName());
		}

		List<Table> a = new ArrayList<>();
		List<Table> b = new ArrayList<>();
//		for (String string : keys) {
//			Table tablea = indexofTable(localTables, string);
//			Table tableb = indexofTable(remoteTables, string);
//			a.add(tablea);
//			b.add(tableb);
//		}
//
//		// 去除相同的数据
//		if (dbConfig.isHidenSameValue) {
//			remove(a, b);
//		}
//
//		createSql(a, b);

		Map<String, List<Table>> result = new HashMap<>();
		result.put("a", a);
		result.put("b", b);
		return result;
	}
//
//	/**
//	 * 标记差异字段为 Table.STYLE_RED
//	 * 
//	 * @param tableLeft
//	 * @param tableRight
//	 */
//	private static void compare(List<Table> tableLeft, List<Table> tableRight) {
//		// 比较表
//		for (Table tableInfoa : tableLeft) {
//			if (indexofTable(tableRight, tableInfoa.getTableName()) != null) {
//				tableInfoa.setStyle(Table.STYLE_RED); // 有差异
//			}
//		}
//
//		// 比较字段
//		for (Table tablea : tableLeft) {
//			Table tableb = indexofTable(tableRight, tablea.getTableName());
//			if (tableb == null) {
//				for (Field field : tablea.getFields()) {
//					field.setStyle(Table.STYLE_RED);
//				}
//			} else {
//				for (Field fielda : tablea.getFields()) {
//					if (indexofField(tableb.getFields(), fielda.getFieldName()) != null) {
//						fielda.setStyle(Table.STYLE_RED);
//					}
//				}
//			}
//		}
//	}
//
//	private static void remove(List<Table> tablesa, List<Table> tablesb) {
//		for (int i = tablesa.size() - 1; i >= 0; i--) {
//			Table tablea = tablesa.get(i);
//			Table tableb = tablesb.get(i);
//
//			List<Field> fieldsa = tablea.getFields();
//			List<Field> fieldsb = tableb.getFields();
//
//			boolean isContainRed = false;
//			if (fieldsa != null) {
//				for (Field field : fieldsa) {
//					if (field.getStyle().equals("red")) {
//						isContainRed = true;
//						break;
//					}
//				}
//			}
//			if (fieldsb != null) {
//				for (Field field : fieldsb) {
//					if (field.getStyle().equals("red")) {
//						isContainRed = true;
//						break;
//					}
//				}
//			}
//			if (tablea.getStyle().equals("red") || tableb.getStyle().equals("red")) {
//				isContainRed = true;
//			}
//			if (!isContainRed) {
//				tablesa.remove(i);
//				tablesb.remove(i);
//			}
//		}
//	}
//
//	private static Table indexofTable(List<Table> tables, String tableName) {
//		for (Table tableInfo : tables) {
//			if (tableInfo.getTableName().equals(tableName)) {
//				return tableInfo;
//			}
//		}
//		return null;
//	}
//
//	private static Field indexofField(List<Field> fields, String fieldName) {
//		for (Field field : fields) {
//			if (field.getFieldName().equals(fieldName)) {
//				return field;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 生成sql语句
//	 * @param tablesa
//	 * @param tablesb
//	 */
//	private static void createSql(List<Table> tablesa, List<Table> tablesb) {
//		for (int i = tablesa.size() - 1; i >= 0; i--) {
//			Table tablea = tablesa.get(i);
//			Table tableb = tablesb.get(i);
//
//			List<Field> fieldsa = tablea.getFields();
//			List<Field> fieldsb = tableb.getFields();
//
//			boolean isContainRed = false;
//			if (fieldsa != null) {
//				for (Field field : fieldsa) {
//					if (field.getStyle().equals("red")) {
//						isContainRed = true;
//						break;
//					}
//				}
//			}
//			if (fieldsb != null) {
//				for (Field field : fieldsb) {
//					if (field.getStyle().equals("red")) {
//						isContainRed = true;
//						break;
//					}
//				}
//			}
//
//			if (isContainRed == true) {
//				// 修改字段
//				if (!tablea.getStyle().equals("none")) {
//					String sqla = createAlert(tablea);
//					tablea.setSql(sqla);
//				}
//				if (!tableb.getStyle().equals("none")) {
//					String sqlb = createAlert(tableb);
//					tableb.setSql(sqlb);
//				}
//			}
//
//			if (tablea.getStyle().equals("red") || tableb.getStyle().equals("red")) {
//				isContainRed = true;
//				// 新建表
//				if (!tablea.getStyle().equals("none")) {
//					String sqla = createInsert(tablea);
//					tablea.setSql(sqla);
//				}
//				if (!tableb.getStyle().equals("none")) {
//					String sqlb = createInsert(tableb);
//					tableb.setSql(sqlb);
//				}
//			}
//		}
//	}
//
//	/**
//	 * 修改表语句
//	 */
//	private static String createAlert(Table table) {
//		StringBuffer alerts = new StringBuffer();
//		String alert = "ALTER TABLE #tablename ADD #fieldname #columnType #isNullable  COMMENT '#comment' ;\n";
//
//		if (table.getFields() != null) {
//			for (Field field : table.getFields()) {
//				if (!field.getStyle().equals("red")) {
//					continue;
//				}
//
//				String value = alert.replaceAll("#tablename", table.getTableName())
//						.replace("#fieldname", field.getFieldName()).replace("#columnType", field.getColumnType());
//
//				if (field.getIsNullable().equals("NO")) {
//					value = value.replace("#isNullable", "NOT NULL");
//				} else {
//					value = value.replace("#isNullable", "DEFAULT NULL");
//				}
//				if (field.getComments() != null) {
//					value = value.replace("#comment", field.getComments());
//				} else {
//					value = value.replace("#comment", "");
//				}
//				alerts.append(value);
//			}
//		}
//		return alerts.toString().replace("\n", "<br>").replace("\r", "&nbsp;&nbsp;&nbsp;&nbsp;");
//	}
//
//	/**
//	 * 创建表语句
//	 * 
//	 * @param table
//	 * @return
//	 */
//	private static String createInsert(Table table) {
//		String insert = " CREATE TABLE `#tablename` ( \n" + " #lines #primary \n"
//				+ " ) ENGINE=InnoDB DEFAULT CHARSET=utf8 \n";
//
//		String line = "\r  `#fieldname` #columnType #isNullable #extra COMMENT '#comment', \n";
//
//		String primary = "\r  PRIMARY KEY (`#key`) ";
//
//		insert = insert.replace("#tablename", table.getTableName());
//
//		boolean isHasPrimarykey = false;
//		StringBuffer lines = new StringBuffer();
//		for (Field field : table.getFields()) {
//
//			String value = line.replace("#fieldname", field.getFieldName()).replace("#columnType",
//					field.getColumnType());
//
//			if (field.getIsNullable().equals("NO")) {
//				value = value.replace("#isNullable", "NOT NULL");
//			} else {
//				value = value.replace("#isNullable", "DEFAULT NULL");
//			}
//			if (field.getExtra() != null && field.getExtra().equals("auto_increment")) {
//				value = value.replace("#extra", "AUTO_INCREMENT");
//			} else {
//				value = value.replace("#extra", "");
//			}
//			if (field.getComments() != null) {
//				value = value.replace("#comment", field.getComments());
//			} else {
//				value = value.replace("#comment", "");
//			}
//			lines.append(value);
//
//			if (field.isPrimaryKey()) {
//				isHasPrimarykey = true;
//				primary = primary.replace("#key", field.getFieldName());
//			}
//		}
//
//		if (isHasPrimarykey) {
//			insert = insert.replace("#primary", primary);
//		} else {
//			insert = insert.replace("#primary", "");
//		}
//
//		insert = insert.replace("#lines", lines.toString());
//
//		return insert.replace("\n", "<br>").replace("\r", "&nbsp;&nbsp;&nbsp;&nbsp;");
//	}
}
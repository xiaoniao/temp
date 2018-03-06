package com.kunyao.assistant.core.feature.dbdiff;

import com.kunyao.assistant.core.feature.dbdiff.model.Field;
import com.kunyao.assistant.core.feature.dbdiff.model.Table;

/**
 * 以本地数据库为基准，给远程数据库 创建表或者添加字段
 */
public class SqlUtils {

	/**
	 * 增加字段
	 * ALTER TABLE ADD field
	 */
	public static String generateFieldCreate(Table table) {
		StringBuffer alerts = new StringBuffer();
		String alert = "ALTER TABLE #tablename ADD #fieldname #columnType #isNullable COMMENT '#comment' ;\n";

		if (table.getFields() != null) {
			for (Field field : table.getFields()) {
				if (!field.getStyle().equals("red")) {
					continue;
				}

				String value = alert.replaceAll("#tablename", table.getTableName())
						.replace("#fieldname", field.getName()).replace("#columnType", field.getColumnType());

				if (field.getIsNullable().equals("NO")) {
					value = value.replace("#isNullable", "NOT NULL");
				} else {
					value = value.replace("#isNullable", "DEFAULT NULL");
				}
				if (field.getComments() != null) {
					value = value.replace("#comment", field.getComments());
				} else {
					value = value.replace("#comment", "");
				}
				alerts.append(value);
			}
		}
		return alerts.toString().replace("\n", "<br>").replace("\r", "&nbsp;&nbsp;&nbsp;&nbsp;");
	}

	/**
	 * 创建表语句
	 * CREATE TABLE
	 */
	public static String generateTableCreate(Table table) {
		String insert = " CREATE TABLE `#tablename` ( \n" + " #lines #primary \n"
				+ " ) ENGINE=InnoDB DEFAULT CHARSET=utf8 \n";

		String line = "\r  `#fieldname` #columnType #isNullable #extra COMMENT '#comment', \n";

		String primary = "\r  PRIMARY KEY (`#key`) ";

		insert = insert.replace("#tablename", table.getTableName());

		boolean isHasPrimarykey = false;
		StringBuffer lines = new StringBuffer();
		for (Field field : table.getFields()) {

			String value = line.replace("#fieldname", field.getName()).replace("#columnType",
					field.getColumnType());

			if (field.getIsNullable().equals("NO")) {
				value = value.replace("#isNullable", "NOT NULL");
			} else {
				value = value.replace("#isNullable", "DEFAULT NULL");
			}
			if (field.getExtra() != null && field.getExtra().equals("auto_increment")) {
				value = value.replace("#extra", "AUTO_INCREMENT");
			} else {
				value = value.replace("#extra", "");
			}
			if (field.getComments() != null) {
				value = value.replace("#comment", field.getComments());
			} else {
				value = value.replace("#comment", "");
			}
			lines.append(value);

			if (field.isPrimaryKey()) {
				isHasPrimarykey = true;
				primary = primary.replace("#key", field.getName());
			}
		}

		if (isHasPrimarykey) {
			insert = insert.replace("#primary", primary);
		} else {
			insert = insert.replace("#primary", "");
		}

		insert = insert.replace("#lines", lines.toString());
		
		return insert.replace("\n", "<br>").replace("\r", "&nbsp;&nbsp;&nbsp;&nbsp;");
	}
}

package com.kunyao.assistant.core.feature.dbdiff;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.feature.dbdiff.model.Field;
import com.kunyao.assistant.core.feature.dbdiff.model.Table;

public class DbTools {

	public static class Db {
		private String url;
		private String name;
		private String userName;
		private String password;
		
		public Db(String url, String name, String userName, String password) {
			this.url = url;
			this.name = name;
			this.userName = userName;
			this.password = password;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	/**
	 * Map<String, List<Table>> 
	 * 	  local
	 *    remote
	 *    
	 * @param local
	 * @param remote
	 */
	public static Map<String, List<Table>> diff(Db local, Db remote, boolean isHidenSameValue) {
		Map<String, List<Table>> result = new HashMap<>();
		List<Table> locals = MysqlConnecter.connect(local.url, local.name, local.userName, local.password, "local");
		List<Table> remotes = MysqlConnecter.connect(remote.url, remote.name, remote.userName, remote.password, "remote");
		result.put("local", locals);
		result.put("remote", remotes);
		compareTable(locals, remotes);
		sort(locals);
		sort(remotes);
		if (isHidenSameValue) {
			removeSame(locals, remotes);
		}
		return result;
	}
	
	// 比较表名的不同
	private static void compareTable(List<Table> locals, List<Table> remotes) {
		// 比较都存在的表
		for (Table local : locals) {
			for (Table remote : remotes) {
				if (local.getTableName().equals(remote.getTableName())) {
					local.setStyle(Table.STYLE_SAME);
					remote.setStyle(Table.STYLE_SAME);
					// 比较字段的差异
					compareField(local, remote);
				}
			}
		}
		// 补全一个表准存在而另一个表准不存在的表，因为要在界面上显示出来
		for (Table table : locals) {
			if (table.getStyle().equals(Table.STYLE_DIFFERENT)) {
				Table remote = new Table(table.getTableName(), Table.STYLE_NO_EXIST);
				remote.setSql(SqlUtils.generateTableCreate(table));
				remotes.add(remote);
			}
		}
		for (Table table : remotes) {
			if (table.getStyle().equals(Table.STYLE_DIFFERENT)) {
				Table local = new Table(table.getTableName(), Table.STYLE_NO_EXIST);
				local.setSql(SqlUtils.generateTableCreate(table));
				locals.add(local);
			}
		}
	}

	// 比较字段的不同
	private static void compareField(Table localTable, Table remoteTable) {
		List<Field> locals = localTable.getFields();
		List<Field> remotes = remoteTable.getFields();
		
		for (Field local : locals) {
			for (Field remote : remotes) {
				if (String.valueOf(local).equals(String.valueOf(remote))) {
					local.setStyle(Table.STYLE_SAME);
					remote.setStyle(Table.STYLE_SAME);
				}
			}
		}
		
		for (Field local : locals) {
			if (local.getStyle().equals(Table.STYLE_DIFFERENT)) {
				localTable.setSql(SqlUtils.generateFieldCreate(remoteTable));
			}
		}
		
		for (Field remote : remotes) {
			if (remote.getStyle().equals(Table.STYLE_DIFFERENT)) {
				remoteTable.setSql(SqlUtils.generateFieldCreate(localTable));
			}
		}
	}

	// 按字母排序 表名和字段名
	private static void sort(List<Table> tables) {
		Collections.sort(tables, new Comparator<Table>() {

			@Override
			public int compare(Table o1, Table o2) {
				String s1 = o1.getTableName();
				String s2 = o2.getTableName();
				return sortString(s1, s2);
			}
		});
		for (Table table : tables) {
			if (table.getFields() == null) {
				continue;
			}
			Collections.sort(table.getFields(), new Comparator<Field>() {

				@Override
				public int compare(Field o1, Field o2) {
					String s1 = o1.getName();
					String s2 = o2.getName();
					return sortString(s1, s2);
				}
			});
		}
	}
	
	private static int sortString(String s1, String s2) {
		int n1 = s1.length();
        int n2 = s2.length();
        int min = Math.min(n1, n2);
        for (int i = 0; i < min; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 != c2) {
                c1 = Character.toUpperCase(c1);
                c2 = Character.toUpperCase(c2);
                if (c1 != c2) {
                    c1 = Character.toLowerCase(c1);
                    c2 = Character.toLowerCase(c2);
                    if (c1 != c2) {
                        // No overflow because of numeric promotion
                        return c1 - c2;
                    }
                }
            }
        }
        return n1 - n2;
	}
	
	/**
	 * 删除没冲突的表和字段
	 */
	private static void removeSame(List<Table> locals, List<Table> remotes) {
		// locals 和 remotes 长度一致
		int size = locals.size();
		for (int i = size - 1; i >= 0; i--) {
			Table l = locals.get(i);
			Table r = remotes.get(i);
			if (l.getSql() == null && r.getSql() == null) {
				locals.remove(i);
				remotes.remove(i);
				continue;
			}
			List<Field> ls = l.getFields();
			List<Field> rs = r.getFields();
			for (int j = ls.size() - 1; j >= 0; j--) {
				if (ls.get(j).getStyle().equals(Table.STYLE_SAME)) {
					ls.remove(j);
				}
			}
			for (int j = rs.size() - 1; j >= 0; j--) {
				if (ls.get(j).getStyle().equals(Table.STYLE_SAME)) {
					ls.remove(j);
				}
			}
		}
	}

}

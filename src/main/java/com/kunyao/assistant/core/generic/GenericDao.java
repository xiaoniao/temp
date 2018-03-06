package com.kunyao.assistant.core.generic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.kunyao.assistant.core.feature.sql.SQLHelper.SQLOrderbyModel;
import com.kunyao.assistant.core.feature.sql.SQLProvider;

/**
 * 所有自定义Dao的顶级接口, 封装常用的增删查改操作,
 * 可以通过Mybatis Generator Maven 插件自动生成Dao,
 * 也可以手动编码,然后继承GenericDao 即可.
 *
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 * @author GeNing
 * @since 2015.4.15
 */
public interface GenericDao<Model, PK> {
	
    /**
     * 插入对象
     * 
     * 只插入不为null的字段,不会影响有默认值的字段
     * 
     * @param model
     * @return
     */
	@InsertProvider(type = SQLProvider.class, method = "createInsertSQL")
	@Options(useGeneratedKeys = true, keyProperty="id")
    int insert(Model model);
    
    /**
     * 按主键ID更新对象
     * 只更新对象里值不为空的属性
     * 
     * @param model
     * @return
     */
	@UpdateProvider(type = SQLProvider.class, method = "createUpdateSQL")
    int updateByID(Model model);
    
    /**
     * 根据主键查询
     * 
     * @param id
     * @return
     */
	@SelectProvider(type = SQLProvider.class, method = "createFindByIdSQL")
    Map<String, Object> findByID(Model model);
	
	/**
	 * 动态查询单个对象（加排序）
	 * @param model
	 * @param orderBy
	 * @return
	 */
	@SelectProvider(type = SQLProvider.class, method = "createFindOneSQL")
	Map<String, Object> findOne(@Param("model") Model model, @Param("orderBy") List<SQLOrderbyModel> orderByList);
	
	/**
     * 根据列表条件查询列表数量
     * @param model
     * @return
     */
	@SelectProvider(type = SQLProvider.class, method = "createFindCountByConditionSQL")
    int findCountByCondition(@Param("model") Model model);
	
	/**
     * 根据列表条件查询列表数量
     * @param model
     * @return
     */
	@SelectProvider(type = SQLProvider.class, method = "createSQL")
    int findCountByConditionSql(@Param("sql") String sql);
                                                                                                                                                                                                                  
	/**
     * 根据列表条件查询（加排序）
     * @param startpos
     * @param pagesize
     * @param model
     * @return
     */
	@SelectProvider(type = SQLProvider.class, method = "createFindListByConditionSQL")
	List<Map<String, Object>> findListByCondition(@Param("startPos") Integer startPos, @Param("pageSize") Integer pageSize, @Param("model") Model model,  @Param("orderBy") List<SQLOrderbyModel> orderBy);

	/**
     * 根据列表条件查询（加排序）
     * @param startpos
     * @param pagesize
     * @param model
     * @return
     */
	@SelectProvider(type = SQLProvider.class, method = "createSQL")
	List<Map<String, Object>> findListBySql(@Param("sql") String sql);
}

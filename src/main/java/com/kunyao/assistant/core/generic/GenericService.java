package com.kunyao.assistant.core.generic;

import java.util.List;

import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.sql.SQLHelper.SQLOrderbyModel;

/**
 * 所有自定义Service的顶级接口,封装常用的增删查改操作
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 * @author GeNing
 * @since 2015.4.15
 */
public interface GenericService<Model, PK> {

    /**
     * 插入对象
     *
     * @param model 对象
     */
    int insert(Model model) throws ServiceException;

    /**
     * 更新对象
     *
     * @param model 对象
     */
    int update(Model model) throws ServiceException;

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     */
    Model findByID(PK id) throws ServiceException;
    
    /**
     * 查询分页数据信息，用于后台管理页面-通用模板列表页里初始化列表数据
     * 
     * @param pageSize 分页大小
     * @param model 对象
     */
    PageInfo findPageInfo(Integer pageSize, Model model) throws ServiceException;
    
    /**
     * 查询分页数据信息，用于后台管理页面-通用模板列表页里初始化列表数据
     * 
     * @param pageSize 分页大小
     * @param sql sql语句
     */
    public PageInfo findPageInfo(Integer pageSize, String sql) throws ServiceException;
	
    /**
     * 查询分页对象列表，用于后台管理页面-通用模板列表页里翻页数据变换
     * 
     * @param currentPage 当前页
     * @param pageSize 页码大小
     * @param model 对象
     * @return
     */
	List<Model> findList(Integer currentPage, Integer pageSize, Model model) throws ServiceException;
	
	/**
	 * 根据sql返回集合
	 * @param sql
	 * @param model
	 * @return
	 * @throws ServiceException
	 */
	List<Model> findList(String sql, Model model) throws ServiceException;
	
	/**
	 * 查询 分页数据+列表数据 信息，用于前台接口通用分页查询
	 * 
	 * @param currentPage 当前页
	 * @param pageSize 页码大小
	 * @param model 对象
	 * @return
	 */
	PageData<Model> findPageData(Integer currentPage, Integer pageSize, Model model) throws ServiceException;
	
	/**
	 * 根据条件查询单个对象
	 * @param model
	 * @return
	 */
	Model findOne(Model model) throws ServiceException;
	
	/**
	 * 根据条件查询单个对象
	 * @param model
	 * @return
	 */
	Model findOne(Model model, List<SQLOrderbyModel> orderbyList) throws ServiceException;
}

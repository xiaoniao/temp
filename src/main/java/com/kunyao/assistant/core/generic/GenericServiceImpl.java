package com.kunyao.assistant.core.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.sql.SQLHelper;
import com.kunyao.assistant.core.feature.sql.SQLHelper.SQLOrderbyModel;


/**
 * GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作,
 * 未实现的方法有 子类各自实现
 * 
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 * @author GeNing
 * @since 2016.04.15
 */
public abstract class GenericServiceImpl<Model, PK> implements GenericService<Model, PK> {
	
	private final static Logger logger = Logger.getLogger(GenericServiceImpl.class);

    /**
     * 定义成抽象方法,由子类实现,完成dao的注入
     *
     * @return GenericDao实现类
     */
    public abstract GenericDao<Model, PK> getDao();
    
    @SuppressWarnings("unchecked")
	public Model getModel() {
    	
    	Type type = getClass().getGenericSuperclass();
    	ParameterizedType p = (ParameterizedType)type;
    	Class<Model> entityClass = (Class<Model>) p.getActualTypeArguments()[0];
    	Model model = null;
    	try {
    		model = (Model) entityClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return model;
    }

    @Override
    public int insert(Model model) throws ServiceException {
    	// TODO Auto-generated method stub
    	try {
    		return getDao().insert(model);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new ServiceException(ResultCode.DB_ERROR);
		}
    }

    @Override
    public int update(Model model) throws ServiceException {
    	// TODO Auto-generated method stub
        try {
        	return getDao().updateByID(model);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new ServiceException(ResultCode.DB_ERROR);
		}
    }

    @Override
    public Model findByID(PK id) throws ServiceException {
    	// TODO Auto-generated method stub
    	
    	Model model = getModel();
    	
    	Method method = null;
		try {
			method = model.getClass().getMethod("setId", new Class[]{ Integer.class });
			method.setAccessible(true);
			method.invoke(model, new Object[]{ id });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Model result = null;
    	
    	try {
    		Map<String, Object> map = getDao().findByID(model);
        	result = buildModel(map);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new ServiceException(ResultCode.DB_ERROR);
		}
    	
        return result;
    }
    
    @Override
	public PageInfo findPageInfo(Integer pageSize, Model model) throws ServiceException {
		// TODO Auto-generated method stub
    	if (model == null)
			getModel();
    	
    	int allRow = 0;
    	
    	try {
    		allRow = getDao().findCountByCondition(model);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new ServiceException(ResultCode.DB_ERROR);
		}
		
		return new PageInfo(allRow, pageSize);
	}
    
    @Override
	public PageInfo findPageInfo(Integer pageSize, String sql) throws ServiceException {
    	
    	int allRow = 0;
    	
    	try {
    		allRow = getDao().findCountByConditionSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ServiceException(ResultCode.DB_ERROR);
		}
		
		return new PageInfo(allRow, pageSize);
	}
    
    @Override
	public List<Model> findList(Integer currentPage, Integer pagesize, Model model) throws ServiceException {
		// TODO Auto-generated method stub
    	if (model == null)
			getModel();
    	
		List<Model> resultList = null;
		
		List<Map<String, Object>> mapList = null;
		Integer startpos = null;
		
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		try {
			mapList = getDao().findListByCondition(startpos, pagesize, model, null);
			
			if (mapList != null && mapList.size() > 0) {
				
				resultList = new ArrayList<Model>();
				
				for (int i = 0; i < mapList.size(); i++) {
					Map<String, Object> map = mapList.get(i);
					Model result = buildModel(map);
					
					if (result != null)
						resultList.add(result);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new ServiceException(ResultCode.DB_ERROR);
		}
		
		return resultList;
	}
    
    @Override
	public List<Model> findList(String sql, Model model) throws ServiceException {
    	if (model == null)
			getModel();
    	
		List<Model> resultList = null;
		
		List<Map<String, Object>> mapList = null;
		
		try {
			mapList = getDao().findListBySql(sql);
			
			if (mapList != null && mapList.size() > 0) {
				
				resultList = new ArrayList<Model>();
				
				for (int i = 0; i < mapList.size(); i++) {
					Map<String, Object> map = mapList.get(i);
					Model result = buildModel(map);
					
					if (result != null)
						resultList.add(result);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ServiceException(ResultCode.DB_ERROR);
		}
		return resultList;
	}
	
	@Override
	public PageData<Model> findPageData(Integer currentPage, Integer pageSize, Model model) throws ServiceException {
		// TODO Auto-generated method stub	
		PageData<Model> pageData = null;
		PageInfo pageInfo = this.findPageInfo(pageSize, model);
		List<Model> modelList = this.findList(currentPage, pageSize, model);
		
		pageData = new PageData<>(modelList, pageInfo.getAllRow(), currentPage, pageSize);
		
		return pageData;
	}
	
	@Override
	public Model findOne(Model model) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String, Object> result = getDao().findOne(model, null);
		model = buildModel(result);
		return model;
	}
	
	@Override
	public Model findOne(Model model, List<SQLOrderbyModel> orderbyList) throws ServiceException {
		// TODO Auto-generated method stub
		Map<String, Object> result = getDao().findOne(model, orderbyList);
		model = buildModel(result);
		return model;
	}
    
    /**
     * 通过Map 为model赋值
     * @param model
     * @param map
     */
    @SuppressWarnings("rawtypes")
	private Model buildModel(Map<String, Object> map) {
    	
    	Model model = getModel();
    	
    	if (map == null || map.size() <= 0) 
    		return null;
    		
    	for (String key : map.keySet()) {
    		
    		String fieldName = SQLHelper.getModelFieldNameByDBFieldName(model, key);
			Field f = null;
			try {
				f = model.getClass().getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				return null;
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				return null;
			}
			
			String methodName = SQLHelper.getModelFieldValueByDBFieldName(model, key);
			Class[] argsClass = new Class[1];
			Object[] params = new Object[1];
			
			params[0] = map.get(key);
			
			if (map.get(key) instanceof Timestamp) {
				
				/*
				 * 如果当前类型是Timestamp，则先调用一次 setxxxTime() 方法，再调用 setXxxDate()
				 */
				argsClass[0] = Date.class;
				String methodName2 = methodName.replace("Time", "Date");
				
				try {
					Method method = model.getClass().getMethod(methodName2, argsClass);
					method.setAccessible(true);
					method.invoke(model, params);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(e.getMessage());
					return null;
				} 
			} else if (map.get(key) instanceof BigDecimal) {
				argsClass[0] = Double.class;
				params[0] = ((BigDecimal) map.get(key)).doubleValue();
			} else { 
				argsClass[0] = f.getType();
			}
			
			try {
				Method method = model.getClass().getMethod(methodName, argsClass);
				method.setAccessible(true);
				method.invoke(model, params);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				return null;
			} 
    	}
    	
    	return model;
    }
}

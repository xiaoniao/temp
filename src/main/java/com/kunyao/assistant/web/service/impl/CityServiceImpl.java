package com.kunyao.assistant.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.baidu.BaiduService;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.CityMapper;
import com.kunyao.assistant.web.dao.ProvinceMapper;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.ProvinceService;

@Service
public class CityServiceImpl extends GenericServiceImpl<City, Integer> implements CityService {
   
	@Resource
    private CityMapper cityMapper;
    
	@Resource
	private OrderService orderService;
	
	@Resource
	private ProvinceService provinceService;
	
	@Resource ProvinceMapper provinceMapper;
	
    public GenericDao<City, Integer> getDao() {
        return cityMapper;
    }

	@Override
	public List<Map<String, Object>> selectCity(Integer status) {
		List<Map<String, Object>> result = new ArrayList<>();
		List<City> citys = cityMapper.selectCity(status); // 按拼音首字母排序
		if (citys != null) {
			String str = "";
			// 按拼音首字母分组
			for (City city : citys) {
				String firstLetter = StringUtils.getFirstSpell(city.getName()).substring(0,1).toUpperCase();
				if (!str.equals(firstLetter)) {
					Map<String, Object> map = new HashMap<>();
					map.put("letter", firstLetter);
					map.put("citys", new ArrayList<City>());
					result.add(map);
				}
				@SuppressWarnings("unchecked")
				List<City> cityList = (List<City>)result.get(result.size() - 1).get("citys");
				cityList.add(city);
				str = firstLetter;
			}
		}
		return result;
	}

	@Override
	public List<City> findCityByMemberId(Integer memberId) {
		
		Order order = new Order();
		order.setUserId(memberId);
		List<Order> findListByCondition = null;
		List<Integer> cityIds = new ArrayList<Integer>();
		List<City> cityList = new ArrayList<City>();
		List<Integer> newCityId = new ArrayList<Integer>();
		
	    try {
			findListByCondition = orderService.findList(null, null, order);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	    
	    if (findListByCondition == null){
	    	return null;
	    }
	    
	    for (Order orderInfo : findListByCondition) {
	    	cityIds.add(orderInfo.getCityId());
		}
	    
	    for(Integer cityId : cityIds){
	        if(Collections.frequency(newCityId, cityId) < 1) newCityId.add(cityId);
	    }
	    Integer status = BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
	    if(newCityId != null && newCityId.size() > 0){
	    	for(int i = 0; i < newCityId.size(); i++){
	    		Integer cityId = newCityId.get(i);
	    		cityList.add(cityMapper.findCityById(cityId,status));
	    	}
	    }
	    
		return cityList;
	}

	@Override
	public City getLocationCity(String latLngs) {
		String cityName = BaiduService.getCityByLoc(latLngs);
		if ("市".equals(cityName.substring(cityName.length() - 1))) {
			cityName = cityName.substring(0, cityName.length() - 1);
		}
		City city = null;
		if (StringUtils.isNull(cityName))
			return null;
		else {
			City model = new City();
			model.setName(cityName);
			model.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			try {
				List<City> citys = super.findList(null, null, model);
				if (citys != null && citys.size() > 0) {
					city = citys.get(0);
					return city;
				}
			} catch (ServiceException e) {
				e.printStackTrace();
				return null;
			}
		}
		return city;
	}

	@Override
	public List<Province> selectAllInfo(Province province, Integer currentPage, Integer pagesize) {
	// TODO Auto-generated method stub
		
//		int startPos = pageUtils.countOffset(pagesize, currentPage);
		
		List<Province> provincesList = null;
		try {
			provincesList = provinceService.findList(null, null, province);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		allrow = cityMapper.selectCountByCondition(city);
				
		if (provincesList == null || provincesList.size() <= 0)
			return null;

		for (int i = 0; i < provincesList.size(); i++) {
			Integer provinceId = provincesList.get(i).getId();

			City city = new City();
			city.setProvinceId(provinceId);
			
			List<City> cityList = cityMapper.findCityByProvinceId(provinceId);
			
			if (cityList == null || cityList.size() == 0)
				continue;
			
			provincesList.get(i).setCityList(cityList);
		}
		
		return provincesList;
	}
	
	@Override
	public boolean openCityStatus(Integer cityId) {
		City city = new City();
		city.setId(cityId);
		city.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
		try {
			cityMapper.updateByID(city);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


	@Override
	public boolean closeCityStatus(Integer cityId) {
		
		City city = new City();
		city.setId(cityId);
		city.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		
		try {
			cityMapper.updateByID(city);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public City findCityById(Integer cityId, Integer status) {
		return cityMapper.findCityById( cityId,  status);
	}

	@Override
	public List<City> findListByProvinceId(Integer provinceId) {
		return cityMapper.findCityByProvinceId(provinceId);
	}

	@Override
	public City findInfo(Integer cityId) throws ServiceException {
		City city = new City();
		city.setId(cityId);
		return findOne(city);
	}

	@Override
	public Map<String, Object> selectAddressInfo(Integer cityId) {
		Map<String, Object> map = new HashMap<>();
		City city = cityMapper.findCity(cityId);
		Province province = provinceMapper.findProvinceByCityId(cityId);
		map.put("city", city);
		map.put("province", province);
		return map;
	}

	@Override
	public List<City> findCityList(Integer status) {
		return cityMapper.selectCity(status);
	}
}

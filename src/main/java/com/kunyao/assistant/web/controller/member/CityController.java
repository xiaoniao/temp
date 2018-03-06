package com.kunyao.assistant.web.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.baidu.BDLocationResult;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.feature.baidu.BaiduService;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.ProvinceService;

/**
 * 城市控制器
 * @author
 *
 */
@Controller("cityConttroller")
@RequestMapping("/mc/city")
public class CityController {
    
	@Resource
	private CityService cityService;
	
	@Resource
	private ProvinceService provinceService;
	
	/**
	 * 获取有效城市列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list/", produces = {"application/json;charset=UTF-8"})
	public Result cityList(Integer memberId) {
		Map<String, Object> city = new HashMap<String, Object>();
		Integer status = BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
		List<Map<String, Object>> cityList = cityService.selectCity(status);
		
		if(memberId != null && memberId > 0){
			List<City> orderCityList = cityService.findCityByMemberId(memberId);
			city.put("orderCityList", orderCityList);
		}
		
		city.put("cityList", cityList);
		return ResultFactory.createJsonSuccess(city);
	}
	
	/**
	 * 根据坐标地址查询所在的城市
	 * @param latLngs
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/location_city", produces = {"application/json;charset=UTF-8"})
	public Result getLocationCity(String latLngs) {
		City city = cityService.getLocationCity(latLngs);
		return ResultFactory.createJsonSuccess(city);
	}
	
	/**
	 * 转换百度坐标
	 * @param lngLats
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/bdlocations", produces = {"application/json;charset=UTF-8"})
	public Result getBDLocations(String latLngs) {
		BDLocationResult bdLocation = BaiduService.getBDLocation(latLngs);
		return ResultFactory.createJsonSuccess(bdLocation);
	}
	
	/**
	 * 查询所有省份
	 */
	@ResponseBody
	@RequestMapping(value = "/province_list", produces = { "application/json;charset=UTF-8" })
	public Result getProvinces() {
		return ResultFactory.createJsonSuccess(provinceService.findAll());
	}
	
	/**
	 * 查询所有城市
	 * @param provinceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/city_list", produces = { "application/json;charset=UTF-8" })
	public Result getCitys(@RequestParam Integer provinceId) {
		return ResultFactory.createJsonSuccess(cityService.findListByProvinceId(provinceId));
	}

	/**
	 * 根据城市id查省市信息
	 */
	@ResponseBody
	@RequestMapping(value = "/address", produces = { "application/json;charset=UTF-8" })
	public Result getAddress(Integer cityId) {
		return ResultFactory.createJsonSuccess(cityService.selectAddressInfo(cityId));
	}
}

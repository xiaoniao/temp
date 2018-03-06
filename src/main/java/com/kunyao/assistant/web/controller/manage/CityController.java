package com.kunyao.assistant.web.controller.manage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.CrossInfoService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.ProvinceService;

@Controller
@RequestMapping("/um/city")
public class CityController {
	
	@Resource
	private CityService cityService;
	
	@Resource
	private ProvinceService provinceService;
	
	@Resource
	private CrossInfoService crossInfoService;
	
	@Resource
	private OrderService orderService;
	
	private final String LIST_PAGE_URL = "um/city-list";
	
	@RequestMapping(value = "/toList")
	public String toListPage(Model model, Integer currentPage) {
		if(currentPage == null){
			currentPage = 1;
		}
		Integer pagesize = 73;
		Province province = new Province();
		
		List<Province> provinceList = cityService.selectAllInfo(province, currentPage, pagesize);
		
		model.addAttribute("provinceList", provinceList);
		return LIST_PAGE_URL;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/open", produces = { "application/json;charset=UTF-8" })
	public Result openCityStatus(@RequestParam Integer cityId) {
		
		boolean bool = cityService.openCityStatus(cityId);
		if (bool)
			return ResultFactory.createSuccess();
		else
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/close", produces = { "application/json;charset=UTF-8" })
	public Result closeCityStatus(@RequestParam Integer cityId) {
		
		Integer crossInfoCount = crossInfoService.findUserByCityIdCount(cityId);    //获取到正在上架的金鹰数量
		Order order = new Order();
		order.setCityId(cityId);
		order.setStatus(OrderEnum.Status.SERVING.getValue());
		try {
			List<Order> orderList = orderService.findList(null, null, order);
			if(crossInfoCount > 0 || (orderList != null && orderList.size() > 0)){
				return ResultFactory.createError(ResultCode.CITY_OFF_STATE);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		boolean bool = cityService.closeCityStatus(cityId);
		if (bool)
			return ResultFactory.createSuccess();
		else
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
	}
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public Result cityList() {
		List<City> citys = cityService.findCityList(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		return ResultFactory.createJsonSuccess(citys);
	}
   
	
}

package com.kunyao.assistant.web.controller.manage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.entity.Plupload;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.CrossTimes;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.FileUtils;
import com.kunyao.assistant.core.utils.PluploadUtil;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.CrossCommentService;
import com.kunyao.assistant.web.service.CrossInfoService;
import com.kunyao.assistant.web.service.CrossTimesService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.ProvinceService;
import com.kunyao.assistant.web.service.UserService;

@Controller
@RequestMapping("/um/crossInfo")
public class CrossInfoController {

	@Resource
	private CrossInfoService crossInfoService;

	@Resource
	private CityService cityService;

	@Resource
	private ProvinceService provinceService;
	
	@Resource
	private CrossTimesService crossTimesService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private CrossCommentService crossCommentService;
	
	private final String ADD_PAGE_URL = "um/cross-add";
	private final String LIST_PAGE_URL = "um/cross-list";
	private final String EDIT_PAGE_URL = "um/cross-edit";
	private final String DETAILS_PAGE_URL = "um/cross-detail";
	private final String CROSS_TIME_URL = "um/cross-time";
	
	private final String uploadPath = "/picture/crossinfo/";

	@RequestMapping(value = "/toAdd")
	public String toAddPage(Model model) {
		Province province = new Province();
		province.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		List<Province> provinceList = null;
		try {
			provinceList = provinceService.findList(null, null, province);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("provinceList", provinceList);

		return ADD_PAGE_URL;
	}

	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		Province province = new Province();
		province.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		List<Province> provinceList = null;
		try {
			provinceList = provinceService.findList(null, null, province);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("provinceList", provinceList);
		
		return LIST_PAGE_URL;
	}

	@RequestMapping(value = "/toDetail")
	public String toDetailPage(@RequestParam("id") Integer certId, Model model) {
		
		CrossInfo cross = crossInfoService.findCrossById(certId);
		int dayCount = 0;
		Order order = new Order();
		order.setCrossUserId(cross.getUserId());
		order.setStatus(OrderEnum.Status.FINISH.getValue());
		try {
			List<Order> orderList = orderService.findList(null, null, order);
			if(orderList != null){
				for (Order orderDate : orderList) {
					try {
						dayCount += DateUtils.getBetweenDay(orderDate.getStartDate(), orderDate.getEndDate());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				model.addAttribute("orderCount", orderList.size());
				model.addAttribute("dayCount", dayCount);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		Map<String, Double> crossComentStarAvg = crossCommentService.findStarAvg(cross.getUserId());
		
		String[] bannersList = cross.getBanners().split(";");
		
		model.addAttribute("cross", cross);
		model.addAttribute("bannersList", bannersList);
		model.addAttribute("comprehensivecSore", crossComentStarAvg.get("star").isNaN()?0:crossComentStarAvg.get("star"));        // 综合评分
		model.addAttribute("ethos", crossComentStarAvg.get("star1").isNaN()?0:crossComentStarAvg.get("star1"));                    // 精神气质
		model.addAttribute("abilityAccomplishment", crossComentStarAvg.get("star2").isNaN()?0:crossComentStarAvg.get("star2"));    // 能力素养
		model.addAttribute("serviceAttitude", crossComentStarAvg.get("star3").isNaN()?0:crossComentStarAvg.get("star3"));          // 服务态度
		model.addAttribute("behaviorEtiquette", crossComentStarAvg.get("star4").isNaN()?0:crossComentStarAvg.get("star4"));	     // 行为礼仪
		return DETAILS_PAGE_URL;
	}
	
	@RequestMapping(value = "/toCrossTime")
	public String toCrossTime(@RequestParam("id") Integer id, Model model) {
		
		List<CrossTimes> crossTimesLists = crossTimesService.selectCrossList(id);
		
		model.addAttribute("crossTimesLists", crossTimesLists);
		return CROSS_TIME_URL;
	}
	
	@RequestMapping(value = "/toEdit")
	public String toEditPage(@RequestParam("id") Integer certId, Model model) {
		
		CrossInfo cross = crossInfoService.findCrossById(certId);
		
		Province province = new Province();
		province.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
		City city = new City();
		city.setProvinceId(cross.getProvinceId());
		city.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
		City nativePlaceCity = new City();
		nativePlaceCity.setProvinceId(cross.getNativePlaceProvinceId());
		
		String[] bannersList = cross.getBanners().split(";");
		try {
			// 查询出所有城市
			List<Province> provinceList = provinceService.findList(null, null, province);
			List<City> cityList =  cityService.findList(null, null, city);
			List<City> nativePlaceCityList =  cityService.findList(null, null, nativePlaceCity);
			
			// 根据籍贯和服务城市，查询出相应的城市数据
			Province newProvinceList = provinceService.findProvinceByIdAndStatus(cross.getProvinceId(),BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			City newCityList = cityService.findCityById(cross.getCityId(), BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			Province newNativePlaceProvinceList = provinceService.findByID(cross.getNativePlaceProvinceId());
			City newNativePlaceCityList = cityService.findByID(cross.getNativePlaceCityId());
			
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cityList", cityList);
			model.addAttribute("nativePlaceCityList", nativePlaceCityList);
			model.addAttribute("newProvinceList", newProvinceList);
			model.addAttribute("newCityList", newCityList);
			model.addAttribute("newNativePlaceProvinceList", newNativePlaceProvinceList);
			model.addAttribute("newNativePlaceCityList", newNativePlaceCityList);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("bannersList", bannersList);
		model.addAttribute("cross", cross);
		return EDIT_PAGE_URL;
	}

	@ResponseBody
	@RequestMapping(value = "/lscount", produces = { "application/json;charset=UTF-8" })
	public Result CrossInfoListCount(Integer pageSize, CrossInfo crossInfo) {
		PageInfo page = null;
		try {
			page = crossInfoService.findPageInfo(pageSize, crossInfo);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
	public Result CrossInfoList(Integer currentPage, Integer pageSize, CrossInfo crossInfo) {

		List<CrossInfo> crossInfoList = null;
		try {
			crossInfoList = crossInfoService.selectCrossInfoList(currentPage, pageSize, crossInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResultFactory.createJsonSuccess(crossInfoList);
	}

	@ResponseBody
	@RequestMapping(value = "/add", produces = { "application/json;charset=UTF-8" })
	public Result addCrossInfo(@Valid CrossInfo crossInfo, BindingResult bresult, 
			@RequestParam(value="imageFile", required=false) MultipartFile imageFile ,HttpServletRequest request) throws ServiceException{
		Result result = null;
		
		if (bresult.hasErrors()) {
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		
		if (StringUtils.isNull(crossInfo.getBanners())) {
			return ResultFactory.createError(ResultCode.LOAD_PICTURE_ERROR);
		}
		
		String imagePath = FileUtils.springMvcUploadFile(imageFile, uploadPath);
		
		if (StringUtils.isNull(imagePath)) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		crossInfo.setPic(imagePath);
		
		int i = crossInfoService.createCrossInfo(crossInfo);
		
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
		    
		return result;
	}

	// 多图上传
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(Plupload plupload, HttpServletRequest request, HttpServletResponse response, String directory) {
		String path = null;
		if (!StringUtils.isNull(directory)) {
			path = "/picture/" + directory + "/";
		} else {
			path = uploadPath;
		}
		
		plupload.setRequest(request);
		// 文件存储路径
		File dir = new File(plupload.getRequest().getSession().getServletContext().getRealPath("/") + path);
		try {
			// 上传文件
			PluploadUtil.upload(plupload, dir);
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String imagePath = path + plupload.getImagePath();
		return imagePath;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteBanners", produces = { "application/json;charset=UTF-8" })
	public Result deleteBanners(@RequestParam Integer crossId, @RequestParam String banners) {
		Result result = null; 
		int i = crossInfoService.deleteBanners(crossId, banners);
		
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		case -1:
			result = ResultFactory.createError(ResultCode.DELETE_PICTURE_ERROR);
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit", produces = { "application/json;charset=UTF-8" })
	public Result editHotel(@Valid CrossInfo crossInfo, BindingResult bresult, @RequestParam(value="imageFile", required=false) MultipartFile imageFile,
			HttpServletRequest request) throws ServiceException{
		Result result = null; 
		
		if (bresult.hasErrors()) {
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}
		
		if (imageFile != null && !StringUtils.isNull(imageFile.getName())) {
			String imagePath = FileUtils.springMvcUploadFile(imageFile, uploadPath);
			if (StringUtils.isNull(imagePath)) {
				return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
			}
			crossInfo.setPic(imagePath);
		}
		
		int i = crossInfoService.updateCrossInfo(crossInfo);
		
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
		
		return result;
	}
	 

	@ResponseBody
	@RequestMapping(value = "/cityList", produces = { "application/json;charset=UTF-8" })
	public Result countyList(@RequestParam Integer provinceId) {

		City city = new City();
		city.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		city.setProvinceId(provinceId);
		List<City> countyList = null;
		try {
			countyList = cityService.findList(null, null, city);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createJsonSuccess(countyList);
	}

	@ResponseBody
	@RequestMapping(value = "/nativePlaceCityList", produces = { "application/json;charset=UTF-8" })
	public Result nativePlaceCityList(@RequestParam Integer nativePlaceProvinceId) {

		City city = new City();
		city.setProvinceId(nativePlaceProvinceId);
		List<City> countyList = null;

		try {
			countyList = cityService.findList(null, null, city);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createJsonSuccess(countyList);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/open", produces = { "application/json;charset=UTF-8" })
	public Result openRoomTypeState(@RequestParam Integer crossTimesId) {
		
		boolean bool = crossTimesService.openCrossTimesDate(crossTimesId);
		if (bool)
			return ResultFactory.createSuccess();
		else
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/close", produces = { "application/json;charset=UTF-8" })
	public Result closeRoomTypeState(@RequestParam Integer crossTimesId) {
		
		boolean bool = crossTimesService.closeCrossTimesDate(crossTimesId);
		if (bool)
			return ResultFactory.createSuccess();
		else
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePassword", produces = { "application/json;charset=UTF-8" })
	public Result updatePassword(@RequestParam Integer userId, @RequestParam String passWord) {
		
		String afterSecretPassword = StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + passWord);
		User user = new User();
		user.setId(userId);
		user.setPassword(afterSecretPassword);
		try {
			userService.update(user);
		} catch (Exception e) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
}

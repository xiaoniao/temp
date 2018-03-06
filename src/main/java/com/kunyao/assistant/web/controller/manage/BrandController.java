package com.kunyao.assistant.web.controller.manage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Brand;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.core.utils.FileUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.BrandService;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.ProvinceService;

@Controller
@RequestMapping("/um/brand")
public class BrandController {
	
	@Resource
	private BrandService brandService;
	
	@Resource
	private ProvinceService provinceService;
	
	@Resource
	private CityService cityService;
	
	private final String ADD_PAGE_URL = "um/brand-add";
	private final String LIST_PAGE_URL = "um/brand-list";
	private final String EDIT_PAGE_URL = "um/brand-edit";
	private final String TO_PIC_URL =  "um/brand-pic";
	private final String uploadPath = "/picture/brand/";
	
	
	@RequestMapping(value = "/toAdd")
	public String toAddPage(Model model) {
		List<Province> provinceList = provinceService.findAll();
		model.addAttribute("provinceList", provinceList);
		return ADD_PAGE_URL;
	}

	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		List<Province> provinceList = provinceService.findAll(); 
		
		model.addAttribute("provinceList", provinceList);
		return LIST_PAGE_URL;
	}
	
	@RequestMapping(value = "/toPic")
	public String toPic(Model model, @RequestParam("id") Integer id) {
		try {
			Brand brand = brandService.findByID(id);
			model.addAttribute("brand", brand);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TO_PIC_URL;
	}
	
	@RequestMapping(value="/toEdit")
	public String toEditPage(@RequestParam("id") Integer certId, Model model) {
		try {
			Brand brand = brandService.findByID(certId);
			List<Province>  provinceList = provinceService.findAll();
			
			City city = new City();
			city.setProvinceId(brand.getProvinceId());
			List<City> cityList = cityService.findList(null, null, city);
			
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cityList", cityList);
			model.addAttribute("brand", brand);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return EDIT_PAGE_URL;
	}
	
	@ResponseBody
	@RequestMapping(value="/lscount", produces = {"application/json;charset=UTF-8"})
	public Result brandListCount(Integer pageSize, Brand brand) {
		PageInfo page = null;
		try {
			page = brandService.findPageInfo(pageSize, brand);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return ResultFactory.createJsonSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping(value="/list", produces = {"application/json;charset=UTF-8"})
	public Result brandList(Integer currentPage, Integer pageSize, Brand brand) {
		
		List<Brand> travelDescList = null;
		
		try {
			travelDescList = brandService.selectBrandList(currentPage, pageSize, brand);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createJsonSuccess(travelDescList);
	}
	
	@ResponseBody
	@RequestMapping(value="/add", produces = {"application/json;charset=UTF-8"})
	public Result addbrand(@Valid Brand brand, BindingResult bresult,
			@RequestParam(value="imageFile", required=false) MultipartFile imageFile,
			HttpServletRequest request) {
		
		if (bresult.hasErrors()) {
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
        }
		
		String imagePath = FileUtils.springMvcUploadFile(imageFile, uploadPath);
		
		if (StringUtils.isNull(imagePath)) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		brand.setImg(imagePath);
		brand.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		brand.setSort(1);
		try {
			brandService.insert(brand);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value="/edit", produces = {"application/json;charset=UTF-8"})
	public Result editbrand(@Valid Brand brand, BindingResult bresult,
			@RequestParam(value="imageFile", required=false) MultipartFile imageFile,
			HttpServletRequest request) {
		
		if (bresult.hasErrors()) {
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
        }
		
		if (imageFile != null && !StringUtils.isNull(imageFile.getName())) {
			String imagePath = FileUtils.springMvcUploadFile(imageFile, uploadPath);
			if (StringUtils.isNull(imagePath)) {
				return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
			}
			brand.setImg(imagePath);
		}
		
		try {
			brandService.update(brand);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value = "/provinceList", produces = { "application/json;charset=UTF-8" })
	public Result provinceList() {
		List<Province> provinceList = provinceService.findAll(); 
		return ResultFactory.createJsonSuccess(provinceList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/cityList", produces = { "application/json;charset=UTF-8" })
	public Result countyList(@RequestParam Integer provinceId) {

		City city = new City();
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

}

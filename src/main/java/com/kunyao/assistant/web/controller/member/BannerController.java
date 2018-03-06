package com.kunyao.assistant.web.controller.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Banner;
import com.kunyao.assistant.core.model.Brand;
import com.kunyao.assistant.web.service.BannerService;
import com.kunyao.assistant.web.service.BrandService;

@RequestMapping("/mc/banner")
@Controller
@ResponseBody
public class BannerController {

	@Resource
	private BannerService bannerService;
	
	@Resource
	private BrandService brandService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	public Result list() throws ServiceException {
		Banner model = new Banner();
		model.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		List<Banner> banners = bannerService.findList(null, null, model);
		return ResultFactory.createJsonSuccess(banners);
	}
	
	/**
	 * 发现列表
	 */
	@RequestMapping(value = "discovery")
	public Result discovery(Integer cityId) throws ServiceException {
		Brand brand = new Brand();
		brand.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		brand.setCityId(cityId);
		return ResultFactory.createJsonSuccess(brandService.findList(null, null, brand));
	}
}

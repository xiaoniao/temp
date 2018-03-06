package com.kunyao.assistant.web.controller.manage;

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
import com.kunyao.assistant.core.model.Banner;
import com.kunyao.assistant.core.utils.FileUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.BannerService;

@Controller
@RequestMapping("/um/banner")
public class MBannberController {

	@Resource
	private BannerService bannerService;

	private final String ADD_PAGE_URL = "um/banner-add";
	private final String LIST_PAGE_URL = "um/banner-list";
	private final String EDIT_PAGE_URL = "um/banner-edit";
	private final String uploadPath = "/picture/banner/";

	@RequestMapping(value = "/toAdd")
	public String toAddPage(Model model) {
		return ADD_PAGE_URL;
	}

	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		return LIST_PAGE_URL;
	}

	@RequestMapping(value = "/toEdit")
	public String toEditPage(@RequestParam("id") Integer certId, Model model) {
		try {
			Banner banner = bannerService.findByID(certId);
			model.addAttribute("banner", banner);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return EDIT_PAGE_URL;
	}

	@ResponseBody
	@RequestMapping(value = "/lscount")
	public Result brandListCount(Integer pageSize, Banner banner) {
		PageInfo page = null;
		try {
			page = bannerService.findPageInfo(pageSize, banner);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Result brandList(Integer currentPage, Integer pageSize, Banner brand) {
		try {
			return ResultFactory.createJsonSuccess(bannerService.selectBannerList(currentPage, pageSize, brand));
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public Result addbrand(@Valid Banner banner, BindingResult bresult,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile, HttpServletRequest request) {
		if (bresult.hasErrors()) {
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}

		String imagePath = FileUtils.springMvcUploadFile(imageFile, uploadPath);
		if (StringUtils.isNull(imagePath)) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		banner.setImage(imagePath);
		banner.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		try {
			bannerService.insert(banner);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		return ResultFactory.createSuccess();
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public Result editbrand(@Valid Banner banner, BindingResult bresult,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile, HttpServletRequest request) {
		if (bresult.hasErrors()) {
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
		}

		if (imageFile != null && !StringUtils.isNull(imageFile.getName())) {
			String imagePath = FileUtils.springMvcUploadFile(imageFile, uploadPath);
			if (StringUtils.isNull(imagePath)) {
				return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
			}
			banner.setImage(imagePath);
		}

		try {
			bannerService.update(banner);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createSuccess();
	}
}

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
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.Banner;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.core.model.ResourceType;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.web.service.ProvinceService;
import com.kunyao.assistant.web.service.ResourceTypeService;
import com.kunyao.assistant.web.service.UserRoleService;
import com.kunyao.assistant.web.service.UserService;

@Controller
@RequestMapping("/um/privilege")
public class PrivilegeController {

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private UserService userService;
	
	@Resource
	private ProvinceService provinceService;
	
	@Resource
	private ResourceTypeService resourceTypeService;

	private final String ADD_PAGE_URL = "um/privilege-add";
	private final String LIST_PAGE_URL = "um/privilege-list";
	private final String EDIT_PAGE_URL = "um/privilege-edit";

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
		List<ResourceType> resourceTypes = null;
		try {
			ResourceType resourceType = new ResourceType();
			resourceType.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			resourceTypes = resourceTypeService.findList(null, null, resourceType);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("resourceTypes", resourceTypes);
		return ADD_PAGE_URL;
	}

	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		return LIST_PAGE_URL;
	}

	@RequestMapping(value = "/toEdit")
	public String toEditPage(@RequestParam("id") Integer certId, Model model) {
		try {
			User user = userService.findByID(certId);
			model.addAttribute("user", user);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return EDIT_PAGE_URL;
	}

	@ResponseBody
	@RequestMapping(value = "/lscount")
	public Result brandListCount(Integer pageSize, Banner banner) {
		PageInfo page = new PageInfo(pageSize, pageSize);
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Result brandList(Integer currentPage, Integer pageSize, Banner brand) {
		try {
			return ResultFactory.createJsonSuccess(userRoleService.findSubManage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}
	
	/**
	 * 添加普通管理员
	 * 
	 * 添加资源管理人-城市
	 * 添加资源管理人-全国
	 * 
	 * 添加资源开发人-实习
	 * 添加资源开发人-全职
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public Result addbrand(String type, String username, Integer provinceId, Integer cityId, String resourceTypeIds, String encryptionPassword) throws ServiceException {
		return ResultFactory.createJsonSuccess(userService.addManager(type, username, provinceId, cityId, resourceTypeIds, encryptionPassword));
	}

	/**
	 * 禁用/开启帐户
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public Result editbrand(Integer userId, Integer status) throws ServiceException{
		if (status.intValue() == BaseEnum.Status.BASE_STATUS_ENABLE.getValue()) {
			userService.enable(userId);
		} else {
			userService.disable(userId);
		}
		return ResultFactory.createSuccess();
	}
}

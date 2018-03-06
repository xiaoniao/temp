
package com.kunyao.assistant.web.controller.manage;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.ResourceEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.ManagerInfo;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.core.model.ResourceSuggest;
import com.kunyao.assistant.core.model.ResourceType;
import com.kunyao.assistant.core.model.Role;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.utils.SessionUtils;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.ManagerService;
import com.kunyao.assistant.web.service.ProvinceService;
import com.kunyao.assistant.web.service.ResourceService;
import com.kunyao.assistant.web.service.ResourceSuggestService;
import com.kunyao.assistant.web.service.ResourceTypeService;
import com.kunyao.assistant.web.service.RoleService;

@Controller
@RequestMapping("/um/resourceSuggest")
public class MResourceSuggestController {

	@javax.annotation.Resource
	private ResourceTypeService resourceTypeService;

	@javax.annotation.Resource
	private ResourceSuggestService resourceSuggestService;

	@javax.annotation.Resource
	private ProvinceService provinceService;
	
	@javax.annotation.Resource
	private ResourceService resourceService;

	@javax.annotation.Resource
	private CityService cityService;
	
	@javax.annotation.Resource
	private ManagerService managerService;
	
	@javax.annotation.Resource
	private RoleService roleService;

	private final String LIST_PAGE_URL = "um/resource-suggest-list"; // 资源审核
	private final String EDIT_PAGE_URL = "um/resource-suggest-info"; // 资源审核详情

	/**
	 * 省份和资源分类
	 */
	private void addAttribute(Model model) {
		List<Province> provinceList = provinceService.findAll();
		ResourceType resourceType = new ResourceType();
		resourceType.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		List<ResourceType> resourceTypes = null;
		try {
			resourceTypes = resourceTypeService.findList(null, null, resourceType);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("resourceTypes", resourceTypes);
	}

	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		// 是否不显示省份城市搜索
		User user = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
		List<Role> roles = roleService.findRolesByUserId(user.getId());
		model.addAttribute("hidenCity", 0);
		if (roles != null) {
			for (Role role : roles) {
				if (Constant.ROLE_SIGN_RES_MANAGE_CITY.equals(role.getSign())) {
					model.addAttribute("hidenCity", 1);
					break;
				}
			}
		}
		addAttribute(model);
		return LIST_PAGE_URL;
	}

	@RequestMapping(value = "/toEdit")
	public String toEditPage(@RequestParam("id") Integer certId, Model model) {
		try {
			ResourceSuggest resourceSuggest = resourceSuggestService.findByID(certId);
			City city = cityService.findByID(resourceSuggest.getCityId());
			Province province = provinceService.findByID(city.getProvinceId());
			ResourceType resourceType = resourceTypeService.findByID(resourceSuggest.getTypeId());
			resourceSuggest.setProvinceName(province.getName());
			resourceSuggest.setCityName(city.getName());
			resourceSuggest.setTypeName(resourceType.getName());
			resourceSuggest.setProvinceAndcityName(province.getName() + " " + city.getName());
			
			ManagerInfo managerInfo = new ManagerInfo();
			managerInfo.setUserId(resourceSuggest.getSubmitUserId());
			ManagerInfo resultManagerInfo = managerService.findOne(managerInfo);
			resourceSuggest.setSubmitUserName(resultManagerInfo.getName());
			
			if (resourceSuggest.getImages() != null) {
				resourceSuggest.setImageList(resourceSuggest.getImages().split(","));
			}
			model.addAttribute("resourceSuggest", resourceSuggest);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return EDIT_PAGE_URL;
	}
	
	private void appendSearchCondition(ResourceSuggest resourceSuggest) {
		User user = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
		ManagerInfo managerInfo = managerService.findByUserId(user.getId());
		if (managerInfo.getProvinceId() != null) {
			resourceSuggest.setProvinceId(managerInfo.getProvinceId());
		}
		if (managerInfo.getCityId() != null) {
			resourceSuggest.setCityId(managerInfo.getCityId());				
		}
	}

	@ResponseBody
	@RequestMapping(value = "/lscount")
	public Result resourceListCount(Integer pageSize, ResourceSuggest resourceSuggest) {
		appendSearchCondition(resourceSuggest);
		PageInfo page = resourceSuggestService.listCount(pageSize, resourceSuggest);
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Result resourceList(Integer currentPage, Integer pageSize, ResourceSuggest resourceSuggest) {
		try {
			appendSearchCondition(resourceSuggest);
			List<ResourceSuggest> resourceSuggests = resourceSuggestService.list(currentPage, pageSize, resourceSuggest);
			if (resourceSuggests != null) {
				for (ResourceSuggest entity : resourceSuggests) {
					City city = cityService.findByID(entity.getCityId());
					Province province = provinceService.findByID(city.getProvinceId());
					ResourceType resourceType = resourceTypeService.findByID(entity.getTypeId());
					entity.setProvinceName(province.getName());
					entity.setCityName(city.getName());
					entity.setTypeName(resourceType.getName());
					entity.setProvinceAndcityName(province.getName() + " " + city.getName());
					
					ManagerInfo managerInfo = new ManagerInfo();
					managerInfo.setUserId(resourceSuggest.getSubmitUserId());
					ManagerInfo resultManagerInfo = managerService.findOne(managerInfo);
					entity.setSubmitUserName(resultManagerInfo.getName());
				}
			}
			return ResultFactory.createJsonSuccess(resourceSuggests);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}

	/**
	 * 审核资源
	 */
	@ResponseBody
	@RequestMapping(value = "/operate")
	public Result editresource(Integer id, Integer checkStatus) {
		try {
			ResourceSuggest resourceSuggest = resourceSuggestService.findByID(id);
			resourceSuggest.setCheckStatus(checkStatus);
			resourceSuggestService.update(resourceSuggest);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 我的资源
	 */
	@ResponseBody
	@RequestMapping(value = "/myList")
	public Result myResourceList(Integer currentPage, Integer pageSize, ResourceSuggest resourceSuggest) {
		User user = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
		if (user == null) return ResultFactory.createError(ResultCode.NO_LOGIN);
		resourceSuggest.setSubmitUserId(user.getId());
		return resourceList(currentPage, pageSize, resourceSuggest);
	}
	
	/**
	 * 资源开发人只能添加他绑定的城市
	 */
	private void setPermissionCondition(User user, ResourceSuggest resourceSuggest) {
		ManagerInfo managerInfo = managerService.findByUserId(user.getId());
		if (managerInfo != null) {
			if (managerInfo.getProvinceId() != null) {
				resourceSuggest.setProvinceId(managerInfo.getProvinceId());
			}
			if (managerInfo.getCityId() != null) {
				resourceSuggest.setCityId(managerInfo.getCityId());
			}
		}
	}
	
	/**
	 * 提交资源
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public Result addresource(ResourceSuggest resourceSuggest) {
		try {
			User user = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
			resourceSuggest.setSubmitUserId(user.getId());
			resourceSuggest.setCheckStatus(ResourceEnum.CheckStatus.WAIT.getValue());
			resourceSuggest.setCreateTime(new Date());
			resourceSuggest.setProvinceId(provinceService.findByCityId(resourceSuggest.getCityId()).getId());
			setPermissionCondition(user, resourceSuggest);
			resourceSuggestService.insert(resourceSuggest);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 提交资源更新
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Result updateResource(ResourceSuggest resourceSuggest) {
		try {
			User user = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
			resourceSuggest.setTypeId(resourceService.findByID(resourceSuggest.getResourceId()).getTypeId());
			resourceSuggest.setSubmitUserId(user.getId());
			resourceSuggest.setCheckStatus(ResourceEnum.CheckStatus.WAIT.getValue());
			resourceSuggest.setCreateTime(new Date());
			resourceSuggest.setProvinceId(provinceService.findByCityId(resourceSuggest.getCityId()).getId());
			setPermissionCondition(user, resourceSuggest);
			resourceSuggestService.insert(resourceSuggest);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		return ResultFactory.createSuccess();
	}
}

package com.kunyao.assistant.web.controller.manage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.ManagerInfo;
import com.kunyao.assistant.core.model.Province;
import com.kunyao.assistant.core.model.Resource;
import com.kunyao.assistant.core.model.ResourceType;
import com.kunyao.assistant.core.model.Role;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.utils.SessionUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.ManagerService;
import com.kunyao.assistant.web.service.ProvinceService;
import com.kunyao.assistant.web.service.ResourceService;
import com.kunyao.assistant.web.service.ResourceTypeService;
import com.kunyao.assistant.web.service.RoleService;

@Controller
@RequestMapping("/um/resource")
public class MResourceController {

	@javax.annotation.Resource
	private ResourceTypeService resourceTypeService;
	
	@javax.annotation.Resource
	private ResourceService resourceService;
	
	@javax.annotation.Resource
	private ProvinceService provinceService;
	
	@javax.annotation.Resource
	private CityService cityService;
	
	@javax.annotation.Resource
	private ManagerService managerService;
	
	@javax.annotation.Resource
	private RoleService roleService;
	
	private final String ADD_PAGE_URL = "um/resource-add"; // 添加资源
	private final String LIST_PAGE_URL = "um/resource-list"; // 资源列表
	private final String EDIT_PAGE_URL = "um/resource-edit"; // 编辑资源

	/**
	 * 页面添加省份和资源分类
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
	
	@RequestMapping(value = "/toAdd")
	public String toAddPage(Model model) {
		addAttribute(model);
		return ADD_PAGE_URL;
	}

	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		// 是否显示省份和城市搜索
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
		addAttribute(model);
		try {
			Resource resource = resourceService.findByID(certId);
			if(!StringUtils.isNull(resource.getImages()) && !resource.getImages().equals("empty")) {
				resource.setImageList(Arrays.asList(resource.getImages().split(",")));
			}
			model.addAttribute("resource", resource);
			List<City> cityList = cityService.findListByProvinceId(resource.getProvinceId());
			model.addAttribute("cityList", cityList);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return EDIT_PAGE_URL;
	}

	// 全国资源管理人 和 城市资源管理人 权限不一样
	private void addPermissionCondition(Resource resource) {
		User user = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
		ManagerInfo managerInfo = managerService.findByUserId(user.getId());
		if (managerInfo.getProvinceId() != null) {
			resource.setProvinceId(managerInfo.getProvinceId());
		}
		if (managerInfo.getCityId() != null) {
			resource.setCityId(managerInfo.getCityId());				
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/lscount")
	public Result resourceListCount(Integer pageSize, Resource resource) {
		addPermissionCondition(resource);
		
		PageInfo page = resourceService.listCount(pageSize, resource);
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Result resourceList(Integer currentPage, Integer pageSize, Resource resource) {
		try {
			addPermissionCondition(resource);

			List<Resource> resources = resourceService.list(currentPage, pageSize, resource);
			if (resources != null) {
				for (Resource entity : resources) {
					City city = cityService.findByID(entity.getCityId());
					Province province = provinceService.findByID(city.getProvinceId());
					ResourceType resourceType = resourceTypeService.findByID(entity.getTypeId());
					entity.setProvinceName(province.getName());
					entity.setCityName(city.getName());
					entity.setTypeName(resourceType.getName());
					entity.setProvinceAndcityName(province.getName() + " " + city.getName());
				}
			}
			return ResultFactory.createJsonSuccess(resources);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/page")
	public Result resourcePage(Integer currentPage, Integer pageSize, Resource resource) {
		try {
			resource.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			PageInfo page = resourceService.listCount(pageSize, resource);
			List<Resource> modelList = resourceService.list(currentPage, pageSize, resource);
			PageData<Resource> resources = new PageData<>(modelList, page.getAllRow(), currentPage, pageSize);
			if (resources != null && resources.getList() != null) {
				for (Resource entity : resources.getList()) {
					City city = cityService.findByID(entity.getCityId());
					Province province = provinceService.findByID(city.getProvinceId());
					ResourceType resourceType = resourceTypeService.findByID(entity.getTypeId());
					entity.setProvinceName(province.getName());
					entity.setCityName(city.getName());
					entity.setTypeName(resourceType.getName());
					entity.setProvinceAndcityName(province.getName() + " " + city.getName());
				}
			}
			return ResultFactory.createJsonSuccess(resources);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/info")
	public Result resourceInfo(Integer id) {
		try {
			Resource resource = resourceService.findByID(id);
			return ResultFactory.createJsonSuccess(resource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/add")
	public Result addresource(@Valid Resource resource) {
		try {
			resource.setCreateTime(new Date());
			resource.setUpdateTime(new Date());
			resource.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			resourceService.insert(resource);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createSuccess();
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public Result editresource(@Valid Resource resource) {
		try {
			resource.setUpdateTime(new Date());
			resourceService.update(resource);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		return ResultFactory.createSuccess();
	}
	
	@RequestMapping("/resource_list")
	public String listPage() {
		return "resourceModel/resource-list";
	}
	
	@RequestMapping("/resource_detail")
	public String infoPage(Integer resourceId) {
		return "resourceModel/resource-detail.jsp?id=" + resourceId;
	}
	
	@RequestMapping("/my_commit")
	public String commitPage() {
		return "resourceModel/my-commit";
	}
	
	@RequestMapping("/search_list")
	public String searchPage() {
		return "resourceModel/search-list";
	}
	
	@RequestMapping("/resource_push")
	public String pushPage() {
		return "resourceModel/resource-push";
	}
}

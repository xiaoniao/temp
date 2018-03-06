package com.kunyao.assistant.web.controller.manage;

import java.util.ArrayList;
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
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.ManagerInfo;
import com.kunyao.assistant.core.model.Resource;
import com.kunyao.assistant.core.model.ResourceType;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.utils.ListUtils;
import com.kunyao.assistant.core.utils.SessionUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.ManagerService;
import com.kunyao.assistant.web.service.ResourceService;
import com.kunyao.assistant.web.service.ResourceTypeService;

@Controller
@RequestMapping("/um/resourceType")
public class MResourceTypeController {

	@javax.annotation.Resource
	private ResourceTypeService resourceTypeService;

	@javax.annotation.Resource
	private ResourceService resourceService;

	@javax.annotation.Resource
	private ManagerService managerService;

	private final String ADD_PAGE_URL = "um/resource-type-add";
	private final String LIST_PAGE_URL = "um/resource-type-list";
	private final String EDIT_PAGE_URL = "um/resource-type-edit";

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
			ResourceType resourceType = resourceTypeService.findByID(certId);
			model.addAttribute("resourceType", resourceType);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return EDIT_PAGE_URL;
	}

	@ResponseBody
	@RequestMapping(value = "/lscount")
	public Result brandListCount(Integer pageSize, ResourceType resourceType) {
		PageInfo page = null;
		try {
			page = resourceTypeService.findPageInfo(pageSize, resourceType);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping(value = "/type_list")
	public Result typeList(Integer currentPage, Integer pageSize, ResourceType resourceType) {
		try {
			List<ResourceType> resourceTypes = new ArrayList<>();
			resourceTypes = resourceTypeService.findList(currentPage, pageSize, resourceType);
			new ListUtils<ResourceType>().sort(resourceTypes, "status", "desc");
			
			return ResultFactory.createJsonSuccess(resourceTypes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Result brandList(Integer currentPage, Integer pageSize, ResourceType resourceType) {
		try {
			List<ResourceType> resourceTypes = new ArrayList<>();
			User user = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
			ManagerInfo managerInfo = managerService.findByUserId(user.getId());
			if (!StringUtils.isNull(managerInfo.getResourceTypeIds())) {
				// 资源开发人所属分类
				Integer id = Integer.valueOf(managerInfo.getResourceTypeIds());
				resourceTypes.add(resourceTypeService.findByID(id));
			} else {
				// 所有分类
				resourceType.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
				resourceTypes = resourceTypeService.findList(currentPage, pageSize, resourceType);
			}
			
			/** 搜索分类下面要显示文章个数 **/
			if (resourceType.getIsShowNumber() != null && resourceType.getIsShowNumber() == 1) {
				for (ResourceType entity : resourceTypes) {
					Resource resource = new Resource();
					resource.setCityId(resourceType.getCityId());
					resource.setTitle(resourceType.getTitle());
					resource.setTypeId(entity.getId());
					PageInfo pageInfo = resourceService.listCount(pageSize, resource);
					entity.setNumber(pageInfo.getAllRow());
				}
			}
			return ResultFactory.createJsonSuccess(resourceTypes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public Result addbrand(@Valid ResourceType resourceType) {
		resourceType.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		try {
			resourceTypeService.insert(resourceType);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createSuccess();
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public Result editbrand(@Valid ResourceType resourceType) {
		try {
			resourceTypeService.update(resourceType);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 前台接口
	 */

	/**
	 * 资源分类
	 */
	@RequestMapping(value = "/mlist")
	@ResponseBody
	public Result resourceType() {
		ResourceType resourceType = new ResourceType();
		resourceType.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		List<ResourceType> resourceTypes = null;
		try {
			resourceTypes = resourceTypeService.findList(null, null, resourceType);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(resourceTypes);
	}
}

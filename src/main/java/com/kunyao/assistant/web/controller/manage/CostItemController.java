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
import com.kunyao.assistant.core.model.CostItem;
import com.kunyao.assistant.core.utils.FileUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.CostItemService;

@Controller
@RequestMapping("/um/costItem")
public class CostItemController {
	
	@Resource
	private CostItemService costItemService;
	
	private final String ADD_PAGE_URL = "um/cost-item-add";
	private final String LIST_PAGE_URL = "um/cost-item-list";
	private final String EDIT_PAGE_URL = "um/cost-item-edit";
	
	private final String uploadPath = "/picture/costItem/";
	
	@RequestMapping(value = "/toAdd")
	public String toAddPage(Model model) {
		return ADD_PAGE_URL;
	}

	@RequestMapping(value = "/toList")
	public String toListPage(Model model) {
		return LIST_PAGE_URL;
	}
	
	@RequestMapping(value="/toEdit")
	public String toEditPage(@RequestParam("id") Integer certId, Model model) {
		CostItem costItem = null;
		try {
			costItem = costItemService.findByID(certId);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("costItem", costItem);
		return EDIT_PAGE_URL;
	}
	
	@ResponseBody
	@RequestMapping(value="/lscount", produces = {"application/json;charset=UTF-8"})
	public Result costItemListCount(Integer pageSize, CostItem costItem) {
		PageInfo page = null;
		try {
			page = costItemService.findPageInfo(pageSize, costItem);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return ResultFactory.createJsonSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping(value="/list", produces = {"application/json;charset=UTF-8"})
	public Result costItemList(Integer currentPage, Integer pageSize, CostItem costItem) {
		
		List<CostItem> travelDescList = null;
		
		try {
			travelDescList = costItemService.findList(currentPage, pageSize, costItem);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createJsonSuccess(travelDescList);
	}
	
	@ResponseBody
	@RequestMapping(value="/add", produces = {"application/json;charset=UTF-8"})
	public Result addCostItem(@Valid CostItem costItem, BindingResult bresult,
			@RequestParam(value="imageFile", required=false) MultipartFile imageFile,
			HttpServletRequest request) {
		
		if (bresult.hasErrors()) {
			return ResultFactory.createError(ResultCode.PARAMETER_ERROR);
        }
		
		String imagePath = FileUtils.springMvcUploadFile(imageFile, uploadPath);
		
		if (StringUtils.isNull(imagePath)) {
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		costItem.setIcon(imagePath);
		costItem.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
		try {
			costItemService.insert(costItem);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	@ResponseBody
	@RequestMapping(value="/edit", produces = {"application/json;charset=UTF-8"})
	public Result editCostItem(@Valid CostItem costItem, BindingResult bresult,
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
			costItem.setIcon(imagePath);
		}
		
		try {
			costItemService.update(costItem);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
}

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
import com.kunyao.assistant.core.model.CommonAddress;
import com.kunyao.assistant.web.service.CommonAddressService;

@Controller
@RequestMapping("/mc/address")
@ResponseBody
public class CommonAddressController {

	@Resource
	private CommonAddressService commonAddressService;
	
	/** 查询邮寄地址 */
	@RequestMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
	public Result findCommonAddress(Integer userId) {
		CommonAddress model = new CommonAddress();
		model.setUserId(userId);
		model.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		try {
			List<CommonAddress> addresses = commonAddressService.findList(null, null, model);
			return ResultFactory.createJsonSuccess(addresses);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	@RequestMapping(value = "/add")
	public Result add(CommonAddress commonAddress) throws ServiceException {
		return ResultFactory.createJsonSuccess(commonAddressService.add(commonAddress));
	}
	
	@RequestMapping(value = "/remove")
	public Result remove(CommonAddress commonAddress) throws ServiceException {
		return ResultFactory.createJsonSuccess(commonAddressService.remove(commonAddress));
	}
	
	@RequestMapping(value = "/edit")
	public Result edit(CommonAddress commonAddress) throws ServiceException {
		return ResultFactory.createJsonSuccess(commonAddressService.edit(commonAddress));
	}
}

package com.kunyao.assistant.web.controller.cross;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.web.service.CrossInfoService;

@Controller
@RequestMapping(value = "/cc/cross")
@ResponseBody
public class CrossUserController {

	@Resource
	private CrossInfoService crossInfoService;
	
	/**
	 * 登录接口
	 */
	@RequestMapping(value = "/login")
	public Result login(@RequestParam String username, @RequestParam String password) throws ServiceException {
		CrossInfo crossInfo = crossInfoService.login(username, password);
		return ResultFactory.createJsonSuccess(crossInfo);
	}
	
	/**
	 * 金鹰用户信息（简版）
	 */
	@RequestMapping(value = "/simple")
	public Result simple(@RequestParam Integer crossInfoId) throws ServiceException {
		CrossInfo crossInfo = crossInfoService.querySimpleCrossInfo(crossInfoId);
		return ResultFactory.createJsonSuccess(crossInfo);
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/updPwd")
	public Result updPwd(@RequestParam Integer crossUserId, @RequestParam String oldPassword, @RequestParam String newPassword) throws ServiceException {
		crossInfoService.updatePassword(crossUserId, oldPassword, newPassword);
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 修改联系电话
	 */
	@RequestMapping(value = "/updPhone")
	public Result updPhone(@RequestParam Integer crossUserId, @RequestParam String phone) throws ServiceException {
		crossInfoService.updateContactPhone(crossUserId, phone);
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 日程安排
	 */
	@RequestMapping(value = "/schedule")
	public Result schedule(@RequestParam Integer crossUserId) throws ServiceException {
		return ResultFactory.createJsonSuccess(crossInfoService.queryCrossTimeList(crossUserId));
	}
	
	/**
	 * 上传个推id
	 */
	@RequestMapping(value = "/uploadGetuiId")
	public Result uploadGetuiId(@RequestParam Integer crossUserId, @RequestParam String getuiId) throws ServiceException {
		crossInfoService.uploadGetuiId(crossUserId, getuiId);
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 上传坐标
	 */
	@RequestMapping(value = "/uploadLocation")
	public Result uploadLocation(@RequestParam Integer crossUserId, @RequestParam String lat, @RequestParam String lng) throws ServiceException {
		crossInfoService.uploadLocation(crossUserId, lat, lng);
		return ResultFactory.createSuccess();
	}
}

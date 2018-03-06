package com.kunyao.assistant.web.controller.manage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Push;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.MemberEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.MemberInfo;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.web.service.MemberInfoService;
import com.kunyao.assistant.web.service.PushService;
import com.kunyao.assistant.web.service.UserService;

@Controller
@RequestMapping("/um/member")
public class MemberInfoController {
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private PushService pushService;
	
	@Resource
	private UserService userService;
	
	/*订单列表*/
	private final String LIST_PAGE_URL = "um/member-list";
	
	/*审核订单列表*/
	private final String LIST_AUDIT_PAGE_URL = "um/member-audit-list";
	
	/*审核页*/
	private final String AUDIT_ID_CARD_URL = "um/member-audit-id-card";
	
	/*会员详情*/
	private final String TO_DETAIL_URL = "um/member-detail";
	
	@RequestMapping(value = "/toList")
	public String toList(Model model) {
		return LIST_PAGE_URL;
	}
	
	@RequestMapping(value = "/toAuditList")
	public String toAuditList(Model model) {
		return LIST_AUDIT_PAGE_URL;
	}
	
	@RequestMapping(value = "/toAuditIdCard")
	public String toAuditIdCard(Model model, Integer id) {
		try {
			MemberInfo memberInfo = memberInfoService.findByID(id);
			model.addAttribute("memberInfo", memberInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return AUDIT_ID_CARD_URL;
	}
	
	@RequestMapping(value = "/toDetail")
	public String toDetail(Model model, Integer id) {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(id);
		List<MemberInfo> memberInfoList = memberInfoService.selectListByCondition(null, null, memberInfo);
		MemberInfo info = memberInfoList.get(0);
		try {
			User user = userService.findByID(info.getUserId());
			info.setStatus(String.valueOf(user.getStatus()));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		model.addAttribute("memberInfo", info);
		return TO_DETAIL_URL;
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Result edit(Model model, Integer userId, Integer status) {
		try {
			if (status.intValue() == BaseEnum.Status.BASE_STATUS_ENABLE.getValue()) {
				userService.enable(userId);
			} else {
				userService.disable(userId);
			}
			return ResultFactory.createSuccess();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/lscount", produces = { "application/json;charset=UTF-8" })
	public Result memberInfoListCount(Integer pageSize, MemberInfo memberInfo) {
		PageInfo page = null;
		page = memberInfoService.selectListCount(pageSize, memberInfo);
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
	public Result memberInfoList(Integer currentPage, Integer pageSize, MemberInfo memberInfo) {
		List<MemberInfo> memberInfoList = memberInfoService.selectListByCondition(currentPage, pageSize, memberInfo);
		return ResultFactory.createJsonSuccess(memberInfoList);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/auditLscount", produces = { "application/json;charset=UTF-8" })
	public Result auditMemberInfoListCount(Integer pageSize, MemberInfo memberInfo) {
		PageInfo page = null;
		memberInfo.setStatusBy("audit");
		page = memberInfoService.selectListCount(pageSize, memberInfo);
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/auditList", produces = { "application/json;charset=UTF-8" })
	public Result auditMemberInfoList(Integer currentPage, Integer pageSize, MemberInfo memberInfo) {
		memberInfo.setStatusBy("audit");
		List<MemberInfo> memberInfoList = memberInfoService.selectListByCondition(currentPage, pageSize, memberInfo);
		return ResultFactory.createJsonSuccess(memberInfoList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/auditPass", produces = { "application/json;charset=UTF-8" })
	public Result auditPass(@RequestParam Integer id) {
		
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(id);
		memberInfo.setIdcardCheckPassStatus(MemberEnum.idcardCheckPassStatus.AUDIT_PASS.getValue());
		
		try {
			memberInfoService.update(memberInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/auditFailure", produces = { "application/json;charset=UTF-8" })
	public Result auditFailure(@RequestParam Integer id) {
		
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(id);
		memberInfo.setIdcardCheckPassStatus(MemberEnum.idcardCheckPassStatus.AUDIT_DOES_NOT_PASS.getValue());
		
		try {
			memberInfoService.update(memberInfo);
			
			MemberInfo member = memberInfoService.findByID(id);
			// 推送给用户
			pushService.push(Push.createTemplate6(member.getUserId(), member.getGetuiId(), member.getMobile()));
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
		
	}
}

package com.kunyao.assistant.web.controller.manage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.PageRequestDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.CompanyComment;
import com.kunyao.assistant.core.model.CrossComment;
import com.kunyao.assistant.core.model.MemberComment;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.CompanyCommentService;
import com.kunyao.assistant.web.service.CrossCommentService;
import com.kunyao.assistant.web.service.MemberCommentService;

@Controller
@RequestMapping("/um/comment")
public class MCommentController {

	// 金鹰评价
	private final String CROSS_COMMENT_LIST_URL = "um/comment-cross-list";

	// 会员评价
	private final String MEMBER_COMMENT_LIST_URL = "um/comment-member-list";
	
	// 公司评价
	private final String COMPANY_COMMENT_LIST_URL = "um/comment-company-list";

	@Resource
	private CityService cityService;
	
	@Resource
	private CrossCommentService crossCommentService;

	@Resource
	private MemberCommentService memberCommentService;
	
	@Resource
	private CompanyCommentService companyCommentService;

	/**
	 * 评价金鹰 评论列表
	 */
	@RequestMapping(value = "/toCrossCommentList")
	public String toRechargeList(Model model) {
		City city = new City();
		city.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
	    List<City> cityList = null;
		try {
			cityList = cityService.findList(null, null, city);
		    model.addAttribute("cityList", cityList);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return CROSS_COMMENT_LIST_URL;
	}

	@RequestMapping(value = "/cross/listPageCount")
	@ResponseBody
	public Result crossListPageCount(CrossComment crossComment) {
		PageInfo page;
		try {
			page = new PageInfo(crossCommentService.queryListCount(crossComment), 12);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/cross/listPage")
	@ResponseBody
	public Result crossListPage(CrossComment crossComment, PageRequestDto pageRequestDto) {
		try {
			return ResultFactory.createJsonSuccess(crossCommentService.queryList(pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize(), crossComment));
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	@RequestMapping(value = "/cross/operate")
	@ResponseBody
	public Result crossCommentOperate(Integer id) {
		try {
			CrossComment crossComment = crossCommentService.findByID(id);
			crossComment.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			crossCommentService.update(crossComment);
			return ResultFactory.createSuccess();
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}

	/**
	 * 评价会员 评论列表
	 */
	@RequestMapping(value = "/toMemberCommentList")
	public String toMemberCommentList(Model model) {
		City city = new City();
		city.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
	    List<City> cityList = null;
		try {
			cityList = cityService.findList(null, null, city);
		    model.addAttribute("cityList", cityList);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return MEMBER_COMMENT_LIST_URL;
	}

	@RequestMapping(value = "/member/listPageCount")
	@ResponseBody
	public Result memberListPageCount(MemberComment memberComment) {
		PageInfo page;
		try {
			page = new PageInfo(memberCommentService.queryListCount(memberComment), 12);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/member/listPage")
	@ResponseBody
	public Result memberListPage(MemberComment memberComment, PageRequestDto pageRequestDto) {
		try {
			return ResultFactory.createJsonSuccess(memberCommentService.queryList(pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize(), memberComment));
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
	
	/**
	 * 评价公司 评价列表
	 */
	@RequestMapping(value = "/toCompanyCommentList")
	public String toCompanyCommentList(Model model) {
		City city = new City();
		city.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
	    List<City> cityList = null;
		try {
			cityList = cityService.findList(null, null, city);
		    model.addAttribute("cityList", cityList);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return COMPANY_COMMENT_LIST_URL;
	}

	@RequestMapping(value = "/company/listPageCount")
	@ResponseBody
	public Result companyListPageCount(CompanyComment companyComment) {
		PageInfo page;
		try {
			page = new PageInfo(companyCommentService.queryListCount(companyComment), 12);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(page);
	}

	@RequestMapping(value = "/company/listPage")
	@ResponseBody
	public Result companyListPage(CompanyComment companyComment, PageRequestDto pageRequestDto) {
		try {
			return ResultFactory.createJsonSuccess(companyCommentService.queryList(pageRequestDto.getCurrentPage(), pageRequestDto.getPageSize(), companyComment));
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
}

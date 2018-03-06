package com.kunyao.assistant.web.controller.member;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.weixin.WxSettings;
import com.kunyao.assistant.core.model.MemberInfo;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.FileUtils;
import com.kunyao.assistant.core.utils.HttpUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.BankService;
import com.kunyao.assistant.web.service.MemberInfoService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/mc/member")
public class MemberController {
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private BankService bankService;
	
	private final String uploadPath = "/picture/memberinfo/";
	
	/**
	 * 发送短信验证码到指定手机号
	 */
	@ResponseBody
	@RequestMapping(value = "/send_sms/", produces = {"application/json;charset=UTF-8"})
	public Result sendSms(String mobile, Integer type) {
		Result result = null;
		int sr = memberInfoService.sendSms(mobile, type);
		
		switch (sr) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
        case -1:
        	result = ResultFactory.createError(ResultCode.VERIFICATION_CODE_SENT_OVER_A_NUMBER_OF);
			break;
        case -2:
        	result = ResultFactory.createError(ResultCode.VERIFICATION_CODE_SENT_FAILED);
	       break;
		default:
			break;
		}
		return result;
	}
	
	
	/**
	 * 会员短信验证注册登陆
	 * @param code 短信验证码
	 * @param phone 接收验证手机号
	 */
	@ResponseBody
	@RequestMapping(value = "/bind_phone/", produces = {"application/json;charset=UTF-8"})
	public Result bindPhone(@RequestParam("code") String code, @RequestParam("mobile") String mobile) throws ServiceException{
		Result result = null;
		int id = memberInfoService.createSmsLogin(code, mobile);
		if (id > 0) {
			MemberInfo member = null;
			try {
				member = memberInfoService.selectMember(id);
			} catch (ServiceException e) {
				e.printStackTrace();
				return result = ResultFactory.createError(ResultCode.INFORMATION_QUERY_ERROR);
			}
			result = ResultFactory.createJsonSuccess(member);
		} else {
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);	
		}
		
		return result;
	}
	
	
	/**
	 * 自动登录
	 */
	@ResponseBody
	@RequestMapping(value = "/auto_login", produces = {"application/json;charset=UTF-8"})
	public Result autoLogin(Integer userId) {
		try {
			MemberInfo memberInfo = memberInfoService.autoLogin(userId);
			return ResultFactory.createJsonSuccess(memberInfo);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
	}
	
	
	/**
	 * 会员基本信息
	 * @param memberId 会员ID
	 * @param sex 性别
	 * @param address 工作地
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/essential_information/", produces = {"application/json;charset=UTF-8"})
	public Result essentialInformation(@RequestParam("memberId") Integer memberId, @RequestParam("name") String name, @RequestParam("sex") Integer sex, 
			@RequestParam("address") String address, @RequestParam("bankMobile") String bankMobile) {
		Result result = null;
		
		int i = memberInfoService.addEssentialInformation(memberId, name, sex, address, bankMobile);
		
		if (i > 0) {
			MemberInfo member = null;
			try {
				member = memberInfoService.selectMember(i);
			} catch (ServiceException e) {
				e.printStackTrace();
				return result = ResultFactory.createError(ResultCode.INFORMATION_QUERY_ERROR);
			}
			result = ResultFactory.createJsonSuccess(member);
		} else {
			switch (i) {
			case -1:
				result = ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
				break;
			case -2:
				result = ResultFactory.createError(ResultCode.NAME_PHONE_VALIDATION_ERROR);
				break;
			case -3:
				result = ResultFactory.createError(ResultCode.PARAMETER_ERROR);
				break;
			default:
				result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
				break;
			}
		}
		return result;
	}
	
	/**
	 * 身份证信息保存
	 * @param memberId 会员ID
	 * @param sex 性别
	 * @param address 工作地
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/id_authentication/", produces = {"application/json;charset=UTF-8"})
	public Result idAuthentication(@RequestParam("memberId") Integer memberId, 
			@RequestParam(value = "idcardPic1") String idcardPic1,
			@RequestParam(value = "idcardPic2") String idcardPic2, 
			@RequestParam("name") String name, @RequestParam("idcard") String idcard) {
		
		Result result = null;
		
		int i = memberInfoService.addIdAuthentication(memberId, idcardPic1, idcardPic2, name, idcard);
		
		if (i > 0) {
			MemberInfo member = null;
			try {
				member = memberInfoService.selectMember(i);
			} catch (ServiceException e) {
				e.printStackTrace();
				return result = ResultFactory.createError(ResultCode.INFORMATION_QUERY_ERROR);
			}
			result = ResultFactory.createJsonSuccess(member);
		} else {
			switch (i) {
			case -1:
				result = ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
				break;
			case -2:
				result = ResultFactory.createError(ResultCode.NAME_IDCARD_VALIDATION_ERROR);
				break;
			case -3:
				result = ResultFactory.createError(ResultCode.PARAMETER_ERROR);
				break;
			case -4:
				result = ResultFactory.createError(ResultCode.LOAD_PICTURE_ERROR);
				break;
			default:
				result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
				break;
			}
		}
		return result;
	}
	
	/**
	 * 银行卡信息保存
	 * @param memberId 会员ID
	 * @param bankCard 银行卡号
	 * @param name 姓名
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/bank_card/", produces = {"application/json;charset=UTF-8"})
	public Result addBankCard(@RequestParam("memberId") Integer memberId, @RequestParam("bankCard") String bankCard, @RequestParam("name") String name) {
		
		Result result = null;
		
		int i = -1;
		try {
			i = memberInfoService.addBankCard(memberId, bankCard, name);
		} catch (ServiceException e1) {
			e1.printStackTrace();
			return result = ResultFactory.createError(e1.getError());
		}
		
		if (i > 0) {
			MemberInfo member = null;
			try {
				member = memberInfoService.selectMember(i);
			} catch (ServiceException e) {
				e.printStackTrace();
				return result = ResultFactory.createError(ResultCode.INFORMATION_QUERY_ERROR);
			}
			result = ResultFactory.createJsonSuccess(member);
		} else {
			switch (i) {
			case -1:
				result = ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
				break;
			case -2:
				result = ResultFactory.createError(ResultCode.BANDCARD_NAME_VALIDATION_ERROR);
				break;
			case -3:
				result = ResultFactory.createError(ResultCode.PARAMETER_ERROR);
				break;
			default:
				result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
				break;
			}
		}
		return result;
	}
	
	
	/**
	 * 选填信息
	 */
	@ResponseBody
	@RequestMapping(value = "/optional_information/", produces = {"application/json;charset=UTF-8"})
	public Result addOptionalInformation(MemberInfo memberInfo) {
		
		MemberInfo member = null;
		try {
			member = memberInfoService.updateMemberOptionalInformation(memberInfo);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createJsonSuccess(member);
	}
	
	/**
	 * 上传图片
	 */
	@ResponseBody
	@RequestMapping(value = "/upload_img")
	public Result uploadImage(@RequestParam(value = "file") MultipartFile file) {
		if (file == null) {
			return ResultFactory.createError(ResultCode.FILE_NULL);
		}
		String imgPath = FileUtils.springMvcUploadFile(file, uploadPath);
		if (StringUtils.isNull(imgPath)) {
			return ResultFactory.createError(ResultCode.FILE_CREATE_ERROR);
		}
		return ResultFactory.createJsonSuccess(imgPath);
	}
	
	/**
	 * 用户详细信息
	 */
	@ResponseBody
	@RequestMapping(value = "/info")
	public Result info(Integer userId) {
		return ResultFactory.createJsonSuccess(memberInfoService.findMemberInfo(userId));
	}
	
	/**
	 * 退出登录
	 */
	@ResponseBody
	@RequestMapping(value = "/logout")
	public Result logout(Integer userId) {
		try {
			memberInfoService.logout(userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 信用能量
	 */
	@ResponseBody
	@RequestMapping(value = "/score")
	public Result score(Integer userId) {
		return ResultFactory.createJsonSuccess(memberInfoService.caculateScoreTotal(userId));
	}

	/**
	 * 了解分
	 */
	@ResponseBody
	@RequestMapping(value = "/aboutScore")
	public Result aboutScore() {
		return ResultFactory.createJsonSuccess("TODO");
	}
	
	/**
	 * 添加银行卡
	 */
	@ResponseBody
	@RequestMapping(value = "/bankAdd")
	public Result bankAdd(Integer userId, String userName, String banCard) throws ServiceException {
		return ResultFactory.createJsonSuccess(bankService.add(userId, userName, banCard));
	}
	
	/**
	 * 银行卡列表
	 */
	@ResponseBody
	@RequestMapping(value = "/bankList")
	public Result bankList(Integer userId) throws ServiceException {
		return ResultFactory.createJsonSuccess(bankService.list(userId));
	}
	
	/**
	 * 解绑银行卡
	 */
	@ResponseBody
	@RequestMapping(value = "/bankRemove")
	public Result bankRemove(Integer userId, Integer id) throws ServiceException {
		return ResultFactory.createJsonSuccess(bankService.remove(userId, id));
	}
	
	/**
	 * 上传个推id
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadGetuiId")
	public Result uploadGetuiId(Integer userId, String getuiId) throws ServiceException {
		if (StringUtils.isNull(getuiId)) {
			return ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
		}
		memberInfoService.uploadGetuiId(userId, getuiId);
		return ResultFactory.createSuccess();
	}

	/**
	 * 查询身份证信息
	 */
	@ResponseBody
	@RequestMapping(value = "/idcard_info")
	public Result findIdcardInfo(Integer memberId) {
		return ResultFactory.createJsonSuccess(memberInfoService.selectMemberIdcardInfo(memberId));
	}
	
	/**
	 * 隐私电话绑定
	 */
	@ResponseBody
	@RequestMapping(value = "/anon_bind")
	public Result anonyBind(String memberMobile, String crossMobile) {
		return ResultFactory.createJsonSuccess(memberInfoService.anonymousCall(memberMobile, crossMobile, 1));
	}
	
	/**
	 * 隐私电话解绑
	 */
	@ResponseBody
	@RequestMapping(value = "/anon_unbind")
	public Result anonyUnbind(String memberMobile, String crossMobile) {
		return ResultFactory.createJsonSuccess(memberInfoService.anonymousCall(memberMobile, crossMobile, 0));
	}
	
	/**
	 * 微信授权获取openId
	 */
	@ResponseBody
	@RequestMapping(value = "/openId")
	public Result openId(String code) {
		// 获取Token
		String url = WxSettings.GET_OPEN_ID.replace("{0}", WxSettings.APP_ID).replace("{1}", WxSettings.APP_SECRET).replace("{2}", code);
		String result = HttpUtils.getByUrlConnection(url);
		JSONObject jsonObj = JSONObject.fromObject(result);
		if (!jsonObj.has("access_token")) {
			return ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
		}
		String openId = jsonObj.getString("openid");
		return ResultFactory.createJsonSuccess(openId);
	}
	
	/**
	 * 接受邀请发送短信验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/shared_sendsms", produces = {"application/json;charset=UTF-8"})
	public Result sharedSendSms(String mobile, String name, String identifying) {
		try {
			memberInfoService.sharedSendSms(mobile, name, identifying);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 接受邀请注册
	 */
	@ResponseBody
	@RequestMapping(value = "/shared_signup", produces = {"application/json;charset=UTF-8"})
	public Result sharedSignup(String mobile, String code, Integer sex, String name, String identifying) {
		try {
			memberInfoService.sharedSignup(mobile, code, sex, name, identifying);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 年会发放优惠券专题活动
	 * 验证手机号姓名并发送短信验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/active_sendSms", produces = {"application/json;charset=UTF-8"})
	public Result activeSendSms(String mobile, String name) {
		try {
			memberInfoService.activeSendSms(mobile, name);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
		return ResultFactory.createSuccess();
	}
	
	/**
	 * 年会发放优惠券专题活动
	 * 注册/登录
	 * @return userId
	 */
	@ResponseBody
	@RequestMapping(value = "/active_bind", produces = {"application/json;charset=UTF-8"})
	public Result activeBind(String mobile, String code, String name, Integer sex) {
		try {
			int userId = memberInfoService.updateActiveBind(mobile, code, name, sex);
			return ResultFactory.createJsonSuccess(userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(e.getError());
		}
	}
}

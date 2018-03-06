package com.kunyao.assistant.web.service.impl;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.AccountRecordEnum;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.MemberEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.juhe.JuheUtils;
import com.kunyao.assistant.core.feature.sms.YunPianSMS;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Account;
import com.kunyao.assistant.core.model.MemberInfo;
import com.kunyao.assistant.core.model.OrderConfig;
import com.kunyao.assistant.core.model.Role;
import com.kunyao.assistant.core.model.Share;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.model.UserRole;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.AccountMapper;
import com.kunyao.assistant.web.dao.AccountRecordMapper;
import com.kunyao.assistant.web.dao.CouponMapper;
import com.kunyao.assistant.web.dao.MemberInfoMapper;
import com.kunyao.assistant.web.dao.OrderConfigMapper;
import com.kunyao.assistant.web.dao.UserRoleMapper;
import com.kunyao.assistant.web.service.ActivityService;
import com.kunyao.assistant.web.service.BankService;
import com.kunyao.assistant.web.service.MemberCommentService;
import com.kunyao.assistant.web.service.MemberInfoService;
import com.kunyao.assistant.web.service.RoleService;
import com.kunyao.assistant.web.service.ShareService;
import com.kunyao.assistant.web.service.UserService;

import net.sf.json.JSONObject;

@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, Integer> implements MemberInfoService {
	
    @Resource
    private MemberInfoMapper memberInfoMapper;
    
    @Resource
    private UserService userService;
    
    @Resource
    private AccountMapper accountMapper;
    
    @Resource 
    private RoleService roleService;
    
    @Resource
    private UserRoleMapper userRoleMapper;
    
    @Resource
    private MemberCommentService memberCommentService;
    
    @Resource
    private BankService bankService;
    
    @Resource
    private ShareService shareService;
    
    @Resource
    private CouponMapper couponMapper;
    
    @Resource
	private ActivityService activityService;
    
    @Resource
    private OrderConfigMapper orderConfigMapper;
    
    @Resource
    private AccountRecordMapper accountRecordMapper;
    
    private static final boolean debug = false;
    
    public GenericDao<MemberInfo, Integer> getDao() {
        return memberInfoMapper;
    }
	
	@Override
	public int sendSms(String mobile, Integer type) {
		if (debug) {
			String vCode = "8888";
			String codeValue = vCode + "," + new Date().getTime();
			YunPianSMS.PHONE_CODE_MAP.put(mobile, codeValue);
			return 1;
		}
		String vCode = StringUtils.getRandom(6);
		String codeValue = vCode + "," + new Date().getTime();
		YunPianSMS.PHONE_CODE_MAP.put(mobile, codeValue);
		int i = 0;
		if(type == 1){
			// 发短信
			try {
				 i = YunPianSMS.sendLoginText(mobile, vCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(type == 2){
			// 语音
			try {
				 i = YunPianSMS.sendVoice(mobile, vCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return i;
	}

	@Override
	public int createSmsLogin(String code, String mobile) throws ServiceException{
		if (!YunPianSMS.PHONE_CODE_MAP.containsKey(mobile))
			return -4;
			
	    String[] codeMapValue = YunPianSMS.PHONE_CODE_MAP.get(mobile).split(",");
		String sourceCode = codeMapValue[0];
		String sourceDate = codeMapValue[1];
		
		long sourceTime = Long.valueOf(sourceDate);
		long nowTime = new Date().getTime();
		long middleTime = nowTime - sourceTime;
		
		if (middleTime > (long) 15 * 60 * 1000)
			throw new ServiceException(ResultCode.VERIFICATION_CODE_TIMEOUT);
		
		if (!sourceCode.equals(code))
			throw new ServiceException(ResultCode.VERIFICATION_CODE_ERROR);
		
		User uModel = new User();
		uModel.setMobile(mobile);
		List<User> userList = null;
		User user = null;
		MemberInfo memberInfo = null;
		User Umodel = null;
		try {
			userList = userService.findList(null, null, uModel);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		
		if(userList != null && userList.size() > 0){
			user = userList.get(0);
		}
		
		if(user == null){
			// 添加用户帐号
			Umodel = new User();
			Umodel.setMobile(mobile);
			Umodel.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			String afterSecretPassword = StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + mobile);
			Umodel.setPassword(afterSecretPassword);
			Umodel.setUsername(mobile);
			Umodel.setCreateTime(new Date());
			Umodel.setType("member");
			try {
				userService.insert(Umodel);
			} catch (ServiceException e) {
				e.printStackTrace();
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
			
			// 添加会员信息
			memberInfo = new MemberInfo();
			memberInfo.setBankMobile(mobile);
			memberInfo.setUserId(Umodel.getId());
			try {
				memberInfoMapper.insert(memberInfo);
			} catch (Exception e) {
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
			
			// 添加会员帐户
			Account account = new Account();   
			account.setUserId(Umodel.getId());
			account.setBalance(0.0);
			try {
				accountMapper.insert(account);
			} catch (Exception e) {
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
			
			// 添加角色权限信息
			UserRole userRole = new UserRole();
			
			Role role = new Role();
			role.setSign(Constant.ROLE_SIGN_MEMBER);
			try {
				Role roleID = roleService.findList(null, null, role).get(0);
				userRole.setRoleId(roleID.getId());
				userRole.setUserId(Umodel.getId());
				userRoleMapper.insert(userRole);
			} catch (ServiceException e1) {
				e1.printStackTrace();
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
			
			//把添加好的会员id更新到用户信息中
			try {
			  	User updateUserinfoId = new User();
			  	updateUserinfoId.setId(Umodel.getId());
			  	updateUserinfoId.setUserinfoId(memberInfo.getId());
			  	userService.update(updateUserinfoId);
			} catch (ServiceException e) {
				e.printStackTrace();
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
			
		} else if (user.getStatus().equals(BaseEnum.Status.BASE_STATUS_DISABLED.getValue())){
			throw new ServiceException(ResultCode.REGISTRATION_IS_NOT_ALLOWED);
		}
		
		YunPianSMS.PHONE_CODE_MAP.remove(mobile);
		
		if (user != null)
			return user.getUserinfoId();
		
		User newUser = new User();
		try {
			newUser = userService.findByID(Umodel.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return newUser.getUserinfoId();
	}

	@Override
	public int addEssentialInformation(Integer memberId, String name, Integer sex, String address, String bankMobile) {
		
		if (memberId == null || memberId < 1 || name == null || name.equals("") || sex == null || address == null ||
				address.equals("") || bankMobile == null || bankMobile.equals(""))
			return -3;
		
		boolean i = JuheUtils.verifyPhone(bankMobile, name);
		
		if (i == false)
			return -2;
	
		MemberInfo memberInfo = new MemberInfo();
		try {
			memberInfo.setId(memberId);
			memberInfo.setName(name);
			memberInfo.setSex(sex);
			memberInfo.setAddress(address);
			memberInfoMapper.updateByID(memberInfo);
		} catch (Exception e) {
			return -1;
		}
		return memberInfo.getId();
	}

	@Override
	public int addIdAuthentication(Integer memberId, String cardPic1, String cardPic2, String name, String idcard) {
		
		if (StringUtils.isNull(cardPic1) || StringUtils.isNull(cardPic2)) {
			return -4;
		}
		
		if (memberId == null || memberId < 1 || name == null || name.equals("") || idcard == null || idcard.equals(""))
			return -3;
		
		boolean i = JuheUtils.verifyIdcard(idcard, name);
		if (i == false)
			return -2;
		
		MemberInfo memberInfo = new MemberInfo();
		try {
			memberInfo.setId(memberId);
			memberInfo.setName(name);
			memberInfo.setIdcard(idcard);
			memberInfo.setIdcardPic1(cardPic1);
			memberInfo.setIdcardPic2(cardPic2);
			memberInfo.setIdcardCheckPassStatus(MemberEnum.idcardCheckPassStatus.NOT_AUDITED.getValue());
			memberInfoMapper.updateByID(memberInfo);
		} catch (Exception e) {
			return -1;
		}
		
		return memberInfo.getId();
		
	}

	@Override
	public int addBankCard(Integer memberId, String bankCard, String name) throws ServiceException {
		
		if (memberId == null || memberId < 1 || name == null || name.equals("") ||bankCard == null ||
				bankCard.equals("") )
			return -3;
		
		
		boolean i = JuheUtils.verifyBankcard(bankCard, name);
		if (i == false)
			return -2;
		
		MemberInfo memberInfo = new MemberInfo();
		try {
			memberInfo.setId(memberId);
			memberInfo.setName(name);
			memberInfo.setBankCard(bankCard);
			memberInfoMapper.updateByID(memberInfo);
		} catch (Exception e) {
			return -1;
		}
		
		// 添加银行卡
		MemberInfo m = findOne(memberInfo);
		if (m != null) {
			bankService.add(m.getUserId(), name, bankCard);
		}
		return memberInfo.getId();
	}

	@Override
	public MemberInfo updateMemberOptionalInformation(MemberInfo memberInfo) throws ServiceException {
		
		memberInfoMapper.updateByID(memberInfo);
		
		return memberInfoMapper.findMember(memberInfo.getId());
	}

	@Override
	public MemberInfo findByOpenId(String openId) {
		if (StringUtils.isNull(openId)) {
			return null;
		}
		return memberInfoMapper.findByOpenId(openId);
	}
	
	@Override
	public MemberInfo findByOrderId(Integer orderId) {
		return memberInfoMapper.findByOrderId(orderId);
	}

	@Override
	public MemberInfo findMemberInfo(Integer userId) {
		if (userId == null) {
			// TODO 加异常
			return null;
		}
		return memberInfoMapper.findMemberInfo(userId);
	}

	/**
	 * 计算用户完成资料的评分
	 * 必填信息（20分）、身份证（20分）、银行卡（20分）、籍贯（5分）、居住地（5分）、行业（5分）、
	 * 公司（5分）、职位（5分）、出差频率（5分）、喜好（5分）、忌讳（5分），全部完成100分
	 */
	@Override
	public double caculateMemberInfoScore(Integer userId) {
		MemberInfo memberInfo = findMemberInfo(userId);
		double score = 0.0;
		if (memberInfo == null) {
			return score;
		}
		if (!StringUtils.isNull(memberInfo.getName()) && memberInfo.getSex() != null) score += 20;
		if (!StringUtils.isNull(memberInfo.getIdcard())) score += 20;
		if (!StringUtils.isNull(memberInfo.getBankCard())) score += 20;
		if (!StringUtils.isNull(memberInfo.getNativePlace())) score += 5;
		if (!StringUtils.isNull(memberInfo.getLivePlace())) score += 5;
		if (!StringUtils.isNull(memberInfo.getBusiness())) score += 5;
		if (!StringUtils.isNull(memberInfo.getCompany())) score += 5;
		if (!StringUtils.isNull(memberInfo.getPosition())) score += 5;
		if (!StringUtils.isNull(memberInfo.getTravelFrequency())) score += 5;
		if (!StringUtils.isNull(memberInfo.getHabit())) score += 5;
		if (!StringUtils.isNull(memberInfo.getTaboo())) score += 5;
		return score;
	}

	@Override
	public void logout(Integer userId) throws ServiceException {
		MemberInfo memberInfo = memberInfoMapper.findMemberInfo(userId);
		memberInfo.setGetuiId(null);
		update(memberInfo);
	}

	@Override
	public MemberInfo selectMember(Integer memberId) throws ServiceException {
		MemberInfo memberInfo = memberInfoMapper.findMember(memberId);
		if (memberInfo == null)
			throw new ServiceException(ResultCode.NO_DATA);
		return memberInfo;
	}

	@Override
	public void uploadGetuiId(Integer userId, String getuiId) throws ServiceException {
		MemberInfo memberInfo = memberInfoMapper.findMemberInfo(userId);
		memberInfo.setGetuiId(getuiId);;
		update(memberInfo);
	}
	

	@Override
	public List<MemberInfo> selectListByCondition(Integer currentPage, Integer pagesize, MemberInfo memberInfo) {
		List<MemberInfo> memberInfos = null;
		Integer startpos = null;     //当前页
		
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		if (memberInfo.getRegistrationTimeMin() != null && memberInfo.getRegistrationTimeMax() != null){
			String time  = memberInfo.getRegistrationTimeMax();
			Date date = DateUtils.parseYMDDate(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1); 
			Date torrowDate = calendar.getTime();
			
			String registrationTimeMax = DateUtils.parseYMDTime(torrowDate);
			memberInfo.setRegistrationTimeMax(registrationTimeMax);
		}
		
		if (memberInfo.getLastLoginTimeMin() != null && memberInfo.getLastLoginTimeMax() != null){
			String time  = memberInfo.getLastLoginTimeMax();
			Date date = DateUtils.parseYMDDate(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1); 
			Date torrowDate = calendar.getTime();
			
			String lastLoginTimeMax = DateUtils.parseYMDTime(torrowDate);
			memberInfo.setLastLoginTimeMax(lastLoginTimeMax);
		}
		
		memberInfos = memberInfoMapper.selectListByCondition(startpos, pagesize, memberInfo);
		if (memberInfos != null) {
			for (MemberInfo member : memberInfos) {
				Account account = accountMapper.findAccount(member.getUserId());
				member.setBalance(account.getBalance());
			}
		}
		return memberInfos;
	}

	@Override
	public PageInfo selectListCount(Integer pageSize, MemberInfo memberInfo) {
		
		if (memberInfo.getRegistrationTimeMin() != null && memberInfo.getRegistrationTimeMax() != null){
			String time  = memberInfo.getRegistrationTimeMax();
			Date date = DateUtils.parseYMDDate(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1); 
			Date torrowDate = calendar.getTime();
			
			String registrationTimeMax = DateUtils.parseYMDTime(torrowDate);
			memberInfo.setRegistrationTimeMax(registrationTimeMax);
		}
		
		if (memberInfo.getLastLoginTimeMin() != null && memberInfo.getLastLoginTimeMax() != null){
			String time  = memberInfo.getLastLoginTimeMax();
			Date date = DateUtils.parseYMDDate(time);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1); 
			Date torrowDate = calendar.getTime();
			
			String lastLoginTimeMax = DateUtils.parseYMDTime(torrowDate);
			memberInfo.setLastLoginTimeMax(lastLoginTimeMax);
		}
		
		int allRow = memberInfoMapper.selectCountByCondition(memberInfo);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}

	@Override
	public double caculateScoreTotal(Integer userId) {
		OrderConfig config = orderConfigMapper.findOrderServiceFee();
		Account account = accountMapper.findAccount(userId);
		
		// 用户资料完善程度分数
		double personalInfoScore = this.caculateMemberInfoScore(userId);
		
		// 账户余额计算分数
		double accountScore = 0;
		if (account != null) {
			if(account.getBalance() >= config.getFullBalance()){
				accountScore = 100;
			} else {
				accountScore = account.getBalance() / config.getFullBalance() * 100;
			}
		}
		
		// 用户评分计算分数
		double commnetScore = memberCommentService.calculateScore(userId);
		
		// 代支付总额计算分数
		double costMoneyScore = accountRecordMapper.sumMoneyRecord(userId, AccountRecordEnum.ORDER_COST.getValue()) / config.getFullPayment() * 100;
		
		// 总分数
		double totalScore = personalInfoScore + accountScore + commnetScore + costMoneyScore;
		return Double.valueOf(new DecimalFormat("0.00").format(totalScore / 4));
	}
	
	@Override
	public MemberInfo selectMemberIdcardInfo(Integer memberId) {
		return memberInfoMapper.findMemberIdcardInfo(memberId);
	}

	@Override
	public JSONObject anonymousCall(String memberMobile, String crossMobile, Integer method) {
		JSONObject result = null;
		if (method == 1) {
			result = YunPianSMS.anonymousBind(memberMobile, crossMobile);
		} else if (method == 0)
			result = YunPianSMS.anonymousUnbind(memberMobile, crossMobile);
		return result;
	}
	
	@Override
	public void sharedSendSms(String mobile, String name, String identifying) throws ServiceException {
		if (mobile == null || name == null)
			throw new ServiceException(ResultCode.PARAMETER_ERROR);
		Share share = new Share();
		share.setCode(identifying);
		Share findShare = shareService.findOne(share);
		if (findShare == null)
			throw new ServiceException(ResultCode.NO_DATA);
		// 防止重复注册
		User model = new User();
		model.setMobile(mobile);
		User user = userService.findOne(model);
		if (user != null) {
			if (user.getStatus() == BaseEnum.Status.BASE_STATUS_DISABLED.getValue())
				throw new ServiceException(ResultCode.REGISTRATION_IS_NOT_ALLOWED);
			else
				throw new ServiceException(ResultCode.USER_EXIST);
		}
		// 实名认证
		boolean res = JuheUtils.verifyPhone(mobile, name);
		if (!res)
			throw new ServiceException(ResultCode.NAME_PHONE_VALIDATION_ERROR);
		// 发送验证码
		if (debug) {
			String vCode = "8888";
			String codeValue = vCode + "," + new Date().getTime();
			YunPianSMS.PHONE_CODE_MAP.put(mobile, codeValue);
			return;
		}
		String vCode = StringUtils.getRandom(6);
		String codeValue = vCode + "," + new Date().getTime();
		YunPianSMS.PHONE_CODE_MAP.put(mobile, codeValue);
		int i = 0;
		i = YunPianSMS.sendLoginText(mobile, vCode);
		if (i == -1)
			throw new ServiceException(ResultCode.VERIFICATION_CODE_SENT_OVER_A_NUMBER_OF);
		else if (i == -2)
			throw new ServiceException(ResultCode.VERIFICATION_CODE_SENT_FAILED);
	}

	@Override
	public void sharedSignup(String mobile, String code, Integer sex, String name, String identifying) throws ServiceException {
		if (StringUtils.isNull(mobile) || StringUtils.isNull(code) || sex == null || StringUtils.isNull(identifying))
			throw new ServiceException(ResultCode.PARAMETER_ERROR);
		
		// 认证验证码
		if (!YunPianSMS.PHONE_CODE_MAP.containsKey(mobile))
			throw new ServiceException(ResultCode.UNKNOWN_ERROR);
		
		String[] codeMapValue = YunPianSMS.PHONE_CODE_MAP.get(mobile).split(",");
		String sourceCode = codeMapValue[0];
		String sourceDate = codeMapValue[1];
		long sourceTime = Long.valueOf(sourceDate);
		long nowTime = new Date().getTime();
		long middleTime = nowTime - sourceTime;
		
		if (middleTime > (long) 15 * 60 * 1000)
			throw new ServiceException(ResultCode.VERIFICATION_CODE_TIMEOUT);
		
		if (!sourceCode.equals(code))
			throw new ServiceException(ResultCode.VERIFICATION_CODE_ERROR);
		
		// 实名认证
		boolean res = JuheUtils.verifyPhone(mobile, name);
		if (res == false)
			throw new ServiceException(ResultCode.NAME_PHONE_VALIDATION_ERROR);
		
		YunPianSMS.PHONE_CODE_MAP.remove(mobile);
		
		// 添加user数据
		User newUser = new User();
		newUser.setMobile(mobile);
		newUser.setUsername(mobile);
		newUser.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		newUser.setType("member");
		newUser.setPassword(StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + mobile));
		newUser.setCreateTime(new Date());
		userService.insert(newUser);
		
		// 添加member数据
		MemberInfo member = new MemberInfo();
		member.setBankMobile(mobile);
		member.setUserId(newUser.getId());
		member.setName(name);
		member.setSex(sex);
		memberInfoMapper.insert(member);
		
		// 添加account数据
		Account account = new Account();
		account.setBalance(0d);
		account.setFreezeBalance(0d);
		account.setUserId(newUser.getId());
		accountMapper.insert(account);
		
		// 添加userRole数据
		UserRole userRole = new UserRole();
		Role role = new Role();
		role.setSign(Constant.ROLE_SIGN_MEMBER);
		Role findRole = roleService.findOne(role);
		userRole.setRoleId(findRole.getId());
		userRole.setUserId(newUser.getId());
		userRoleMapper.insert(userRole);
		
		// 将userId添加到share中的被分享人
		Share share = new Share();
		share.setCode(identifying);
		Share findShare = shareService.findOne(share);
		if (findShare == null)
			throw new ServiceException(ResultCode.NO_DATA);
		String sharedUserId = findShare.getSharedUserId();
		findShare.setSharedUserId((StringUtils.isNull(sharedUserId) ? "" : (sharedUserId + ",")) + newUser.getId());
		shareService.update(findShare);
		
		// 更新user的memberId
		User updUser = new User();
		updUser.setId(newUser.getId());
		updUser.setUserinfoId(member.getId());
		userService.update(updUser);
		/*
		// 分享来源用户获得1张500优惠券
		Coupon coupon = new Coupon();
		coupon.setUserId(findShare.getShareUserId());
		coupon.setMoney(500d);
		coupon.setStatus(CouponEnum.UN_USER.getValue());
		coupon.setName("邀请注册得优惠券");
		coupon.setSource(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		coupon.setSharedUserId(newUser.getId());
		Date startDate = DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date()));
		Date endDate = DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date(startDate.getTime() + DateUtils.DAY * 119)) + " 23:59:59");// 四个月,往后数119天
		coupon.setStartTime(startDate);
		coupon.setEndTime(endDate);
		couponMapper.insert(coupon);
		
		// 新注册用户获得1张500优惠券
		coupon.setSharedUserId(null);
		coupon.setUserId(newUser.getId());
		couponMapper.insert(coupon);*/
	}

	@Override
	public int updateActiveBind(String mobile, String code, String name, Integer sex) throws ServiceException {
		int memberId = createSmsLogin(code, mobile);
		MemberInfo selectMember = selectMember(memberId);
		if (selectMember != null && selectMember.getName() == null) {
			if (!JuheUtils.verifyPhone(mobile, name)) throw new ServiceException(ResultCode.NAME_PHONE_VALIDATION_ERROR);
			MemberInfo model = new MemberInfo();
			model.setId(memberId);
			model.setName(name);
			model.setSex(sex);
			memberInfoMapper.updateByID(model);
		}
		return selectMember.getUserId();
	}

	@Override
	public void activeSendSms(String mobile, String name) throws ServiceException {
		if (!JuheUtils.verifyPhone(mobile, name)) throw new ServiceException(ResultCode.NAME_PHONE_VALIDATION_ERROR);
		int res = sendSms(mobile, 1);
		if (res == -1) throw new ServiceException(ResultCode.VERIFICATION_CODE_SENT_OVER_A_NUMBER_OF);
		else if (res == -2) throw new ServiceException(ResultCode.VERIFICATION_CODE_SENT_FAILED);
		else if (res != 1) throw new ServiceException(ResultCode.EXCEPTION_ERROR);
	}

	@Override
	public MemberInfo autoLogin(Integer userId) throws ServiceException {
		if (userId == null) throw new ServiceException(ResultCode.PARAMETER_ERROR);
		
		User user = userService.findByID(userId);
		if (user == null) 
			throw new ServiceException(ResultCode.NO_DATA);
		if (user.getStatus() != BaseEnum.Status.BASE_STATUS_ENABLE.getValue())
			throw new ServiceException(ResultCode.USER_FORBBIDEN);
		
		return findMemberInfo(userId);
	}

}

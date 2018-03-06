package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.MemberInfo;

import net.sf.json.JSONObject;

public interface MemberInfoService extends GenericService<MemberInfo, Integer> {

	public MemberInfo findMemberInfo(Integer userId);

	public MemberInfo findByOpenId(String openId);
	
	public MemberInfo findByOrderId(Integer orderId);

	/**
	 * 手机发送手机验证码
	 * @param mobile 发送的手机
	 * @param type 发送方式，1是短信，2是语音
	 * @return {@link int}
	 */
	public int sendSms(String mobile, Integer type);

	/**
	 * 用户根据验证码注册登录
	 * @param code
	 * @param mobile
	 * @return
	 */
	public int createSmsLogin(String code, String mobile) throws ServiceException;

	/**
	 * 会员添加基本信息
	 * @param memberId
	 * @param sex
	 * @param address
	 * @return
	 */
	public int addEssentialInformation(Integer memberId, String name, Integer sex, String address, String bankMobile);

	/**
	 * 身份证信息添加
	 * @param memberId
	 * @param idcardPic1
	 * @param idcardPic2
	 * @param name
	 * @param idcard
	 * @return
	 */
	public int addIdAuthentication(Integer memberId, String cardPic1, String cardPic2, String name, String idcard);

	/**
	 * 银行卡号信息添加
	 * @param memberId
	 * @param bankCard
	 * @param name
	 * @return
	 */
	public int addBankCard(Integer memberId, String bankCard, String name) throws ServiceException ;

	public MemberInfo updateMemberOptionalInformation(MemberInfo memberInfo) throws ServiceException;
	
	/**
	 * 计算用户完成资料的评分
	 */
	public double caculateMemberInfoScore(Integer userId);
	
	/**
	 * 计算用户能量柱分数
	 * 三项合计
	 */
	public double caculateScoreTotal(Integer userId);
	
	/**
	 * 退出登录
	 */
	public void logout(Integer userId) throws ServiceException ;
	
	/**	用户端查询用户信息 */
	MemberInfo selectMember(Integer memberId) throws ServiceException;
	
	/**
	 * 上传个推id
	 */
	void uploadGetuiId(Integer userId, String getui) throws ServiceException;
	
	List<MemberInfo> selectListByCondition(Integer startpos, Integer pagesize,  MemberInfo memberInfo);
	
	PageInfo selectListCount(Integer pageSize, MemberInfo memberInfo);

	/** 单独查询身份证信息 */
	MemberInfo selectMemberIdcardInfo(Integer memberId);

	/**
	 * 用户通过隐私电话联系金鹰
	 * @param memberMobile
	 * @param crossMobile
	 * @param method (0 解绑; 1 绑定)
	 * @return
	 */
	public JSONObject anonymousCall(String memberMobile, String crossMobile, Integer method);

	public void sharedSignup(String mobile, String code, Integer sex, String name, String identifying) throws ServiceException;

	public void sharedSendSms(String mobile, String name, String identifying) throws ServiceException;

	public int updateActiveBind(String mobile, String code, String name, Integer sex) throws ServiceException;

	public void activeSendSms(String mobile, String name) throws ServiceException;

	public MemberInfo autoLogin(Integer userId) throws ServiceException;

}

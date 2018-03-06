package com.kunyao.assistant.core.constant;

public class Constant {

	/**
	 * 管理员用密码加密秘钥
	 */
	public static final String USER_PASSWORD_SECRET = "-Assistant@Kun#yao.www.COM";

	public static final String LOGIN_MEMBER = "login_member";
	public static final String LOGIN_CROSS = "login_cross";
	
	/**
	 * 登录后 session存贮的 Key
	 */
	public static final String LOGIN_USER = "login_user";
	
	/**
	 * 认证签名加密Key
	 */
	public static final String AUTH_SIGN_KEY = "GeNing@Ai#Qing1314.";
	
	/**
	 * 权限标识
	 */
	public static final String ROLE_SIGN_ADMIN = "admin"; // 管理员
	public static final String ROLE_SIGN_MEMBER = "member"; // 会员
	public static final String ROLE_SIGN_CROSS = "cross"; // 金鹰
	public static final String ROLE_SIGN_CUSTOMER = "customer"; // 客服
	public static final String ROLE_SIGN_RES_DEV_PRACTICE = "res_dev_practice"; // 资源开发人-实习
	public static final String ROLE_SIGN_RES_DEV_FULL = "res_dev_full"; // 资源开发人-全职
	public static final String ROLE_SIGN_RES_MANAGE_CITY = "res_manage_city"; // 资源管理人-城市
	public static final String ROLE_SIGN_RES_MANAGE_ALL = "res_manage_all"; // 资源管理人-全国
	public static final String ROLE_SIGN_MANAGE_NORMAL = "normal_manage"; // 普通管理员
	
	public static final Double MIN_BALANCE_PER_DAY = 10000.0; // 金鹰代付时 用户预存款每天保底金额1万元
	
	public static volatile boolean isLoop = false; // 是否正在轮训订单
	
	public static final String TIME_BOUNDARY = " 16:30:00";  // 延时费判定，下单时间分界，在这之后下单当天不需要延时费
	
	// 琨瑶客服电话
	public static final String KUNYAO_KEFU_PHONE = "";
	
	public static String CMBC_PUBLIC_KEY = "";
	public static String CMBC_PUBLIC_KEY_TIME = "";
}

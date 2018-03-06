package com.kunyao.assistant.core.constant;

public interface ResultCode {

	/*
	 *  系统级错误码
	 */
	static final String SUCCESS = "0";
    static final String ABNORMAL_REQUEST = "10001";    // 非法请求，缺少接口访问必要参数
    static final String REQUEST_TIMEOUT  = "10002";    // URL已过期
    static final String TOKEN_INVALID    = "10003";    // Token失效
    static final String SIGN_ERROR       = "10004";    // 签名错误
    static final String UID_INVALID      = "10005";    // UID无效

    /*
     *  功能级别错误码
     */
    static final String DB_ERROR = "20000";
    static final String NO_DATA = "20001";

	/*
	 * 业务级别错误码
	 */
	static final String NO_LOGIN = "30001"; // 未登录
	static final String NO_CERTIFIED = "30002"; // 未实名认证
	static final String NO_IDCARD = "30003"; // 身份证未认证
	static final String NO_BANK = "30004"; // 银行卡未认证
	static final String BALANCE_ENOUGH = "30005"; // 预存款不足
	static final String SERVICE_MONEY_CONFIG = "30006"; // 服务费未配置
	static final String DATE_FORMAT_ERROR = "30007"; // 时间格式错误
	static final String FILE_NULL = "30008"; // 文件为空
	static final String FILE_CREATE_ERROR = "30009"; // 图片创建失败
	static final String LOGIN_ERROR = "30010"; // 用户名或密码错误
	static final String TRAVEL_NOT_ALLOW_OPERATE = "30011"; // 行程单已确认，不可再编辑修改
	static final String TRAVEL_REMOVE_ERROR = "30012"; // 行程单下有账单不可以被删除
	static final String TRAVEL_NOT_FOUND = "30013"; // 无行程单
	static final String COST_NOT_FOUND = "30014"; // 未查询到账单
	static final String ALREADY_COMMENT = "30015"; // 已经评价过
	static final String PASSWORD_WRONG = "30016"; // 原密码错误
	static final String TRAVEL_NOT_ENOUGH = "30017"; // 请给每天的行程添加行程单
	static final String BANK_WRONG = "30018"; // 银行卡和持卡人不匹配
	static final String PARAMETER_TYPEMISMATCH = "30019";    // 参数类型错误
	static final String USER_EXIST = "30020";	// 用户已存在
	static final String USER_FORBBIDEN = "30021"; // 用户被禁用
	static final String ORDER_STATUS_NEGATIVE = "31001"; // 订单状态不支持该操作
	static final String HAVE_ORDER_NOT_FINISH = "31002"; // 有未完成订单(不包括未评价)
	static final String TRAVEL_DAYS_OVERFLOW = "31003"; // 行程天数超出限制
	static final String HAVE_ORDER_NOT_COMMENT = "31004"; // 有未评价订单
	static final String TIME_ERROR = "31005"; // 用户端时间与服务器时间不匹配
	static final String ORDER_START_TIME_ILLEGAL = "31006"; // 订单开始时间超过当天19:00
	static final String DOUBT_EXIST = "32001"; // 该疑议已提交过
	static final String NOT_ORDER_IN_SERVICE = "32002"; // 不是服务中的订单
	static final String ACTIVITY_ALREADY_JOIN = "32003"; // 已经参加过活动
	static final String ACTIVITY_NOT_START = "32004"; // 活动未开始
	static final String ACTIVITY_EXPIRE = "32005"; // 活动已过期
	static final String ACTIVITY_TOKEN_ERROR = "32006"; // 二维码错误
	static final String ACTIVITY_USERD = "32007"; // 二维码失效
	static final String SERVICE_CODE_ERROR = "32008"; // 服务编码输入错误
	static final String CONPON_MONEY_OVERTOP = "32009"; // 优惠券金额超出
	static final String SERVICE_CODE_ORDER_WRONG_STATUS = "32010"; // 请添加行程并等待用户确认行程之后再输入服务编码
	
	/*
	 * 后台错误码
	 */					
	static final String PARAMETER_ERROR = "40001";    // 参数验证错误
	static final String EXCEPTION_ERROR = "40002";    // 服务端执行异常
	static final String LOAD_PICTURE_ERROR = "40003";       // 图片上传失败
	static final String INFORMATION_QUERY_ERROR = "40004";       // 信息查询错误
	static final String UNKNOWN_ERROR = "40007";       // 未知错误
	static final String REGISTRATION_IS_NOT_ALLOWED = "40008";   // 不允许注册
	static final String VERIFICATION_CODE_ERROR = "40009";       // 验证码错误
	static final String VERIFICATION_CODE_TIMEOUT = "40010";     // 验证码超时
	static final String NAME_PHONE_VALIDATION_ERROR = "40011";     // 姓名与注册手机验证错误
	static final String NAME_IDCARD_VALIDATION_ERROR = "40012";     // 姓名与身份证验证错误
	static final String BANDCARD_NAME_VALIDATION_ERROR = "40013";     // 银行卡号与姓名不一致
	static final String VERIFICATION_CODE_SENT_FAILED = "40014";     // 验证码发送失败
	static final String VERIFICATION_CODE_SENT_OVER_A_NUMBER_OF = "40015";     // 同一手机一小时内只能发送3次验证码,一天内10次
	static final String CITY_OFF_STATE = "40016";     // 判断城市关闭状态出错状态码
	static final String DELETE_PICTURE_ERROR = "40017";     // 图片删除失败
	static final String CROSS_UPDATE_STATUS = "40018";     // 下架金鹰时，判断他是否有服务订单
	static final String MOBILE_PHONE_ALREADY_EXISTS = "40019";     // 手机已存在
	static final String ORDERS_ARE_BEING_WHEEL_GUARD = "40020";    // 订单正在轮训中
	static final String REPEATED_SUBMISSION = "40021";             // 重复提交
	static final String LOOP_TIME_OVER_SCOPE = "40022";    // 轮训时间只能在1-59分钟内
	static final String LOOP_TIME_CHANGE_SUCCESS = "40023";   // 服务器重启中，请稍等
	static final String USERNAME_EXIST = "40024";   // 用户名已存在
	
}

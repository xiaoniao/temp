package com.kunyao.assistant.core.feature.weixin;

/**
 * 微信配置
 * @author GeNing
 * @since  2016.08.23
 */
public class WxSettings {
	
	/**
	 * 微信公众号 APPID
	 */
	public final static String APP_ID = "wx73476ea6f0eab76e";
	
	/**
	 * 微信公众号 Secret
	 */
	public final static String APP_SECRET   = "346658062ab1df3bf2dce5d9640633e0";
	
	/**
	 * 微信商户平台  商户号
	 */
	public final static String BUSINESS_ID  = "1418399402";
	
	/**
	 * 微信商户平台 商户秘钥，由自己编辑生成
	 */
	public final static String BUSINESS_KEY = "hang1zhou2kun3yao4shang5wu6fu7wu";
	
	/**
	 * 获取 JsAPI时 所以来的 Token 相关定义
	 */
	public static long GET_TIME = 0;                         // 上次获取 微信 Ticket 的时间
	public static int  VALID_TIME = 7180 * 1000;             // 微信 Ticket 的有效时间
	public static String ACCESS_TOKEN = "";                  // 已获取到的 ACCESS_TOKEN
	public static String JSAPI_TICKET = "";                  // 已获取到的 JSAPI_TICKET
	
	/**
	 * 微信统一支付时使用的API形式
	 */
	public final static String WEIXIN_TRADE_TYPE = "JSAPI";
	
	
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/************************************************************ 微信URL 配置 ******************************************************/
	/*******************************************************************************************************************************/
	/*******************************************************************************************************************************/
	/**
	 * 获取 Token  链接（用于调用JSAPI接口）
	 */
	public final static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APP_ID + "&secret=" + APP_SECRET;
	
	/**
	 * 获取 JSAPI_Ticket 链接
	 */
	public final static String GET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={1}&type=jsapi";
	
	/**
	 * 获取 OpenId 链接
	 */
	public final static String GET_OPEN_ID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
	
	/**
	 * 获取 用户信息
	 */
	public static final String GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
	
	/**
	 * 微信统一支付链接（参数传递 XML）
	 */
	public final static String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 支付完成后微信的回调链接
	 */
	public final static String WEIXIN_PAY_NOTIFY_URL = "http://app.cityjinying.com/rest/mc/wx/pay_success/";
	
	/**
	 * 微信统支付已生成订单查询
	 */
	public final static String QUERY_ORDER = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	/**
	 * 发送微信模板消息 
	 */
	public final static String WX_TEMPLATE_MESG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
}

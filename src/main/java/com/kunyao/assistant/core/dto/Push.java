package com.kunyao.assistant.core.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.feature.sms.YunPianSMS;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 推送
 */
public class Push {

	private String cid; // 个推id
	private String mobile; // 手机号
	private String title; // 标题
	private String subTitle; // 副标题
	private String content; // 内容
	private Integer userId; // userid
	private String userType; // 金鹰还是用户
	private String tplId; // 短信推送模版id
	private String tplValue; // 短信推送内容
	private String type = "notify"; // notify push 默认notiry通知 push是透传content是json

	private Push() {
		
	}

	private Push(Integer userId, String cid, String mobile, String title, String subTitle, String content, String userType, String tplId, String tplValue) {
		this.userId = userId;
		this.cid = cid;
		this.mobile = mobile;
		this.title = title;
		this.subTitle = subTitle;
		this.content = content;
		this.userType = userType;
		this.tplId = tplId;
		this.tplValue = tplValue;
	}

	/**
	 * 创建用户推送 
	 * @param cid 不为null有app推送
	 * @param mobile 不为null有发送短信
	 */
	private static Push createClientPush(Integer userId, String cid, String mobile, String title, String subTitle, String content, String tplId, String tplValue) {
		return new Push(userId, cid, mobile, title, subTitle, content, "member", tplId, tplValue);
	}

	/**
	 * 创建金鹰推送 
	 * @param cid 不为null有app推送
	 * @param mobile 不为null有发送短信
	 */
	private static Push createCrossPush(Integer userId, String cid, String mobile, String title, String subTitle, String content, String tplId, String tplValue) {
		return new Push(userId, cid, mobile, title, subTitle, content, "cross", tplId, tplValue);
	}
	
	/**
	 * 接单成功，金鹰服务
	 */
	public static Push createTemplate1(Integer userId, String cid, String mobile, Order order, CrossInfo crossInfo) {
		String title    = "金鹰#{crossNumber}已接单";
		String subTitle = "金鹰#{crossNumber}已接单，城侍金鹰竭诚为您服务。";
		String content  = "尊敬的贵宾，您好！您的订单#{orderCard}已预定成功。城市：#{city}；行程日期：#{startDate}至#{endDate}。" + 
						  "金鹰#{crossNumber}已接单，城侍金鹰竭诚为您服务。 如需调整、退订或其他特殊要求，欢迎致电客服热线#{phone}。祝您旅途愉快！";
		title    =    title.replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber());
		subTitle = subTitle.replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber());
		content  =  content.replaceAll("#\\{orderCard\\}", order.getOrderCard())
						   .replaceAll("#\\{city\\}", order.getCityName())
						   .replaceAll("#\\{startDate\\}", DateUtils.parseYMDTime(order.getStartTime()))
						   .replaceAll("#\\{endDate\\}", DateUtils.parseYMDTime(order.getEndTime()))
						   .replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber())
						   .replaceAll("#\\{phone\\}", Constant.KUNYAO_KEFU_PHONE);

		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#content#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(content, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677504, tplValue);
	}	
	
	/**
	 * 接单失败，全额退款
	 */
	public static Push createTemplate2(Integer userId, String cid, String mobile, Order order, CrossInfo crossInfo, String score) {
		String title    = "金鹰#{crossNumber}接单失败";
		String subTitle = "金鹰#{crossNumber}接单失败，您可重新选择服务金鹰";
		String content  = "尊敬的贵宾，您好！您的订单#{orderCard}预订失败。金鹰#{crossNumber}被邀约能量#{score}分的贵宾优先预约，" + 
						  "本次订单金额已全额退款至您的“钱包”。对您造成的不便敬请谅解，城侍金鹰期待您的再次预约！";
		title    =    title.replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber());
		subTitle = subTitle.replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber());
		content  =  content.replaceAll("#\\{orderCard\\}", order.getOrderCard())
						   .replaceAll("#\\{score\\}", score)
						   .replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber());

		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#ddbh#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(order.getOrderCard(), YunPianSMS.ENCODING);
			tplValue += URLEncoder.encode("#jybh#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(crossInfo.getCrossNumber(), YunPianSMS.ENCODING);
			tplValue += URLEncoder.encode("#nlz#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(score, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677516, tplValue);
	}
	
	/**
	 * 通知金鹰接单
	 */
	public static Push createTemplate3(Integer userId, String cid, String mobile, Order order) {
		String title    = "新订单";
		String subTitle = "有一新订单，请及时处理";
		String content  = "亲爱的城侍金鹰，您好！您有一新订单，订单号：#{orderCard}。请及时进入工作流程，祝您工作愉快！";
		content  =  content.replaceAll("#\\{orderCard\\}", order.getOrderCard());
		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#ddbh#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(order.getOrderCard(), YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createCrossPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677590, tplValue);
	}
	
	/**
	 * 通知金鹰接单 透传消息
	 */
	public static Push createTemplate3_2(Integer userId, String cid, String mobile, Order order) {
		String title    = "新订单";
		String subTitle = "有一新订单，请及时处理";
		String content  = String.valueOf(order.getId());
		String tplValue = "";
		Push push = Push.createCrossPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677590, tplValue);
		push.setType("push");
		return push;
	}
	
	/**
	 * 金鹰电话沟通后提交行程，通知用户确认行程
	 */
	public static Push createTemplate4(Integer userId, String cid, String mobile, Order order, CrossInfo crossInfo) {
		String title    = "金鹰#{crossNumber}已为您安排好行程";
		String subTitle = "金鹰#{crossNumber}已为您安排好行程，您可在行程也查看并确认。";
		String content  = "尊敬的贵宾，您好！您的订单号：#{orderCard}。城市：#{city}；行程日期：#{startDate}至#{endDate}。金鹰#{crossNumber}已为您安排好行程，" + 
						  "您可在行程也查看并确认。如需调整、退订或其他特殊要求，欢迎致电客服热线#{phone}。祝您旅途愉快！";
		title    =    title.replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber());
		subTitle = subTitle.replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber());
		content  =  content.replaceAll("#\\{orderCard\\}", order.getOrderCard())
						   .replaceAll("#\\{city\\}", order.getCityName())
						   .replaceAll("#\\{crossNumber\\}", crossInfo.getCrossNumber())
						   .replaceAll("#\\{startDate\\}", DateUtils.parseYMDTime(order.getStartTime()))
						   .replaceAll("#\\{endDate\\}", DateUtils.parseYMDTime(order.getEndTime()))
						   .replaceAll("#\\{phone\\}", Constant.KUNYAO_KEFU_PHONE);
		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#content#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(content, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677504, tplValue);
	}
	
	/**
	 * 金鹰结束一天服务，提交账单
	 */
	public static Push createTemplate5(Integer userId, String cid, String mobile, Order order, CrossInfo crossInfo) {
		String title    = "今日行程账单已出单";
		String subTitle = "今日行程账单已出单，您可前往“行程”页面查看并确认";
		String content  = "今日行程所产生的费用清单已出单，您可前往“行程”页面查看并确认。";

		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#content#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(content, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677518, tplValue);
	}
	
	/**
	 * 后台身份证审核不通过
	 */
	public static Push createTemplate6(Integer userId, String cid, String mobile) {
		String title    = "身份证照片不符合要求";
		String subTitle = "您上传的身份证照片不符合要求，请到“个人资料”页面重新上传身份证照片审核。";
		String content  = "尊敬的贵宾，您好！我们很遗憾地通知您，您上传的身份证照片不符合要求，请到“个人资料”页面重新上传身份证照片审核。" +
						  "温馨提示：请将身份证置于照片正中位置，照片模糊、光线过暗、图片格式不正确等都有可能导致审核无法通过。";

		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677500, null);
	}
	
	/**
	 * 用户取消订单或提前结束行程
	 */
	public static Push createTemplate7(Integer userId, String cid, String mobile, Double accountRefund, String orderCard) {
		String title    = "退款完成";
		String subTitle = "总计金额#{accountRefund}元已完成退款，您可前往您的“钱包”进行查看。";
		String content  = "尊敬的贵宾，您好！您的订单号：#{orderCard}。由于您的行程调整，总计金额#{accountRefund}元已完成退款，您可前往您的“钱包”进行查看。期待您再次选择城侍金鹰。"
						+ "如有其他疑问欢迎拨打客服热线" + Constant.KUNYAO_KEFU_PHONE + "。城侍金鹰祝您旅途愉快！";

		subTitle = subTitle.replaceAll("#\\{accountRefund\\}", String.valueOf(accountRefund));
		content  =  content.replaceAll("#\\{orderCard\\}", orderCard)
						   .replaceAll("#\\{accountRefund\\}", String.valueOf(accountRefund));

		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#content#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(content, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677518, tplValue);
	}
	
	/**
	 * 提示用户需要支付延时费用
	 */
	public static Push createTemplate8(Integer userId, String cid, String mobile, String date, Double delayMoney, String crossNumber) {
		String title    = "延长服务时间通知";
		String subTitle = "如需延长服务，需支付延时费用。";
		String content  = "尊敬的贵宾，您好！当前时间已为晚上#{date}，劳累了一天请您注意休息，如需延长服务，需支付延时费#{delayMoney}元/小时（不足1小时按1小时计）。" +
						  "温馨提示：请注意与金鹰#{crossNumber}确认延时服务；21:00-24:00可随时结束服务，24:00城侍金鹰将自动结束服务。";

		content = content.replaceAll("#\\{date\\}", date)
							.replaceAll("#\\{delayMoney\\}", delayMoney.toString())
							.replaceAll("#\\{crossNumber\\}", crossNumber);
		
		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#ysf#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(delayMoney.toString(), YunPianSMS.ENCODING);
			tplValue += URLEncoder.encode("#jybh#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(crossNumber, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677538, tplValue);
	}
	
	/**
	 * 提示用户确认账单
	 */
	public static Push createTemplate9(Integer userId, String cid, String mobile, String orderCard) {
		String title    = "您有一笔订单交易未确认";
		String subTitle = "您有一笔订单交易未确认，请尽快确认";
		String content  = "尊敬的贵宾，您好！您的订单#{orderCard}，需要您及时点击“确认账单”，如未确认，系统将在次日22:00自动扣款。感谢您对城侍金鹰的选择与支持，祝您旅途愉快！";

		content  =  content.replaceAll("#\\{orderCard\\}", orderCard);

		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#content#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(content, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677518, tplValue);
	}
	
	/**
	 * 客服处理完成客户账单存疑的问题
	 * TODO还没有被调用
	 */
	public static Push createTemplate10(Integer userId, String cid, String mobile, String orderCard) {
		String title    = "客服已为您处理好存疑的账单";
		String subTitle = "客服已为您处理好存疑的账单，您可前往“行程”页面查看并确认";
		String content  = "您提交的存疑账单客服已为您处理完毕，您可前往“行程”页面查看并确认，为此给您带来的不变我们深感抱歉。";

		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#content#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(content, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677570, tplValue);
	}
	
	/**
	 * 每天22:00自动确认前一天账单后推送给客户
	 */
	public static Push createTemplate11(Integer userId, String cid, String mobile, String orderCard, String date) {
		String title    = "账单已确认";
		String subTitle = "您在#{date}的账单已系统自动确认";
		String content  = "尊敬的贵宾，您好！您的订单#{orderCard}，在#{date}的行程账单已由系统自动确认。感谢您对城侍金鹰的选择与支持，祝您旅途愉快！";

		subTitle = subTitle.replaceAll("#\\{date\\}", date);
		content  =  content.replaceAll("#\\{orderCard\\}", orderCard)
						   .replaceAll("#\\{date\\}", date);

		String tplValue = null;
		try {
			tplValue = URLEncoder.encode("#content#", YunPianSMS.ENCODING) +"=" + URLEncoder.encode(content, YunPianSMS.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Push.createClientPush(userId, cid, mobile, title, subTitle, content, YunPianSMS.TPL_ID_1677518, tplValue);
	}
	
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTplId() {
		return tplId;
	}

	public void setTplId(String tplId) {
		this.tplId = tplId;
	}

	public String getTplValue() {
		return tplValue;
	}

	public void setTplValue(String tplValue) {
		this.tplValue = tplValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Push [cid=" + cid + ", mobile=" + mobile + ", title=" + title + ", subTitle=" + subTitle + ", content="
				+ content + ", userId=" + userId + ", userType=" + userType + ", tplId=" + tplId + ", tplValue="
				+ tplValue + ", type=" + type + "]";
	}
}

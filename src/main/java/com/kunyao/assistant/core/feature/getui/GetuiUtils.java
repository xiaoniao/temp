package com.kunyao.assistant.core.feature.getui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.google.gson.Gson;
import com.kunyao.assistant.core.dto.Push;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.Order;

/**
 * 个推推送
 */
public class GetuiUtils {
	private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

	// 用户端个推配置
	public static final String C_AppID = "z5kf8dgDC18JEZ5HQB7c98";
	public static final String C_AppSecret = "J53ESjpt9bALrdW0w9a8M9";
	public static final String C_AppKey = "D9Mb7GkwBh5I2zuEP4vRx3";
	public static final String C_MasterSecret = "phNax8RnbI682qM4Bu0KY4";

	// 金鹰端个推配置
	public static final String J_AppID = "FklI7LvfaZ67ARePtB3Vx2";
	public static final String J_AppSecret = "pf2e9AY20JAIahmkl0ULs9";
	public static final String J_AppKey = "tUqwCJ4RNX9LoITIMyLq1A";
	public static final String J_MasterSecret = "4V4dJJHiKe7RWX5sHo3gY5";
	
	
	public static void main(String getui) {
		CrossInfo crossInfo = new CrossInfo();
		crossInfo.setCrossNumber("11111");
		Order order = new Order();
		order.setId(111);
		try {
			pushToClient(Push.createTemplate3_2(30, getui, "176008122217", order));
			pushToCross(Push.createTemplate3_2(30, getui, "176008122217", order));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/********************************************** 用户 ****************************************************/
	
	/**
	 * 用户端推送
	 */
	public static boolean pushToClient(Push pushDto) throws IOException {
		pushToClientTransmission(pushDto); // 透传推送
		
		IGtPush push = new IGtPush(url, C_AppKey, C_MasterSecret);
		push.connect();
		
		ListMessage message = new ListMessage();
		message.setData(notificationTemplateDemo(C_AppID, C_AppKey, pushDto.getTitle(), pushDto.getSubTitle()));
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);

		// 配置推送目标
		List<Target> targetList = new ArrayList<Target>();
		Target target = new Target();
		target.setAppId(C_AppID);
		target.setClientId(pushDto.getCid());
		targetList.add(target);

		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targetList);
		if (ret.getResponse().get("result").equals("ok")) {
			return true;
		}
		return false;
	}
	
	static class IOSType {
		private String type;
		private String title;
		private String content;
		private Integer orderId;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Integer getOrderId() {
			return orderId;
		}
		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}
	}
	
	/**
	 * 推送给用户 透传消息推送
	 */
	public static boolean pushToClientTransmission(Push pushDto) throws IOException {
		IGtPush push = new IGtPush(url, C_AppKey, C_MasterSecret);
		push.connect();
		
		ListMessage message = new ListMessage();
		
		// 兼容ios
		if (pushDto != null) System.out.println(pushDto);
		IOSType iosType = new IOSType();
		iosType.setType(pushDto.getType());
		iosType.setTitle(pushDto.getTitle());
		if (pushDto.getType().equals("notify")) {
			iosType.setContent(pushDto.getSubTitle());
		} else {
			iosType.setOrderId(Integer.valueOf(pushDto.getContent()));
			iosType.setContent(pushDto.getSubTitle());
		}
		message.setData(transmissionTemplateDemo(C_AppID, C_AppKey, iosType));
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);

		// 配置推送目标
		List<Target> targetList = new ArrayList<Target>();
		Target target = new Target();
		target.setAppId(C_AppID);
		target.setClientId(pushDto.getCid());
		targetList.add(target);

		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targetList);
		if (ret.getResponse().get("result").equals("ok")) {
			return true;
		}
		return false;
	}
	
	/********************************************** 金鹰 ****************************************************/

	/**
	 * 推送给金鹰 通知栏推送
	 */
	public static boolean pushToCross(Push pushDto) throws IOException {
		pushToCrossTransmission(pushDto); // 透传消息
		
		IGtPush push = new IGtPush(url, J_AppKey, J_MasterSecret);
		push.connect();
		
		ListMessage message = new ListMessage();
		message.setData(notificationTemplateDemo(J_AppID, J_AppKey, pushDto.getTitle(), pushDto.getSubTitle()));
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);

		// 配置推送目标
		List<Target> targetList = new ArrayList<Target>();
		Target target = new Target();
		target.setAppId(J_AppID);
		target.setClientId(pushDto.getCid());
		targetList.add(target);

		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targetList);
		if (ret.getResponse().get("result").equals("ok")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 推送给金鹰 透传消息推送
	 */
	public static boolean pushToCrossTransmission(Push pushDto) throws IOException {
		IGtPush push = new IGtPush(url, J_AppKey, J_MasterSecret);
		push.connect();
		
		// 兼容ios
		IOSType iosType = new IOSType();
		iosType.setType(pushDto.getType());
		iosType.setTitle(pushDto.getTitle());
		if (pushDto.getType().equals("notify")) {
			iosType.setContent(pushDto.getSubTitle());
		} else {
			iosType.setOrderId(Integer.valueOf(pushDto.getContent()));
			iosType.setContent(pushDto.getSubTitle());
		}
		ListMessage message = new ListMessage();
		message.setData(transmissionTemplateDemo(J_AppID, J_AppKey, iosType));
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);

		// 配置推送目标
		List<Target> targetList = new ArrayList<Target>();
		Target target = new Target();
		target.setAppId(J_AppID);
		target.setClientId(pushDto.getCid());
		targetList.add(target);

		String taskId = push.getContentId(message);
		IPushResult ret = push.pushMessageToList(taskId, targetList);
		if (ret.getResponse().get("result").equals("ok")) {
			return true;
		}
		return false;
	}
	
	/********************************************** 工具类 ****************************************************/

	/** 通知模版  **/
	public static NotificationTemplate notificationTemplateDemo(String appId, String appKey, String title, String content) {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionType(1); // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionContent("");
		
		Style0 style = new Style0();
		style.setTitle(title); // 设置通知栏标题与内容
		style.setText(content);
		style.setLogo("icon.png"); // 配置通知栏图标
		style.setLogoUrl(""); // 配置通知栏网络图标
		style.setRing(true); // 设置通知是否响铃，震动，或者可清除
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		return template;
	}
	
	/** 透传消息模版 **/
	public static TransmissionTemplate transmissionTemplateDemo(String appId, String appKey, IOSType iosType) {
		TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(1);
	    template.setTransmissionContent(new Gson().toJson(iosType));
	    
	    APNPayload payload = new APNPayload();
	    payload.setAutoBadge("+1");
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory("$由客户端定义");
	    payload.setAlertMsg(getDictionaryAlertMsg(iosType));
	    payload.addCustomMsg("title", iosType.getTitle());
	    payload.addCustomMsg("type", iosType.getType()); 
	    payload.addCustomMsg("orderId", iosType.getOrderId());
	    payload.addCustomMsg("content", iosType.getContent());
	    template.setAPNInfo(payload);
	    
		return template;
	}
	
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(IOSType iosType){
	    APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    alertMsg.setBody(iosType.getContent());
	    alertMsg.setActionLocKey("ActionLockey");
	    alertMsg.setLocKey("LocKey");
	    alertMsg.addLocArg("loc-args");
	    alertMsg.setLaunchImage("launch-image");
	    // iOS8.2以上版本支持
	    alertMsg.setTitle(iosType.getTitle());
	    alertMsg.setTitleLocKey("TitleLocKey");
	    alertMsg.addTitleLocArg("TitleLocArg");
	    return alertMsg;
	}
}

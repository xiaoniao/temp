package com.kunyao.assistant.core.feature.aop;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderLog;
import com.kunyao.assistant.web.service.OrderLogService;


/**
 * 订单操作日志记录
 * @author GeNing
 * @since  2016.06.09
 */
@Aspect
public class OrderLogAspect {
	
	@Resource
	private OrderLogService orderLogService;

	/**
	 * 添加切入点 
	 */					   
	@Pointcut("execution(* com.kunyao.assistant.web.service..OrderService.*(..))")
	public void addOrderLog() { }
	
	@AfterReturning(value="addOrderLog()", argNames="rtv", returning="rtv")
    public void addOrderLogCall(JoinPoint joinPoint, Object rtv) throws Throwable {
		
//		Object[] params = joinPoint.getArgs();
		String methodName = joinPoint.getSignature().getName();
		
		Integer orderId = 0;
		String  userName = null;
		String  logInfo = "";
		
		
		if (methodName.equals("createOrder")) {
			
			if (rtv == null)
				return;
			
			Order model = (Order) rtv;
			orderId = model.getId();
			userName = OrderEnum.LogOperatUser.MEMBER.getMText();
			logInfo = "提交新订单";
			
		}  else if (methodName.equals("updateEndComplaintOrder")) {
			
			Object[] params = joinPoint.getArgs();
			orderId = Integer.parseInt(String.valueOf(params[0]));
			
			userName = OrderEnum.LogOperatUser.MANAGER.getMText();
			logInfo = "管理员结束用户投诉订单";
			
		}  else if (methodName.equals("updateEndComplaintOrder")) {
			
			Object[] params = joinPoint.getArgs();
			orderId = Integer.parseInt(String.valueOf(params[0]));
			
			userName = OrderEnum.LogOperatUser.MANAGER.getMText();
			logInfo = "管理员结束用户投诉订单";
			
		}  /*else if (methodName.equals("updateEndService")) {
			
			if (rtv == null)
				return;
			
			Order model = (Order) rtv;
			orderId = model.getId();
			userName = OrderEnum.LogOperatUser.MANAGER.getMText();
			logInfo = "管理员结束金鹰取消订单";
			
		} else if (methodName.equals("cannelOrder")) {
			
			int result = Integer.parseInt(String.valueOf(rtv));
			if (result < 1)
				return;
			
			orderId = Integer.parseInt(String.valueOf(params[0]));
			operatType = OrderEnum.LogOperatType.MEMBER_CANNEL.getValue();
			userId = Integer.parseInt(String.valueOf(params[1]));
			operatNote = "取消订单";
			
		} else if (methodName.equals("applyRefund")) {
			
			int result = Integer.parseInt(String.valueOf(rtv));
			if (result < 1)
				return;
			
			orderId = Integer.parseInt(String.valueOf(params[0]));
			operatType = OrderEnum.LogOperatType.MEMBER_APPLY_REFUND.getValue();
			userId = Integer.parseInt(String.valueOf(params[1]));
			operatNote = "申请退款";
			
			// 查询订单信息
			Order model = new Order();
			model.setId(orderId);
			List<Order> modelList = orderService.selectList(null, null, model);
			if (modelList != null && modelList.size() > 0)
				model = modelList.get(0);
			// 生成微信模板对象
			WxTemplate template = WxTemplate.createApplyRefundTemlate(model);
			// 刷新微信Token
			WxMethod.refereshWXToken();
			// 发送微信模板消息
			WxService.sendWxTemplate(template);
			
		} else if (methodName.equals("orderReceive")) {
			
			int result = Integer.parseInt(String.valueOf(rtv));
			if (result < 1)
				return;
			
			HttpServletRequest request = (HttpServletRequest) params[0];
			orderId = Integer.parseInt(String.valueOf(params[1]));
			String subCard = String.valueOf(params[2]);
			
			User loginUser = (User) request.getSession().getAttribute(CommonFinal.LOGIN_USER);
			userId = loginUser.getId();
			username = loginUser.getUsername();
			if (loginUser.getType().equals(UserEnum.Type.ADMIN)) {
				operatType = OrderEnum.LogOperatType.ADMIN_RECEIVE.getValue();
				operatNote = "管理员代替酒店确认订单, 订单确认号：" + subCard;
			} else if (loginUser.getType().equals(UserEnum.Type.HOTEL)) {
				operatType = OrderEnum.LogOperatType.HOTEL_RECEIVE.getValue();
				operatNote = "酒店确认订单, 订单确认号：" + subCard;
			}
			
			// 查询订单信息
			Order model = new Order();
			model.setId(orderId);
			List<Order> modelList = orderService.selectList(null, null, model);
			if (modelList != null && modelList.size() > 0)
				model = modelList.get(0);
			// 生成微信模板对象
			WxTemplate template = WxTemplate.createConfirmOrderTemlate(model);
			// 刷新微信Token
			WxMethod.refereshWXToken();
			// 发送微信模板消息
			WxService.sendWxTemplate(template);
			
		} else if (methodName.equals("orderRefused")) {
			int result = Integer.parseInt(String.valueOf(rtv));
			
			if (result < 1)
				return;
			
			HttpServletRequest request = (HttpServletRequest) params[0];
			orderId = Integer.parseInt(String.valueOf(params[1]));
			String refusedReason = String.valueOf(params[2]);
			
			User loginUser = (User) request.getSession().getAttribute(CommonFinal.LOGIN_USER);
			userId = loginUser.getId();
			username = loginUser.getUsername();
			
			if (loginUser.getType().equals(UserEnum.Type.ADMIN)) {
				operatType = OrderEnum.LogOperatType.ADMIN_REFUSED.getValue();
				operatNote = "管理员代替酒店取消订单，取消原因：" + refusedReason;
			} else if (loginUser.getType().equals(UserEnum.Type.HOTEL)) {
				operatType = OrderEnum.LogOperatType.HOTEL_REFUSED.getValue();
				operatNote = "酒店取消订单，取消原因：" + refusedReason;
			}
			
			// 查询订单信息
			Order model = new Order();
			model.setId(orderId);
			List<Order> modelList = orderService.selectList(null, null, model);
			if (modelList != null && modelList.size() > 0)
				model = modelList.get(0);
			// 生成微信模板对象
			WxTemplate template = WxTemplate.createCannelOrderTemlate(model);
			// 刷新微信Token
			WxMethod.refereshWXToken();
			// 发送微信模板消息
			WxService.sendWxTemplate(template);
			
		} else if (methodName.equals("confirmArrival")) {
			
			int result = Integer.parseInt(String.valueOf(rtv));
			if (result < 1)
				return;
			
			HttpServletRequest request = (HttpServletRequest) params[0];
			orderId = Integer.parseInt(String.valueOf(params[1]));
			
			User loginUser = (User) request.getSession().getAttribute(CommonFinal.LOGIN_USER);
			userId = loginUser.getId();
			username = loginUser.getUsername();
			
			if (loginUser.getType().equals(UserEnum.Type.ADMIN)) {
				operatType = OrderEnum.LogOperatType.ADMIN_CONFIRM_ARRIVAL.getValue();
				operatNote = "管理员代替酒店确认会员已到店";
			} else if (loginUser.getType().equals(UserEnum.Type.HOTEL)) {
				operatType = OrderEnum.LogOperatType.HOTEL_CONFIRM_ARRIVAL.getValue();
				operatNote = "酒店确认会员已到店";
			}
			
		} else if (methodName.equals("confirmLeave")) {
			
			int result = Integer.parseInt(String.valueOf(rtv));
			if (result < 1)
				return;
			
			HttpServletRequest request = (HttpServletRequest) params[0];
			orderId = Integer.parseInt(String.valueOf(params[1]));

			User loginUser = (User) request.getSession().getAttribute(CommonFinal.LOGIN_USER);
			userId = loginUser.getId();
			username = loginUser.getUsername();
			
			if (loginUser.getType().equals(UserEnum.Type.ADMIN)) {
				operatType = OrderEnum.LogOperatType.ADMIN_CONFIRM_LEAVE.getValue();
				operatNote = "管理员代替酒店确认会员已离店";
			} else if (loginUser.getType().equals(UserEnum.Type.HOTEL)) {
				operatType = OrderEnum.LogOperatType.HOTEL_CONFIRM_LEAVE.getValue();
				operatNote = "酒店确认会员已离店";
			}
			
			// 查询订单信息
			Order model = new Order();
			model.setId(orderId);
			List<Order> modelList = orderService.selectList(null, null, model);
			if (modelList != null && modelList.size() > 0)
				model = modelList.get(0);
			
			// 生成微信模板对象
			WxTemplate template = WxTemplate.createFinishOrderTemlate(model.getPayPrice().intValue(), model.getWxOpenId(), model.getOrderCard(), DateUtils.parseYMDHMTime(new Date()));
			// 刷新微信Token
			WxMethod.refereshWXToken();
			// 发送微信模板消息
			WxService.sendWxTemplate(template);
			
		} else if (methodName.equals("confirmRefund")) {
			
			int result = Integer.parseInt(String.valueOf(rtv));
			if (result < 1)
				return;
			
			HttpServletRequest request = (HttpServletRequest) params[0];
			orderId = Integer.parseInt(String.valueOf(params[1]));
			operatType = OrderEnum.LogOperatType.ADMIN_FINISH_REFUND.getValue();
			User loginUser = (User) request.getSession().getAttribute(CommonFinal.LOGIN_USER);
			userId = loginUser.getId();
			username = loginUser.getUsername();
			operatNote = "确认退款完成";
			
			// 查询订单信息
			Order model = new Order();
			model.setId(orderId);
			List<Order> modelList = orderService.selectList(null, null, model);
			if (modelList != null && modelList.size() > 0)
				model = modelList.get(0);
			// 生成微信模板对象
			WxTemplate template = WxTemplate.createRefundTemlate(model);
			// 刷新微信Token
			WxMethod.refereshWXToken();
			// 发送微信模板消息
			WxService.sendWxTemplate(template);
			
		} else if (methodName.equals("confirmAuditOrder")) {
			int result = Integer.parseInt(String.valueOf(rtv));
			if (result < 1)
				return;
			
			int status = Integer.parseInt(String.valueOf(params[2]));
			String subCard = String.valueOf(params[6]);
			String stayPersons = String.valueOf(params[7]);
			
			HttpServletRequest request = (HttpServletRequest) params[0];
			orderId = Integer.parseInt(String.valueOf(params[1]));
			User loginUser = (User) request.getSession().getAttribute(CommonFinal.LOGIN_USER);
			userId = loginUser.getId();
			username = loginUser.getUsername();
			
			if (status == 10) {
				
				if (loginUser.getType().equals(UserEnum.Type.ADMIN.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.MANAGER.getValue();
					operatType = OrderEnum.LogOperatType.ADMIN_CONFIRM_ARRIVAL.getValue();
					operatNote = "管理员代替酒店确认已入住, 订单确认号：" + subCard + ",订单确房间号：" + stayPersons;
					
				} else if (loginUser.getType().equals(UserEnum.Type.HOTEL.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.HOTEL.getValue();
					operatType = OrderEnum.LogOperatType.HOTEL_CONFIRM_ARRIVAL.getValue();
					operatNote = "酒店确认已入住, 订单确认号：" + subCard + ",订单确房间号：" + stayPersons;
				
				}
				
			} else if (status == 11) {
				
				if (loginUser.getType().equals(UserEnum.Type.ADMIN.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.MANAGER.getValue();
					operatType = OrderEnum.LogOperatType.ADMIN_CONFIRM_LEAVE.getValue();
					operatNote = "管理员代替酒店确认已离店";
					
				} else if (loginUser.getType().equals(UserEnum.Type.HOTEL.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.HOTEL.getValue();
					operatType = OrderEnum.LogOperatType.HOTEL_CONFIRM_LEAVE.getValue();
					operatNote = "酒店确认已离店";
					
				}
				
			} else if (status == 12) {
				
				if (loginUser.getType().equals(UserEnum.Type.ADMIN.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.MANAGER.getValue();
					operatType = OrderEnum.LogOperatType.ADMIN_CONFIRM_NOSHOW.getValue();
					operatNote = "管理员代替酒店确认预定未入住";
					
				} else if (loginUser.getType().equals(UserEnum.Type.HOTEL.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.HOTEL.getValue();
					operatType = OrderEnum.LogOperatType.HOTEL_CONFIRM_NOSHOW.getValue();
					operatNote = "酒店确认预定未入住";
					
				}
				
			} else if (status == 13) {
				
				if (loginUser.getType().equals(UserEnum.Type.ADMIN.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.MANAGER.getValue();
					operatType = OrderEnum.LogOperatType.ADMIN_CONFIRM_OTHER.getValue();
					operatNote = "管理员代替酒店确认其他";
					
				} else if (loginUser.getType().equals(UserEnum.Type.HOTEL.getValue())) {
					
					operatUser = OrderEnum.LogOperatUser.HOTEL.getValue();
					operatType = OrderEnum.LogOperatType.HOTEL_CONFIRM_OTHER.getValue();
					operatNote = "酒店确认其他";
					
				}
				
			}
		}
		*/
		
		if (orderId != null && !orderId.equals(0) && userName != null && logInfo != null) {
			OrderLog orderLog = new OrderLog(orderId, userName, logInfo);
			orderLogService.insert(orderLog);
		}
	}
	

}

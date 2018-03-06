package com.kunyao.assistant.web.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Push;
import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.AccountRecordEnum;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.CouponEnum;
import com.kunyao.assistant.core.enums.CrossEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.enums.OrderEnum.Travel;
import com.kunyao.assistant.core.enums.TimeEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.getui.GetuiUtils;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Account;
import com.kunyao.assistant.core.model.AccountRecord;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.Coupon;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.CrossTimes;
import com.kunyao.assistant.core.model.MemberComment;
import com.kunyao.assistant.core.model.MemberInfo;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderCancel;
import com.kunyao.assistant.core.model.OrderComplaint;
import com.kunyao.assistant.core.model.OrderConfig;
import com.kunyao.assistant.core.model.OrderCost;
import com.kunyao.assistant.core.model.OrderDelay;
import com.kunyao.assistant.core.model.OrderDoubt;
import com.kunyao.assistant.core.model.OrderPayRecord;
import com.kunyao.assistant.core.model.OrderTravel;
import com.kunyao.assistant.core.model.TravelDesc;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.Graph;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.AccountMapper;
import com.kunyao.assistant.web.dao.AccountRecordMapper;
import com.kunyao.assistant.web.dao.CityMapper;
import com.kunyao.assistant.web.dao.CouponMapper;
import com.kunyao.assistant.web.dao.CrossInfoMapper;
import com.kunyao.assistant.web.dao.CrossTimesMapper;
import com.kunyao.assistant.web.dao.MemberInfoMapper;
import com.kunyao.assistant.web.dao.OrderConfigMapper;
import com.kunyao.assistant.web.dao.OrderCostMapper;
import com.kunyao.assistant.web.dao.OrderDelayMapper;
import com.kunyao.assistant.web.dao.OrderMapper;
import com.kunyao.assistant.web.dao.OrderPayRecordMapper;
import com.kunyao.assistant.web.dao.OrderTravelMapper;
import com.kunyao.assistant.web.service.AccountRecordService;
import com.kunyao.assistant.web.service.AccountService;
import com.kunyao.assistant.web.service.CrossTimesService;
import com.kunyao.assistant.web.service.MemberCommentService;
import com.kunyao.assistant.web.service.MemberInfoService;
import com.kunyao.assistant.web.service.OrderCancelService;
import com.kunyao.assistant.web.service.OrderComplaintService;
import com.kunyao.assistant.web.service.OrderCostService;
import com.kunyao.assistant.web.service.OrderDoubtService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.OrderTravelService;
import com.kunyao.assistant.web.service.PushService;
import com.kunyao.assistant.web.service.TravelDescService;
import com.kunyao.assistant.web.service.UserService;

@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Integer> implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Resource
	private OrderMapper orderMapper;

	@Resource
	private OrderConfigMapper configMapper;

	@Resource
	private CouponMapper couponMapper;
	
	@Resource
	private AccountMapper accountMapper;
	
	@Resource
	private MemberInfoMapper memberInfoMapper;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private OrderPayRecordMapper orderPayRecordMapper;
	
	@Resource
	private OrderTravelMapper orderTravelMapper;
	
	@Resource
	private OrderTravelService orderTravelService;

	@Resource
	private OrderComplaintService orderComplaintService;
	
	@Resource
	private OrderCancelService orderCancelService;
	
	@Resource
	private OrderDoubtService orderDoubtService;
	
	@Resource
	private AccountRecordService accountRecordService;
	
	@Resource
	private OrderCostService orderCostService;
	
	@Resource
	private MemberInfoService memberInfoService;
	
	@Resource
	private MemberCommentService memberCommentService;
	
	@Resource
	private TravelDescService travelDescService;
	
	@Resource
	private OrderConfigMapper orderConfigMapper;
	
	@Resource
	private CrossTimesMapper crossTimeMapper;
	
	@Resource
	private CrossTimesService crossTimesService;
	
	@Resource
	private CrossInfoMapper crossInfoMapper;
	
	@Resource
	private CityMapper cityMapper;
	
	@Resource
	private PushService pushService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private OrderDelayMapper orderDelayMapper;
	
	@Resource
	private OrderCostMapper orderCostMapper;
	
	@Resource
	private AccountRecordMapper accountRecordMapper;
	
	public GenericDao<Order, Integer> getDao() {
		return orderMapper;
	}

	/**
	 * 生成订单
	 */
	@Override
	public Order createOrder(Integer userId, Integer crossUserId, String startTime, String endTime, 
			String couponId, Integer payMethod, Integer cityId, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile) 
			throws ParseException, ServiceException {
		if(userId == null) {
			 throw new ServiceException(ResultCode.NO_LOGIN);
		}
		// 判断用户端与服务器时间是否匹配，防止恶意订单
		if (DateUtils.parseYMDDate(startTime).getTime() < DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date()) + " 00:00:00").getTime()) {
			throw new ServiceException(ResultCode.TIME_ERROR);
		}
		
		// 判断用户是否已完成身份验证和绑定银行卡
		MemberInfo memberInfo = memberInfoMapper.findMemberInfo(userId);
		if (StringUtils.isNull(memberInfo.getName())) {
			throw new ServiceException(ResultCode.NO_CERTIFIED);
		}
		if (StringUtils.isNull(memberInfo.getIdcard())) {
			throw new ServiceException(ResultCode.NO_IDCARD);
		}
		if (StringUtils.isNull(memberInfo.getBankCard())) {
			throw new ServiceException(ResultCode.NO_BANK);
		}
		
		// 判断用户是否还有未完成订单
		List<Order> orders = orderMapper.findOrderListNotFinished(userId);
		if (orders != null && orders.size() > 0) {
			if (orders.get(0).getStatus() == OrderEnum.Status.WAIT_COMMENT.getValue())
				throw new ServiceException(ResultCode.HAVE_ORDER_NOT_COMMENT);
			else 
				throw new ServiceException(ResultCode.HAVE_ORDER_NOT_FINISH);
		}
		
		// 若系统时间超过当日19:00，行程开始日期最早只能为次日
		if (new Date().getTime() > DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date())).getTime() + 19L * 60 * 60 * 1000 && DateUtils.parseFullDate(startTime).getTime() < DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date())).getTime() + DateUtils.DAY) {
			throw new ServiceException(ResultCode.ORDER_START_TIME_ILLEGAL);
		}

		// 计算服务费
		int day = DateUtils.getBetweenDay(startTime, endTime);
		if (day > 5) throw new ServiceException(ResultCode.TRAVEL_DAYS_OVERFLOW); // 行程天数限制
		logger.info("服务天数：" + day);
		OrderConfig orderConfig = configMapper.findOrderServiceFee();
		Double serviceMoney = orderConfig.getServiceMoney() * day;
		
		// 实际支付费用
		Double payMoney = serviceMoney;
		if (!StringUtils.isNull(couponId)) {
			String[] ids = couponId.split(";");
			// 限制订单最大可使用优惠券金额
			List<Coupon> coupons = new ArrayList<>();
			double totalCouponMoney = 0.0;
			for (String id : ids) {
				Coupon coupon = couponMapper.findCouponInfo(Integer.parseInt(id));
				if (coupons != null) {
					coupons.add(coupon);
					totalCouponMoney += coupon.getMoney();
				}
			}
			if (totalCouponMoney > (orderConfig.getMaxDiscount() * day)) {
				throw new ServiceException(ResultCode.CONPON_MONEY_OVERTOP);
			}
			for (Coupon coupon : coupons) {
				payMoney -= coupon.getMoney();
				coupon.setStatus(CouponEnum.USERD.getValue());
				coupon.setUseTime(new Date());
				couponMapper.updateByID(coupon);
			}
		}
		
		// 生成订单
		Order order = new Order(memberInfo.getUserId(), crossUserId, startTime, endTime, serviceMoney, payMoney, couponId, payMethod, cityId, memberInfo.getOpenId(), OrderEnum.CostPayMethod.MEMBER.getValue(), OrderEnum.ContactMethod.CROSS.getValue(), isNeedInvoice);
		if (isNeedInvoice != null && isNeedInvoice == BaseEnum.Status.BASE_STATUS_ENABLE.getValue()) {
			order.setInvoiceTitle(invoiceTitle);
			order.setInvoiceAddress(invoiceAddress);
			order.setInvoicePeople(invoicePeople);
			order.setInvoiceMobile(invoiceMobile);
		}
		orderMapper.insert(order);
		return order;
	}

	/**
	 * 支付订单
	 */
	@Override
	public Map<String, Object> payOrder(HttpServletRequest request, Integer orderId) throws ServiceException {
		Order order = orderMapper.findOrderInfo(orderId);
		return payOrder(request, order);
	}
	
	/**
	 * 支付订单
	 */
	@Override
	public Map<String, Object> payOrder(HttpServletRequest request, Order order) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		int payMethod = order.getPayMethod();
//		String TradeNo = "order_" + order.getOrderCard();
//		if (payMethod == OrderEnum.ServicePayMethod.ALIPAY.getValue()) {
//			/** 支付宝支付 **/
//			String signOrderInfo = AliPayUtils.createOrder(TradeNo, order.getServiceMoney(), "支付服务费用", "支付服务费用");
//			map.put("result", signOrderInfo);
//			
//		} else if (payMethod == OrderEnum.ServicePayMethod.WXPAY_JS.getValue()) {
//			/** 公众号支付 **/
//			WXOrder wxOrder = WxService.unifiedOrder(order.getId(), order.getOpenId(), TradeNo, order.getServiceMoney(), "JSAPI", request);
//			if (wxOrder != null) {
//				map.put("result", wxOrder);
//			} else {
//				map.put("result", "wxpay_js_error");
//			}
//			
//		} else if (payMethod == OrderEnum.ServicePayMethod.WXPAY_APP.getValue()) {
//			/** 微信APP支付 **/
//			WXOrder wxOrder = WxService.unifiedOrder(order.getId(), order.getOpenId(), TradeNo, order.getServiceMoney(), "APP", request);
//			if (wxOrder != null) {
//				map.put("result", wxOrder);
//			} else {
//				map.put("result", "wxpay_app_error");
//			}
//			
//		} else if (payMethod == OrderEnum.ServicePayMethod.BANK.getValue()) {
//			/** 银行支付 **/
//			
//		} else if (payMethod == OrderEnum.ServicePayMethod.BALANCE.getValue()) {
//			/** 余额支付 **/
//			accountService.order(order);
//			finishPay(order.getId(), "");
//			map.put("result", "paysuccess");
//			
//		}
		/** 余额支付 **/
		accountService.order(order);
		finishPay(order.getId(), "");
		map.put("result", "paysuccess");
		map.put("orderId", order.getId());
		map.put("payMethod", payMethod);
		return map;
	}

	/**
	 * 完善订单
	 */
	@Override
	public Order fillOrder(Integer orderId, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress,
			String invoicePeople, String invoiceMobile, Integer contactMethod, Integer costPayMethod,
			String travelDescId, String travelRequireText, String travelRequireVoice) throws ServiceException {
		
		if (costPayMethod != null && costPayMethod.intValue() == OrderEnum.CostPayMethod.CROSS.getValue()) {
			// 检查余额是否可以满足代付要求
			Order o = orderMapper.findOrderInfo(orderId);
			Account account = accountMapper.findAccount(o.getUserId());
			int day = DateUtils.getBetweenDay(o.getStartTime(), o.getEndTime());
			if (account.getBalance() < (day * Constant.MIN_BALANCE_PER_DAY)) {
				throw new ServiceException(ResultCode.BALANCE_ENOUGH);
			}
		}
		
		if (isNeedInvoice.equals(BaseEnum.Status.BASE_STATUS_DISABLED)) {
			invoiceTitle = "";
			invoiceAddress = "";
			invoiceMobile = "";
			invoicePeople = "";
		}
		if (contactMethod != null && !contactMethod.equals(OrderEnum.ContactMethod.ORTHER.getValue())) {
			travelDescId = "";
			travelRequireText = "";
			travelRequireVoice = "";
		}
		Order order = new Order(orderId, isNeedInvoice, invoiceTitle, invoiceAddress, invoicePeople, invoiceMobile,
				contactMethod, costPayMethod, travelDescId, travelRequireText, travelRequireVoice);
		if (costPayMethod != null) // 前端只有填写行程要求时才传costPayMethod，将order标记为已填写
			order.setTravelRequireFlag(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		orderMapper.updateByID(order);
		return order;
	}

	/**
	 * 完成支付
	 */
	@Override
	public Order finishPay(Integer orderId, String thirdTradeNo) {
		/** 更新订单状态 **/
		Order order = orderMapper.findOrderInfo(orderId);
		order.setStatus(OrderEnum.Status.WAIT_LOOP.getValue());
		orderMapper.updateByID(order);
		
		/** 添加订单支付记录 **/
		OrderPayRecord orderPayRecord = new OrderPayRecord(orderId, order.getPayMethod(), order.getServiceMoney(), thirdTradeNo, new Date());
		orderPayRecordMapper.insert(orderPayRecord);
		return order;
	}
	
	/**
	 * 查询订单配置信息
	 * 1、查询服务费
	 * 2、用户优惠券数量
	 * 3、用户预存款余额
	 */
	@Override
	public Map<String, Object> queryPayInfo(Integer userId, Integer day) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		OrderConfig orderConfig = configMapper.findOrderServiceFee();
		if (orderConfig == null || orderConfig.getServiceMoney() == null) {
			throw new ServiceException(ResultCode.SERVICE_MONEY_CONFIG);
		}
		Double serviceMoney = orderConfig.getServiceMoney() * day;
		List<Coupon> coupons = couponMapper.findListByUserId(userId, CouponEnum.UN_USER.getValue());
		Account account = accountMapper.findAccount(userId);
		int couponNumber = 0;
		
		map.put("serviceMoney", serviceMoney);
		if (coupons != null && coupons.size() > 0) {
			for (Coupon coupon : coupons) {
				if (coupon.getMoney() <= orderConfig.getMaxDiscount() * day){
					map.put("coupon", coupon);
					break;
				}
			}
			couponNumber = coupons.size();
		}
		map.put("couponNumber", couponNumber);
		map.put("accountMoney", account == null ? 0 : account.getBalance());
		return map;
	}
	
	/**
	 * 查询待服务、服务中、待评价、待确认行程订单
	 */
	@Override
	public PageData<Order> queryByCrossUserId(Integer crossUserId, String search, Integer currentPage, Integer pagesize) throws ServiceException{
		if (StringUtils.isNull(search)) { search = "";}
		search = "%" + search + "%";
		
		Integer startpos = null;
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		// TODO 没有使用到订单状态是在mapper中写死值
		List<Order> crossInfos = orderMapper.findByCross(crossUserId, 
				OrderEnum.Status.WAIT_SERVICE.getValue(),
				OrderEnum.Status.WAIT_COMMENT.getValue(),
				OrderEnum.Travel.REMOVE.getValue(), 
				OrderEnum.Cost.REMOVE.getValue(), startpos, pagesize);
		
		// 设置订单花费金额
		if (crossInfos != null) {
			for (Order order : crossInfos) {
				List<OrderCost> list = orderMapper.findOrderCostList(order.getId(), OrderEnum.Travel.REMOVE.getValue(), OrderEnum.Cost.REMOVE.getValue());
				double costTotal = 0.0;
				if (list != null) {
					for (OrderCost orderCost : list) {
						costTotal += orderCost.getMoney();
					}
				}
				order.setCostTotal(costTotal);
			}
		}
		
		Integer allRow = orderMapper.findByCrossCount(crossUserId, 
				OrderEnum.Status.WAIT_SERVICE.getValue(),
				OrderEnum.Status.WAIT_COMMENT.getValue(),
				OrderEnum.Travel.REMOVE.getValue(), 
				OrderEnum.Cost.REMOVE.getValue(), startpos, pagesize);
		PageData<Order> pageData = new PageData<>(crossInfos, allRow, currentPage++, pagesize);
		return pageData;
	}
	
	@Override
	public Order queryOrderDetail(Integer orderId) throws ServiceException {
		Order order = orderMapper.findByOrderId(orderId);
		if (order == null) {
			return null;
		}
		
		// 计算订单总花费
		List<OrderCost> list = orderMapper.findOrderCostList(order.getId(), OrderEnum.Travel.REMOVE.getValue(), OrderEnum.Cost.REMOVE.getValue());
		double costTotal = 0.0;
		if (list != null) {
			for (OrderCost orderCost : list) {
				costTotal += orderCost.getMoney();
			}
		}
		order.setCostTotal(costTotal);
		return order;
	}
	
	// 行程要求
	@Override
	public Order queryOrderRequire(Integer orderId) {
		return orderMapper.findOrderRequire(orderId);
	}
	
	/********************* 基本查询方法 ***************************/
	@Override
	public Order queryOrderInfo(Integer orderId) {
		return orderMapper.findOrderInfo(orderId);
	}
	
	@Override
	public Order queryOrderByOrderCard(String orderCard) throws ServiceException{
		if (StringUtils.isNull(orderCard)) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		return orderMapper.findOrderByOrderCard(orderCard);
	}

	@Override
	public PageData<Order> queryOrderList(Integer userId, Integer currentPage, Integer pageSize) {
		Integer startPos = PageInfo.countOffset(pageSize, currentPage);
		List<Order> orderList = orderMapper.findOrderListByMemberUserId(userId, startPos, pageSize);
		int count = orderMapper.findOrderCountByMemberUserId(userId);
		return new PageData<>(orderList, count, currentPage, pageSize);
	}

	@Override
	public Order updateCancelOrder(Integer orderId) throws ServiceException {
		Order findOrder = orderMapper.findOrderInfo(orderId);
		if (findOrder == null)
			throw new ServiceException(ResultCode.NO_DATA);
		if (findOrder.getStatus() != OrderEnum.Status.WAIT_LOOP.getValue())
			throw new ServiceException(ResultCode.ORDER_STATUS_NEGATIVE);
		Order updOrder = new Order();
		// 订单状态修改
		updOrder.setId(orderId);
		updOrder.setStatus(OrderEnum.Status.CANCEL_AFTER_PAY.getValue());
		orderMapper.updateByID(updOrder);
		String couponIds = findOrder.getCouponId();
		if (!StringUtils.isNull(couponIds)) {
			String[] couponId = couponIds.split(";");
			for (String id : couponId) {
				Coupon coupon = couponMapper.findCouponInfo(Integer.parseInt(id));
				if (coupon.getEndTime().getTime() <= new Date().getTime())
					coupon.setStatus(CouponEnum.OVER_DATE.getValue());
				else
					coupon.setStatus(CouponEnum.UN_USER.getValue());
				couponMapper.updateByID(coupon);
			}
		}
		// 支付费用返还到账户
		Account account = accountMapper.findAccount(findOrder.getUserId());
		account.setBalance(account.getBalance() + findOrder.getPayMoney());
		accountMapper.updateByID(account);
		// 账户余额变动记录
		AccountRecord record = new AccountRecord(findOrder.getUserId(), orderId, findOrder.getPayMoney(), AccountRecordEnum.REFUND.getValue(), new Date());
		accountRecordService.insert(record);
		return updOrder;
	}

	@Override
	public int findOrderByCrossUserId(Integer crossUserId) {
		return orderMapper.findOrderByCrossUserId(crossUserId);
	}

	@Override
	public PageData<Order> findOrderListWithTravel(Integer userId, Integer currentPage, Integer pageSize) {
		Integer startPos = PageInfo.countOffset(pageSize, currentPage);
		List<Order> orderList = orderMapper.findOrderListForTravel(userId, startPos, pageSize);
		if (orderList != null && orderList.size() > 0) {
			for (Order order : orderList){
				order.setStartDate(order.getStartTime());
				order.setEndDate(order.getEndTime());
				if (order.getStatus() == OrderEnum.Status.SERVING.getValue() || order.getStatus() == OrderEnum.Status.WAIT_CONFIRM.getValue() || order.getStatus() == OrderEnum.Status.WAIT_SERVICE_CODE.getValue() || order.getStatus() == OrderEnum.Status.WAIT_COMMENT.getValue())
					order.setOrderTravelList(orderTravelService.queryOrderTravelList(order.getId()));
				
				if (order.getStatus() == OrderEnum.Status.SERVING.getValue() && order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue()) {
					order.setBalance(accountMapper.findAccount(order.getUserId()).getBalance());
					List<OrderCost> costList = orderCostService.findOrderCostListByOrderId(order.getId());
					double totalCost = 0;
					for (OrderCost orderCost : costList) {
						totalCost += orderCost.getMoney();
					}
					order.setCostTotal(totalCost);
				}
				// 行程首日  且下单时间在16:30之后，当天不再需要支付延时费，否则超过21:00正常计费
				if (DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date())).getTime() == order.getStartTime().getTime() && DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date()) + Constant.TIME_BOUNDARY).getTime() < order.getCreateTime().getTime()) 
					order.setDelayNeedPay(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
				else
					order.setDelayNeedPay(needPayDelay(order));
			}
		}
		int count = orderMapper.findOrderCountServing(userId);
		return new PageData<>(orderList, count, currentPage, pageSize);
	}
	
	private int needPayDelay(Order order) {
		Date nowTime = new Date();
		if (nowTime.getTime() >= DateUtils.parseYMDHMDate(DateUtils.parseYMDTime(nowTime) + " 21:00").getTime()) {
			int num = orderTravelMapper.findCountTravelServing(order.getId(), DateUtils.parseYMDTime(nowTime), OrderEnum.Travel.COST_WAIT_CONFIRM.getValue());
			if (num > 0) {
				List<OrderDelay> delays = orderDelayMapper.findListByOrderIdAndDate(order.getId(), DateUtils.parseYMDTime(nowTime));
				if (delays == null || delays.size() <= 0) {
					return BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
				}
//				int totalHours = 0;
//				for (OrderDelay orderDelay : delays) {
//					totalHours += orderDelay.getHour();
//				}
//				if (nowTime.getTime() >= DateUtils.parseYMDHMDate(DateUtils.parseCNMDTime(nowTime) + " 22:00").getTime() && totalHours < 2) {
//					return BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
//				} else if (nowTime.getTime() > DateUtils.parseYMDHMDate(DateUtils.parseCNMDTime(nowTime) + " 23:00").getTime() && totalHours < 3) {
//					return BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
//				}
				return BaseEnum.Status.BASE_STATUS_DISABLED.getValue();
			}
			return BaseEnum.Status.BASE_STATUS_DISABLED.getValue();
		}
		return BaseEnum.Status.BASE_STATUS_DISABLED.getValue();
	}

	@Override
	public List<Order> queryList(Integer currentPage, Integer pagesize, Order model) {
		List<Order> modelList = null;
		Integer startpos = null;     //当前页
		
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		if (model.getOrderDate() != null || model.getTravelDate() != null || model.getEndDate() != null){
			if(model.getLogmax() != null && model.getLogmin() != null){
				String time  = model.getLogmax();
				Date date = DateUtils.parseYMDDate(time);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, 1); 
				Date torrowDate = calendar.getTime();
				
				String logmax = DateUtils.parseYMDTime(torrowDate);
				model.setLogmax(logmax);
			}
		}
		
		modelList = orderMapper.selectOrderList(startpos, pagesize, model);
		
		return this.orderList(modelList);
	}
	
	
	@Override
	public List<Order> objectionOrderList(Integer currentPage, Integer pagesize, Order model) {
		List<Order> modelList = null;
		Integer startpos = null;     //当前页
				
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
				
		OrderDoubt orderDoubtModel = new OrderDoubt();
		orderDoubtModel.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		List<Integer> orderId = new ArrayList<Integer>();
		try {
			List<OrderDoubt> orderDoubtsList = orderDoubtService.findList(null, null, orderDoubtModel);
			if (orderDoubtsList == null){
				return null;
			}
			for (OrderDoubt orderDoubt : orderDoubtsList) {
				orderId.add(orderDoubt.getOrderId());
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		model.setOrderId(orderId);
		model.setStateQuery("stateQuery");
		
		modelList = orderMapper.selectOrderList(startpos, pagesize, model);
				
		return this.orderList(modelList);
	}
	
	
	@Override
	public List<Order> orderComplaintList(Integer currentPage, Integer pagesize, Order model) {
		List<Order> modelList = null;
		Integer startpos = null;     //当前页
				
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
				
		OrderComplaint orderComplaint = new OrderComplaint();
		orderComplaint.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		List<Integer> orderId = new ArrayList<Integer>();
		try {
			List<OrderComplaint> orderComplaintList = orderComplaintService.findList(null, null, orderComplaint);
			if (orderComplaintList == null){
				return null;
			}
			for (OrderComplaint orderComplaintInfo : orderComplaintList) {
				orderId.add(orderComplaintInfo.getOrderId());
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		model.setOrderId(orderId);
		model.setStateQuery("stateQuery");
		
		modelList = orderMapper.selectOrderList(startpos, pagesize, model);
				
		return this.orderList(modelList);
	}
	
	@Override
	public List<Order> orderEndList(Integer currentPage, Integer pagesize, Order model) {
		List<Order> modelList = null;
		Integer startpos = null;     //当前页
				
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
				
		OrderCancel orderCancel = new OrderCancel();
		orderCancel.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		List<Integer> orderId = new ArrayList<Integer>();
		try {
			List<OrderCancel> orderCancelList = orderCancelService.findList(null, null, orderCancel);
			if (orderCancelList == null){
				return null;
			}
			for (OrderCancel orderCancelInfo : orderCancelList) {
				orderId.add(orderCancelInfo.getOrderId());
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		model.setOrderId(orderId);
		model.setStateQuery("stateQuery");
		
		modelList = orderMapper.selectOrderList(startpos, pagesize, model);
				
		return this.orderList(modelList);
	}
	
	@Override
	public PageInfo selectListCount(Integer pageSize, Order order) {
		int allRow = orderMapper.selectOrderCount(order);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}
	
	@Override
	public PageInfo objectionOrderCount(Integer pageSize, Order order) {
		
		OrderDoubt orderDoubtModel = new OrderDoubt();
		orderDoubtModel.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		List<Integer> orderId = new ArrayList<Integer>();
		try {
			List<OrderDoubt> orderDoubtsList = orderDoubtService.findList(null, null, orderDoubtModel);
			if (orderDoubtsList == null){
				return null;
			}
			for (OrderDoubt orderDoubt : orderDoubtsList) {
				orderId.add(orderDoubt.getOrderId());
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		order.setOrderId(orderId);
		order.setStateQuery("stateQuery");
		
		int allRow = orderMapper.selectOrderCount(order);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}
	
	@Override
	public PageInfo orderEndCount(Integer pageSize, Order order) {
		
		OrderCancel orderCancel = new OrderCancel();
		orderCancel.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		List<Integer> orderId = new ArrayList<Integer>();
		try {
			List<OrderCancel> orderCancelList = orderCancelService.findList(null, null, orderCancel);
			if (orderCancelList == null){
				return null;
			}
			for (OrderCancel orderCancelInfo : orderCancelList) {
				orderId.add(orderCancelInfo.getOrderId());
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		order.setOrderId(orderId);
		order.setStateQuery("stateQuery");
		
		int allRow = orderMapper.selectOrderCount(order);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}
	
	@Override
	public PageInfo orderComplaintCount(Integer pageSize, Order order) {
		
		OrderComplaint orderComplaint = new OrderComplaint();
		orderComplaint.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		List<Integer> orderId = new ArrayList<Integer>();
		try {
			List<OrderComplaint> orderComplaintList = orderComplaintService.findList(null, null, orderComplaint);
			if (orderComplaintList == null){
				return null;
			}
			for (OrderComplaint orderComplaintInfo : orderComplaintList) {
				orderId.add(orderComplaintInfo.getOrderId());
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		order.setOrderId(orderId);
		order.setStateQuery("stateQuery");
		
		int allRow = orderMapper.selectOrderCount(order);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}

	@Override
	public Order queryDetail(Integer orderId) {
		Order order = new Order();
		order.setId(orderId);
		List<Order> resultList = orderMapper.selectOrderList(null, null, order);
		return resultList.get(0);
	}

	/**
	 * 投诉订单处理结果
	 */
	@Override
	public int updateEndComplaintOrder(Integer orderId, Integer refundMethod) throws ServiceException{
		try {
			Order oModel = new Order();
			oModel.setId(orderId);
			List<Order> orderList = orderMapper.selectOrderList(null, null, oModel);
			Order order = orderList.get(0);
			Account accountInfo = accountMapper.findAccount(order.getUserId());
			Double balance = accountInfo.getBalance();
			Double orderServiceMoney = order.getServiceMoney();
			Double refundAmount = 0.0;
			Account mAccount = new Account();
			Order updateOrder = new Order();
			OrderConfig orderConfig = orderConfigMapper.findOrderServiceFee();
			Double orderConfigServiceMoney = orderConfig.getServiceMoney();                    // 服务费
			Double accountRefund = 0.0;
			
			if (refundMethod == 2) {                                        // 全额退
				accountRefund = orderServiceMoney;
				mAccount.setBalance(balance + orderServiceMoney);
				mAccount.setId(accountInfo.getId());
				
				updateOrder.setId(order.getId());
				updateOrder.setStatus(OrderEnum.Status.CANCEL_BY_ADMIN.getValue());   // 已取消状态
				
			} else if (refundMethod == 1) {                 // 规则退款
				
				 // 待服务只扣除当天的服务费加上第二天服务费减半
				if (order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_CONFIRM.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE_CODE.getValue())){        
					accountRefund = orderConfigServiceMoney + orderConfigServiceMoney * 0.5;
					refundAmount = order.getServiceMoney() - accountRefund;
					
				} else if (order.getStatus().equals(OrderEnum.Status.SERVING.getValue())){ 
					Integer dateCount = DateUtils.getBetweenDay(DateUtils.parseYMDTime(order.getStartTime()) , DateUtils.parseYMDTime(new Date()));
					Double serviceMoney = 0.0;
					for (int i = 0; i < dateCount; i++){
						serviceMoney += orderConfigServiceMoney;
					}
					accountRefund = serviceMoney + orderConfigServiceMoney * 0.5;
					refundAmount = order.getServiceMoney() - accountRefund;
				}	
				
				mAccount.setId(accountInfo.getId());
				mAccount.setBalance(balance + refundAmount);
				
				updateOrder.setId(order.getId());
				updateOrder.setStatus(OrderEnum.Status.CANCEL_BY_ADMIN.getValue());   // 已取消状态
			}
			
			// 更改金鹰服务时间 比如 3 4 5 6。5号处理投诉订单 更改6号为可预约
		    String today = DateUtils.parseYMDTime(new Date());
		    long todayTime = DateUtils.parseYMDDate(today).getTime();
		    List<String> days = order.setServiceDays();
		    for (String day : days) {
		    	long dayTime = DateUtils.parseYMDDate(day).getTime();
		    	if (dayTime > todayTime) {
		    		crossTimesService.updateCrossTimeStatus(order.getCrossUserId(), TimeEnum.CAN_BOOK.getValue(), day);
		    	}
			}
			
			if (order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE.getValue()) || order.getStatus().equals(OrderEnum.Status.SERVING.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_CONFIRM.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE_CODE.getValue())){
				accountMapper.updateByID(mAccount);
				
				orderMapper.updateByID(updateOrder);
				
				AccountRecord accountRecord = new AccountRecord();
				accountRecord.setOrderId(orderId);
				accountRecord.setType(AccountRecordEnum.REFUND.getValue());
				accountRecord.setMoney(accountRefund);
				accountRecord.setUserId(order.getUserId());
				accountRecord.setOperateTime(new Date());
				accountRecordService.insert(accountRecord);
				
				OrderComplaint ocModel = new OrderComplaint();
				ocModel.setOrderId(orderId);
				OrderComplaint orderComplaint = orderComplaintService.findOne(ocModel);
				
				OrderComplaint modelComplaint = new OrderComplaint();
				modelComplaint.setId(orderComplaint.getId());
				modelComplaint.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
				orderComplaintService.update(modelComplaint);
				
				// 推送给用户
				MemberInfo memberinfo = memberInfoService.findByOrderId(order.getId());
				pushService.push(Push.createTemplate7(memberinfo.getUserId(), memberinfo.getGetuiId(), memberinfo.getMobile(), accountRefund, order.getOrderCard()));
			} else{
				throw new ServiceException(ResultCode.EXCEPTION_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		return 1;
	}

	/**
	 * 金鹰 提前取消
	 */
	@Override
	public int updateEndService(Integer orderId) throws ServiceException{
		try {
			OrderComplaint orderComplaint = new OrderComplaint();
			orderComplaint.setOrderId(orderId);
			OrderComplaint orderComplaintInfo = orderComplaintService.findOne(orderComplaint);
			Order oModel = new Order();
			oModel.setId(orderId);
			List<Order> orderList = orderMapper.selectOrderList(null, null, oModel);
			Order order = orderList.get(0);
			
			Account accountInfo = accountMapper.findAccount(order.getUserId());
			Double balance = accountInfo.getBalance();
			OrderConfig orderConfig = orderConfigMapper.findOrderServiceFee();
			Double orderConfigServiceMoney = orderConfig.getServiceMoney();                    // 服务费
			Double accountRefund = 0.0;
			
			if (orderComplaintInfo == null){
				Double refundAmount = 0.0;
				Account aModel = new Account();
				Order updateOrder = new Order();
				
				if (order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_CONFIRM.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE_CODE.getValue())){     // 待服务退款只扣除
					 accountRefund = orderConfigServiceMoney + orderConfigServiceMoney * 0.5;
					 refundAmount = order.getServiceMoney() - accountRefund;
					 aModel.setId(accountInfo.getId());
					 aModel.setBalance(balance + refundAmount);
					 
					 updateOrder.setId(order.getId());
					 updateOrder.setStatus(OrderEnum.Status.CANCEL_BY_ADMIN.getValue());   // 已取消状态
				} else if (order.getStatus().equals(OrderEnum.Status.SERVING.getValue())){   // 服务中退款
					Integer dateCount = DateUtils.getBetweenDay(DateUtils.parseYMDTime(order.getStartTime()) , DateUtils.parseYMDTime(new Date()));
					Double serviceMoney = 0.0;
					for (int i = 0; i < dateCount; i++){
						 serviceMoney += orderConfigServiceMoney;
					}
					accountRefund = serviceMoney + orderConfigServiceMoney * 0.5;
					refundAmount = order.getServiceMoney() - accountRefund;
					aModel.setId(accountInfo.getId());
					aModel.setBalance(balance + refundAmount);
					
					updateOrder.setId(order.getId());
					updateOrder.setStatus(OrderEnum.Status.WAIT_COMMENT.getValue());   // 先填个数，到时候改，待评价状态
				}
				
				if (order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE.getValue()) || order.getStatus().equals(OrderEnum.Status.SERVING.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_CONFIRM.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE_CODE.getValue())) {
					accountMapper.updateByID(aModel);
					orderMapper.updateByID(updateOrder);
					
					// 更改金鹰服务时间 
				    String today = DateUtils.parseYMDTime(new Date());
				    long todayTime = DateUtils.parseYMDDate(today).getTime();
				    List<String> days = order.setServiceDays();
				    for (String day : days) {
				    	long dayTime = DateUtils.parseYMDDate(day).getTime();
				    	if (dayTime > todayTime) {
				    		crossTimesService.updateCrossTimeStatus(order.getCrossUserId(), TimeEnum.CAN_BOOK.getValue(), day);
				    	}
					}
					
				    // 帐户余额变动
					AccountRecord accountRecord = new AccountRecord();
					accountRecord.setOrderId(orderId);
					accountRecord.setType(AccountRecordEnum.REFUND.getValue());
					accountRecord.setMoney(accountRefund);
					accountRecord.setUserId(order.getUserId());
					accountRecord.setOperateTime(new Date());
					accountRecordService.insert(accountRecord);
					
					// 修改取消申请状态
					OrderCancel ocModel = new OrderCancel();
					ocModel.setOrderId(orderId);
					OrderCancel orderCancel = orderCancelService.findOne(ocModel);
					
					OrderCancel orderCancelModel = new OrderCancel();
					orderCancelModel.setId(orderCancel.getId());
					orderCancelModel.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
					orderCancelService.update(orderCancelModel);
				    
					MemberInfo memberinfo = memberInfoService.findByOrderId(order.getId());
					// 推送给用户
					pushService.push(Push.createTemplate7(memberinfo.getUserId(), memberinfo.getGetuiId(), memberinfo.getMobile(), accountRefund, order.getOrderCard()));
				} else{
					throw new ServiceException(ResultCode.EXCEPTION_ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		return 1;
	}

	@Override
	public int updateOrderReplaceCross(Integer orderId, Integer crossId) throws ServiceException{
		try {
			OrderComplaint model = new OrderComplaint();
			model.setOrderId(orderId);
			model.setStatus(0);
			OrderComplaint orderComplaint = orderComplaintService.findOne(model);
			
			OrderComplaint updateComplaint = new OrderComplaint();
			updateComplaint.setId(orderComplaint.getId());
			updateComplaint.setStatus(1);                                 // 表示已解决
			orderComplaintService.update(updateComplaint);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		return this.updateReplaceCross(orderId, crossId);
	}
	
	@Override
	public int updateCrossReplaceCross(Integer orderId, Integer crossId) throws ServiceException{
		try {
			OrderCancel model = new OrderCancel();
			model.setOrderId(orderId);
			model.setStatus(0);
			OrderCancel orderCancel = orderCancelService.findOne(model);
			
			OrderCancel updateOrderCancel = new OrderCancel();
			updateOrderCancel.setId(orderCancel.getId());
			updateOrderCancel.setStatus(1);                                 // 表示已解决
			orderCancelService.update(updateOrderCancel);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}
		return this.updateReplaceCross(orderId, crossId);
	}
	
	
	/**
	 * 把订单按金鹰进行分组
	 */
	private Map<Integer, List<Order>> groupByCross(List<Order> allOrders) {
		Map<Integer, List<Order>> map = new HashMap<>();
		for (Order order : allOrders) {
			List<Order> list = map.get(order.getCrossUserId());
			if (list == null) {
				list = new ArrayList<>();
				map.put(order.getCrossUserId(), list);
			}
			list.add(order);
			order.setServiceDays();
		}
		return map;
	}
	
	/**
	 * 生成冲突图
	 */
	private Graph createGraph(List<Order> singleCrossOrders) {
		Graph graph = new Graph(singleCrossOrders.size());
		for (int i = 0; i < singleCrossOrders.size(); i++) {
			Order order = singleCrossOrders.get(i);
			for (int j = i + 1; j < singleCrossOrders.size(); j++) { // 比较是否冲突
				Order afterOrder = singleCrossOrders.get(j);
				if (order.isTimeConflict(afterOrder)) {
					graph.AddEdge(i, j); // 索引是点
				}
			}
		}
		return graph;
	}
	
	// log 生成集合订单id
	private String testCreateOrderIds(List<Order> singleCrossOrders) {
		StringBuffer stringBuffer = new StringBuffer();
		for (Order order : singleCrossOrders) {
			stringBuffer.append(order.getId() + ",");
		}
		return "[" + stringBuffer.toString() + "]";
	}

	/**
	 * 是否可以被预约
	 */
	public boolean canBook(Integer crossUserId, String startDate, String endDate) throws ParseException, ServiceException {
		// 检查金鹰是否离职
		User userModel = new User();
		userModel.setId(crossUserId);
		User authentication = userService.findOne(userModel);
		if (authentication == null || authentication.getStatus().intValue() == BaseEnum.Status.BASE_STATUS_DISABLED.getValue()) {
			return false;
		}
		// 检查是否可以预约
		int day = DateUtils.getBetweenDay(startDate, endDate);
		startDate += " 00:00:00";
		endDate += " 00:00:00";
		List<CrossTimes> crossTimes = crossTimeMapper.findCrossTimeByStatus(crossUserId, CrossEnum.BookStatus.IDLE.getValue(), startDate, endDate);
		if (crossTimes != null && crossTimes.size() == day) {
			return true;
		}
		return false;
	}
	
	// 指派成功 推送用户订单成功、更新time表
	private void assignSuccess(Order order) {
		// 指派前再判断一次金鹰是否可以被预约（保险起见）
		try {
			boolean flag = canBook(order.getCrossUserId(), DateUtils.parseYMDTime(order.getStartTime()), DateUtils.parseYMDTime(order.getEndTime()));
			if (!flag) {
				assignFail(order, 100.0);
				return;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			assignFail(order, 100.0);
			return;
		}
		
		// 更改订单状态
		order.setStatus(OrderEnum.Status.WAIT_SERVICE.getValue());
		order.setAssignTime(new Date());
		try {
			update(order);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		// 更新金鹰服务时间
		List<String> days = order.setServiceDays();
		for (String day : days) {
			crossTimesService.updateCrossTimeStatus(order.getCrossUserId(), TimeEnum.BOOKED.getValue(), day);
		}
		
		// 推送
		City city = cityMapper.findCity(order.getCityId());
		order.setCityName(city.getName());
		CrossInfo crossInfo = crossInfoMapper.findByOrderId(order.getId());
		MemberInfo memberInfo = memberInfoService.findByOrderId(order.getId());
		pushService.push(Push.createTemplate1(memberInfo.getUserId(), memberInfo.getGetuiId(), memberInfo.getBankMobile(), order, crossInfo)); // 推送给用户
		pushService.push(Push.createTemplate3(crossInfo.getUserId(), crossInfo.getGetuiId(), crossInfo.getContactPhone(), order)); // 推送给金鹰
		try {
			GetuiUtils.pushToCrossTransmission(Push.createTemplate3_2(crossInfo.getUserId(), crossInfo.getGetuiId(), crossInfo.getContactPhone(), order)); // 透传推送金鹰
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 指派失败 推送用户“您预约的金鹰被能量柱高大89分的会员预订，请选择其他金鹰”、服务费全额退还至余额
	private void assignFail(Order order, Double score) {
		// 更改订单状态
		order.setStatus(OrderEnum.Status.FAIL.getValue());
		order.setAssignTime(new Date());
		try {
			update(order);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		// 全额退服务费
		try {
			accountService.updateRefund(order);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		// 推送给用户
		MemberInfo memberInfo = memberInfoService.findByOrderId(order.getId());
		CrossInfo crossInfo = crossInfoMapper.findByOrderId(order.getId());
		pushService.push(Push.createTemplate2(memberInfo.getUserId(), memberInfo.getGetuiId(), memberInfo.getBankMobile(), order, crossInfo, String.valueOf(score))); // 推送给用户
	}
	
	/**
	 * 轮训订单
	 * 1.查询待轮训订单
	 * 2.循环订单 没有冲突则修改订单状态并推送用户信息  如果相同金鹰有服务时间冲突进入下一步
	 * 3.计算用户能量柱值 用户信息 用户帐户 评价分数 |能判断直接走6
	 * 4.已完成消费天数<用户订单finish总天数>|能判断直接走6
	 * 5.下单时间|能判断直接走6
	 * 6.匹配上的用户修改订单状态推送消息
	 * 7.匹配不上的用户修改订单状态推送消息
	 */
	@Override
	public void loop() throws ServiceException {
		// 查询待轮训订单
		List<Order> allOrders = orderMapper.findAllByStatus(OrderEnum.Status.WAIT_LOOP.getValue());
		if (allOrders == null || allOrders.size() == 0) {
			logger.info("待轮训订单数量:0");
			return;
		}
		logger.info("待轮训订单数量:" + allOrders.size());
		
		// 按金鹰分组
		Map<Integer, List<Order>> map = groupByCross(allOrders);
		for (Integer crossUserId : map.keySet()) {
			
			// 某一个金鹰的订单
			List<Order> crossOrders = map.get(crossUserId);
			logger.info("金鹰id：" + crossUserId + " 总订单数量：" + crossOrders.size() + testCreateOrderIds(crossOrders));
			if (crossOrders.size() == 1) {
				// 只有一条订单直接指派
				assignSuccess(crossOrders.get(0));
				logger.info("只有一条订单直接指派 order:" + crossOrders.get(0).getId());
				continue;
			}
			
			// 生成冲突图
			Graph graph = createGraph(crossOrders);
			logger.info("生成冲突图 总共冲突数量:" + graph.GetEdges());
			
			// 没有冲突订单
			if (graph.GetEdges() == 0) {
				for (int i = 0; i < crossOrders.size(); i++) {
					assignSuccess(crossOrders.get(i));
					logger.info("订单没有冲突 指派 order:" + crossOrders.get(i).getId());
				}
				continue;
			}
			
			// 有冲突订单
			while (graph.GetEdges() > 0) {
				
				// ******筛选有冲突订单和无冲突订单并把无冲突订单指派掉ps.可以把筛选无冲突订单放到while外面******
				List<Order> conflictOrders = new ArrayList<>(); // 有冲突订单
				orderByConfit(graph, crossOrders, conflictOrders, crossUserId);
				
				// ******筛选能量柱******
				List<Order> sameScoreOrders = new ArrayList<>();
				int scoreFlag = orderByScore(graph, conflictOrders, crossOrders, sameScoreOrders);
				if (scoreFlag == 1) {
					continue;
				}
				
				// "******筛选消费天数******"
				List<Order> sameDayorders = new ArrayList<>(); // 消费天数相同的订单
				int flag = orderByDay(graph, sameScoreOrders, crossOrders, sameDayorders);
				if (flag == 1) {
					continue;
				}
				
				// ******筛选下单时间******
				orderByCreateTime(graph, sameDayorders, crossOrders);
			}
		}
	}
	
	/** 筛选有冲突订单和无冲突订单并把无冲突订单指派掉 **/
	private void orderByConfit(Graph graph, List<Order> crossOrders, List<Order> conflictOrders, Integer crossUserId) {
		List<Order> noConfictOrders = new ArrayList<>(); // 没有冲突的订单
		for (int i = crossOrders.size() - 1; i >= 0; i--) {
			if (isInConflict(graph.getUsedPoint(), i)) {
				conflictOrders.add(crossOrders.get(i));
			} else {
				noConfictOrders.add(crossOrders.get(i));
			}
		}
		
		// 把没有冲突的订单指派完
		logger.info("金鹰：" + crossUserId + " 有冲突订单数量：" + conflictOrders.size() + testCreateOrderIds(conflictOrders) + 
					" 无冲突订单数量：" + noConfictOrders.size() + testCreateOrderIds(noConfictOrders));
		for (Order order : noConfictOrders) {
			if(order.getAssignTime() == null) { // 防止重复指派订单，其实只会在第一次执行
				assignSuccess(order);
				logger.info("指派无冲突订单 order:" + order.getId());
			}
		}
	}
	
	/** 筛选能量柱 **/
	private int orderByScore(Graph graph, List<Order> conflictOrders, List<Order> crossOrders, List<Order> sameScoreOrders) {
		logger.info("******筛选能量柱******");
		StringBuffer logInfo = new StringBuffer();
		logInfo.append("订单能量柱分数[");
		for (Order order : conflictOrders) {
			if (order.getTotalScore() == null) { // 避免重复计算能量柱分数
				double totalScore = memberInfoService.caculateScoreTotal(order.getUserId());
				order.setTotalScore(totalScore);
			}
			logInfo.append(order.getId() + "," + order.getTotalScore() + " ");
		}
		logInfo.append("]");
		logger.info(logInfo.toString());
		
		// 按能量柱分数排序
		Collections.sort(conflictOrders, new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				return (int) (o1.getTotalScore() - o2.getTotalScore());
			}
		});
		logger.info("订单数量：" + conflictOrders.size() + " 最小能量柱分数" + conflictOrders.get(0).getTotalScore() + 
					" 最大能量柱分数" + conflictOrders.get(conflictOrders.size() - 1).getTotalScore());
		
		// 查看是否能量柱分数相同的订单
		int index = conflictOrders.size() - 1;
		Order topScoreOrder = conflictOrders.get(index); // 最大能量柱分数订单
		while (index >= 0) {
			Order o = conflictOrders.get(index);
			if (o.getTotalScore().doubleValue() == topScoreOrder.getTotalScore().doubleValue()) {
				sameScoreOrders.add(o);
			}
			index = index - 1;
		}
		
		logger.info("最高分能量柱分数订单数量:" + sameScoreOrders.size() + testCreateOrderIds(sameScoreOrders));
		
		// 指派能量柱分数最高的订单
		if (sameScoreOrders.size() == 1) {
			assignSuccess(sameScoreOrders.get(0));
			Set<Integer> removeIndexs = graph.removePoint(getIndexInCrossOrder(crossOrders, sameScoreOrders.get(0)));
			logger.info("指派能量柱分数最高的订单 order:" + sameScoreOrders.get(0).getId());
			if (removeIndexs != null) {
				for (Integer i : removeIndexs) {
					// 更新指派失败
					assignFail(crossOrders.get(i.intValue()), sameScoreOrders.get(0).getTotalScore());
					logger.info("指派能量柱不匹配订单order:" + crossOrders.get(i.intValue()).getId());
				}
			}
			return 1;
		}
		return 0;
	}
	
	/** 筛选消费天数 **/
	private int orderByDay(Graph graph, List<Order> sameScoreOrders, List<Order> singleCrossOrders, List<Order> sameDayorders) {
		logger.info("******筛选消费天数******");
		StringBuffer logInfo = new StringBuffer();
		// 计算消费天数
		logInfo.setLength(0);
		logInfo.append("订单消费天数[");
		for (Order order : sameScoreOrders) {
			if (order.getTotalDays() == null) { // 避免重复计算订单
				int totalDays = calculateAllOrderDays(order.getUserId());
				order.setTotalDays(totalDays);
				logInfo.append(order.getId() + "," + totalDays + " ");
			}
		}
		logInfo.append("]");
		logger.info(logInfo.toString());
		
		// 按消费天数进行排序
		Collections.sort(sameScoreOrders, new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				return o1.getTotalDays() - o2.getTotalDays();
			}
		});
		logger.info(
				"订单数量：" + sameScoreOrders.size() + 
				" 最小消费天数：" + sameScoreOrders.get(0).getTotalDays() + 
				" 最大消费天数：" + sameScoreOrders.get(sameScoreOrders.size() - 1).getTotalDays());
		
		int mindex = sameScoreOrders.size() - 1;
		Order mlast = sameScoreOrders.get(mindex);
		while (mindex >= 0) {
			Order o = sameScoreOrders.get(mindex);
			if (o.getTotalDays().equals(mlast.getTotalDays())) {
				sameDayorders.add(o);
			}
			mindex = mindex - 1;
		}
		
		logger.info("消费天数最高订单数量" + sameDayorders.size() + testCreateOrderIds(sameDayorders));
		
		// 指派消费天数最高的订单
		if (sameDayorders.size() == 1) {
			assignSuccess(sameDayorders.get(0));
			logger.info("指派消费天数最高的订单:" + sameDayorders.get(0).getId());
			Set<Integer> removeIndexs = graph.removePoint(getIndexInCrossOrder(singleCrossOrders, sameDayorders.get(0)));
			if (removeIndexs != null) {
				for (Integer i : removeIndexs) {
					// 更新指派失败
					assignFail(singleCrossOrders.get(i.intValue()), sameDayorders.get(0).getTotalScore());
					logger.info("指派不匹配消费天数订单 order:" + singleCrossOrders.get(i.intValue()).getId());
				}
			}
			return 1;
		}
		return 0;
	}
	
	/** 筛选下单时间 **/
	private void orderByCreateTime(Graph graph, List<Order> orders, List<Order> allorders) {
		logger.info("******筛选下单时间******");
		// 按下单时间进行排序
		Collections.sort(orders, new Comparator<Order>() {

			@Override
			public int compare(Order o1, Order o2) {
				return (int) (o1.getCreateTime().getTime() - o2.getCreateTime().getTime());
			}
		});
		logger.info("下单时间最早的订单 " + DateUtils.parseFullTime(orders.get(0).getCreateTime()) + 
					" 下单时间最晚的订单 " + DateUtils.parseFullTime(orders.get(orders.size() - 1).getCreateTime()));
		
		// 更新指派成功
		Order order = orders.get(0);
		assignSuccess(order);
		logger.info("指派匹配下单时间订单order:" + orders.get(0).getId());

		// 更新指派失败
		Set<Integer> removeIndexs = graph.removePoint(getIndexInCrossOrder(allorders, order));
		for (Integer index : removeIndexs) {
			assignFail(allorders.get(index.intValue()), orders.get(0).getTotalScore());
			logger.info("指派不匹配下单时间订单order:" + allorders.get(index.intValue()).getId());
		}
	}
	
	/**
	 * 获得订单在金鹰订单中的索引位置
	 */
	private int getIndexInCrossOrder(List<Order> singleCrossOrders, Order order) {
		return singleCrossOrders.indexOf(order);
	}
	
	/**
	 * 是否在冲突订单内
	 */
	private boolean isInConflict(Set<Integer> sets, Integer i) {
		for (Integer index : sets) {
			if (index.intValue() == i.intValue()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 计算会员消费天数
	 */
	private int calculateAllOrderDays(Integer userId) {
		int totalCount = 0;
		List<Order> orders = orderMapper.findOrderDays(userId, OrderEnum.Status.FINISH.getValue());
		if (orders != null) {
			for (int i = 0; i < orders.size(); i++) {
				totalCount += DateUtils.getBetweenDay(orders.get(i).getStartTime(), orders.get(i).getEndTime());
			}
		}
		return totalCount;
	}
	
	public List<Order> orderList(List<Order> modelList) {

		if (modelList != null && modelList.size() > 0) {
			for (int i = 0; i < modelList.size(); i++) {
				Order order = modelList.get(i);
				String appendDataA = order.getCityName() + "<br>" + order.getOrderCard();
				String appendDataB = "";
					if (order.getAssignDate() != null){	
						appendDataB = "下单：" + order.getCreateDate() + "<br>" + "指派：" + order.getAssignDate();
					} else {
						appendDataB = "下单：" + order.getCreateDate();
					}
				String appendDataC = order.getMemberName() + "<br>" + order.getMobile();
				String appendDataD = "开始:" + order.getStartDate() + "<br>" + "结束:" + order.getEndDate();
				String appendDataE = order.getWorkName() + "<br>" + order.getCrossNumber();
				String appendDataF = order.getOrderStatus();
				OrderComplaint orderComplaint = new OrderComplaint();
				orderComplaint.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
				orderComplaint.setOrderId(order.getId());
				OrderCancel orderCancel = new OrderCancel();
				orderCancel.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
				orderCancel.setOrderId(order.getId());
				OrderDoubt orderDoubt = new OrderDoubt();
				orderDoubt.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
				orderDoubt.setOrderId(order.getId());
				
				if (order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE.getValue()) || order.getStatus().equals(OrderEnum.Status.SERVING.getValue()) || 
						order.getStatus().equals(OrderEnum.Status.WAIT_CONFIRM.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE_CODE.getValue())) {
					try {
	
						List<OrderComplaint> orderComplaintInfo = orderComplaintService.findList(null, null, orderComplaint);
						List<OrderCancel> orderCancelInfo = orderCancelService.findList(null, null, orderCancel);
						List<OrderDoubt> orderDoubtInfo = orderDoubtService.findList(null, null, orderDoubt);
						
						if (orderComplaintInfo != null && orderComplaintInfo.size() > 0) {
							appendDataF += "<font color='#FF0000'>" + "<br>" + "投诉受理中" + "</font> ";
						} else if (orderCancelInfo != null && orderCancelInfo.size() > 0) {
							appendDataF +=  "<font color='#FF0000'>" + "<br>" + "取消行程审核中" + "</font> ";
						} else if (orderDoubtInfo != null && orderDoubtInfo.size() > 0) {
							appendDataF += "<font color='#FF0000'>" + "<br>" + "账单疑异" + "</font> ";
						}
	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(order.getStatus() == OrderEnum.Status.WAIT_SERVICE.getValue()
						|| order.getStatus() == OrderEnum.Status.SERVING.getValue()
						|| order.getStatus() == OrderEnum.Status.WAIT_CONFIRM.getValue()
						|| order.getStatus() == OrderEnum.Status.WAIT_SERVICE_CODE.getValue()){
					modelList.get(i).setShowAndHide(1);
				}
				
				modelList.get(i).setAppendDataA(appendDataA);
				modelList.get(i).setAppendDataB(appendDataB);
				modelList.get(i).setAppendDataC(appendDataC);
				modelList.get(i).setAppendDataD(appendDataD);
				modelList.get(i).setAppendDataE(appendDataE);
				modelList.get(i).setAppendDataF(appendDataF);
			}
		}
		return modelList;
	}
	
	/**
	 * 更换金鹰
	 */
	public int updateReplaceCross(Integer orderId, Integer crossId) throws ServiceException{
		try {
			// 原订单
			Order oModel = new Order();
			oModel.setId(orderId);
			List<Order> orderList = orderMapper.selectOrderList(null, null, oModel);
			Order order = orderList.get(0);
			
			// 原行程
		    OrderTravel orModel = new OrderTravel();
		    orModel.setOrderId(orderId);
		    List<OrderTravel> oldOrderTravelList = orderTravelService.findList(null, null, orModel);
		    
		    // 更改原订单订单状态
		    Order oldOrder = new Order();
		    oldOrder.setId(orderId);
		    oldOrder.setStatus(OrderEnum.Status.EXCHANGE.getValue());   // 先改订单为9
		    orderMapper.updateByID(oldOrder);
		    
		    // 插入新订单
		    Order newOrder = new Order(order.getUserId(), crossId, order.getCityId(), order.getStartTime(), order.getEndTime(),
		    		order.getAssignTime(), order.getServiceCode(), order.getServiceMoney(), order.getCouponId(), order.getPayMethod(), order.getPayMoney(), order.getIsNeedInvoice(),
		    		order.getInvoiceTitle(), order.getInvoiceAddress(), order.getInvoicePeople(), order.getInvoiceMobile(), order.getContactMethod(), order.getCostPayMethod(),
		    		order.getTravelDescId(), order.getTravelRequireText(), order.getTravelRequireVoice(), order.getCreateTime(), order.getOpenId(),
		    		order.getStatus());
		    orderMapper.insert(newOrder);
		    
		    // 插入新行程单
		    for (OrderTravel orderTravel : oldOrderTravelList) {
		    	OrderTravel newOrderTravel = new OrderTravel(newOrder.getId(), DateUtils.parseYMDDate(orderTravel.getStartDate()), DateUtils.parseYMDDate(orderTravel.getEndDate()), orderTravel.getTitle(), orderTravel.getStatus());
		    	orderTravelService.insert(newOrderTravel);
		    	
		    	// 插入新账单
		    	OrderCost ocModel = new OrderCost();
		    	ocModel.setOrderTravelId(orderTravel.getId());
		    	List<OrderCost> oldOrderCostList = orderCostService.findList(null, null, ocModel);
		    	if (oldOrderCostList != null && oldOrderCostList.size() > 0){
			    	for (OrderCost orderCost : oldOrderCostList) {
			    		OrderCost newOrderCost = new OrderCost(newOrderTravel.getId(), orderCost.getCostItemId(), orderCost.getMoney(), orderCost.getRemark(), orderCost.getStatus());
				    	orderCostService.insert(newOrderCost);
					}
		    	}
			}
		    
		    // 更改金鹰服务时间
		    String today = DateUtils.parseYMDTime(new Date());
		    long todayTime = DateUtils.parseYMDDate(today).getTime();
		    List<String> days = order.setServiceDays();
		    
		    boolean isServiceing = false; // 是否在服务天数中
		    for (String day : days) {
				if (day.equals(today)) {
					isServiceing = true;
					break;
				}
			}
		    logger.info("isServiceing:" + isServiceing);
		    
		    for (String day : days) {
		    	long dayTime = DateUtils.parseYMDDate(day).getTime();
		    	if (isServiceing) {
		    		// 服务中
		    		if (dayTime > todayTime) {
		    			crossTimesService.updateCrossTimeStatus(order.getCrossUserId(), TimeEnum.CAN_BOOK.getValue(), day); // 更改原金鹰服务时间
		    		}
				} else {
					// 未开始服务
					crossTimesService.updateCrossTimeStatus(order.getCrossUserId(), TimeEnum.CAN_BOOK.getValue(), day);
				}
		    	if (dayTime >= todayTime) {
		    		crossTimesService.updateCrossTimeStatus(crossId, TimeEnum.BOOKED.getValue(), day); // 更改现金鹰服务时间
		    	}
			}
		} catch (Exception e) {
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}    
		return 1;
	}

	@Override
	public Map<String, Object> findOrderTotalInfo(Integer orderId) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		Order order = orderMapper.findOrderDetail(orderId);
		order.setStartDate(order.getStartTime());
		order.setEndDate(order.getEndTime());
		map.put("order", order);
		if (order.getStatus() == OrderEnum.Status.WAIT_SERVICE.getValue() || order.getStatus() == OrderEnum.Status.SERVING.getValue() || order.getStatus().equals(OrderEnum.Status.WAIT_CONFIRM.getValue()) || order.getStatus().equals(OrderEnum.Status.WAIT_SERVICE_CODE.getValue())) {
			OrderComplaint orderComplaint = new OrderComplaint();
			orderComplaint.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
			orderComplaint.setOrderId(order.getId());
			OrderCancel orderCancel = new OrderCancel();
			orderCancel.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
			orderCancel.setOrderId(order.getId());

			OrderComplaint complaint = orderComplaintService.findOne(orderComplaint);
			OrderCancel cancel = orderCancelService.findOne(orderCancel);
			
			if (complaint != null) {
				map.put("complaint", complaint);
			} else if (cancel != null) {
				map.put("cancel", cancel);
			}
		}
		if (order.getStatus() == OrderEnum.Status.WAIT_COMMENT.getValue() || order.getStatus() == OrderEnum.Status.CANCEL_BY_ADMIN.getValue()) {
			AccountRecord model = new AccountRecord();
			model.setOrderId(orderId);
			model.setType(AccountRecordEnum.REFUND.getValue());
			AccountRecord refund = accountRecordService.findOne(model);
			if (refund != null) {
				map.put("refund", refund);
			}
		}
		return map;
	}

	@Override
	public Order findOrderRequireInfo(Integer orderId) throws NumberFormatException, ServiceException {
		Order order = orderMapper.findOrderInfo(orderId);
		if (order.getContactMethod() == OrderEnum.ContactMethod.ORTHER.getValue()) {
			List<TravelDesc> descList = new ArrayList<>();
			String idString = order.getTravelDescId();
			if (idString != null)  {
				String[] descIds = idString.split(",");
				for (String descId : descIds) {
					descList.add(travelDescService.findByID(Integer.parseInt(descId)));
				}
			}
			order.setDescList(descList);
		}
		return order;
	}

	/**
	 * 查询金鹰历史订单
	 */
	@Override
	public PageData<Order> queryCrossHistoryOrder(Integer crossUserId, Integer currentPage, Integer pagesize) {
		Integer startpos = null;
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		List<Order> orders = orderMapper.findCrossHistoryOrder(crossUserId, OrderEnum.Status.WAIT_SERVICE.getValue(), startpos, pagesize);
		if (orders != null && orders.size() > 0) {
			for (Order order : orders) {
				if (order.getStatus().equals(OrderEnum.Status.WAIT_COMMENT.getValue()) || order.getStatus().equals(OrderEnum.Status.FINISH.getValue())) {
					boolean isCrossCommented = false;
					MemberComment model = new MemberComment();
					model.setCrossUserId(order.getCrossUserId());
					model.setOrderId(order.getId());
					try {
						if (memberCommentService.findOne(model) != null) {
							isCrossCommented = true;
						}
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					order.setIsCrossCommented(isCrossCommented);
				}
			}
		}
		Integer allRow = orderMapper.findCrossCountHistoryOrder(crossUserId, OrderEnum.Status.WAIT_SERVICE.getValue());
		PageData<Order> pageData = new PageData<>(orders, allRow, currentPage++, pagesize);
		return pageData;
	}

	@Override
	public Order queryCrossOrderInfo(Integer orderId) {
		return orderMapper.findCrossOrderInfo(orderId);
	}
	
	@Override
	public Integer queryLastOrderNo() {
		String date = DateUtils.parseYMDTime(new Date(System.currentTimeMillis()));
        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        Integer no = 1;
        Order order = orderMapper.findLastOne(startTime, endTime);
        if (order != null && !StringUtils.isNull(order.getOrderCard())) {
			no = Integer.valueOf(order.getOrderCard().substring(9, 15));
	        no++;
		}
		return no;
	}

	@Override
	public Order serviceCode(Integer orderId, String serviceCode) throws ServiceException {
		// 验证订单状态
		Order order = orderMapper.findOrderInfo(orderId);
		if (order.getStatus().intValue() != OrderEnum.Status.WAIT_SERVICE_CODE.getValue()) {
			throw new ServiceException(ResultCode.SERVICE_CODE_ORDER_WRONG_STATUS);
		}
		// 判断服务编码忽略大小写
		if (!order.getServiceCode().equalsIgnoreCase(serviceCode)) {
			throw new ServiceException(ResultCode.SERVICE_CODE_ERROR);
		}
		// 更新订单状态
		order.setStatus(OrderEnum.Status.SERVING.getValue());
		update(order);
		return order;
	}
	
	@Override
	public Order edit(Integer orderId, Integer contactMethod, Integer costPayMethod) throws ServiceException {
		Order order = orderMapper.findOrderInfo(orderId);
		// 只允许修改待服务订单
		if (order.getStatus().intValue() != OrderEnum.Status.WAIT_SERVICE.getValue()) {
			throw new ServiceException(ResultCode.ORDER_STATUS_NEGATIVE);
		}
		if (contactMethod != null) {
			order.setContactMethod(contactMethod);
		}
		if (costPayMethod != null) {
			order.setCostPayMethod(costPayMethod);
		}
		update(order);
		return order;
	}

	@Override
	public Order selectOrderNotFinished(Integer userId) throws ServiceException {
		Order model = new Order();
		model.setUserId(userId);
		model.setStatus(OrderEnum.Status.WAIT_COMMENT.getValue());
		List<Order> orders = findList(null, null, model);
		if (orders != null && orders.size() > 0)
			return orders.get(0);
		return null;
	}
	
	@Override
	public int updatePlaceOrder(Integer orderId, String date, Double money) throws ServiceException{
		
		int orderDoubtId = orderDoubtService.placeOrder(orderId, date);
		if (orderDoubtId < 1){
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
		}	
        
		Order order = orderMapper.findOrderById(orderId);
		
		Account account = accountMapper.findAccount(order.getUserId());
		
		Double newMoney = 0.0;
		Double newBalance = 0.0;
		Double freezeBalance = 0.0;
		if (money > account.getFreezeBalance()){
			newMoney = money - account.getFreezeBalance();
			newBalance = account.getBalance() - newMoney;
			freezeBalance = newMoney + account.getFreezeBalance();
		} else if (money < account.getFreezeBalance()){
			newMoney = account.getFreezeBalance() - money;
			newBalance = account.getBalance() + newMoney;
			freezeBalance = account.getFreezeBalance() - newMoney;
		}
		
		Account accountModel = new Account();
		accountModel.setBalance(newBalance);
		accountModel.setId(account.getId());
		accountModel.setFreezeBalance(freezeBalance);
		
		accountMapper.updateByID(accountModel);
		
		
		// 若存疑的账单是一天前的，将账单确认并从冻结款中扣除
		if (DateUtils.parseYMDDate(date).getTime() <= new Date(DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date())).getTime() - DateUtils.DAY).getTime()) {
			String startDate = date + " 00:00:00";
			String endDate = date + " 23:59:59";
			List<OrderTravel> orderTravels = orderTravelMapper.queryTodayTravelList(orderId, OrderEnum.Travel.REMOVE.getValue(), startDate, endDate);
			if (orderTravels != null) {
				double totalCost = 0;
				for (OrderTravel orderTravel : orderTravels) {
					if (orderTravel.getStatus() == OrderEnum.Travel.FINISH.getValue())
						continue;
					orderTravel.setStatus(OrderEnum.Travel.FINISH.getValue()); // 修改当天行程为已完成
					orderTravelService.update(orderTravel);
					if (order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue()) {
						List<OrderCost> costList = orderCostMapper.findCostListByTravelId(orderTravel.getId(), OrderEnum.Cost.REMOVE.getValue());
						if (costList != null) {
							for (OrderCost cost : costList) {
								totalCost += cost.getMoney();
							}
						}
					}
				}
				
				if (order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue() && totalCost > 0) {
					// 从用户账户冻结款项中扣除账单数额
					Account model = new Account();
					model.setUserId(order.getUserId());
					Account memberAccount = accountService.findOne(model);
					memberAccount.setUserId(order.getUserId());
					memberAccount.setFreezeBalance(accountService.findOne(memberAccount).getFreezeBalance() - totalCost);
					accountMapper.updateByID(memberAccount);
					
					// 添加账单操作记录
					accountRecordMapper.insert(new AccountRecord(order.getUserId(), orderId, totalCost, AccountRecordEnum.ORDER_COST.getValue(), new Date()));
				}
			}
			boolean flag = true;
			if (order.getEndTime().getTime() > DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date()) + " 23:59:59").getTime())
				flag = false;
			// 若订单中所有行程都已完成，修改订单状态为待评价
			if (flag) {
				List<OrderTravel> travels = orderTravelMapper.queryTravelList(orderId, OrderEnum.Travel.REMOVE.getValue());
				for (OrderTravel travel : travels) {
					if (travel.getStatus() != OrderEnum.Travel.FINISH.getValue()){
						flag = false;
						break;
					}
				}
			}
			if (flag) {
				order.setStatus(OrderEnum.Status.WAIT_COMMENT.getValue());
				orderMapper.updateByID(order);
			}
		}
		
		OrderDoubt orderDoubt = new OrderDoubt();
		orderDoubt.setId(orderDoubtId);
		orderDoubt.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
		orderDoubtService.update(orderDoubt);
		
		return 1;
	}

	@Override
	public int findCountServing(Integer userId) throws ServiceException {
		if (userId == null)
			throw new ServiceException(ResultCode.PARAMETER_ERROR);
		return orderMapper.findCountNotFinish(userId);
	}

	@Override
	public Integer orderNineCount() {
		String startTime = DateUtils.parseYMDTime(new Date()) + " 00:00:00";
		String endTime =  DateUtils.parseYMDTime(new Date()) + " 23:59:59";
		Integer orderStatus = OrderEnum.Status.SERVING.getValue();
		Integer travelStatus = Travel.SERVING.getValue();
		List<Integer> orderIds = orderTravelMapper.queryServingTravelListToday(startTime, endTime, orderStatus, travelStatus);
		if (orderIds != null) {
			return orderIds.size();
		}
		return 0;
	}

	@Override
	public List<Order> orderNineList(Integer currentPage, Integer pagesize, Order model) {
		Integer startpos = null;
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		
		List<Order> orders = new ArrayList<>();
		String startTime = DateUtils.parseYMDTime(new Date()) + " 00:00:00";
		String endTime =  DateUtils.parseYMDTime(new Date()) + " 23:59:59";
		Integer orderStatus = OrderEnum.Status.SERVING.getValue();
		Integer travelStatus = Travel.SERVING.getValue();
		List<Integer> orderIds = orderTravelMapper.queryServingTravelListTodayPage(startTime, endTime, orderStatus, travelStatus, startpos, pagesize);
		
		model.setOrderId(orderIds);
		model.setStateQuery("stateQuery");
		orders = orderMapper.selectOrderList(startpos, pagesize, model);
		return this.orderList(orders);
	}
}
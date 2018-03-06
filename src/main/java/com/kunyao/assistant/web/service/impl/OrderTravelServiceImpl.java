package com.kunyao.assistant.web.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Push;
import com.kunyao.assistant.core.entity.wx.WxTemplate;
import com.kunyao.assistant.core.enums.AccountRecordEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.enums.OrderEnum.Travel;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.weixin.WxService;
import com.kunyao.assistant.core.feature.weixin.WxUtils;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Account;
import com.kunyao.assistant.core.model.AccountRecord;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.Coupon;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.MemberInfo;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderCancel;
import com.kunyao.assistant.core.model.OrderComplaint;
import com.kunyao.assistant.core.model.OrderConfig;
import com.kunyao.assistant.core.model.OrderCost;
import com.kunyao.assistant.core.model.OrderDoubt;
import com.kunyao.assistant.core.model.OrderTravel;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.AccountMapper;
import com.kunyao.assistant.web.dao.AccountRecordMapper;
import com.kunyao.assistant.web.dao.CityMapper;
import com.kunyao.assistant.web.dao.CouponMapper;
import com.kunyao.assistant.web.dao.CrossInfoMapper;
import com.kunyao.assistant.web.dao.OrderConfigMapper;
import com.kunyao.assistant.web.dao.OrderCostMapper;
import com.kunyao.assistant.web.dao.OrderDoubtMapper;
import com.kunyao.assistant.web.dao.OrderMapper;
import com.kunyao.assistant.web.dao.OrderTravelMapper;
import com.kunyao.assistant.web.service.AccountService;
import com.kunyao.assistant.web.service.InvoiceRecordService;
import com.kunyao.assistant.web.service.MemberInfoService;
import com.kunyao.assistant.web.service.OrderCancelService;
import com.kunyao.assistant.web.service.OrderComplaintService;
import com.kunyao.assistant.web.service.OrderDoubtService;
import com.kunyao.assistant.web.service.OrderTravelService;
import com.kunyao.assistant.web.service.PushService;

@Service
public class OrderTravelServiceImpl extends GenericServiceImpl<OrderTravel, Integer> implements OrderTravelService {
    @Resource
    private OrderTravelMapper orderTravelMapper;
    
    @Resource
    private AccountMapper accountMapper;
    
    @Resource
    private OrderCostMapper orderCostMapper;
    
    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private AccountService accountService;
    
    @Resource
    private AccountRecordMapper accountRecordMapper;
    
    @Resource
    private MemberInfoService memberInfoService;
    
    @Resource
    private OrderDoubtMapper orderDoubtMapper;

    @Resource
    private CityMapper cityMapper;
    
    @Resource
    private CrossInfoMapper crossInfoMapper;
    
    @Resource
    private PushService pushService;
    
    @Resource
    private OrderConfigMapper orderConfigMapper;
    
	@Resource
    private InvoiceRecordService invoiceRecordService;
    @Resource
    private OrderComplaintService orderComplaintService;
    
    @Resource
    private OrderDoubtService orderDoubtService;
    
    @Resource
    private OrderCancelService orderCancelService;
    
    @Resource
    private CouponMapper couponMapper;
    
    public GenericDao<OrderTravel, Integer> getDao() {
        return orderTravelMapper;
    }
    
    /**
     * 行程单详情
     */
	@Override
	public OrderTravel queryInfo(Integer travelId) throws ServiceException {
		return orderTravelMapper.findInfo(travelId);
	}
	
	/*
	 * 返回json格式
	 * 
	 * [
	 * 	{
	 * 		date:'2016-10-18',
	 * 		list: []
	 *  },
	 *  {
	 * 		date:'2016-10-18',
	 * 		list: []
	 *  }
	 * ]
	 * 
	 */
    @Override
    public List<Map<String, Object>> queryTravelList(Integer orderId) {
    	List<OrderTravel> list = orderTravelMapper.queryTravelList(orderId, OrderEnum.Travel.REMOVE.getValue());
    	if (list == null) {
			return null;
		}
    	for (int i = list.size()- 1; i >= 0; i--) {
    		OrderTravel o = list.get(i);
    		if (o.getTitle().equals("前置消费")) {
				list.remove(o);
			}
		}
    	// 按日期进行分组排序
    	List<Map<String, Object>> result = new ArrayList<>();
    	String lastDate = "";
    	for (OrderTravel orderTravel : list) {
    		Date date = orderTravel.getStartTime();
    		String str = DateUtils.parseYMDTime(date);
    		OrderDoubt model = new OrderDoubt();
    		model.setOrderId(orderId);
    		
    		// 查询账单疑义
    		SimpleDateFormat YMD_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
			try {
				model.setDateOfDoubtTime(YMD_FORMAT.parse(YMD_FORMAT.format(orderTravel.getStartTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		model.setStatus(OrderEnum.Doubt.INIT.getValue());
    		int doubtNum = orderDoubtMapper.findCountByCondition(model);
    		if (!lastDate.equals(str)) {
    			Map<String, Object> map = new HashMap<>();
    			map.put("status", doubtNum > 0 ? 500 : orderTravel.getStatus());
    			map.put("date", str);
    			map.put("list", new ArrayList<OrderTravel>());
    			result.add(map);
			}
    		@SuppressWarnings("unchecked")
			List<OrderTravel> orderTravels = (List<OrderTravel>) result.get(result.size() - 1).get("list");
    		orderTravels.add(orderTravel);
    		lastDate = str;
		}
    	// 补全订单天数
    	Order order = orderMapper.findOrderInfo(orderId);
    	List<String> days = order.setServiceDays();
    	for (Map<String, Object> map : result) {
    		String day = (String) map.get("date");
    		days.remove(day);
		}
    	for (String string : days) {
    		Map<String, Object> map = new HashMap<>();
			map.put("status", -1);
			map.put("date", string);
			map.put("list", new ArrayList<OrderTravel>());
			result.add(map);
		}
    	Collections.sort(result, new Comparator<Map<String, Object>>() {
    		
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Date d1 = DateUtils.parseYMDDate(String.valueOf(o1.get("date")));
				Date d2 = DateUtils.parseYMDDate(String.valueOf(o2.get("date")));
				return (int) (d1.getTime() - d2.getTime());
			}
		});
    	return result;
    }
    
    /**
	 * 查询订单下行程单列表
	 * 按时间分组
	 */
	@Override
	public List<Map<String, Object>> queryTravelByOrderId(Integer orderId) {
		List<Map<String, Object>> result = new ArrayList<>();
		List<OrderTravel> travels = orderTravelMapper.queryTravelList(orderId, OrderEnum.Travel.REMOVE.getValue());
		if (travels != null) {
			String lastDate = "";
			for (OrderTravel orderTravel : travels) {
				String date = DateUtils.YMD_FORMAT.format(orderTravel.getStartTime());
				if (!lastDate.equals(date)) {
					Map<String, Object> map = new HashMap<>();
					map.put("date", date);
					map.put("list", new ArrayList<OrderTravel>());
					result.add(map);
				}
				@SuppressWarnings("unchecked")
				List<OrderTravel> list = (List<OrderTravel>) result.get(result.size() - 1).get("list");
				list.add(orderTravel);
				lastDate = date;
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> selectTravelListWithCost(Integer orderId, Integer costPayMethod) {
		List<OrderTravel> list = orderTravelMapper.queryTravelList(orderId, OrderEnum.Travel.REMOVE.getValue());
		Order order = orderMapper.findOrderInfo(orderId);
    	// 按日期进行分组排序
    	List<Map<String, Object>> result = new ArrayList<>();
    	String lastDate = "";
    	for (int i=0; i<list.size(); i++) {
    		if (costPayMethod == OrderEnum.CostPayMethod.CROSS.getValue() && order.getStatus() >= OrderEnum.Status.SERVING.getValue() && order.getStatus() <= OrderEnum.Status.WAIT_COMMENT.getValue())
    			list.get(i).setCostList(orderCostMapper.findCostListByTravelId(list.get(i).getId(), OrderEnum.Cost.REMOVE.getValue()));
    		Date date = list.get(i).getStartTime();
    		String str = DateUtils.parseYMDTime(date);
    		if (!lastDate.equals(str)) {
    			Map<String, Object> map = new HashMap<>();
    			map.put("status", list.get(i).getStatus());
    			map.put("date", str);
    			map.put("list", new ArrayList<OrderTravel>());
    			if ("前置消费".equals(list.get(i).getTitle())) {
    				map.put("front", list.get(i));
    			}
    			OrderDoubt doubt = orderDoubtMapper.findDoubtByDate(orderId, str, OrderEnum.Doubt.INIT.getValue());
    			if (doubt != null)
    				map.put("doubt", doubt);
    			result.add(map);
			}
    		if (!"前置消费".equals(list.get(i).getTitle())) {
	    		@SuppressWarnings("unchecked")
				List<OrderTravel> orderTravels = (List<OrderTravel>) result.get(result.size() - 1).get("list");
	    		orderTravels.add(list.get(i));
    		}
    		lastDate = str;
		}
    	return result;
	}

	@Override
	public void updateConfirmTravel(Integer orderId, Integer costPayMethod, Integer dayNum) throws ServiceException {
		if (costPayMethod == OrderEnum.CostPayMethod.CROSS.getValue() && dayNum * 10000 > accountMapper.findAccount(orderMapper.findOrderInfo(orderId).getUserId()).getBalance()) {
			throw new ServiceException(ResultCode.BALANCE_ENOUGH);
		}
		List<OrderTravel> orderTravels = orderTravelMapper.queryTravelList(orderId, OrderEnum.Travel.REMOVE.getValue());
		if (orderTravels != null) {
			for (OrderTravel travel : orderTravels) {
				if (travel.getStatus() != OrderEnum.Travel.INIT.getValue())
					continue;
				travel.setStatus(OrderEnum.Travel.CONFIRMED.getValue());
				update(travel);
			}
		}
		// 修改订单状态 等待输入服务编码
		Order model = new Order();
		model.setId(orderId);
		model.setStatus(OrderEnum.Status.WAIT_SERVICE_CODE.getValue());
		orderMapper.updateByID(model);
	}

	/**
	 * 添加行程单
	 * 前置条件：订单状态为 待服务、服务中、待确认行程
	 */
	@Override
	public OrderTravel insertOrderTravel(Integer orderId, Date startTime, Date endTime, String title) throws ServiceException {
		Order order = orderMapper.findOrderInfo(orderId);
		if (order == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		
		// 订单状态 1、待服务 2、服务中 3、待确认行程 4、服务编码待确认   可以添加行程
		int orderStatus = order.getStatus().intValue();
		if (orderStatus != OrderEnum.Status.WAIT_SERVICE.getValue() && orderStatus != OrderEnum.Status.SERVING.getValue()
				&& orderStatus != OrderEnum.Status.WAIT_CONFIRM.getValue() && orderStatus != OrderEnum.Status.WAIT_SERVICE_CODE.getValue()) {
			throw new ServiceException(ResultCode.ORDER_STATUS_NEGATIVE);
		}
		
		// 如果是服务中添加行程（今天添加，行程单状态为服务中，不是今天添加是行程单状态是已确认）
		Integer defaultTravelStatus = Travel.INIT.getValue();
		if (orderStatus == OrderEnum.Status.SERVING.getValue()) {
			if (DateUtils.isSameDate(startTime, new Date())) {
				defaultTravelStatus = Travel.SERVING.getValue();
			} else {
				defaultTravelStatus = Travel.CONFIRMED.getValue();
			}
		}
		
		// 判断是否添加前置消费
		String startDate = DateUtils.parseYMDTime(startTime) + " 00:00:00";
		String endDate = DateUtils.parseYMDTime(endTime) + " 23:59:59";
		List<OrderTravel> orderTravels = orderTravelMapper.queryTodayTravelList(orderId, OrderEnum.Travel.REMOVE.getValue(), startDate, endDate);
		if (orderTravels == null || orderTravels.size() == 0) {
			// 添加前置消费
			OrderTravel preOrderTravel = new OrderTravel(orderId, DateUtils.parseFullDate(startDate), 
					DateUtils.parseFullDate(startDate), "前置消费", defaultTravelStatus); // 时间为 00:00~00:00
			insert(preOrderTravel);
		}
		
		// 添加行程
		OrderTravel orderTravel = new OrderTravel(orderId, startTime, endTime, title, defaultTravelStatus);
		insert(orderTravel);
		return orderTravel;
	}

	@Override
	public OrderTravel updateRemove(Integer travelId) throws ServiceException {
		OrderTravel orderTravel = orderTravelMapper.findInfo(travelId);
		if (orderTravel == null) {
			throw new ServiceException(ResultCode.TRAVEL_NOT_FOUND);
		}
		// 已完成的行程不可以被删除
		if (orderTravel.getStatus().intValue() == OrderEnum.Travel.FINISH.getValue()) {
			throw new ServiceException(ResultCode.TRAVEL_NOT_ALLOW_OPERATE);
		}
		// 查询行程下面是否有账单，有账单的不可以被删除
		List<OrderCost> orderCosts = orderCostMapper.queryTravelCostList(travelId, OrderEnum.Travel.REMOVE.getValue());
		if (orderCosts != null && orderCosts.size() > 0) {
			throw new ServiceException(ResultCode.TRAVEL_REMOVE_ERROR);
		}
		// 删除行程
		orderTravel.setStatus(OrderEnum.Travel.REMOVE.getValue());
		update(orderTravel);
		return orderTravel;
	}

	@Override
	public OrderTravel updateEdit(Integer travelId, Date startTime, Date endTime, String title) throws ServiceException {
		OrderTravel orderTravel = orderTravelMapper.findInfo(travelId);
		if (orderTravel == null) {
			throw new ServiceException(ResultCode.TRAVEL_NOT_FOUND);
		}
		// 已完成的账单不可以被修改
		if (orderTravel.getStatus().intValue() == OrderEnum.Travel.FINISH.getValue()) {
			throw new ServiceException(ResultCode.TRAVEL_NOT_ALLOW_OPERATE);
		}
		if (startTime != null) { orderTravel.setStartTime(startTime); }
		if (endTime !=  null) { orderTravel.setEndTime(endTime); }
		if (!StringUtils.isNull(title)) { orderTravel.setTitle(title); }
		update(orderTravel);
		return orderTravel;
	}

	@Override
	public void updatePush(Integer orderId) throws ServiceException {
		// 只有待服务状态可以推送订单
		Order order = orderMapper.findOrderInfo(orderId);
		if (order.getStatus().intValue() != OrderEnum.Status.WAIT_SERVICE.getValue()) {
			throw new ServiceException(ResultCode.ORDER_STATUS_NEGATIVE);
		}
		// 判断订单中的每天是否都添加了行程单
		List<String> days = order.setServiceDays();
		for (String day : days) {
			Integer count = orderTravelMapper.queryTravelCountByDay(orderId, Travel.REMOVE.getValue(), day);
			if (count == null || count.intValue() == 0) {
				throw new ServiceException(ResultCode.TRAVEL_NOT_ENOUGH);
			}
		}
		// 更改为待确认行程订单状态
		order.setStatus(OrderEnum.Status.WAIT_CONFIRM.getValue());
		orderMapper.updateByID(order);
		// 推送
		// wxPush(orderId);
		geTuipush(orderId);
	}
	
	// 个推推送 通知用户确认行程
	public void geTuipush(Integer orderId) {
		Order order = orderMapper.findOrderInfo(orderId);
		City city = cityMapper.findCity(order.getCityId());
		order.setCityName(city.getName());
		CrossInfo crossInfo = crossInfoMapper.findByOrderId(order.getId());
		MemberInfo memberInfo = memberInfoService.findByOrderId(order.getId());
		pushService.push(Push.createTemplate4(memberInfo.getUserId(), memberInfo.getGetuiId(), memberInfo.getBankMobile(), order, crossInfo));
	}
	
	// 微信推送
	public boolean wxPush(Integer orderId) {
		@SuppressWarnings("unused")
		Order order = orderMapper.findOrderInfo(orderId);
		// 生成微信模板对象
		WxTemplate template = WxTemplate.createSample();
		// 刷新微信Token
		WxUtils.refereshWXToken();
		// 发送微信模板消息
		return WxService.sendWxTemplate(template);
	}
	
	/**
	 * 今日上班
	 */
	@Override
	public boolean updateCrossStart(Integer orderId, String date) throws ServiceException {
		// 查询今日行程
		String startDate = date + " 00:00:00";
		String endDate = date + " 23:59:59";
		List<OrderTravel> orderTravels = orderTravelMapper.queryTodayTravelList(orderId, OrderEnum.Travel.REMOVE.getValue(), startDate, endDate);
		if (orderTravels == null) {
			return true;
		}
		// 把当天行程单改为服务中
		for (OrderTravel orderTravel : orderTravels) {
			orderTravel.setStatus(OrderEnum.Travel.SERVING.getValue());
			update(orderTravel);
		}
		return true;
	}
	
	/**
	 * 今日下班，推送用户或直接修改行程单状态
	 */
	@Override
	public boolean updateCrossFinish(Integer orderId, String date) throws ServiceException {
		// 查询今日行程
		String startDate = date + " 00:00:00";
		String endDate = date + " 23:59:59";
		List<OrderTravel> orderTravels = orderTravelMapper.queryTodayTravelList(orderId, OrderEnum.Travel.REMOVE.getValue(), startDate, endDate);
		if (orderTravels == null) {
			return true;
		}
		// 今日行程是否有账单
		if (isHasBillCost(orderTravels)) {
			// 更改账单待确认
			for (OrderTravel orderTravel : orderTravels) {
				orderTravel.setStatus(OrderEnum.Travel.COST_WAIT_CONFIRM.getValue());
				update(orderTravel);
			}
			// 推送给会员，等待会员完成确认
			geTuipushFinish(orderId);
			return true;
		}
		// 把当天行程单改为已完成
		for (OrderTravel orderTravel : orderTravels) {
			orderTravel.setStatus(OrderEnum.Travel.FINISH.getValue());
			update(orderTravel);
		}
		// 查询待服务行程数量
		Integer unFinishCount = orderTravelMapper.queryWaitServiceTravelCount(orderId, OrderEnum.Travel.FINISH.getValue(), OrderEnum.Travel.REMOVE.getValue());
		if (unFinishCount == 0) {
			// 若订单中所有行程都已完成，修改订单状态为待评价
			Order order = orderMapper.findOrderInfo(orderId);
			order.setStatus(OrderEnum.Status.WAIT_COMMENT.getValue());
			orderMapper.updateByID(order);
			// 增加待开发票记录
			invoiceRecordService.addOrderInvoice(orderId);
		}
		return true;
	}
	
	// 个推推送 今日下班
	public void geTuipushFinish(Integer orderId) {
		Order order = orderMapper.findOrderInfo(orderId);
		CrossInfo crossInfo = crossInfoMapper.findByOrderId(order.getId());
		MemberInfo memberInfo = memberInfoService.findByOrderId(order.getId());
		pushService.push(Push.createTemplate5(memberInfo.getUserId(), memberInfo.getGetuiId(), memberInfo.getBankMobile(), order, crossInfo));
	}
	
	/** 查询是否有账单 **/
	private boolean isHasBillCost(List<OrderTravel> orderTravels) {
		for (OrderTravel orderTravel : orderTravels) {
			List<OrderCost> orderCosts = orderCostMapper.queryTravelCostList(orderTravel.getId(), OrderEnum.Travel.REMOVE.getValue());
			if (orderCosts != null && orderCosts.size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查询所有已确认行程
	 */
	@Override
	public List<OrderTravel> queryConfirmedTravelListToday() {
		String startTime = DateUtils.parseYMDTime(new Date()) + " 00:00:00";
		String endTime =  DateUtils.parseYMDTime(new Date()) + " 23:59:59";
		Integer status = Travel.CONFIRMED.getValue();
		return orderTravelMapper.queryConfirmedTravellistToday(startTime, endTime, status);
	}
	
	/**
	 * 自动确认前一天所有行程
	 */
	@Override
	public void updateAutoFinish() {
		long time = DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date())).getTime();
		String date = DateUtils.parseYMDTime(new Date(time - (DateUtils.DAY)));		// 前一天日期
		try {
			updateAutoFinish(date);		// 前一天的账单修改为 '已完成'(若没有存疑、投诉等问题)
			
		} catch (ServiceException e) {
			System.err.println(e.getError());
		}
		
	}
	
	@Override
	public void updateAutoFinish(String date) throws ServiceException {
		List<Order> orders = orderMapper.findListTravelServing(date);
		for (Order order : orders) {
			boolean flag = true;
			List<OrderDoubt> doubts = orderDoubtService.findList(null, null, new OrderDoubt(null, order.getId(), DateUtils.parseYMDDate(date), OrderEnum.Doubt.INIT.getValue(), null));
			if (doubts != null && doubts.size() > 0) {
				flag = false;
			}
			String startDate = date + " 00:00:00";
			String endDate = date + " 23:59:59";
			List<OrderTravel> orderTravels = orderTravelMapper.queryTodayTravelList(order.getId(), OrderEnum.Travel.REMOVE.getValue(), startDate, endDate);
			if (orderTravels != null) {
				double totalCost = 0;
				for (OrderTravel orderTravel : orderTravels) {
					if (orderTravel.getStatus() == OrderEnum.Travel.FINISH.getValue())
						continue;
					if (!flag) {
						if (order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue())
							orderTravel.setStatus(OrderEnum.Travel.COST_WAIT_CONFIRM.getValue());
						else
							orderTravel.setStatus(OrderEnum.Travel.FINISH.getValue());
						update(orderTravel);
					} else {
						orderTravel.setStatus(OrderEnum.Travel.FINISH.getValue());
						update(orderTravel);
						if (order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue()) {
							List<OrderCost> costList = orderCostMapper.findCostListByTravelId(orderTravel.getId(), OrderEnum.Cost.REMOVE.getValue());
							if (costList != null) {
								for (OrderCost cost : costList) {
									totalCost += cost.getMoney();
								}
							}
						}
					}
				}
				if (flag) {
					MemberInfo member = memberInfoService.findMemberInfo(order.getUserId());
					pushService.push(Push.createTemplate11(member.getUserId(), member.getGetuiId(), member.getBankMobile(), order.getOrderCard(), date));
					continue;
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
					accountRecordMapper.insert(new AccountRecord(order.getUserId(), order.getId(), totalCost, AccountRecordEnum.ORDER_COST.getValue(), new Date()));
				}
			}
			if (order.getEndTime().getTime() > DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date()) + " 23:59:59").getTime())
				flag = false;
			// 若订单中所有行程都已完成，修改订单状态为待评价
			if (flag) {
				List<OrderTravel> travels = orderTravelMapper.queryTravelList(order.getId(), OrderEnum.Travel.REMOVE.getValue());
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
				// 增加待开发票记录
				invoiceRecordService.addOrderInvoice(order.getId());
			}
		}
		
	}
	
	@Override
	public Integer updateFinishTodayTravel(Integer orderId, String date) throws ServiceException {
		String startDate = date + " 00:00:00";
		String endDate = date + " 23:59:59";
		Order order = orderMapper.findOrderInfo(orderId);
		List<OrderTravel> orderTravels = orderTravelMapper.queryTodayTravelList(orderId, OrderEnum.Travel.REMOVE.getValue(), startDate, endDate);
		if (orderTravels != null) {
			double totalCost = 0;
			for (OrderTravel orderTravel : orderTravels) {
				if (orderTravel.getStatus() == OrderEnum.Travel.FINISH.getValue())
					continue;
				if (order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue())
					orderTravel.setStatus(OrderEnum.Travel.COST_WAIT_CONFIRM.getValue());
				else {
					orderTravel.setStatus(OrderEnum.Travel.FINISH.getValue());
					List<OrderCost> costList = orderCostMapper.findCostListByTravelId(orderTravel.getId(), OrderEnum.Cost.REMOVE.getValue());
					if (costList != null)
						for (OrderCost cost : costList) {
							totalCost += cost.getMoney();
						}
				}

				update(orderTravel);
			}
			MemberInfo member = memberInfoService.findMemberInfo(order.getUserId());
			if (order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue()) {
				pushService.push(Push.createTemplate9(member.getUserId(), member.getGetuiId(), member.getBankMobile(), order.getOrderCard()));
			} else {
				pushService.push(Push.createTemplate11(member.getUserId(), member.getGetuiId(), member.getBankMobile(), order.getOrderCard(), date));
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
		// 是否存疑
		List<OrderDoubt> doubts = orderDoubtService.findList(null, null, new OrderDoubt(null, orderId, DateUtils.parseYMDDate(date), OrderEnum.Doubt.INIT.getValue(), null));
		if (doubts != null) flag = false;
		
		// 是否有投诉
		if (flag) {
			OrderComplaint model2 = new OrderComplaint();
			model2.setOrderId(orderId);
			model2.setStatus(OrderEnum.Doubt.INIT.getValue());
			List<OrderComplaint> complaints = orderComplaintService.findList(null, null, model2);
			if (complaints != null) flag = false;
		}
		
		// 是否有结束行程请求
		if (flag) {
			OrderCancel model1 = new OrderCancel();
			model1.setOrderId(orderId);
			model1.setStatus(OrderEnum.Doubt.INIT.getValue());
			List<OrderCancel> cancels = orderCancelService.findList(null, null, model1);
			if (cancels != null) flag = false;
		}

		if (flag && order.getEndTime().getTime() > DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date()) + " 23:59:59").getTime())
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
			// 增加待开发票记录
			invoiceRecordService.addOrderInvoice(orderId);
		}
		return order.getStatus();
	}
	
	/**
	 * 确认行程
	 */
	@Override
	public int updateMemberFinish(Integer orderId, String date) throws ServiceException {
		String startDate = date + " 00:00:00";
		String endDate = date + " 23:59:59";
		Order order = orderMapper.findOrderInfo(orderId);
		List<OrderTravel> orderTravels = orderTravelMapper.queryTodayTravelList(orderId, OrderEnum.Travel.REMOVE.getValue(), startDate, endDate);
		if (orderTravels != null) {
			double totalCost = 0;
			for (OrderTravel orderTravel : orderTravels) {
				if (orderTravel.getStatus() == OrderEnum.Travel.FINISH.getValue())
					continue;
				orderTravel.setStatus(OrderEnum.Travel.FINISH.getValue()); // 修改当天行程为已完成
				update(orderTravel);
				if (order.getCostPayMethod() == OrderEnum.CostPayMethod.CROSS.getValue()) {
					List<OrderCost> costList = orderCostMapper.findCostListByTravelId(orderTravel.getId(), OrderEnum.Cost.REMOVE.getValue());
					if (costList != null) {
						for (OrderCost cost : costList) {
							totalCost += cost.getMoney();
						}
					}
				}
				
			}
			MemberInfo member = memberInfoService.findMemberInfo(order.getUserId());
			pushService.push(Push.createTemplate11(member.getUserId(), member.getGetuiId(), member.getBankMobile(), order.getOrderCard(), date));
			
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
		
//		行程首日 且16:30之后下单，以当天24:00为限，按照提前结束的小时数赠送代金券；否则按照以21:00为限，按照提前结束的小时数赠送代金券
//		以21:00为限，每提前1小时结束，赠送300元代金券一张，五张封顶；
//		以24:00为限，每提前1小时结束，赠送500元代金券一张，三张封顶；
		Date currentTime = new Date();
		if (DateUtils.parseYMDDate(date).getTime() == order.getStartTime().getTime() && DateUtils.parseFullDate(date + Constant.TIME_BOUNDARY).getTime() < order.getCreateTime().getTime()) {
			int couponNum = (int)((DateUtils.parseYMDDate(DateUtils.parseYMDTime(currentTime)).getTime() + DateUtils.DAY - currentTime.getTime()) / (1000 * 60 * 60));
			Date startTime = DateUtils.parseYMDDate(date);	// 暂定120天有效期 
			Date endTime = new Date(startTime.getTime() + 120L * DateUtils.DAY);
			Coupon coupon = new Coupon("提早下班获赠", startTime, endTime, order.getUserId(), 500D, 2);
			for (int i = 0; i < (couponNum > 3 ? 3 : couponNum); i++)
				couponMapper.insert(coupon);
		} else {
			int couponNum = (int)((DateUtils.parseFullDate(DateUtils.parseYMDTime(currentTime) + " 21:00:00").getTime() - currentTime.getTime()) / (1000 * 60 * 60));
			Date startTime = DateUtils.parseYMDDate(date);	// 暂定120天有效期
			Date endTime = new Date(startTime.getTime() + 120L * DateUtils.DAY);
			Coupon coupon = new Coupon("提早下班获赠", startTime, endTime, order.getUserId(), 300D, 2);
			for (int i = 0; i < (couponNum > 5 ? 5 : couponNum); i++)
				couponMapper.insert(coupon);
		}
		
		// 将订单的存疑都改为已处理
		List<OrderDoubt> doubts = orderDoubtService.findList(null, null, new OrderDoubt(null, orderId, DateUtils.parseYMDDate(date), OrderEnum.Doubt.INIT.getValue(), null));
		if (doubts != null) {
			for (OrderDoubt doubt : doubts) {
				doubt.setStatus(OrderEnum.Doubt.FINISH.getValue());
				orderDoubtService.update(doubt);
			}
		}
		
		boolean flag = true;
		if (order.getEndTime().getTime() > DateUtils.parseFullDate(DateUtils.parseYMDTime(currentTime) + " 23:59:59").getTime())
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
			// 若行程都已结束,将所有投诉、提前取消都改为已处理
			OrderComplaint model2 = new OrderComplaint();
			model2.setOrderId(orderId);
			model2.setStatus(OrderEnum.Doubt.INIT.getValue());
			List<OrderComplaint> complaints = orderComplaintService.findList(null, null, model2);
			if (complaints != null) {
				for (OrderComplaint complaint : complaints) {
					complaint.setStatus(OrderEnum.Doubt.FINISH.getValue());
					orderComplaintService.update(complaint);
				}
			}
			OrderCancel model1 = new OrderCancel();
			model1.setOrderId(orderId);
			model1.setStatus(OrderEnum.Doubt.INIT.getValue());
			List<OrderCancel> cancels = orderCancelService.findList(null, null, model1);
			if (cancels != null) {
				for (OrderCancel cancel : cancels) {
					cancel.setStatus(OrderEnum.Doubt.FINISH.getValue());
					orderCancelService.update(cancel);
				}
			}
			order.setStatus(OrderEnum.Status.WAIT_COMMENT.getValue());
			orderMapper.updateByID(order);
			// 增加待开发票记录
			invoiceRecordService.addOrderInvoice(orderId);
		}
		
		return order.getStatus();
	}

	/**
	 * 未下班则推送延迟支付
	 */
	@Override
	public void updateIsCrossOffWork() throws ServiceException {
		String startTime = DateUtils.parseYMDTime(new Date()) + " 00:00:00";
		String endTime =  DateUtils.parseYMDTime(new Date()) + " 23:59:59";
		Integer orderStatus = OrderEnum.Status.SERVING.getValue();
		Integer travelStatus = Travel.SERVING.getValue();
		List<Integer> orderIds = orderTravelMapper.queryServingTravelListToday(startTime, endTime, orderStatus, travelStatus);
		if (orderIds == null) {
			return;
		}
		for (Integer orderId : orderIds) {
			Order order = orderMapper.findOrderInfo(orderId);
			if (order == null) {
				continue;
			}
			MemberInfo memberInfo = memberInfoService.findMemberInfo(order.getUserId());
			if (memberInfo == null) {
				continue;
			}
			CrossInfo crossInfo = crossInfoMapper.findByUserId(order.getCrossUserId());
			OrderConfig orderConfig = orderConfigMapper.findOrderServiceFee();
			String date = DateUtils.parseHHMMTime(new Date());
			pushService.push(Push.createTemplate8(memberInfo.getUserId(), memberInfo.getGetuiId(), memberInfo.getBankMobile(), date, orderConfig.getDelayMoney(), crossInfo.getCrossNumber()));
		}
	}

	@Override
	public List<Map<String, Object>> queryOrderTravelList(Integer orderId) {
    	List<OrderTravel> list = orderTravelMapper.queryTravelList(orderId, OrderEnum.Travel.REMOVE.getValue());
    	if (list == null) {
			return null;
		}
    	for (int i = list.size()- 1; i >= 0; i--) {
    		OrderTravel o = list.get(i);
    		if (o.getTitle().equals("前置消费")) {
				list.remove(o);
			}
		}
    	// 按日期进行分组排序
    	List<Map<String, Object>> result = new ArrayList<>();
    	String lastDate = "";
    	for (OrderTravel orderTravel : list) {
    		Date date = orderTravel.getStartTime();
    		String str = DateUtils.parseYMDTime(date);
    		OrderDoubt model = new OrderDoubt();
    		model.setOrderId(orderId);
    		
    		// 查询账单疑义
    		SimpleDateFormat YMD_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
			try {
				model.setDateOfDoubtTime(YMD_FORMAT.parse(YMD_FORMAT.format(orderTravel.getStartTime())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		model.setStatus(OrderEnum.Doubt.INIT.getValue());
    		int doubtNum = orderDoubtMapper.findCountByCondition(model);
    		if (!lastDate.equals(str)) {
    			Map<String, Object> map = new HashMap<>();
    			map.put("status", doubtNum > 0 ? 500 : orderTravel.getStatus());
    			map.put("date", str);
    			map.put("list", new ArrayList<OrderTravel>());
    			result.add(map);
			}
    		@SuppressWarnings("unchecked")
			List<OrderTravel> orderTravels = (List<OrderTravel>) result.get(result.size() - 1).get("list");
    		orderTravels.add(orderTravel);
    		lastDate = str;
		}
    	Collections.sort(result, new Comparator<Map<String, Object>>() {
    		
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Date d1 = DateUtils.parseYMDDate(String.valueOf(o1.get("date")));
				Date d2 = DateUtils.parseYMDDate(String.valueOf(o2.get("date")));
				return (int) (d1.getTime() - d2.getTime());
			}
		});
    	return result;
    }
	
}

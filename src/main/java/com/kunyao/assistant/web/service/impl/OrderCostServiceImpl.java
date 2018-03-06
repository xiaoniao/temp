package com.kunyao.assistant.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.CostDto;
import com.kunyao.assistant.core.dto.CostDto.CostItemDto;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CostItem;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderCost;
import com.kunyao.assistant.core.model.OrderTravel;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.CostItemMapper;
import com.kunyao.assistant.web.dao.OrderCostMapper;
import com.kunyao.assistant.web.dao.OrderMapper;
import com.kunyao.assistant.web.dao.OrderTravelMapper;
import com.kunyao.assistant.web.service.AccountService;
import com.kunyao.assistant.web.service.OrderCostService;

@Service
public class OrderCostServiceImpl extends GenericServiceImpl<OrderCost, Integer> implements OrderCostService {
    @Resource
    private OrderCostMapper orderCostMapper;
    
    @Resource
    private OrderTravelMapper orderTravelMapper;
    
    @Resource
    private AccountService accountService;
    
    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private CostItemMapper costItemMapper;
    
    public GenericDao<OrderCost, Integer> getDao() {
        return orderCostMapper;
    }

	@Override
	public OrderCost queryById(Integer costId) throws ServiceException {
		return orderCostMapper.findInfo(costId);
	}
	
	@Override
	public List<CostDto> queryCostList(Integer orderId) {
		List<CostDto> costDtos = new ArrayList<>();
		List<OrderCost> orderCosts = orderCostMapper.queryOrderCostList(orderId, OrderEnum.Cost.REMOVE.getValue());
		
		// 使用日期和行程单进行分组排序
		String lastDate = "";
		Integer lastOrderTravelId = -1;
		for (OrderCost orderCost : orderCosts) {
			Date date = orderCost.getStartTime();
    		String dateStr = DateUtils.parseYMDTime(date);
    		if (!lastDate.equals(dateStr)) {
    			CostDto costDto = new CostDto(dateStr, new ArrayList<CostItemDto>());
    			costDtos.add(costDto);
			}
    		
    		List<CostItemDto> dtos = costDtos.get(costDtos.size() - 1).getList();
    		if (!orderCost.getOrderTravelId().equals(lastOrderTravelId)) {
    			OrderTravel orderTravel = orderTravelMapper.findInfo(orderCost.getOrderTravelId());
    			CostItemDto dto = new CostItemDto(orderCost.getTitle(), orderTravel.getStatus(), new ArrayList<OrderCost>());
    			dtos.add(dto);
			}
    		dtos.get(dtos.size() - 1).getCosts().add(orderCost);
    		lastOrderTravelId = orderCost.getOrderTravelId();
    		lastDate = dateStr;
		}
		
		// 补全订单天数
		Order order = orderMapper.findOrderInfo(orderId);
		List<String> days = order.setServiceDays();
    	for (CostDto dto : costDtos) {
    		days.remove(dto.getDate());
		}
    	for (String string : days) {
			CostDto costDto = new CostDto(string, new ArrayList<CostItemDto>());
			costDtos.add(costDto);
		}
    	Collections.sort(costDtos, new Comparator<CostDto>() {

			@Override
			public int compare(CostDto o1, CostDto o2) {
				Date d1 = DateUtils.parseYMDDate(o1.getDate());
				Date d2 = DateUtils.parseYMDDate(o2.getDate());
				return (int) (d1.getTime() - d2.getTime());
			}
		});
		return costDtos;
	}

	/**
	 * 1、账单只能在服务中添加
	 * 2、行程单状态必须不为已完成和已删除
	 */
	@Override
	public OrderCost createAdd(Integer orderId, Integer travelId, Integer costItemId, Double money, String remark) throws ServiceException {
		checkOrderStatus(orderId);
		checkTravelStatus(travelId);
		OrderCost orderCost = new OrderCost(travelId, costItemId, money, remark);
		insert(orderCost);
		
		// 冻结余额
		Integer userId = orderTravelMapper.findUserByOrderTravel(travelId);
		if (userId == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		accountService.freeze(userId, money);
		
		return orderCost;
	}
	
	@Override
	public OrderCost updateRemove(Integer costId) throws ServiceException {
		OrderCost orderCost = orderCostMapper.findInfo(costId);
		checkNotNull(orderCost);
		checkTravelStatus(orderCost.getOrderTravelId());
		
		orderCost.setStatus(OrderEnum.Cost.REMOVE.getValue());
		update(orderCost);
		
		// 返还冻结余额
		Integer userId = orderCostMapper.findUserByOrderCost(costId);
		if (userId == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		accountService.freeze(userId, -orderCost.getMoney());
		
		return orderCost;
	}

	@Override
	public OrderCost updateEdit(Integer orderId, Integer costId, Double money, String remark) throws ServiceException {
		checkOrderStatus(orderId);
		OrderCost orderCost = orderCostMapper.findInfo(costId);
		checkNotNull(orderCost);
		checkTravelStatus(orderCost.getOrderTravelId());
		
		Double differMoney = money - orderCost.getMoney();
		
		if (money != null && money.doubleValue() > 0.0) {
			orderCost.setMoney(money);
		}
		if (!StringUtils.isNull(remark)) {
			orderCost.setRemark(remark);
		}
		update(orderCost);
		
		// 冻结余额
		Integer userId = orderCostMapper.findUserByOrderCost(costId);
		if (userId == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		accountService.freeze(userId, differMoney);
		
		return orderCost;
	}

	/** 检查账单是否存在，否则抛出异常 **/
	private void checkNotNull(OrderCost orderCost) throws ServiceException {
		if (orderCost == null) {
			throw new ServiceException(ResultCode.COST_NOT_FOUND);
		}
	}
	
	/** 检查行程单状态，否则抛出异常，行程已结束和已删除的不允许操作  **/
	private void checkTravelStatus(Integer id) throws ServiceException   {
		OrderTravel orderTravel = orderTravelMapper.findInfo(id);
		if (orderTravel.getStatus().intValue() == OrderEnum.Travel.FINISH.getValue()) {
			throw new ServiceException(ResultCode.TRAVEL_NOT_ALLOW_OPERATE);
		}
		if (orderTravel.getStatus().intValue() == OrderEnum.Travel.REMOVE.getValue()) {
			throw new ServiceException(ResultCode.TRAVEL_NOT_ALLOW_OPERATE);
		}
	}
	
	/** 检查订单状态，否则抛出异常 ，只允许服务中的订单操作 **/
	private void checkOrderStatus(Integer orderId) throws ServiceException   {
		Order order = orderMapper.findOrderInfo(orderId);
		if (order.getStatus().intValue() != OrderEnum.Status.SERVING.getValue()) {
			throw new ServiceException(ResultCode.ORDER_STATUS_NEGATIVE);
		}
	}

	@Override
	public List<CostDto> queryOrderCostListByOrderId(Integer orderId, Integer status) {
		List<CostDto> costDtos = new ArrayList<>();
		List<OrderCost> orderCosts = orderCostMapper.queryOrderCostListByOrderId(orderId, status);
		
		// 使用日期和行程单进行分组排序
		String lastDate = "";
		Integer lastOrderTravelId = -1;
		
		for (OrderCost orderCost : orderCosts) {
			Date date = orderCost.getStartTime();
			Date endTime = orderCost.getEndTime();
    		String dateStr = DateUtils.parseYMDTime(date);
    		
    		if (!lastDate.equals(dateStr)) {
    			Calendar calendar = new GregorianCalendar();
    			calendar.setTime(orderCost.getStartTime());
    			calendar.add(Calendar.DATE, 1); 		// 把日期往后增加一天.整数往后推,负数往前移动
    			Date torrowDate = calendar.getTime(); 	// 这个时间就是日期往后推一天的结果
    			
    			List<Double> moneyList = orderCostMapper.queryMoneyListByOrderId(orderId, dateStr, DateUtils.parseYMDTime(torrowDate));
    			Double money = 0.0;
    			for (Double moneys : moneyList) {
    				money += moneys;
				}
    			CostDto costDto = new CostDto(dateStr, orderCost.getTravelStatus(), money, orderId, new ArrayList<CostItemDto>());
    			costDtos.add(costDto);
			}
    		
    		List<CostItemDto> dtos = costDtos.get(costDtos.size() - 1).getList();
    		if (!orderCost.getOrderTravelId().equals(lastOrderTravelId)) {
    			List<Double> moneyList = orderCostMapper.queryMoneyListByTravelId(orderCost.getOrderTravelId());
    			Double money = 0.0;
    			for (Double moneys : moneyList) {
    				money += moneys;
				}
    			String startDate = DateUtils.parseHHMMTime(date);
    			String endDate = DateUtils.parseHHMMTime(endTime);
    			CostItemDto dto = new CostItemDto(orderCost.getTitle(), money, startDate, endDate, new ArrayList<OrderCost>());
    			dtos.add(dto);
			}
    		dtos.get(dtos.size() - 1).getCosts().add(orderCost);
    		lastOrderTravelId = orderCost.getOrderTravelId();
    		lastDate = dateStr;
		}
		return costDtos;
	}

	@Override
	public List<OrderCost> findOrderCostListByOrderId(Integer orderId) {
		return orderCostMapper.queryOrderCostList(orderId, OrderEnum.Cost.REMOVE.getValue());
	}

	@Override
	public List<CostItem> queryAllCostItem() {
		return costItemMapper.findAll();
	}

	@Override
	public List<OrderCost> selectOrderCostList(Integer currentPage, Integer pagesize, OrderCost orderCost) {
		Integer startpos = null;
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		List<OrderCost> orderCosts = orderCostMapper.selectOrderCostList(startpos, pagesize, orderCost);
		if (orderCosts != null) {
			for (OrderCost cost : orderCosts) {
				cost.dto();
			}
		}
		return orderCosts;
	}

	@Override
	public PageInfo selectOrderCostListCount(Integer pageSize, OrderCost orderCost) {
		Integer allRow = orderCostMapper.selectOrderCostListCount(orderCost);
		PageInfo page = new PageInfo(allRow, pageSize);
		return page;
	}
}

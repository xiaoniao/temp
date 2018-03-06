package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.dto.CostDto;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.CostItem;
import com.kunyao.assistant.core.model.OrderCost;

public interface OrderCostService extends GenericService<OrderCost, Integer> {
	List<CostDto> queryCostList(Integer orderId);

	OrderCost createAdd(Integer orderId, Integer orderTravelId, Integer costItemId, Double money, String remark) throws ServiceException;

	OrderCost queryById(Integer costId) throws ServiceException;
	
	OrderCost updateRemove(Integer costId) throws ServiceException;

	OrderCost updateEdit(Integer orderId, Integer costId, Double money, String remark) throws ServiceException;
	
	/**
	 * 根据订单id查询行程数据
	 * @param orderId
	 * @return
	 */
	List<CostDto> queryOrderCostListByOrderId(Integer orderId, Integer status);
	
	List<OrderCost> findOrderCostListByOrderId(Integer orderId);
	
	List<CostItem> queryAllCostItem();
	
	List<OrderCost> selectOrderCostList(Integer currentPage, Integer pagesize, OrderCost orderCost);
	
	PageInfo selectOrderCostListCount(Integer pageSize, OrderCost orderCost);
}

package com.kunyao.assistant.web.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kunyao.assistant.core.entity.PageData;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Order;

public interface OrderService extends GenericService<Order, Integer> {

	void loop() throws ServiceException;
	
	Order createOrder(Integer userId, Integer crossUserId, String startTime, String endTime, String couponId, Integer payMethod,
			Integer cityId, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile) throws ParseException, ServiceException;

	Order fillOrder(Integer orderId, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress,
			String invoicePeople, String invoiceMobile, Integer contactMethod, Integer costPayMethod,
			String travelDescId, String travelRequireText, String travelRequireVoice) throws ServiceException;

	Map<String, Object> payOrder(HttpServletRequest request, Order order) throws ServiceException;

	Map<String, Object> payOrder(HttpServletRequest request, Integer orderId) throws ServiceException;

	Order finishPay(Integer orderId, String thirdTradeNo);

	Order queryOrderInfo(Integer orderId);
	
	Order queryOrderByOrderCard(String orderCard) throws ServiceException;
	
	Order queryOrderRequire(Integer orderId);

	Map<String, Object> queryPayInfo(Integer userId, Integer day) throws ServiceException;

	/**
	 * 金鹰端 查询金鹰订单列表
	 */
	PageData<Order> queryByCrossUserId(Integer crossUserId, String search, Integer currentPage, Integer pagesize) throws ServiceException;

	/**
	 * 金鹰端 订单详情
	 */
	Order queryOrderDetail(Integer orderId) throws ServiceException;
	
	/**
	 * 订单列表-用户端
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageData<Order> queryOrderList(Integer userId, Integer currentPage, Integer pageSize);
	
	/**
	 * 用户取消 订单
	 * @param orderId
	 */
	public Order updateCancelOrder(Integer orderId) throws ServiceException;
	
	/**
	 * 根据金鹰用户id查询有无服务中的订单
	 * @param crossUserId
	 * @return
	 */
	public int findOrderByCrossUserId(Integer crossUserId);
	
	public PageData<Order> findOrderListWithTravel(Integer userId, Integer currentPage, Integer pageSize);
	
	
	/**
	 * 后台订单列表
	 * @param currentPage
	 * @param rowNum
	 * @param order
	 * @return
	 */
    public List<Order> queryList(Integer currentPage, Integer rowNum, Order order);
    
    /**
     * 后台订单列表数量
     * 
     * @param model
     * @return int
     */
    PageInfo selectListCount(Integer pageSize, Order order);
	
    /**
     * 订单详情
     * @param orderId
     * @return
     */
    public Order queryDetail(Integer orderId);
	
    
    
    /**
     * 结束服务投诉订单
     * @param orderId
     * @param refundMethod
     * @return
     */
    public int updateEndComplaintOrder(Integer orderId, Integer refundMethod) throws ServiceException;
    
    /**
     * 直接结束服务退款
     * @param orderId
     * @return
     */
    public int updateEndService(Integer orderId) throws ServiceException;
    
    /**
     * 用户更换金鹰
     * @param orderId
     * @return
     */
    public int updateOrderReplaceCross(Integer orderId, Integer crossId) throws ServiceException;
    
    /**
     * 金鹰更换金鹰
     * @param orderId
     * @return
     */
    public int updateCrossReplaceCross(Integer orderId, Integer crossId) throws ServiceException;
    
    /**
     * 账单异议订单列表
     * @return
     */
    public List<Order> objectionOrderList(Integer currentPage, Integer pagesize, Order model);
    
    /**
     * 账单异议订单列表数量
     * 
     * @param model
     * @return int
     */
    PageInfo objectionOrderCount(Integer pageSize, Order order);
    
    /**
     * 提前结束订单列表
     * @param currentPage
     * @param pagesize
     * @param model
     * @return
     */
    public List<Order> orderEndList(Integer currentPage, Integer pagesize, Order model);
    
    
    /**
     * 晚9点未结束订单数量
     * 
     * @param model
     * @return int
     */
    public Integer orderNineCount();
    
    /**
     * 晚9点未结束订单
     * @param currentPage
     * @param pagesize
     * @param model
     * @return
     */
    public List<Order> orderNineList(Integer currentPage, Integer pagesize, Order model);
    
    
    /**
     * 提前结束订单列表数量
     * 
     * @param model
     * @return int
     */
    PageInfo orderEndCount(Integer pageSize, Order order);
    
    /**
     * 投诉订单列表
     * @param currentPage
     * @param pagesize
     * @param model
     * @return
     */
    public List<Order> orderComplaintList(Integer currentPage, Integer pagesize, Order model);
    
    
    /**
     * 投诉订单列表数量
     * 
     * @param model
     * @return int
     */
    PageInfo orderComplaintCount(Integer pageSize, Order order);

    Map<String, Object> findOrderTotalInfo(Integer orderId) throws ServiceException;

	Order findOrderRequireInfo(Integer orderId) throws NumberFormatException, ServiceException;
    
	/**
	 * 查询金鹰历史订单
	 */
	PageData<Order> queryCrossHistoryOrder(Integer crossUserId, Integer currentPage, Integer pagesize);
	
	/**
	 * 金鹰端 查询订单详情
	 */
	Order queryCrossOrderInfo(Integer orderId);
	
	/**
	 * 查找最后一条订单的流水号
	 */
	Integer queryLastOrderNo();
	
	/**
	 * 输入服务编码
	 */
	Order serviceCode(Integer orderId, String serviceCode) throws ServiceException;
	
	/**
	 * 金鹰编辑订单
	 */
	Order edit(Integer orderId, Integer contactMethod, Integer costPayMethod) throws ServiceException;

	Order selectOrderNotFinished(Integer userId) throws ServiceException;
	
	int updatePlaceOrder(Integer orderId,  String date,  Double money) throws ServiceException;

	int findCountServing(Integer userId) throws ServiceException; 
}
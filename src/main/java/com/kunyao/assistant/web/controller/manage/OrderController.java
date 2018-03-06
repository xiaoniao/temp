package com.kunyao.assistant.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.CostDto;
import com.kunyao.assistant.core.dto.CostDto.CostItemDto;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.CrossEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.City;
import com.kunyao.assistant.core.model.CostItem;
import com.kunyao.assistant.core.model.CrossInfo;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderCancel;
import com.kunyao.assistant.core.model.OrderComplaint;
import com.kunyao.assistant.core.model.OrderCost;
import com.kunyao.assistant.core.model.OrderDelay;
import com.kunyao.assistant.core.model.OrderDoubt;
import com.kunyao.assistant.core.model.OrderLog;
import com.kunyao.assistant.core.model.TravelDesc;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.web.service.CityService;
import com.kunyao.assistant.web.service.CostItemService;
import com.kunyao.assistant.web.service.CrossInfoService;
import com.kunyao.assistant.web.service.CrossTimesService;
import com.kunyao.assistant.web.service.OrderCancelService;
import com.kunyao.assistant.web.service.OrderComplaintService;
import com.kunyao.assistant.web.service.OrderConfigService;
import com.kunyao.assistant.web.service.OrderCostService;
import com.kunyao.assistant.web.service.OrderDelayService;
import com.kunyao.assistant.web.service.OrderDoubtService;
import com.kunyao.assistant.web.service.OrderLogService;
import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.OrderTravelService;
import com.kunyao.assistant.web.service.TravelDescService;


@Controller("umOrderController")
@RequestMapping("/um/order")
public class OrderController {

	@Resource
	private OrderService orderService;
	
	@Resource
	private CityService cityService;
	
	@Resource
	private OrderComplaintService orderComplaintService;
	
	@Resource
	private OrderCancelService orderCancelService;
	
	@Resource
	private CrossTimesService crossTimesService;
	
	@Resource
	private CrossInfoService crossInfoService;
	
	@Resource
	private TravelDescService travelDescService;
	
	@Resource
	private OrderConfigService orderConfigService;
	
	@Resource
	private OrderCostService orderCostService;
	
	@Resource
	private OrderTravelService orderTravelService;
	
	@Resource
	private OrderDoubtService orderDoubtService;
	
	@Resource
	private CostItemService costItemService;
	
	@Resource
	private OrderLogService orderLogService;
	
	@Resource
	private OrderDelayService orderDelayService;
	
	// 订单列表页
	private final String LIST_PAGE_URL = "um/order-list";
	
	// 异议账单订单
	private final String ORDER_OBJECTION_PAGE_URL = "um/order-objection";
	
	// 投诉订单
	private final String ORDER_COMPLAINT_PAGE_URL = "um/order-complaint";
	
	// 提前结束订单
    private final String ORDER_END_PAGE_URL = "um/order-end";
    
    // 晚9点未结束订单
    private final String ORDER_NINE_PAGE_URL = "um/order-nine";
    
    // 待轮询订单
    private final String ORDER_PENDING_PAGE_URL = "um/order-pending";
		
	// 订单详情页
	private final String ORDER_DETAIL_PAGE_URL = "um/order-detail";
	
	// 用户更换金鹰
	private final String REPLACE_CROSS_PAGE_URL = "um/order-replace-cross";
	
	// 金鹰更换金鹰
	private final String CROSS_REPLACE_CROSS_PAGE_URL = "um/order-cross-replace-cross";
		
	// 结束服务
	private final String END_SERVICE_PAGE_URL = "um/order-end-service";
	
	// 行程详情
	private final String TRAVEL_DETAILS_PAGE_URL = "um/order-travel-details";
	
	// 编辑账单
	private final String ORDER_EDIT_BILL_PAGE_URL = "um/order-edit-bill";
	
	// 金鹰位置
    private final String CROSS_LOCATION_PAGE_URL = "um/cross-location";
	
	@RequestMapping(value = "/toList")
	public String toListPage(Model model, Integer currentPage, Integer pageSize, Order order) {
		City city = new City();
		city.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
		
	    List<City> cityList = null;
		try {
			cityList = cityService.findList(null, null, city);
		    model.addAttribute("cityList", cityList);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return LIST_PAGE_URL;
	}
	
	// 跳转账单异议订单
	@RequestMapping(value = "/toObjectionOrderList")
	public String toObjectionOrderList() {
		return ORDER_OBJECTION_PAGE_URL;
	}
	
	// 跳转投诉订单
	@RequestMapping(value = "/toComplaintOrderList")
	public String toComplaintOrderList() {
		return ORDER_COMPLAINT_PAGE_URL;
	}
	
	// 跳转提前结束订单
	@RequestMapping(value = "/toOrderEnd")
	public String toOrderEnd() {
		return ORDER_END_PAGE_URL;
	}
	
	// 跳转待轮询订单
	@RequestMapping(value = "/toOrderPending")
	public String toOrderPending() {
		return ORDER_PENDING_PAGE_URL;
	}
	
	// 晚9点未结束订单
	@RequestMapping(value = "/toOrderNine")
	public String toOrderNine() {
		return ORDER_NINE_PAGE_URL;
	}
	
	// 金鹰位置
	@RequestMapping(value = "/toCrossLocation")
	public String toCrossLocation(Integer id, Model model) {
		CrossInfo crossInfo = crossInfoService.findByOrderId(id);
		model.addAttribute("lat", crossInfo.getLat() == null ? 39.897445 : crossInfo.getLat());
		model.addAttribute("lng", crossInfo.getLng() == null ? 116.331398 : crossInfo.getLng());
		return CROSS_LOCATION_PAGE_URL;
	}
	
	// 跳转订单详情
	@RequestMapping(value="/toOrderDetail")
	public String toOrderDetail(Integer id, Model model) {
		
		Order orderDetail = orderService.queryDetail(id);
		model.addAttribute("orderDetail", orderDetail);
		
		OrderComplaint orderComplaint = new OrderComplaint();
		orderComplaint.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		orderComplaint.setOrderId(id);
		OrderCancel orderCancel = new OrderCancel();
		orderCancel.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		orderCancel.setOrderId(id);
		
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderId(id);
		
		OrderCancel orderCancelRemark = orderCancelService.findOrderCancelByOrderId(id);
		OrderComplaint orderComplaintRemark = orderComplaintService.findOrderComplaintByOrderId(id);
		model.addAttribute("orderCancelRemark", orderCancelRemark);
		model.addAttribute("orderComplaintRemark", orderComplaintRemark);
		
		try {
			OrderDelay orderDelay = new OrderDelay();
			orderDelay.setOrderId(id);
			orderDelay.setStatus(BaseEnum.Status.BASE_STATUS_ENABLE.getValue());
			List<OrderDelay> orderDelayList = orderDelayService.findList(null, null, orderDelay);
			model.addAttribute("orderDelayList", orderDelayList);
			
			if (orderDetail.getContactMethod().equals(OrderEnum.ContactMethod.ORTHER.getValue())){
				String[] travelDescs = orderDetail.getTravelDescId().split(",");
				List<TravelDesc> travelDescList = new ArrayList<TravelDesc>();
				for (int i = 0; i < travelDescs.length; i++){
					travelDescList.add(travelDescService.findByID(Integer.parseInt(travelDescs[i])));
				}
				model.addAttribute("travelDescList", travelDescList);
			}
			
			List<OrderLog> orderLogInfo = orderLogService.findList(null, null, orderLog);
			List<OrderComplaint> orderComplaintInfo = orderComplaintService.findList(null, null, orderComplaint);
			List<OrderCancel> orderCancelInfo = orderCancelService.findList(null, null, orderCancel);
			if (orderComplaintInfo != null && orderComplaintInfo.size() > 0){
				model.addAttribute("orderComplaintInfo", orderComplaintInfo);
			} else if (orderCancelInfo != null && orderCancelInfo.size() > 0){
				model.addAttribute("orderCancelInfo", orderCancelInfo);
			}
			model.addAttribute("orderLogInfo", orderLogInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ORDER_DETAIL_PAGE_URL;
	}
	
	// 跳转行程页面
	@RequestMapping(value = "/toTravelDetails")
	public String toTravelDetails(Model model, Integer id) {
		try {
			List<CostDto> costDtoList = orderCostService.queryOrderCostListByOrderId(id, OrderEnum.Cost.REMOVE.getValue());
			for (CostDto costDto : costDtoList) {
				OrderDoubt orderDoubt = new OrderDoubt();
				orderDoubt.setOrderId(id);
				orderDoubt.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
				orderDoubt.setStayDate(DateUtils.parseYMDDate(costDto.getDate()));
				
    			List<OrderDoubt> OrderDoubt = orderDoubtService.findDoubtBytime(orderDoubt);
    			
				if (OrderDoubt != null && OrderDoubt.size() > 0){
					costDto.setTravelStatus(-1);
				}
			}
			
			Double money = 0.0;
			for (CostDto costDto : costDtoList) {
				List<CostItemDto> costItemDtoList = costDto.getList();
				for (CostItemDto costItemDto : costItemDtoList) {
					List<OrderCost> orderCostList = costItemDto.getCosts();
					for (OrderCost orderCost : orderCostList) {
						money += orderCost.getMoney();
					}
				}
			}
			model.addAttribute("money", money);
			model.addAttribute("costDtoList", costDtoList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TRAVEL_DETAILS_PAGE_URL;
	}
	
	// 跳转更换金鹰页面
	@RequestMapping(value = "/replaceCross")
	public String replaceCross(Model model, Integer id) {
		
		try {
			
			OrderComplaint orderComplaint = new OrderComplaint();
			orderComplaint.setOrderId(id);
			OrderComplaint orderComplaintInfo = orderComplaintService.findOne(orderComplaint);
			
			
			Order oModel = new Order();
			oModel.setId(id);
			List<Order> order = orderService.queryList(null, null, oModel);
			
			String stayDate = orderComplaintInfo.getCreateDate();
			String endDate = order.get(0).getEndDate();
			Integer status = CrossEnum.BookStatus.IDLE.getValue();
			
			
			List<CrossInfo> crossInfoList = crossInfoService.findUserByUserId(status, stayDate, endDate);
		
			model.addAttribute("crossInfoList", crossInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("orderId", id);
		return REPLACE_CROSS_PAGE_URL;
		
	}
	
	// 跳转更换金鹰页面
	@RequestMapping(value = "/toCrossReplaceCross")
	public String toCrossReplaceCross(Model model, Integer id) {
			
		try {
				
			OrderCancel orderCancel = new OrderCancel();
			orderCancel.setOrderId(id);
			OrderCancel orderCancelInfo = orderCancelService.findOne(orderCancel);
				
				
			Order oModel = new Order();
			oModel.setId(id);
			List<Order> order = orderService.queryList(null, null, oModel);
				
			String stayDate = orderCancelInfo.getCreateDate();
			String endDate = order.get(0).getEndDate();
			Integer status = CrossEnum.BookStatus.IDLE.getValue();
				
			List<CrossInfo> crossInfoList = crossInfoService.findUserByUserId(status, stayDate, endDate);
			
			model.addAttribute("crossInfoList", crossInfoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("orderId", id);
		return CROSS_REPLACE_CROSS_PAGE_URL;
			
	}
	
	// 投诉订单跳转页面
	@RequestMapping(value = "/toComplaintOrder")
	public String toComplaintOrder(Model model, Integer id) {
		model.addAttribute("orderId", id);
		return END_SERVICE_PAGE_URL;
	}
	
	
	// 投诉订单退款
	@ResponseBody
	@RequestMapping(value = "/endComplaintOrder")
	public Result endComplaintOrder(@RequestParam Integer id, @RequestParam Integer refundMethod) throws ServiceException{
		Result result = null;
		int i = orderService.updateEndComplaintOrder(id, refundMethod);
		
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
		
		return result;
	}
	
	// 跳转更新账单页面
	@RequestMapping(value = "/orderEditBill")
	public String orderEditBill(Model model, Integer costId) {
		try {
			
			CostItem costItem = new CostItem();
			List<CostItem> costItemList = costItemService.findList(null, null, costItem);
			
			OrderCost orderCost = orderCostService.findByID(costId);
			
			model.addAttribute("costId", costId);
			model.addAttribute("costItemList", costItemList);
			model.addAttribute("orderCost", orderCost);
		} catch (Exception e) {
			
		}
		
		return ORDER_EDIT_BILL_PAGE_URL;
		
	}
	
	// 更新账单
	@ResponseBody
	@RequestMapping(value = "/updateBill")
	public Result updateBill(OrderCost orderCost) {
		
		try {
			orderCostService.update(orderCost);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}
		
		return ResultFactory.createSuccess();
	}
	
	
	// 提交解决存疑账单
	@ResponseBody
	@RequestMapping(value = "/placeOrder")
	public Result placeOrder(@RequestParam Integer orderId, @RequestParam String date, @RequestParam Double money) throws ServiceException{
		
		Result result = null;
		
		int i = orderService.updatePlaceOrder(orderId, date, money);
	
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
		return result;
	}
	
	
	// 用户更换金鹰
	@ResponseBody
	@RequestMapping(value = "/orderReplaceCross")
	public Result orderReplaceCross(Integer id, Integer crossId) throws ServiceException{
		Result result = null;
		int i = orderService.updateOrderReplaceCross(id, crossId);
		
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
		
		return result;
	}
	
	
	// 金鹰更换金鹰
	@ResponseBody
	@RequestMapping(value = "/crossReplaceCross")
	public Result crossReplaceCross(Integer id, Integer crossId) throws ServiceException{
			Result result = null;
		int i = orderService.updateCrossReplaceCross(id, crossId);
			
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
			
		return result;
	}
	
	// 金鹰结束服务
	@ResponseBody
	@RequestMapping(value = "/endService")
	public Result endService(Integer id) throws ServiceException{
		Result result = null;
		int i = orderService.updateEndService(id);
		
		switch (i) {
		case 1:
			result = ResultFactory.createSuccess();
			break;
		default:
			result = ResultFactory.createError(ResultCode.UNKNOWN_ERROR);
			break;
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/lscount", produces = { "application/json;charset=UTF-8" })
	public Result orderListCount(Integer pageSize, Order model) {
		PageInfo page = orderService.selectListCount(pageSize, model);
		return ResultFactory.createJsonSuccess(page);
	}

	@ResponseBody
	@RequestMapping(value = "/list", produces = { "application/json;charset=UTF-8" })
	public Result orderList(Integer currentPage, Integer pageSize, Order model) {
		List<Order> orderlList = null;
		try {
			orderlList = orderService.queryList(currentPage, pageSize, model);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createJsonSuccess(orderlList);
	}
	
	// 待轮询订单
	@ResponseBody
	@RequestMapping(value = "/pendingOrdersList", produces = { "application/json;charset=UTF-8" })
	public Result pendingOrdersList(Integer currentPage, Integer pageSize, Order model) {
		List<Order> orderlList = null;
		try {
			model.setStatus(OrderEnum.Status.WAIT_LOOP.getValue());
			orderlList = orderService.queryList(currentPage, pageSize, model);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createJsonSuccess(orderlList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/pendingOrdersCount", produces = { "application/json;charset=UTF-8" })
	public Result pendingOrdersCount(Integer pageSize, Order model) {
		model.setStatus(OrderEnum.Status.WAIT_LOOP.getValue());
		PageInfo page = orderService.selectListCount(pageSize, model);
		return ResultFactory.createJsonSuccess(page);
	}
	
	
	// 账单异议订单
	@ResponseBody
	@RequestMapping(value = "/objectionOrderList", produces = { "application/json;charset=UTF-8" })
	public Result objectionOrderList(Integer currentPage, Integer pageSize, Order model) {
		List<Order> orderlList = null;
		try {
			orderlList = orderService.objectionOrderList(currentPage, pageSize, model);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createJsonSuccess(orderlList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/objectionOrderCount", produces = { "application/json;charset=UTF-8" })
	public Result objectionOrderCount(Integer pageSize, Order model) {
		PageInfo page = orderService.objectionOrderCount(pageSize, model);
		return ResultFactory.createJsonSuccess(page);
	}
	
	// 投诉订单
	@ResponseBody
	@RequestMapping(value = "/complaintOrderList", produces = { "application/json;charset=UTF-8" })
	public Result complaintOrderList(Integer currentPage, Integer pageSize, Order model) {
		List<Order> orderlList = null;
		try {
			orderlList = orderService.orderComplaintList(currentPage, pageSize, model);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createJsonSuccess(orderlList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/complaintOrderCount", produces = { "application/json;charset=UTF-8" })
	public Result complaintOrderCount(Integer pageSize, Order model) {
		PageInfo page = orderService.orderComplaintCount(pageSize, model);
		return ResultFactory.createJsonSuccess(page);
	}
	
	// 提前结束订单
	@ResponseBody
	@RequestMapping(value = "/orderEndList", produces = { "application/json;charset=UTF-8" })
	public Result orderEndList(Integer currentPage, Integer pageSize, Order model) {
		List<Order> orderlList = null;
		try {
			orderlList = orderService.orderEndList(currentPage, pageSize, model);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultFactory.createError(ResultCode.EXCEPTION_ERROR);
		}

		return ResultFactory.createJsonSuccess(orderlList);
	}
	
	@ResponseBody
	@RequestMapping(value = "/orderEndCount", produces = { "application/json;charset=UTF-8" })
	public Result orderEndCount(Integer pageSize, Order model) {
		PageInfo page = orderService.orderEndCount(pageSize, model);
		return ResultFactory.createJsonSuccess(page);
	}
	
	// 晚9点未结束订单
	@ResponseBody
	@RequestMapping(value = "/orderNineCount", produces = { "application/json;charset=UTF-8" })
	public Result orderNineCount(Integer pageSize, Order model) {
		Integer count = 0;
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		if (Integer.parseInt(hourFormat.format(new Date())) >= 21) {
			count = orderService.orderNineCount();
		}
		PageInfo page = new PageInfo(count, 12);
		return ResultFactory.createJsonSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping(value = "/orderNineList", produces = { "application/json;charset=UTF-8" })
	public Result orderNineList(Integer currentPage, Integer pageSize, Order model) {
		List<Order> orderlList = null;
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		if (Integer.parseInt(hourFormat.format(new Date())) >= 21) {
			orderlList = orderService.orderNineList(currentPage, pageSize, model);
		}
		return ResultFactory.createJsonSuccess(orderlList);
	}
}

package com.kunyao.assistant.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.wx.WXOrder;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.enums.PayEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.weixin.WxService;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Account;
import com.kunyao.assistant.core.model.OrderDelay;
import com.kunyao.assistant.core.utils.AliPayUtils;
import com.kunyao.assistant.web.dao.OrderConfigMapper;
import com.kunyao.assistant.web.dao.OrderDelayMapper;
import com.kunyao.assistant.web.service.AccountService;
import com.kunyao.assistant.web.service.OrderDelayService;

@Service
public class OrderDelayServiceImpl extends GenericServiceImpl<OrderDelay, Integer> implements OrderDelayService {
    @Resource
    private OrderDelayMapper orderDelayMapper;
    
    @Resource
    private OrderConfigMapper orderConfigMapper;
    
    @Resource
    private AccountService accountService;

    public GenericDao<OrderDelay, Integer> getDao() {
        return orderDelayMapper;
    }

	@Override
	public OrderDelay createOrderDelay(Integer userId, Integer orderId, Integer hour, Integer payMethod) {
		OrderDelay orderDelay = new OrderDelay(userId, orderId, hour, orderConfigMapper.findOrderServiceFee().getDelayMoney() * hour, payMethod, PayEnum.UNPAY.getValue());
		orderDelayMapper.insert(orderDelay);
		return orderDelay;
	}

	@Override
	public Map<String, Object> payOrderDelay(HttpServletRequest request, OrderDelay orderDelay, String openId) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		int payMethod = orderDelay.getPayMethod();
		String TradeNo = "delay_" + orderDelay.getId();
		if (payMethod == OrderEnum.ServicePayMethod.ALIPAY.getValue()) {
			/** 支付宝支付 **/
			String signOrderInfo = AliPayUtils.createOrder(TradeNo, orderDelay.getMoney(), "支付服务费用", "支付服务费用");
			map.put("result", signOrderInfo);
			
		} else if (payMethod == OrderEnum.ServicePayMethod.WXPAY_JS.getValue()) {
			/** 公众号支付 **/
			WXOrder wxOrder = WxService.unifiedOrder(orderDelay.getId(), openId, TradeNo, orderDelay.getMoney(), "JSAPI", request);
			if (wxOrder != null) {
				map.put("result", wxOrder);
			} else {
				map.put("result", "wxpay_js_error");
			}
			
		} else if (payMethod == OrderEnum.ServicePayMethod.WXPAY_APP.getValue()) {
			/** 微信APP支付 **/
			WXOrder wxOrder = WxService.unifiedOrder(orderDelay.getId(), openId, TradeNo, orderDelay.getMoney(), "APP", request);
			if (wxOrder != null) {
				map.put("result", wxOrder);
			} else {
				map.put("result", "wxpay_app_error");
			}
			
		} else if (payMethod == OrderEnum.ServicePayMethod.BANK.getValue()) {
			/** 银行支付 **/
			
		} else if (payMethod == OrderEnum.ServicePayMethod.BALANCE.getValue()) {
			/** 余额支付 **/
			accountService.updatePayDelay(orderDelay);
			finishPay(orderDelay.getId(), "");
			map.put("result", "paysuccess");
			
		}
		map.put("payMethod", payMethod);
		return map;
	}
	
	@Override
	public Map<String, Object> pay(HttpServletRequest request, Integer delayId, String openId) throws ServiceException {
		OrderDelay orderDelay = orderDelayMapper.findOrderDelayInfo(delayId);
		return payOrderDelay(request, orderDelay, openId);
	}

	@Override
	public OrderDelay finishPay(Integer delayId, String tradeNo) {
		Date date = new Date();
		OrderDelay orderDelay = orderDelayMapper.findOrderDelayInfo(delayId);
		orderDelay.setCreateTime(date);
		orderDelay.setStatus(PayEnum.FINISHPAY.getValue());
		orderDelay.setTradeNo(tradeNo);
		orderDelayMapper.updateByID(orderDelay);
		
		return orderDelay;
	}

	@Override
	public OrderDelay findOrderDelayInfo(Integer delayId) {
		return orderDelayMapper.findOrderDelayInfo(delayId);
	}

	@Override
	public Map<String, Double> findDelayInfo(Integer userId) throws ServiceException {
		Map<String, Double> map = new HashMap<>();
		Account model = new Account();
		model.setUserId(userId);
		map.put("accountBalance", accountService.findOne(model).getBalance());
		map.put("delayMoney", orderConfigMapper.findOrderServiceFee().getDelayMoney());
		return map;
	}

}

package com.kunyao.assistant.web.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Recharge;

public interface RechargeService extends GenericService<Recharge, Integer> {
	
	public Recharge createRecharge(Integer userId, Double money, Integer payMethod, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile);
	
	public Map<String, Object> payRecharge(HttpServletRequest request, Recharge recharge, String openId);
	
	public Map<String, Object> pay(HttpServletRequest request, Integer rechargeId, String openId);
	
	public Recharge finishPay(Integer rechargeId, String tradeNo);
	
	public Recharge findRechargeInfo(Integer rechargeId);
	
	public Integer queryListCount(Recharge recharge) throws ServiceException;
	
	public List<Recharge> queryList(Integer currentPage, Integer pageSize, Recharge recharge) throws ServiceException;
	
	public List<Recharge> queryAll();
}

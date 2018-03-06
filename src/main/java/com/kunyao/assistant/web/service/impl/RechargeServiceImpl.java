package com.kunyao.assistant.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.entity.wx.WXOrder;
import com.kunyao.assistant.core.enums.AccountRecordEnum;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.enums.PayEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.pay.cmbc.NetPay;
import com.kunyao.assistant.core.feature.weixin.WxService;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Account;
import com.kunyao.assistant.core.model.AccountRecord;
import com.kunyao.assistant.core.model.Recharge;
import com.kunyao.assistant.core.model.WXOrderApp;
import com.kunyao.assistant.core.utils.AliPayUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.AccountMapper;
import com.kunyao.assistant.web.dao.AccountRecordMapper;
import com.kunyao.assistant.web.dao.RechargeMapper;
import com.kunyao.assistant.web.service.RechargeService;

@Service
public class RechargeServiceImpl extends GenericServiceImpl<Recharge, Integer> implements RechargeService {
    @Resource
    private RechargeMapper rechargeMapper;
    
    @Resource
    private AccountMapper accountMapper;
    
    @Resource
    private AccountRecordMapper accountRecordMapper;

    public GenericDao<Recharge, Integer> getDao() {
        return rechargeMapper;
    }

	@Override
	public Recharge createRecharge(Integer userId, Double money, Integer payMethod, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile) {
		if (isNeedInvoice.equals(BaseEnum.Status.BASE_STATUS_DISABLED)) {
			invoiceTitle = "";
			invoiceAddress = "";
			invoiceMobile = "";
			invoicePeople = "";
		}
		Recharge recharge = new Recharge(userId, money, new Date(), payMethod, isNeedInvoice, invoiceTitle, invoiceAddress, invoicePeople, invoiceMobile, PayEnum.UNPAY.getValue());
		// TODO 测试
		recharge.setMoney(0.01);
		// 测试
		rechargeMapper.insert(recharge);
		return recharge;
	}

	@Override
	public Map<String, Object> payRecharge(HttpServletRequest request, Recharge recharge, String openId) {
		Map<String, Object> map = new HashMap<>();
		int payMethod = recharge.getPayMethod();
		String TradeNo = "recharge_" + recharge.getId();
		if (payMethod == OrderEnum.ServicePayMethod.ALIPAY.getValue()) {
			/** 支付宝支付 **/
			String signOrderInfo = AliPayUtils.createOrder(TradeNo, recharge.getMoney(), "城侍管家-支付充值费用", "城侍管家-支付充值费用");
			map.put("result", signOrderInfo);
			
		} else if (payMethod == OrderEnum.ServicePayMethod.WXPAY_JS.getValue()) {
			/** 公众号支付 **/
			WXOrder wxOrder = WxService.unifiedOrder(recharge.getId(), openId, TradeNo, recharge.getMoney(), "JSAPI", request);
			if (wxOrder != null) {
				map.put("result", wxOrder);
			} else {
				map.put("result", "wxpay_js_error");
			}
			
		} else if (payMethod == OrderEnum.ServicePayMethod.WXPAY_APP.getValue()) {
			/** 微信APP支付 **/
			WXOrderApp wxOrderApp = WxService.unifiedOrderApp(recharge.getId(), openId, TradeNo, recharge.getMoney(), "APP", request);
			if (wxOrderApp != null) {
				map.put("result", wxOrderApp);
			} else {
				map.put("result", "wxpay_app_error");
			}
			
		} else if (payMethod == OrderEnum.ServicePayMethod.BANK.getValue()) {
			/** 银行支付 **/
			String orderCard = String.valueOf(recharge.getId());
			String str = "0000000000";
			orderCard = str.substring(0, 10 - orderCard.length()) + orderCard;
			String result = NetPay.pay(orderCard, recharge.getMoney(), recharge.getUserId(), request.getRemoteAddr());
			if (result != null) {
				map.put("result", result);
			} else {
				map.put("result", "error");
			}
		}
		map.put("payMethod", payMethod);
		return map;
	}
	
	@Override
	public Map<String, Object> pay(HttpServletRequest request, Integer rechargeId, String openId) {
		Recharge recharge = rechargeMapper.findRechargeInfoById(rechargeId);
		return payRecharge(request, recharge, openId);
	}

	@Override
	public Recharge findRechargeInfo(Integer rechargeId) {
		return rechargeMapper.findRechargeInfoById(rechargeId);
	}

	@Override
	public Recharge finishPay(Integer rechargeId, String tradeNo) {
		Date date = new Date();
		//更新充值信息
		Recharge model = rechargeMapper.findRechargeInfoById(rechargeId);
		model.setStatus(PayEnum.FINISHPAY.getValue());
		model.setCreateTime(date);
		model.setTradeNo(tradeNo);
		rechargeMapper.updateByID(model);
		//更新用户账户余额
		Account account = accountMapper.findAccount(model.getUserId());
		account.setBalance(account.getBalance() + model.getMoney());
		accountMapper.updateByID(account);
		//添加账户余额变动记录
		accountRecordMapper.insert(new AccountRecord(model.getUserId(), null, model.getMoney(), AccountRecordEnum.CHARGE.getValue(), date));
		
		return model;
	}

	@Override
	public Integer queryListCount(Recharge recharge) throws ServiceException {
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(recharge.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", recharge.getBankMobile()));
		}
		if (!StringUtils.isNull(recharge.getStarDate()) &&!StringUtils.isNull(recharge.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("r.create_time>", recharge.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("r.create_time<", recharge.getEndDate() + " 23:59:59"));
		}
		if (recharge.getPayMethod() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("r.pay_method", recharge.getPayMethod()));
		}
		if (recharge.getStatus() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("r. STATUS", recharge.getStatus()));
		}
		if (!StringUtils.isNull(recharge.getTradeNo())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("r.trade_no", recharge.getTradeNo()));
		}
		
		String sql = RechargeMapper.FIND_LIST.replaceAll("#\\{WHERE\\}", sqlBuffer.toString());
		
		List<Recharge> findList = findList(sql, recharge);
		return findList != null ? findList.size() : 0;
	}

	@Override
	public List<Recharge> queryList(Integer currentPage, Integer pageSize, Recharge recharge) throws ServiceException {
		Integer startpos = null;
		if (currentPage != null && pageSize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pageSize, currentPage);
		}
		
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(recharge.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", recharge.getBankMobile()));
		}
		if (!StringUtils.isNull(recharge.getStarDate()) &&!StringUtils.isNull(recharge.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("r.create_time>", recharge.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("r.create_time<", recharge.getEndDate() + " 23:59:59"));
		}
		if (recharge.getPayMethod() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("r.pay_method", recharge.getPayMethod()));
		}
		if (recharge.getStatus() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("r. STATUS", recharge.getStatus()));
		}
		if (!StringUtils.isNull(recharge.getTradeNo())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("r.trade_no", recharge.getTradeNo()));
		}
		
		String sql = RechargeMapper.FIND_LIST_BY_WHERR
				.replaceAll("#\\{WHERE\\}", sqlBuffer.toString())
				.replaceAll("#\\{startPos\\}", String.valueOf(startpos))
				.replaceAll("#\\{pageSize\\}", String.valueOf(pageSize));
		
		List<Recharge> recharges = findList(sql, recharge);
		if (recharges != null) {
			for (Recharge model : recharges) {
				model.dto();
			}
		}
		return recharges;
	}
	
	private void appendBefore(StringBuffer sqlBuffer) {
		if (sqlBuffer.length() == 0) {
			sqlBuffer.append("WHERE ");
		} else {
			sqlBuffer.append(" AND ");
		}
	}
	
	private String createStringStatement(String name, String value) {
		return "name='value'".replace("name", name).replace("value", value);
	}
	
	private String createIntegerStatement(String name, Integer value) {
		return "name=value".replace("name", name).replace("value", String.valueOf(value));
	}
	
	@Override
	public List<Recharge> queryAll() {
		List<Recharge> result = rechargeMapper.findAll();
		if (result != null) {
			for (Recharge recharge : result) {
				recharge.dto();
			}
		}
		return result;
	}
}

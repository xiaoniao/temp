package com.kunyao.assistant.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.enums.AccountRecordEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Account;
import com.kunyao.assistant.core.model.AccountRecord;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderDelay;
import com.kunyao.assistant.web.dao.AccountMapper;
import com.kunyao.assistant.web.dao.AccountRecordMapper;
import com.kunyao.assistant.web.service.AccountService;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer> implements AccountService {
	
    @Resource
    private AccountMapper accountMapper;
    
    @Resource
    private AccountRecordMapper accountRecordMapper;

    public GenericDao<Account, Integer> getDao() {
        return accountMapper;
    }

    /**
     * 使用余额下单
     */
	@Override
	public void order(Order order) throws ServiceException {
		// 判断预存款是否足够
		Account account = accountMapper.findAccount(order.getUserId());
		if (account.getBalance() == null || account.getBalance().doubleValue() < order.getPayMoney().doubleValue()) {
			throw new ServiceException(ResultCode.BALANCE_ENOUGH);
		}
		
		// 更新余额
		account.setBalance(account.getBalance() - order.getPayMoney());
		accountMapper.updateByID(account);
		
		// 增加余额变动记录
		AccountRecord accountRecord = new AccountRecord(order.getUserId(), order.getId(), 
				order.getPayMoney(), AccountRecordEnum.SERVICE_COST.getValue(), new Date());
		accountRecordMapper.insert(accountRecord);
	}

	/**
	 * 使用余额支付延迟费
	 */
	@Override
	public void updatePayDelay(OrderDelay orderDelay) throws ServiceException {
		Account account = accountMapper.findAccount(orderDelay.getUserId());
		if (account.getBalance() == null || account.getBalance().doubleValue() < orderDelay.getMoney().doubleValue())
			throw new ServiceException(ResultCode.BALANCE_ENOUGH);
		
		account.setBalance(account.getBalance() - orderDelay.getMoney());
		int res1 = accountMapper.updateByID(account);
		
		int res2 = accountRecordMapper.insert(new AccountRecord(orderDelay.getUserId(), orderDelay.getOrderId(), orderDelay.getMoney(), AccountRecordEnum.DELAY_COST.getValue(), new Date()));
		if (res1 <= 0 || res2 <= 0)
			throw new ServiceException(ResultCode.EXCEPTION_ERROR);
	}

	/**
	 * 冻结金额 money 
	 * 	 > 0  增加冻结金额 
	 * 	 < 0  减少冻结金额 
	 * 	 0      不变
	 */
	@Override
	public void freeze(Integer userId, Double money) throws ServiceException {
		Account account = accountMapper.findAccount(userId);
		if (account.getFreezeBalance() == null) {
			account.setFreezeBalance(0.0);
		}
		
		// 检查余额是否足够
		if (money < 0 && account.getBalance().doubleValue() < Math.abs(money.doubleValue())) {
			throw new ServiceException(ResultCode.BALANCE_ENOUGH);
		}
		
		account.setFreezeBalance(account.getFreezeBalance() + money);
		account.setBalance(account.getBalance() - money);
		update(account);
	}

	/**
	 * 查询余额
	 */
	@Override
	public Map<String, Double> balance(Integer userId) throws ServiceException {
		Map<String, Double> map = new HashMap<>();
		Account account = accountMapper.findAccount(userId);
		if (account == null) {
			throw new ServiceException(ResultCode.ABNORMAL_REQUEST);
		}
		map.put("balance", account.getBalance());
		map.put("freezeBalance", account.getFreezeBalance());
		return map;
	}

	/**
	 * 订单全额退还服务费
	 */
	@Override
	public void updateRefund(Order order) throws ServiceException {
		// 退还余额
		Account account = accountMapper.findAccount(order.getUserId());
		account.setBalance(account.getBalance() + order.getServiceMoney());
		update(account);
		
		// 增加余额记录
		AccountRecord accountRecord = new AccountRecord(order.getUserId(), order.getId(), order.getServiceMoney(), AccountRecordEnum.REFUND.getValue(), new Date());
		accountRecordMapper.insert(accountRecord);
	}
}

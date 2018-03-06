package com.kunyao.assistant.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.AccountRecordEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.AccountRecord;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.AccountRecordMapper;
import com.kunyao.assistant.web.service.AccountRecordService;

@Service
public class AccountRecordServiceImpl extends GenericServiceImpl<AccountRecord, Integer>
		implements AccountRecordService {
	@Resource
	private AccountRecordMapper accountRecordMapper;

	public GenericDao<AccountRecord, Integer> getDao() {
		return accountRecordMapper;
	}

	/**
	 * 查询用户资金明细
	 */
	@Override
	public Map<String, List<AccountRecord>> queryAccountRecord(Integer userId) {
		Map<String, List<AccountRecord>> map = new HashMap<>();
		map.put("charge", accountRecordMapper.findByStatus(userId, AccountRecordEnum.CHARGE.getValue()));
		map.put("cost", accountRecordMapper.findByStatusIn(userId));
		map.put("withdraw", accountRecordMapper.findByStatus(userId, AccountRecordEnum.WITHDRAW.getValue()));
		return map;
	}

	/**
	 * 帐户明细数量
	 */
	@Override
	public Integer queryListCount(String type, AccountRecord search) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/");
		String pre = simpleDateFormat.format(new Date());
		if (type.equals("month")) {
			return findByMonthCount(pre + "01 00:00:00", DateUtils.getMonthLastDay() + " 23:59:59", search);
		} else {
			return findByHistoryCount(pre + "01 00:00:00", search);
		}
	}

	/**
	 * 帐户明细
	 */
	@Override
	public List<AccountRecord> queryList(String type, Integer currentPage, Integer pagesize, AccountRecord search) {
		List<AccountRecord> accountRecords = null;
		Integer startpos = null;
		if (currentPage != null && pagesize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pagesize, currentPage);
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/");
		String pre = simpleDateFormat.format(new Date());
		if (type.equals("month")) {
			// 当月
			accountRecords = findByMonth(startpos, pagesize, pre + "01 00:00:00", DateUtils.getMonthLastDay() + " 23:59:59", search);
		} else {
			// 历史
			accountRecords = findByHistory(pre + "01 00:00:00", startpos, pagesize, search);
		}
		if (accountRecords != null) {
			for (AccountRecord accountRecord : accountRecords) {
				accountRecord.dto();
			}
		}
		return accountRecords;
	}
	
	private List<AccountRecord> findByMonth(Integer startpos, Integer pagesize, String startDate, String endDate, AccountRecord search) {
		StringBuffer where = new StringBuffer();
		if (!StringUtils.isNull(search.getOrderCard())) {
			createStringStatement(where, "o.order_card", search.getOrderCard());
		}
		if (!StringUtils.isNull(search.getBankMobile())) {
			createStringStatement(where, "m.bank_mobile", search.getBankMobile());
		}
		if (search.getType() != null) {
			createIntegerStatement(where, "a.type", search.getType());
		}
		appendBefore(where);
		where.append("a.operate_time >= '#{startDate}'".replace("#{startDate}", startDate));
		appendBefore(where);
		where.append("a.operate_time <= '#{endDate}'".replace("#{endDate}", endDate));
		try {
			return findList(AccountRecordMapper.FIND_BY_MONTH
					.replace("#{WHERE}", where.toString())
					.replace("#{startPos}", String.valueOf(startpos))
					.replace("#{pageSize}", String.valueOf(pagesize)), search);
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Integer findByMonthCount(String startDate, String endDate, AccountRecord search) {
		StringBuffer where = new StringBuffer();
		if (!StringUtils.isNull(search.getOrderCard())) {
			createStringStatement(where, "o.order_card", search.getOrderCard());
		}
		if (!StringUtils.isNull(search.getBankMobile())) {
			createStringStatement(where, "m.bank_mobile", search.getBankMobile());
		}
		if (search.getType() != null) {
			createIntegerStatement(where, "a.type", search.getType());
		}
		appendBefore(where);
		where.append("a.operate_time >= '#{startDate}'".replace("#{startDate}", startDate));
		appendBefore(where);
		where.append("a.operate_time <= '#{endDate}'".replace("#{endDate}", endDate));
		try {
			List<AccountRecord> result = findList(AccountRecordMapper.FIND_BY_MONTH_COUNT.replace("#{WHERE}", where.toString()), search);
			return result == null ? 0 : result.size();
		} catch (ServiceException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private List<AccountRecord> findByHistory(String endDate, Integer startPos, Integer pageSize, AccountRecord search) {
		StringBuffer where = new StringBuffer();
		if (!StringUtils.isNull(search.getOrderCard())) {
			createStringStatement(where, "o.order_card", search.getOrderCard());
		}
		if (!StringUtils.isNull(search.getBankMobile())) {
			createStringStatement(where, "m.bank_mobile", search.getBankMobile());
		}
		if (search.getType() != null) {
			createIntegerStatement(where, "a.type", search.getType());
		}
		appendBefore(where);
		where.append("a.operate_time < '#{endDate}' ".replace("#{endDate}", endDate));
		try {
			return findList(AccountRecordMapper.FIND_HISTORY
					.replace("#{WHERE}", where.toString())
					.replace("#{startPos}", String.valueOf(startPos))
					.replace("#{pageSize}", String.valueOf(pageSize)), search);
		} catch (ServiceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private Integer findByHistoryCount(String endDate, AccountRecord search) {
		StringBuffer where = new StringBuffer();
		if (!StringUtils.isNull(search.getOrderCard())) {
			createStringStatement(where, "o.order_card", search.getOrderCard());
		}
		if (!StringUtils.isNull(search.getBankMobile())) {
			createStringStatement(where, "m.bank_mobile", search.getBankMobile());
		}
		if (search.getType() != null) {
			createIntegerStatement(where, "a.type", search.getType());
		}
		appendBefore(where);
		where.append("a.operate_time < '#{endDate}' ".replace("#{endDate}", endDate));
		try {
			List<AccountRecord> result = findList(AccountRecordMapper.FIND_HISTORY_COUNT.replace("#{WHERE}", where.toString()), search);
			return result == null ? 0 : result.size();
		} catch (ServiceException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private void appendBefore(StringBuffer sqlBuffer) {
		if (sqlBuffer.length() == 0) {
			sqlBuffer.append("WHERE ");
		} else {
			sqlBuffer.append(" AND ");
		}
	}
	
	private void createStringStatement(StringBuffer sqlBuffer, String name, String value) {
		appendBefore(sqlBuffer);
		sqlBuffer.append("name='value'".replace("name", name).replace("value", value));
	}
	
	private void createIntegerStatement(StringBuffer sqlBuffer, String name, Integer value) {
		appendBefore(sqlBuffer);
		sqlBuffer.append("name=value".replace("name", name).replace("value", String.valueOf(value)));
	}
	
}

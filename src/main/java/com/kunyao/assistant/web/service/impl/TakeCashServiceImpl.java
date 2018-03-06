package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.TakeCash;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.TakeCashMapper;
import com.kunyao.assistant.web.service.TakeCashService;

@Service
public class TakeCashServiceImpl extends GenericServiceImpl<TakeCash, Integer> implements TakeCashService {
    @Resource
    private TakeCashMapper takeCashMapper;

    public GenericDao<TakeCash, Integer> getDao() {
        return takeCashMapper;
    }

	@Override
	public Integer queryListCount(TakeCash takeCash) throws ServiceException {
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(takeCash.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", takeCash.getBankMobile()));
		}
		if (!StringUtils.isNull(takeCash.getStarDate()) &&!StringUtils.isNull(takeCash.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("t.create_time>", takeCash.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("t.create_time<", takeCash.getEndDate() + " 23:59:59"));
		}
		if (takeCash.getStatus() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("t. STATUS", takeCash.getStatus()));
		}
		if (!StringUtils.isNull(takeCash.getTradeNo())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("t.trade_no", takeCash.getTradeNo()));
		}
		
		String sql = TakeCashMapper.FIND_LIST.replaceAll("#\\{WHERE\\}", sqlBuffer.toString());
		
		List<TakeCash> findList = findList(sql, takeCash);
		return findList != null ? findList.size() : 0;
	}

	@Override
	public List<TakeCash> queryList(Integer currentPage, Integer pageSize, TakeCash takeCash) throws ServiceException {
		Integer startpos = null;
		if (currentPage != null && pageSize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pageSize, currentPage);
		}
		
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(takeCash.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", takeCash.getBankMobile()));
		}
		if (!StringUtils.isNull(takeCash.getStarDate()) &&!StringUtils.isNull(takeCash.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("t.create_time>", takeCash.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("t.create_time<", takeCash.getEndDate() + " 23:59:59"));
		}
		if (takeCash.getStatus() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("t. STATUS", takeCash.getStatus()));
		}
		if (!StringUtils.isNull(takeCash.getTradeNo())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("t.trade_no", takeCash.getTradeNo()));
		}
		
		String sql = TakeCashMapper.FIND_LIST_BY_WHERR
				.replaceAll("#\\{WHERE\\}", sqlBuffer.toString())
				.replaceAll("#\\{startPos\\}", String.valueOf(startpos))
				.replaceAll("#\\{pageSize\\}", String.valueOf(pageSize));
		
		List<TakeCash> takeCashs = findList(sql, takeCash);
		if (takeCashs != null) {
			for (TakeCash model : takeCashs) {
				model.dto();
			}
		}
		return takeCashs;
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
	public List<TakeCash> queryAll() {
		List<TakeCash> result =  takeCashMapper.findAll();
		if (result != null) {
			for (TakeCash takeCash : result) {
				takeCash.dto();
			}
		}
		return result;
	}
}

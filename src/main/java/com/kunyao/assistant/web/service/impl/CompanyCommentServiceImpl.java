package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CompanyComment;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.CompanyCommentMapper;
import com.kunyao.assistant.web.service.CompanyCommentService;

@Service
public class CompanyCommentServiceImpl extends GenericServiceImpl<CompanyComment, Integer> implements CompanyCommentService {
    @Resource
    private CompanyCommentMapper companyCommentMapper;

    public GenericDao<CompanyComment, Integer> getDao() {
        return companyCommentMapper;
    }

	@Override
	public Integer queryListCount(CompanyComment companyComment) throws ServiceException{
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(companyComment.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", companyComment.getBankMobile()));
		}
		if (!StringUtils.isNull(companyComment.getStarDate()) && !StringUtils.isNull(companyComment.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time>", companyComment.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time<", companyComment.getEndDate() + " 23:59:59"));
		}
		if (companyComment.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("t.id", companyComment.getCityId()));
		}
		
		String sql = CompanyCommentMapper.FIND_LIST.replaceAll("#\\{WHERE\\}", sqlBuffer.toString());
		
		return findList(sql, companyComment) != null ? findList(sql, companyComment).size() : 0;
	}

	@Override
	public List<CompanyComment> queryList(Integer currentPage, Integer pageSize, CompanyComment companyComment) throws ServiceException {
		Integer startpos = null;
		if (currentPage != null && pageSize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pageSize, currentPage);
		}
		
		// 会员账号、评价时间、出行城市
		
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(companyComment.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", companyComment.getBankMobile()));
		}
		if (!StringUtils.isNull(companyComment.getStarDate()) && !StringUtils.isNull(companyComment.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time>", companyComment.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time<", companyComment.getEndDate() + " 23:59:59"));
		}
		if (companyComment.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("t.id", companyComment.getCityId()));
		}
		
		String sql = CompanyCommentMapper.FIND_LIST_BY_WHERR
				.replaceAll("#\\{WHERE\\}", sqlBuffer.toString())
				.replaceAll("#\\{startPos\\}", String.valueOf(startpos))
				.replaceAll("#\\{pageSize\\}", String.valueOf(pageSize));
		
		List<CompanyComment> comments = findList(sql, companyComment);
		return comments;
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
}

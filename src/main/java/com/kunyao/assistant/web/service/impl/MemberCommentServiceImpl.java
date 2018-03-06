package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.MemberComment;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.MemberCommentMapper;
import com.kunyao.assistant.web.service.MemberCommentService;

@Service
public class MemberCommentServiceImpl extends GenericServiceImpl<MemberComment, Integer> implements MemberCommentService {

	@Resource
	private MemberCommentMapper memberCommentMapper;

	public GenericDao<MemberComment, Integer> getDao() {
		return memberCommentMapper;
	}

	@Override
	public MemberComment add(Integer orderId, Integer crossUserId, Integer userId, Double star1, Double star2, Double star3,
			Double star4, String content) throws ServiceException {
		// 查询订单是否已经评价过
		if (memberCommentMapper.findCountByOrderId(orderId) > 0) {
			throw new ServiceException(ResultCode.ALREADY_COMMENT);
		}
		MemberComment memberComment = new MemberComment(orderId, crossUserId, userId, star1, star2, star3, star4, content);
		memberCommentMapper.insert(memberComment);
		return memberComment;
	}

	/**
	 * 计算评价能量柱分数
	 */
	@Override
	public double calculateScore(Integer userId) {
		if (memberCommentMapper.findCount(userId) == 0) {
			return 100.0;
		}
		Double avg = memberCommentMapper.findStarAvg(userId);
		return avg * 20.0;
	}

	@Override
	public Integer queryListCount(MemberComment memberComment) throws ServiceException {
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(memberComment.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", memberComment.getBankMobile()));
		}
		if (!StringUtils.isNull(memberComment.getWorkName())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("ci.work_name", memberComment.getWorkName()));
		}
		if (!StringUtils.isNull(memberComment.getStarDate()) &&!StringUtils.isNull(memberComment.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time>", memberComment.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time<", memberComment.getEndDate() + " 23:59:59"));
		}
		if (memberComment.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("ci.city_id", memberComment.getCityId()));
		}
		
		String sql = MemberCommentMapper.FIND_LIST.replaceAll("#\\{WHERE\\}", sqlBuffer.toString());
		
		return findList(sql, memberComment) != null ? findList(sql, memberComment).size() : 0;
	}

	@Override
	public List<MemberComment> queryList(Integer currentPage, Integer pageSize, MemberComment memberComment) throws ServiceException {
		Integer startpos = null;
		if (currentPage != null && pageSize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pageSize, currentPage);
		}
		
		// 会员账号、评价时间、出行城市
		
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(memberComment.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", memberComment.getBankMobile()));
		}
		if (!StringUtils.isNull(memberComment.getWorkName())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("ci.work_name", memberComment.getWorkName()));
		}
		if (!StringUtils.isNull(memberComment.getStarDate()) &&!StringUtils.isNull(memberComment.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time>", memberComment.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time<", memberComment.getEndDate() + " 23:59:59"));
		}
		if (memberComment.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("ci.city_id", memberComment.getCityId()));
		}
		
		String sql = MemberCommentMapper.FIND_LIST_BY_WHERR
				.replaceAll("#\\{WHERE\\}", sqlBuffer.toString())
				.replaceAll("#\\{startPos\\}", String.valueOf(startpos))
				.replaceAll("#\\{pageSize\\}", String.valueOf(pageSize));
		
		List<MemberComment> comments = findList(sql, memberComment);
		if (comments != null) {
			for (MemberComment model : comments) {
				model.dto();
			}
		}
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

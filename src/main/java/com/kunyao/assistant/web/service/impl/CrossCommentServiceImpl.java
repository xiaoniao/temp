package com.kunyao.assistant.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.enums.CouponEnum;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.CompanyComment;
import com.kunyao.assistant.core.model.Coupon;
import com.kunyao.assistant.core.model.CrossComment;
import com.kunyao.assistant.core.model.Order;
import com.kunyao.assistant.core.model.OrderTravel;
import com.kunyao.assistant.core.model.Share;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.AccountMapper;
import com.kunyao.assistant.web.dao.CompanyCommentMapper;
import com.kunyao.assistant.web.dao.CouponMapper;
import com.kunyao.assistant.web.dao.CrossCommentMapper;
import com.kunyao.assistant.web.dao.OrderCostMapper;
import com.kunyao.assistant.web.dao.OrderMapper;
import com.kunyao.assistant.web.dao.OrderTravelMapper;
import com.kunyao.assistant.web.dao.ShareMapper;
import com.kunyao.assistant.web.service.CrossCommentService;

@Service
public class CrossCommentServiceImpl extends GenericServiceImpl<CrossComment, Integer> implements CrossCommentService {
    @Resource
    private CrossCommentMapper crossCommentMapper;
    
    @Resource
    private CompanyCommentMapper companyCommentMapper;
    
    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private CouponMapper couponMapper;
    
    @Resource
    private ShareMapper shareMapper;
    
    @Resource
    private OrderTravelMapper orderTravelMapper;
    
    @Resource
    private OrderCostMapper orderCostMapper;
    
    @Resource
    private AccountMapper accountMapper;

    public GenericDao<CrossComment, Integer> getDao() {
        return crossCommentMapper;
    }

    /**
     * 用户可以查看自己的评价，别人评价的内容需要审核后才能查看
     */
	@Override
	public List<CrossComment> findCrossCommentByCrossUserId(Integer userId, Integer crossUserId) {
		List<CrossComment> list = crossCommentMapper.findCrossCommentByCrossUserId(crossUserId);
		if (list == null) {
			return null;
		}
		
		// 移除掉不是自己的未审核评论
		for (int i = list.size() - 1; i >= 0; i--) {
			CrossComment crossComment = list.get(i);
			if (crossComment.getUserId().intValue() == userId) {
				continue;
			}
			if (crossComment.getStatus() == null || crossComment.getStatus().intValue() != BaseEnum.Status.BASE_STATUS_ENABLE.getValue()) {
				list.remove(i);
			}
		}
		return list;
	}

	@Override
	public void updateCommentCross(CrossComment comment, Integer orderId, String companyContent) throws ServiceException {
		Order order = orderMapper.findOrderInfo(orderId);
		if (order.getStatus() != OrderEnum.Status.WAIT_COMMENT.getValue())
			throw new ServiceException(ResultCode.ORDER_STATUS_NEGATIVE);
		
		double star = 0;
		star = (comment.getStar1() + comment.getStar2() + comment.getStar3() + comment.getStar4())/4;
		comment.setStar(star);
		comment.setCreateTime(new Date());
		comment.setStatus(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
		insert(comment);
		if (!StringUtils.isNull(companyContent)) {
			CompanyComment companyComment = new CompanyComment(orderId, comment.getUserId(), companyContent);
			companyCommentMapper.insert(companyComment);
		}
		order.setStatus(OrderEnum.Status.FINISH.getValue());
		
		Order model = new Order();
		model.setStatus(OrderEnum.Status.FINISH.getValue());
		model.setUserId(order.getUserId());
		
		// 若用户是完成首单，判断是否被分享后注册，是则为邀请用户添加1张1000优惠券
		if (orderMapper.selectOrderCount(model) <= 0) {
			Share share = shareMapper.findOneBySharedUserId(order.getUserId());
			if (share != null) {
				Coupon coupon = new Coupon();
				coupon.setMoney(1000d);
				coupon.setUserId(share.getShareUserId());
				coupon.setStatus(CouponEnum.UN_USER.getValue());
				coupon.setName("邀请下单得优惠券");
				coupon.setSource(BaseEnum.Status.BASE_STATUS_DISABLED.getValue());
				coupon.setSharedUserId(order.getUserId());
				Date startDate = DateUtils.parseYMDDate(DateUtils.parseYMDTime(new Date()));
				Date endDate = DateUtils.parseFullDate(DateUtils.parseYMDTime(new Date(startDate.getTime() + DateUtils.DAY * 119)) + " 23:59:59");// 四个月,往后数119天
				coupon.setStartTime(startDate);
				coupon.setEndTime(endDate);
				couponMapper.insert(coupon);
			}
		}
		
		orderMapper.updateByID(order);
	}
	
	@Override
	public Map<String, Double> findStarAvg(Integer crossUserId) {
		List<CrossComment> crossCommentList = crossCommentMapper.findCrossCommentByCrossUserId(crossUserId);
		Double star = 0.0;
		Double star1 = 0.0;
		Double star2 = 0.0;
		Double star3 = 0.0;
		Double star4 = 0.0;
		Map<String, Double> starMap = new HashMap<String, Double>();
		int i = 0;
		for (CrossComment crossComment : crossCommentList) {
			i++;
			star += crossComment.getStar();
			star1 += crossComment.getStar1();
			star2 += crossComment.getStar2();
			star3 += crossComment.getStar3();
			star4 += crossComment.getStar4();
		}
		starMap.put("star", star/i);
		starMap.put("star1", star1/i);
		starMap.put("star2", star2/i);
		starMap.put("star3", star3/i);
		starMap.put("star4", star4/i);
		return starMap;
	}

	@Override
	public Integer queryListCount(CrossComment crossComment) throws ServiceException {
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(crossComment.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", crossComment.getBankMobile()));
		}
		// 被评金鹰
		if (!StringUtils.isNull(crossComment.getWorkName())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("ci.work_name", crossComment.getWorkName()));
		}
		// 评价时间
		if (!StringUtils.isNull(crossComment.getStarDate()) &&!StringUtils.isNull(crossComment.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time>", crossComment.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time<", crossComment.getEndDate() + " 23:59:59"));
		}
		// 出行城市
		if (crossComment.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("ci.city_id", crossComment.getCityId()));
		}
		
		// 分页
		String sql = CrossCommentMapper.FIND_LIST.replaceAll("#\\{WHERE\\}", sqlBuffer.toString());
		return findList(sql, crossComment) != null ? findList(sql, crossComment).size() : 0;
	}

	@Override
	public List<CrossComment> queryList(Integer currentPage, Integer pageSize, CrossComment crossComment) throws ServiceException {
		Integer startpos = null;
		if (currentPage != null && pageSize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pageSize, currentPage);
		}
		
		// 搜索
		// 会员账号
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(crossComment.getBankMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.bank_mobile", crossComment.getBankMobile()));
		}
		// 被评金鹰
		if (!StringUtils.isNull(crossComment.getWorkName())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("ci.work_name", crossComment.getWorkName()));
		}
		// 评价时间
		if (!StringUtils.isNull(crossComment.getStarDate()) &&!StringUtils.isNull(crossComment.getEndDate())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time>", crossComment.getStarDate() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.create_time<", crossComment.getEndDate() + " 23:59:59"));
		}
		// 出行城市
		if (crossComment.getCityId() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("ci.city_id", crossComment.getCityId()));
		}
		
		// 分页
		String sql = CrossCommentMapper.FIND_LIST_BY_WHERR
						.replaceAll("#\\{WHERE\\}", sqlBuffer.toString())
						.replaceAll("#\\{startPos\\}", String.valueOf(startpos))
						.replaceAll("#\\{pageSize\\}", String.valueOf(pageSize));
		
		List<CrossComment> comments = findList(sql, crossComment);
		if (comments != null) {
			for (CrossComment model : comments) {
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

	@Override
	public Map<String, Double> findCostInfo(Integer orderId, Integer userId) throws ServiceException {
		if (orderId == null || orderId < 0 || userId == null || userId < 0)
			throw new ServiceException(ResultCode.PARAMETER_ERROR);
		Map<String, Double> map = new HashMap<>();
		map.put("balance", accountMapper.findAccount(userId).getBalance());
		List<OrderTravel> travelList = orderTravelMapper.queryTravelList(orderId, OrderEnum.Travel.REMOVE.getValue());
		Double cost = 0.0;
		if (travelList != null)
			for (OrderTravel travel : travelList) {
				List<Double> costMoneys = orderCostMapper.queryMoneyListByTravelId(travel.getId());
				if (costMoneys != null)
					for (Double money : costMoneys) {
						cost += money;
					}
			}
		map.put("orderCost", cost);
		return map;
	}

}

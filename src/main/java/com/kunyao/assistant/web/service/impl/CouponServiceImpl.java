package com.kunyao.assistant.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.entity.PageInfo;
import com.kunyao.assistant.core.enums.CouponEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Coupon;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.CouponMapper;
import com.kunyao.assistant.web.dao.OrderConfigMapper;
import com.kunyao.assistant.web.service.CouponService;

@Service
public class CouponServiceImpl extends GenericServiceImpl<Coupon, Integer> implements CouponService {
    @Resource
    private CouponMapper couponMapper;
    
    @Resource
    private OrderConfigMapper orderConfigMapper;

    public GenericDao<Coupon, Integer> getDao() {
        return couponMapper;
    }

	@Override
	public Map<String, List<Coupon>> selectCouponsMap(Integer userId) {
		Map<String, List<Coupon>> map = new HashMap<>();
		List<Coupon> unusedCoupons = couponMapper.findListByUserId(userId, CouponEnum.UN_USER.getValue());
		List<Coupon> disabledCoupons = couponMapper.findListByUserId(userId, CouponEnum.OVER_DATE.getValue());
		map.put("unused", unusedCoupons);
		map.put("disabled", disabledCoupons);
		return map;
	}

	@Override
	public Double selectLimitInfo(Integer stayDays) {
		return orderConfigMapper.findOrderServiceFee().getMaxDiscount() * stayDays;
	}
	
	@Override
	public List<Coupon> selectListWithShared(Integer userId) {
		return couponMapper.findListWithUserInfo(userId);
	}

	@Override
	public Integer queryListCount(Coupon coupon) throws ServiceException {
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(coupon.getMemberName())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.name", coupon.getMemberName()));
		}
		if (!StringUtils.isNull(coupon.getMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("u.mobile", coupon.getMobile()));
		}
		if (coupon.getStatus() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("c.`status`", coupon.getStatus()));
		}
		if (coupon.getEndDay() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("TIMESTAMPDIFF(DAY,DATE_FORMAT(NOW(),'%Y-%m-%d '),DATE_FORMAT(c.end_time,'%Y-%m-%d '))>", 0));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("TIMESTAMPDIFF(DAY,DATE_FORMAT(NOW(),'%Y-%m-%d '),DATE_FORMAT(c.end_time,'%Y-%m-%d '))<", coupon.getEndDay()));
		}
		if (!StringUtils.isNull(coupon.getStartUseTime()) && !StringUtils.isNull(coupon.getEndUseTime())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.use_time>", coupon.getStartUseTime() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.use_time<", coupon.getEndUseTime() + " 23:59:59"));
		}
		
		String sql = CouponMapper.FIND_LIST.replaceAll("#\\{WHERE\\}", sqlBuffer.toString());
		return findList(sql, coupon) != null ? findList(sql, coupon).size() : 0;
	}
	
	@Override
	public List<Coupon> queryList(Integer currentPage, Integer pageSize, Coupon coupon) throws ServiceException {
		Integer startpos = null;
		if (currentPage != null && pageSize != null) {
			currentPage = currentPage >= 1 ? currentPage : 1;
			startpos = PageInfo.countOffset(pageSize, currentPage);
		}
		
		StringBuffer sqlBuffer = new StringBuffer();
		if (!StringUtils.isNull(coupon.getMemberName())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("m.name", coupon.getMemberName()));
		}
		if (!StringUtils.isNull(coupon.getMobile())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("u.mobile", coupon.getMobile()));
		}
		if (coupon.getStatus() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("c.`status`", coupon.getStatus()));
		}
		if (coupon.getEndDay() != null) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("TIMESTAMPDIFF(DAY,DATE_FORMAT(NOW(),'%Y-%m-%d '),DATE_FORMAT(c.end_time,'%Y-%m-%d '))>", 0));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createIntegerStatement("TIMESTAMPDIFF(DAY,DATE_FORMAT(NOW(),'%Y-%m-%d '),DATE_FORMAT(c.end_time,'%Y-%m-%d '))<", coupon.getEndDay()));
		}
		if (!StringUtils.isNull(coupon.getStartUseTime()) && !StringUtils.isNull(coupon.getEndUseTime())) {
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.use_time>", coupon.getStartUseTime() + " 00:00:00"));
			appendBefore(sqlBuffer);
			sqlBuffer.append(createStringStatement("c.use_time<", coupon.getEndUseTime() + " 23:59:59"));
		}
		
		String sql = CouponMapper.FIND_LIST_BY_WHERR
				.replaceAll("#\\{WHERE\\}", sqlBuffer.toString())
				.replaceAll("#\\{startPos\\}", String.valueOf(startpos))
				.replaceAll("#\\{pageSize\\}", String.valueOf(pageSize));
		
		List<Coupon> coupons = findList(sql, coupon);
		if (coupons != null) {
			for (Coupon c : coupons) {
				c.dto();
			}
		}
		return coupons;
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
	public List<Coupon> createActivityCoupon(String activityName, Integer userId) throws ServiceException {
		List<Coupon> result = new ArrayList<>();
		Date startTime = DateUtils.parseFullDate("2017/04/05 00:00:00");
		Date endTime = new Date(startTime.getTime() + 90L * DateUtils.DAY);
		
		for (int i = 0; i < 20; i++) {
			Coupon coupon = new Coupon(activityName, startTime, endTime, userId, 500.0, 2);
			insert(coupon);
			result.add(coupon);
		}
		for (int i = 0; i < 10; i++) {
			Coupon coupon = new Coupon(activityName, startTime, endTime, userId, 1000.0, 2);
			insert(coupon);
			result.add(coupon);
		}
		return result;
	}
}

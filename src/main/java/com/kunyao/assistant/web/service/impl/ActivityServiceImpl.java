package com.kunyao.assistant.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Activity;
import com.kunyao.assistant.core.model.ActivityLinked;
import com.kunyao.assistant.core.model.Coupon;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.dao.ActivityMapper;
import com.kunyao.assistant.web.service.ActivityLinkedService;
import com.kunyao.assistant.web.service.ActivityService;
import com.kunyao.assistant.web.service.CouponService;

@Service
public class ActivityServiceImpl extends GenericServiceImpl<Activity, Integer> implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    
    @Resource
    private ActivityLinkedService activityLinkedService;
    
    @Resource
    private CouponService couponService;

    public GenericDao<Activity, Integer> getDao() {
        return activityMapper;
    }
    
    @Override
    public ActivityLinked joinActivity(Integer userId, Integer activityId, String token) throws ServiceException {
    	ActivityLinked activityLinked = null;
    	
    	// 查询活动
    	Activity activity = findByID(activityId);
    	if (activity == null) {
    		throw new ServiceException(ResultCode.ACTIVITY_NOT_START);
    	}
    	if (activity.getStatus().intValue() == BaseEnum.Status.BASE_STATUS_DISABLED.getValue()) {
    		throw new ServiceException(ResultCode.ACTIVITY_EXPIRE);
		}
    	long now = System.currentTimeMillis();
    	if (now < activity.getStartTime().getTime()) {
    		throw new ServiceException(ResultCode.ACTIVITY_NOT_START);
		}
    	if (now > activity.getEndTime().getTime()) {
    		throw new ServiceException(ResultCode.ACTIVITY_EXPIRE);
		}
    	
    	// 异常token
    	if (StringUtils.isNull(token) || !activity.getTokens().contains(token)) {
    		throw new ServiceException(ResultCode.ACTIVITY_TOKEN_ERROR);
		}
    	
    	// 活动链接无效
    	if (activity.getUsedTokens() != null && activity.getUsedTokens().contains(token)) {
    		throw new ServiceException(ResultCode.ACTIVITY_ALREADY_JOIN);
		}
    	
    	// 判断用户是否参加过活动
    	ActivityLinked model = new ActivityLinked();
    	model.setActivityId(activityId);
    	model.setUserId(userId);;
    	ActivityLinked isExistActivityLinked = activityLinkedService.findOne(model);
    	if (isExistActivityLinked != null) {
			throw new ServiceException(ResultCode.ACTIVITY_ALREADY_JOIN);
    	}
    	
    	// 生成优惠券(关键)
    	List<Coupon> coupons = couponService.createActivityCoupon(activity.getName(), userId);
    	
    	// 添加会员参加活动的优惠券记录
    	StringBuffer couponIds = new StringBuffer();
    	for (Coupon coupon : coupons) {
    		if (couponIds.length() != 0) {
    			couponIds.append(",");
			}
    		couponIds.append(String.valueOf(coupon.getId()));
		}
    	activityLinked = activityLinkedService.create(activityId, userId, couponIds.toString());
    	
    	// 把活动链接token，加入以参加数组
    	String usedTokens = activity.getUsedTokens();
    	if (StringUtils.isNull(usedTokens)) {
    		usedTokens = "";
		}
    	activity.setUsedTokens(usedTokens + (!StringUtils.isNull(usedTokens) ? "," : "") + token);
    	update(activity);
    	
    	return activityLinked;
    }
}

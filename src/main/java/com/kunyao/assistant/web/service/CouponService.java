package com.kunyao.assistant.web.service;

import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Coupon;

public interface CouponService extends GenericService<Coupon, Integer> {

	/** 用户端查询优惠券 */
	Map<String, List<Coupon>> selectCouponsMap(Integer userId);

	/** 查询优惠券使用上限 */
	Double selectLimitInfo(Integer stayDays);
		
	/** 查询优惠券获取记录 */
	List<Coupon> selectListWithShared(Integer userId);

	Integer queryListCount(Coupon coupon) throws ServiceException;
	
	List<Coupon> queryList(Integer currentPage, Integer pageSize, Coupon Coupon) throws ServiceException;
	
	/** 创建活动所需要的优惠券 **/
	List<Coupon> createActivityCoupon(String activityName, Integer userId) throws ServiceException;
}

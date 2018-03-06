package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Coupon;

public interface CouponMapper extends GenericDao<Coupon, Integer> {

	final String FIND_COUPON_INFO = "SELECT * FROM kycom_u_coupon where id = #{couponId}";
	
	final String FIND_BY_USER_ID = "SELECT * FROM kycom_u_coupon where user_id = #{userId}";
	
	final String FIND_LIST_BY_USER_ID = "SELECT * FROM kycom_u_coupon WHERE user_id = #{userId} AND status = #{status} ORDER BY money DESC, end_time DESC";

	final String FIND_LIST_WITH_USER_INFO = 
			"		SELECT																	"
			+ "		uc.money,																"
			+ "		uc.source,																"
			+ "		CONCAT(SUBSTR(uu.username,1,3),'****',SUBSTR(uu.username,8)) AS mobile	"
			+ "		FROM kycom_u_coupon AS uc												"
			+ "		LEFT JOIN kycom_u_user AS uu ON uu.id = uc.shared_user_id				"
			+ "		WHERE uc.user_id = #{sharedId}											"
			+ "		AND shared_user_id != null";

	
	final String FIND_LIST_BY_WHERR = 
							"    SELECT                                                      " +
							"    	c.*, u.mobile, m.name as memberName                      " +
							"    FROM                                                        " +
							"    	kycom_u_coupon c                                         " +
							"    LEFT JOIN kycom_u_user u ON u.id = c.user_id                " +
							"    LEFT JOIN kycom_u_member_info m ON m.user_id = c.user_id    " +
							"    #{WHERE}                                                    " +
							"    ORDER BY                                                    " +
							"    	id DESC                                                  " +
							"	 LIMIT #{startPos}, #{pageSize}				                 ";
	
	final String FIND_LIST = 
			"    SELECT                                                      " +
			"    	c.*, u.mobile, m.name as memberName                      " +
			"    FROM                                                        " +
			"    	kycom_u_coupon c                                         " +
			"    LEFT JOIN kycom_u_user u ON u.id = c.user_id                " +
			"    LEFT JOIN kycom_u_member_info m ON m.user_id = c.user_id    " +
			"    #{WHERE}                                                    ";
	
	@Select(FIND_COUPON_INFO)
	Coupon findCouponInfo(@Param("couponId") Integer couponId);
	
	@Select(FIND_BY_USER_ID)
	List<Coupon> findByUserId(@Param("userId") Integer userId);
	
	/** 按失效日期,面额倒序 */
	@Select(FIND_LIST_BY_USER_ID)
	List<Coupon> findListByUserId(@Param("userId") Integer userId, @Param("status") Integer status);

	@Select(FIND_LIST_WITH_USER_INFO)
	List<Coupon> findListWithUserInfo(@Param("sharedId") Integer sharedId);
	
	@Select("SELECT count(*) FROM kycom_u_coupon")
	Integer findListCount();
}

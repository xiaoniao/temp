package com.kunyao.assistant.web.dao;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Share;
import java.lang.Integer;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ShareMapper extends GenericDao<Share, Integer> {

	final String FIND_SOURCE_INFO = 
			"	SELECT																					"
			+ "	CONCAT(SUBSTR(mi.bank_mobile,1,3),'****',SUBSTR(mi.bank_mobile,8)) AS sourceMobile,		"
			+ "	SUBSTR(mi.name,1,1) AS sourceName														"
			+ "	FROM kycom_t_share AS sh 																"
			+ "	LEFT JOIN kycom_u_member_info AS mi ON mi.user_id = sh.share_user_id					"
			+ "	WHERE sh.code = #{code}";
	
	final String FIND_SHARE_INFO_BY_SHARE_USER_ID = "SELECT * FROM kycom_t_share WHERE share_user_id = #{shareUserId}";
	
	final String FIND_ONE_BY_SHARED_USER_ID = "SELECT * FROM kycom_t_share WHERE FIND_IN_SET(#{sharedUserId}, shared_user_id)";
	
	@Select(FIND_SOURCE_INFO)
	Share sourceInfo(@Param("code") String code);

	@Select(FIND_SHARE_INFO_BY_SHARE_USER_ID)
	Share findShareByShareUserId(@Param("shareUserId") Integer shareUserId);

	@Select(FIND_ONE_BY_SHARED_USER_ID)
	Share findOneBySharedUserId(@Param("sharedUserId") Integer sharedUserId);
}

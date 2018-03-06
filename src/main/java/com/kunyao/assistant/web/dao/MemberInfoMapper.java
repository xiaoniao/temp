package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.MemberInfo;

public interface MemberInfoMapper extends GenericDao<MemberInfo, Integer> {

	final String FIND_MEMBER_INFO = 
				"   SELECT                                        " +
				"   	m.*, u.mobile                             " +
				"   FROM                                          " +
				"   	kycom_u_member_info m                     " +
				"   LEFT JOIN kycom_u_user u ON u.id = m.user_id  " +
				"   WHERE                                         " +
				"   	m.user_id = #{userId}                     ";
	
	final String FIND_MEMBER = 
			"	SELECT "
			+ "		m.id,"
			+ "		m.name,"
			+ "		m.sex,"
			+ "		m.header,"
			+ "		m.address,"
			+ "		m.idcard_check_pass_status,"
			+ "		m.native_place,"
			+ "		m.live_place,"
			+ "		m.business,"
			+ "		m.company,"
			+ "		m.bank_mobile,"
			+ "		m.position,"
			+ "		m.travel_frequency,"
			+ "		m.habit,"
			+ "		m.taboo,"
			+ "		m.open_id,"
			+ "		m.user_id,"
			+ "		CONCAT(SUBSTR(m.idcard,1,4),'**********',SUBSTR(m.idcard,15)) AS idcard,"
			+ "		u.mobile "
			+ "	FROM "
			+ "		kycom_u_member_info m "
			+ "	LEFT JOIN"
			+ "		kycom_u_user u ON u.id = m.user_id "
			+ "	WHERE "
			+ "		m.id = #{memberId}";
	
	final String FIND_BY_OPEN_ID = "SELECT * FROM kycom_u_member_info where open_id = #{openId}";

	@Select(FIND_MEMBER_INFO)
	MemberInfo findMemberInfo(@Param("userId") Integer userId);

	@Select(FIND_BY_OPEN_ID)
	MemberInfo findByOpenId(@Param("openId") String openId);
	
	@Select("select * from kycom_u_member_info where user_id = (select user_id from kycom_o_order where id = #{orderId})")
	MemberInfo findByOrderId(@Param("orderId") Integer orderId);
	
	@Select(FIND_MEMBER)
	MemberInfo findMember(@Param("memberId") Integer memberId);
	
	List<MemberInfo> selectListByCondition(@Param(value = "startpos") Integer startpos, @Param(value = "pagesize") Integer pagesize, @Param(value = "memberInfo") MemberInfo memberInfo);
	
	int selectCountByCondition(@Param("memberInfo") MemberInfo memberInfo);
	
	@Select("SELECT idcard, idcard_pic1, idcard_pic2 FROM kycom_u_member_info WHERE id = #{memberId}")
	MemberInfo findMemberIdcardInfo(@Param("memberId") Integer memberId);
}

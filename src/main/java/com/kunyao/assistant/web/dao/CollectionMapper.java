package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Collection;
import com.kunyao.assistant.core.model.CrossInfo;

public interface CollectionMapper extends GenericDao<Collection, Integer> {

	final String FIND_COLLECTION_INFO = "select * from kycom_u_collection where user_id = #{userId} and cross_info_id = #{crossInfoId} LIMIT 1";

	final String DELETE_COLLECION_INFO = "delete from kycom_u_collection where user_id = #{userId} and cross_info_id = #{crossInfoId}";

	final String FIND_LIST = 
					"    SELECT c.id, c.pic, c.user_id, c.work_name, m.comment_avg, m.comment_amount, c.cross_number, t2.booked_day_count, c.city_id, ct.cityName" +
					"    FROM kycom_u_collection cl                                                                                                   " +
					"    LEFT JOIN kycom_u_cross_info c                                                                                               " +
					"        ON c.id = cl.cross_info_id                                                                                               " +
					"    LEFT JOIN                                                                                                                    " +
					"        (                                                                                                                        " +
					"    			SELECT user_id, count(*) AS booked_day_count                                                                      " +
					"    			FROM kycom_u_cross_times AS ct                                                                                    " +
					"    			WHERE ct.`status` = #{timeStatus}                                                                                 " +
					"    			GROUP BY  user_id                                                                                                 " +
					"    		) AS t2                                                                                                               " +
					"        ON c.user_id = t2.user_id                                                                                                " +
					"    LEFT JOIN                                                                                                                    " +
					"        (                                                                                                                        " +
					"    			SELECT cm.cross_user_id, avg(cm.star) AS comment_avg , count(*) AS comment_amount                                 " +
					"    			FROM kycom_u_cross_comment AS cm                                                                                  " +
					"    			GROUP BY  cross_user_id                                                                                           " +
					"    		) AS m                                                                                                                " +
					"        ON c.user_id = m.cross_user_id                                                                                           " +
					"    LEFT JOIN                                                                                                                    " +
					"    		(                                                                                                                     " +
					"    			SELECT id, name AS cityName FROM kycom_t_city                                                                     " +
					"    		) AS ct ON ct.id = c.city_id                                                                                          " +
					"    WHERE cl.user_id = #{userId}                                                                                                 " +
					"    ORDER BY  c.city_id, cl.collection_time desc;                                                                                ";

	@Select(FIND_COLLECTION_INFO)
	Collection findCollection(@Param("userId") Integer userId, @Param("crossInfoId") Integer crossInfoId);

	@Select(DELETE_COLLECION_INFO)
	void removeCollection(@Param("userId") Integer userId, @Param("crossInfoId") Integer crossInfoId);

	@Select(FIND_LIST)
	List<CrossInfo> findList(@Param("userId") Integer userId, @Param("timeStatus") Integer timeStatus);
}

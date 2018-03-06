package com.kunyao.assistant.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.model.Messages;

public interface MessagesMapper extends GenericDao<Messages, Integer> {
	
	final String UNREAD_COUNT =
					"    SELECT                  " +
					"    	count(*)             " +
					"    FROM                    " +
					"    	kycom_t_messages     " +
					"    WHERE                   " +
					"    	user_id = #{userId}  " +
					"    AND                     " + 
					"       is_read = 0          ";
	
	final String LIST =
					"    SELECT                  " +
					"    	*                    " +
					"    FROM                    " +
					"    	kycom_t_messages     " +
					"    WHERE                   " +
					"    	user_id = #{userId}  " +
					"    ORDER BY                " + 
					"       create_time desc     " +
					"                            ";
	
	final String INFO =
					"    SELECT                  " +
					"    	*                    " +
					"    FROM                    " +
					"    	kycom_t_messages     " +
					"    WHERE                   " +
					"    	id = #{messageId}    ";
	
	@Select(UNREAD_COUNT)
	Integer unreadCount(@Param("userId") Integer userId);
	
	@Select(LIST)
	List<Messages> list(@Param("userId") Integer userId);
	
	@Select(INFO)
	Messages info(@Param("messageId") Integer messageId);
}

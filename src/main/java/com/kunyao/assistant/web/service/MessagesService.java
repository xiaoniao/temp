package com.kunyao.assistant.web.service;

import java.util.List;

import com.kunyao.assistant.core.generic.GenericService;
import com.kunyao.assistant.core.model.Messages;

public interface MessagesService extends GenericService<Messages, Integer> {

	Integer unreadCount(Integer userId);
	
	List<Messages> list(Integer userId);

	Messages info(Integer messageId);
}

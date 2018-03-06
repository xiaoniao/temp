package com.kunyao.assistant.web.service.impl;

import com.kunyao.assistant.core.generic.GenericDao;
import com.kunyao.assistant.core.generic.GenericServiceImpl;
import com.kunyao.assistant.core.model.Messages;
import com.kunyao.assistant.web.dao.MessagesMapper;
import com.kunyao.assistant.web.service.MessagesService;
import java.lang.Integer;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MessagesServiceImpl extends GenericServiceImpl<Messages, Integer> implements MessagesService {
	@Resource
	private MessagesMapper messagesMapper;

	public GenericDao<Messages, Integer> getDao() {
		return messagesMapper;
	}

	@Override
	public Integer unreadCount(Integer userId) {
		return messagesMapper.unreadCount(userId);
	}
	
	@Override
	public List<Messages> list(Integer userId) {
		return messagesMapper.list(userId);
	}

	@Override
	public Messages info(Integer messageId) {
		Messages messages = messagesMapper.info(messageId);
		messages.setIsRead(1);
		messagesMapper.updateByID(messages);
		return messages;
	}
}

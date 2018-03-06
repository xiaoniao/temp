package com.kunyao.assistant.web.service.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kunyao.assistant.core.dto.Push;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.feature.getui.GetuiUtils;
import com.kunyao.assistant.core.feature.sms.YunPianSMS;
import com.kunyao.assistant.core.model.Messages;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.MessagesService;
import com.kunyao.assistant.web.service.PushService;

@Service
public class PushServiceImpl implements PushService {

	@Resource
	private MessagesService messagesService;

	@Override
	public void push(Push pushDto) {
		// app推送
		if (!StringUtils.isNull(pushDto.getCid())) {
			try {
				if (pushDto.getUserType().equals("member")) {
					// 推送给用户
					GetuiUtils.pushToClient(pushDto);
				} else if (pushDto.getUserType().equals("cross")) {
					// 推送给金鹰
					GetuiUtils.pushToCross(pushDto);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 短信推送
		if (!StringUtils.isNull(pushDto.getMobile())) {
			//发送短信
			YunPianSMS.sendText(pushDto.getMobile(), pushDto.getContent(), pushDto.getTplId(), pushDto.getTplValue());
		}

		// 消息记录
		Messages model = new Messages(pushDto.getUserId(), pushDto.getTitle(), pushDto.getContent());
		try {
			messagesService.insert(model);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}

package com.kunyao.assistant.core.feature.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class ApplicationEventListener implements ApplicationListener<ServletRequestHandledEvent> {

	private static final Logger log = LoggerFactory.getLogger(ApplicationEventListener.class);

	@Override
	public void onApplicationEvent(ServletRequestHandledEvent event) {
		log.info(event.toString());
	}
}

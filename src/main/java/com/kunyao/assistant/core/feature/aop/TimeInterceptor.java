package com.kunyao.assistant.core.feature.aop;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TimeInterceptor extends HandlerInterceptorAdapter {

	//private static final Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);
	//private ThreadLocal<Long> time = new ThreadLocal<Long>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//time.set(System.currentTimeMillis());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//logger.info(request.getRequestURL() + " 耗时：" + (System.currentTimeMillis() - time.get()));
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//logger.info(request.getRequestURL() + " 耗时：" + (System.currentTimeMillis() - time.get()));
	}
}

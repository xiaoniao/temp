package com.kunyao.assistant.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * 具体接口分发 根据传入的 method 的参数，将请求转发到对应的 controller 链接中
 * @author GeNing
 * @since  2016.11.23
 */
@Component
public class ApiForwardFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String method = request.getParameter("method");
		
		/*
		 * 根据传入 method 参数 控制接口具体方法转发
		 */
		String apiUrl = "";
		
		if ("createOperater".equals(method))
			apiUrl = "/rest/business/account/createOperater";
		else if ("operaterList".equals(method))
			apiUrl = "/rest/business/account/operaterList";
		
		request.getRequestDispatcher(apiUrl).forward(request, res);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}
}

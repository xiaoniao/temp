package com.kunyao.assistant.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

@Component
public class AccountLoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String apiUrl = "/rest/business/account/login";
		request.getRequestDispatcher(apiUrl).forward(request, response);
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
}

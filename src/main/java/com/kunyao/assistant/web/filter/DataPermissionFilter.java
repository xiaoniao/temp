package com.kunyao.assistant.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.web.dao.DataCheckMapper;

/**
 * 验证数据所有者 ，比如用户A操作订单B，需要验证订单B输入用户A，否则提示错误
 */
@Component
public class DataPermissionFilter implements Filter {

	@Autowired
	private DataCheckMapper dataCheckMapper;

	/**
	 * 还是使用AOP拦截比较好，这种方式需要硬编码URL，方法也不好维护
	 * 
	 * @Annotation CrossCheckTravel CrossCheckCost CrossCheckOrder
	 * @Annotation MemberCheckTravel MemberCheckCost MemberCheckOrder 分别对注解
	 *             进行拦截，这样相比而言不需要写死代码
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String path = request.getServletPath() + request.getPathInfo();

		if (path.equals("/rest/cc/cost/add") || path.equals("/rest/cc/travel/remove")
				|| path.equals("/rest/cc/travel/edit")) {
			// 验证行程单
			if (!isOwnerTravel(request)) {
				Result result = ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
				write(response, result.toJson());
				return;
			}
		} else if (path.equals("/rest/cc/cost/remove") || path.equals("/rest/cc/cost/edit")) {
			// 验证账单
			if (!isOwnerCost(request)) {
				Result result = ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
				write(response, result.toJson());
				return;
			}
		} else if (path.equals("/rest/cc/order/require") || path.equals("/rest/cc/travel/add")
				|| path.equals("/rest/cc/travel/push") || path.equals("/rest/cc/travel/finish")
				|| path.equals("/rest/cc/travel/list")) {
			// 验证订单
			if (!isOwnerOrder(request)) {
				Result result = ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
				write(response, result.toJson());
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/** 行程单是否属于金鹰 **/
	private boolean isOwnerTravel(HttpServletRequest request) {
		Integer travelId = getIntParameter(request, "travelId");
		if (travelId == null) {
			return false;
		}
		Integer crossUserId = getIntParameter(request, "crossUserId");
		if (crossUserId == null) {
			return false;
		}
		Integer mCrossUserId = dataCheckMapper.findCrossUserByOrderTravel(travelId);
		if (mCrossUserId != null && mCrossUserId.intValue() == crossUserId.intValue()) {
			return true;
		}
		return false;
	}

	/** 账单是否属于金鹰 **/
	private boolean isOwnerCost(HttpServletRequest request) {
		Integer costId = getIntParameter(request, "costId");
		if (costId == null) {
			return false;
		}
		Integer crossUserId = getIntParameter(request, "crossUserId");
		if (crossUserId == null) {
			return false;
		}
		Integer mCrossUserId = dataCheckMapper.findCrossUserByOrderCost(costId);
		if (mCrossUserId != null && mCrossUserId.intValue() == crossUserId.intValue()) {
			return true;
		}
		return false;
	}

	/** 订单是否属于金鹰 **/
	private boolean isOwnerOrder(HttpServletRequest request) {
		Integer orderId = getIntParameter(request, "orderId");
		if (orderId == null) {
			return false;
		}
		Integer crossUserId = getIntParameter(request, "crossUserId");
		if (crossUserId == null) {
			return false;
		}
		Integer mCrossUserId = dataCheckMapper.findCrossUserByOrder(orderId);
		if (crossUserId != null && crossUserId.intValue() == mCrossUserId.intValue()) {
			return true;
		}
		return false;
	}

	/** 获取参数 **/
	private Integer getIntParameter(HttpServletRequest request, String name) {
		try {
			return Integer.valueOf(request.getParameter(name));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
	}

	@Override
	public void destroy() {

	}

	private boolean write(HttpServletResponse response, String content) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		out.println(content);
		out.flush();
		return true;
	}
}

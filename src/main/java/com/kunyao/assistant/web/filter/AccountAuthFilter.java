package com.kunyao.assistant.web.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.constant.ResultCode;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.feature.cache.redis.RedisCache;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.utils.RequestUtils;
import com.kunyao.assistant.core.utils.SignUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.core.utils.XStreamUtils;

/**
 * 认证 Filter，用于在调用开放接口前检查认证信息
 * 
 * 商户在登录后会产生 UID 和  Token信息返回给客户端。
 * 客户端在请求其他接口后需要在url加入 uid，token，timestamp，sign
 * 
 * 1. 验证 必要参数是否缺少
 * 2. 验证 当前系统时间与接口提交时间戳相差过大
 * 3. 验证 Token跟Uid是否可以在Redis中获取到
 * 4. 验证 Url签名是否正确
 * 
 * @author GeNing
 * @since  2016/11/21 
 *
 */
@Component
public class AccountAuthFilter implements Filter {
	
	@Resource
	private RedisCache redisCache;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		Result result = null;
		
		String timestamp = request.getParameter("timestamp");
		String token = request.getParameter("token");
		String sign  = request.getParameter("sign");
		String uid   = request.getParameter("uid");
		
		/*
		 * 缺少必要参数，如 timestamp、token、sign 其中一项为空
		 */
		if (StringUtils.isNull(timestamp) || StringUtils.isNull(token) || StringUtils.isNull(sign) || StringUtils.isNull(uid)) {
			result = ResultFactory.createError(ResultCode.ABNORMAL_REQUEST);
			write(response, result.toJson());
			return;
		}
		
		/*
		 * 当前系统时间与接口提交时间戳相差过大（5分钟-600秒）
		 */
		if (new Date().getTime() - Long.parseLong(timestamp) > 1000 * 60 * 5) {
			result = ResultFactory.createError(ResultCode.REQUEST_TIMEOUT);
			write(response, result.toJson());
			return;
		}
		
		/*
		 * Token失效，不对应或超时
		 */
		User account = null;
		String cacheInfo = redisCache.get(token);
		
		// cache 取不到token
		if (StringUtils.isNull(cacheInfo)) {
			result = ResultFactory.createError(ResultCode.TOKEN_INVALID);
			write(response, result.toJson());
			return;
		}
		
		// 取到的用户信息转换失败
		try {
			account = (User) XStreamUtils.xml2Bean(cacheInfo, User.class.getSimpleName(), User.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = ResultFactory.createError(ResultCode.TOKEN_INVALID);
			write(response, result.toJson());
			return;
		}
		
		// token不一致或超时
		if (!token.equals(account.getToken()) || 
				new Date().getTime() - account.getGetTokenTime() > 1000 * 60 * 60 * 3) {
			result = ResultFactory.createError(ResultCode.TOKEN_INVALID);
			write(response, result.toJson());
			return;
		}
		
		/* 
		 * UID 无效
		 */
		if (!account.getId().equals(Integer.parseInt(uid))) {
			result = ResultFactory.createError(ResultCode.UID_INVALID);
			write(response, result.toJson());
			return;
		}
		
		// 生成签名
		SortedMap<String, String> signedMap = RequestUtils.getRequestAllParams(request);
		signedMap.put("signKey", Constant.AUTH_SIGN_KEY);
		String sysSign =  SignUtils.createUrlSign("UTF-8", signedMap);
		
		// Sign 签名错误
		if (!sign.equals(sysSign)) {
			result = ResultFactory.createError(ResultCode.SIGN_ERROR);
			write(response, result.toJson());
			return ;
		}
		
		chain.doFilter(req, res);
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		
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

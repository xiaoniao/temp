package com.kunyao.assistant.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;

import com.kunyao.assistant.core.utils.StringUtils;

/**
 * 网关层页面控制器 如：400、500等自定义页面跳转
 * @author GeNing
 * @since 2016.06.27
 */
@Controller
@RequestMapping(value = "/page")
public class GatewayPageController {
	
	/**
	 * 登录页
	 * @return
	 */
	@RequestMapping(value = "/tologin")
	public String toLogin(Model model) {
		model.addAttribute("formKey", StringUtils.getRandomString(32));
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			return "index";
		}
		return "login";
	}

	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}

	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "welcome";
	}

	/**
	 * 404页
	 */
	@RequestMapping("/404")
	public String error404() {
		return "/404";
	}

	/**
	 * 401页
	 */
	@RequestMapping("/401")
	public String error401() {
		return "/401";
	}

	/**
	 * 500页
	 */
	@RequestMapping("/500")
	public String error500() {
		return "/500";
	}

	/**
	 * 操作结果页
	 * @return
	 */
	@RequestMapping("/result")
	public String success() {
		return "/result";
	}
}

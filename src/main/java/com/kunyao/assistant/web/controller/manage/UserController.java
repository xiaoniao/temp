package com.kunyao.assistant.web.controller.manage;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.kunyao.assistant.core.constant.Constant;
import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.exception.ServiceException;
import com.kunyao.assistant.core.model.User;
import com.kunyao.assistant.core.utils.SessionUtils;
import com.kunyao.assistant.core.utils.StringUtils;
import com.kunyao.assistant.web.service.ManagerService;
import com.kunyao.assistant.web.service.UserService;

/**
 * 用户控制器
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private Producer captchaProducer;
	
	@Resource
	private ManagerService managerService;

	/**
	 * 获取登录验证码
	 * @param request
	 * @param response
	 * @param formKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/kaptcha")
	public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response, String formKey) throws Exception {

		HttpSession session = request.getSession();

		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		session.setAttribute(formKey, capText);

		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);

		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 用户登录
	 * @param user
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
		try {
			Subject subject = SecurityUtils.getSubject();

			// 已登陆则 跳到首页
			if (subject.isAuthenticated()) {
				User login = (User) SessionUtils.getAttribute(Constant.LOGIN_USER);
				if ("admin".equals(login.getType()))
					return "redirect:/rest/page/index";
				else if (Constant.ROLE_SIGN_RES_DEV_PRACTICE.equals(login.getType()) || Constant.ROLE_SIGN_RES_DEV_FULL.equals(login.getType())) {
					request.getSession().setAttribute("username", managerService.findByUserId(login.getId()).getName());
					return "redirect:/rest/um/resource/resource_list";
				}
			}

			if (result.hasErrors()) {
				model.addAttribute("loginTips", "请输出正确格式的登录名、密码");
				model.addAttribute("formKey", StringUtils.getRandomString(32));
				model.addAttribute("username", user.getUsername());
				return "login";
			}

			// 检查验证码是否输入正确
			String captcha = (String) request.getSession().getAttribute(user.getFormKey());

			if (captcha != null && !captcha.toUpperCase().equals(user.getCaptcha().toUpperCase())) {
				model.addAttribute("loginTips", "验证码错误");
				model.addAttribute("formKey", StringUtils.getRandomString(32));
				model.addAttribute("username", user.getUsername());
				return "login";
			}

			String afterSecretPassword = StringUtils.getMD5(Constant.USER_PASSWORD_SECRET + user.getPassword());
			user.setPassword(afterSecretPassword);
			
			// 60分钟有效期
			subject.getSession().setTimeout(1000 * 60 * 60);
			
			// 身份验证
			subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));

			if (!subject.isAuthenticated()) {
				model.addAttribute("loginTips", "登录错误");
				model.addAttribute("formKey", StringUtils.getRandomString(32));
				model.addAttribute("username", user.getUsername());
				return "login";
			}
			
			// 验证成功后重新查询出User信息，根据用户名获取用户信息
			User authUserInfo = userService.selectByUsername(user.getUsername());

			// 将用户信息在 session 中保存
			request.getSession().setAttribute(Constant.LOGIN_USER, authUserInfo);

			if ("admin".equals(authUserInfo.getType()))
				return "redirect:/rest/page/index";
			else if (Constant.ROLE_SIGN_RES_DEV_PRACTICE.equals(authUserInfo.getType()) || Constant.ROLE_SIGN_RES_DEV_FULL.equals(authUserInfo.getType())) {
				request.getSession().setAttribute("username", managerService.findByUserId(authUserInfo.getId()).getName());
				return "redirect:/rest/um/resource/resource_list";
			}
		} catch (AuthenticationException e) {
			// 身份验证失败
			model.addAttribute("loginTips", "用户名或密码错误");
			model.addAttribute("formKey", StringUtils.getRandomString(32));
			model.addAttribute("username", user.getUsername());
			return "login";
		}
		return "redirect:/rest/page/index";
	}

	/**
	 * 用户登出
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute(Constant.LOGIN_USER);
		Subject subject = SecurityUtils.getSubject(); // 登出操作
		subject.logout();
		return "redirect:/rest/page/tologin";
	}
	
	/**
	 * 修改密码
	 */
	@ResponseBody
	@RequestMapping(value = "/updPwd")
	public Result updPwd(@RequestParam String oldPassword, @RequestParam String newPassword, HttpSession session) {
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		try {
			userService.updatePassword(user.getId(), oldPassword, newPassword);
		} catch (ServiceException e) {
			return ResultFactory.createError(e.getError());
		}
		session.removeAttribute(Constant.LOGIN_USER);
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return ResultFactory.createSuccess();
	}
}

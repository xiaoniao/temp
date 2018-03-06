package com.kunyao.assistant.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.feature.getui.GetuiUtils;
import com.kunyao.assistant.core.feature.socket.SocketServer;

@Controller
@RequestMapping(value = "/test")
public class TestController {
	
	@RequestMapping(value = "/getui")
	public String toLogin(String getuiId) {
		GetuiUtils.main(getuiId);
		return "success";
	}
	
	@RequestMapping(value = "/springmvc")
	public String pringmvc() {
		return "success";
	}

	@RequestMapping(value = "socket")
	@ResponseBody
	public void test(String platform) {
		SocketServer.sendNotify("通知", "您有一条新订单");
	}
}

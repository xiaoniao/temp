package com.kunyao.assistant.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.web.service.OrderService;
import com.kunyao.assistant.web.service.VersionService;

@Controller
@RequestMapping("/version")
public class VersionController {
	
	@Resource
	private VersionService versionService;
	
	@Resource
	private OrderService orderService;
	
	/**
	 * 城市管家
	 * @param platform iOS\android
	 */
	@RequestMapping(value = "city")
	@ResponseBody
	public Map<String, Object> checkCityVersion(String platform) {
		return versionService.findInfo(platform, "city");
	}
	
	/**
	 * 金鹰端
	 * @param platform iOS\android
	 */
	@RequestMapping(value = "cross")
	@ResponseBody
	public Map<String, Object> checkCrossVerion(String platform) {
		return versionService.findInfo(platform, "cross");
	}
}
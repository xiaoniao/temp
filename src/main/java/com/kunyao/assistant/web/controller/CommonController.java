package com.kunyao.assistant.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kunyao.assistant.core.utils.FileUtils;
import com.kunyao.assistant.core.utils.StringUtils;

@Controller
@RequestMapping("/common")
public class CommonController {

	private final String uploadPath = "/picture/";

	/**
	 * 上传图片
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadImage")
	public String uploadImage(@RequestParam(value = "dir") String dir, @RequestParam(value = "imgFile") MultipartFile file) {
		if (file == null) {
			return getError("文件为空");
		}
		String imgPath = FileUtils.springMvcUploadFile(file, uploadPath + dir);
		if (StringUtils.isNull(imgPath)) {
			return getError("创建图片失败");
		}
		return getSuccess(imgPath);
	}

	private String getSuccess(String url) {
		Map<String, Object> obj = new HashMap<>();
		obj.put("error", 0);
		obj.put("url", url);
		return new Gson().toJson(obj);
	}

	private String getError(String message) {
		Map<String, Object> obj = new HashMap<>();
		obj.put("error", 1);
		obj.put("message", message);
		return new Gson().toJson(obj);
	}
}

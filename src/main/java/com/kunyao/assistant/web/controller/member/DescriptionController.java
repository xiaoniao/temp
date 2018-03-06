package com.kunyao.assistant.web.controller.member;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.utils.FileUtils;

@Controller
@RequestMapping("/mc/desc")
public class DescriptionController {
	
	@ResponseBody
	@RequestMapping(value = "/user_agreement")
	public Result userAgreement() {
		String path = FileUtils.getClassPath();
		String file = "/html/userServiceAgreement.html";
		StringBuffer agreement = new StringBuffer();
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path + file), "utf-8"));
			String line;
			while ((line = bufferedReader.readLine()) != null)
				agreement.append(line);
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return ResultFactory.createError(e.getMessage());
		}
		return ResultFactory.createJsonSuccess(agreement);
	}
}

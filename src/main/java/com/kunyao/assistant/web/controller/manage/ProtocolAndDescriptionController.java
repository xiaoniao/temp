package com.kunyao.assistant.web.controller.manage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kunyao.assistant.core.dto.Result;
import com.kunyao.assistant.core.dto.ResultFactory;
import com.kunyao.assistant.core.utils.FileUtils;

@Controller
@RequestMapping("/um/protocolAndDescription")
public class ProtocolAndDescriptionController {
	
	private final String USER_SERVICE_AGREEMENT = "um/user_service_agreement";
	private final String INFORMATION_ENERGY_DESCRIPTION = "um/information_energy_description";
	
	
	@RequestMapping(value = "/toUserServiceAgreement")
	public String toUserServiceAgreement(Model model) throws Exception{
		String roomPath = FileUtils.getClassPath();
		String file = "/html/userServiceAgreement.html";
			
		InputStreamReader read = new InputStreamReader(new FileInputStream(roomPath + file), "utf-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		StringBuffer addString = new StringBuffer();
		while ((lineTxt = bufferedReader.readLine()) != null)
         {
			addString.append(lineTxt);
         }
          bufferedReader.close();
          read.close();
		
		model.addAttribute("string", addString);		
		return USER_SERVICE_AGREEMENT;
	}
	
	@ResponseBody
	@RequestMapping(value = "/save")
	public Result save(String descHtml){
		String roomPath = FileUtils.getClassPath();
		String file = "/html/userServiceAgreement.html";
		
		try {
		
			File f= new File(roomPath + file) ;	
		
			OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			String str = descHtml;		
			oStreamWriter.write(str);		
			oStreamWriter.close();	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultFactory.createSuccess();
	}
	
	@RequestMapping(value = "/toDescription")
	public String toDescription(Model model) throws Exception{
		String roomPath = FileUtils.getClassPath();
		String file = "/html/description.html";
			
		InputStreamReader read = new InputStreamReader(new FileInputStream(roomPath + file), "utf-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		StringBuffer addString = new StringBuffer();
		while ((lineTxt = bufferedReader.readLine()) != null)
         {
			addString.append(lineTxt);
         }
          bufferedReader.close();
          read.close();
		
		model.addAttribute("string", addString);		
		return INFORMATION_ENERGY_DESCRIPTION;
	}
	
	@ResponseBody
	@RequestMapping(value = "/descriptionSave")
	public Result descriptionSave(String descHtml){
		String roomPath = FileUtils.getClassPath();
		String file = "/html/description.html";
		
		try {
		
			File f= new File(roomPath + file) ;	
		
			OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			String str = descHtml;		
			oStreamWriter.write(str);		
			oStreamWriter.close();	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultFactory.createSuccess();
	}
}

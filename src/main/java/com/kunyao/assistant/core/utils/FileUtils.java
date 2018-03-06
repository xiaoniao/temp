package com.kunyao.assistant.core.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class FileUtils {

	private static final String ROOT_PATH = getClassPath();
	
	public static void save(File save, List<String> list) throws IOException {
		if (!save.exists())
			save.createNewFile();
		
		FileWriter fileWriter = new FileWriter(save);
		for (String value : list) {
			fileWriter.write(String.valueOf(value) + "\r\n");
		}
		fileWriter.flush();
		fileWriter.close();
	}
	
	public static String springMvcUploadFile(MultipartFile file, String uploadPath) {
		
		// 获取文件后缀
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));  
		
		// 生成图片文件实际存贮名: 时间戳 + 6位随机串
        String fileName = new Date().getTime() + StringUtils.getRandomString(6) + suffix;
        
        // 得到文件实际保存目录，如果不存在则重新创建
        String savePath = ROOT_PATH + uploadPath;
        File saveFile = new File(savePath);
        
        if(!saveFile.exists())
        	saveFile.mkdirs();
        
        // 定义实际上传文件
        File upFile = new File(ROOT_PATH + uploadPath + fileName);
        
        try {
			file.transferTo(upFile);
			Thumbnails.of(upFile).scale(0.25f).toFile(ROOT_PATH + uploadPath + fileName);;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
        return uploadPath + fileName;
	}
	
	/**
     * 获取tomcat项目根目录
     * 通过得到本类物理路径所在文件夹来获取根目录
     * @return
     */
	public static String getClassPath() { 
		String strClassName = FileUtils.class.getName(); 
		String strPackageName = ""; 
        
		if(FileUtils.class.getPackage() != null) { 
			strPackageName = FileUtils.class.getPackage().getName(); 
		} 
		
		String strClassFileName = ""; 
		if(!"".equals(strPackageName)) {
			strClassFileName = strClassName.substring(strPackageName.length() + 1,strClassName.length()); 
		} else { 
			strClassFileName = strClassName; 
		}
		
		URL url = null;
		url = FileUtils.class.getResource(strClassFileName + ".class");
		
		// file:/E:/Product/JavaWeb/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/ngyishenghuo/WEB-INF/classes/com/baili/ngyishenghuo/core/util/FileUtils.class
		String strURL = url.toString();
		
		// E:/Product/JavaWeb/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/ngyishenghuo/WEB-INF/classes/com/baili/ngyishenghuo/core/util
		strURL = strURL.substring(strURL.indexOf( "/" ) + 1, strURL.lastIndexOf( "/" ));
		
		// E:/Product/JavaWeb/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/ngyishenghuo
		strURL = "/" + strURL.substring(0, strURL.indexOf("/WEB-INF/"));
		
		return strURL; 
    }
}

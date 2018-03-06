package com.kunyao.assistant.core.utils;

import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;  
  
import org.springframework.util.MultiValueMap;  
import org.springframework.web.multipart.MultipartFile;  
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kunyao.assistant.core.entity.Plupload;

  
/** 
 * Plupload是一个上传插件。 
 * 上传原理为单个文件依次发送至服务器. 
 * 上传打文件时可以将其碎片化上传。但是一般情况下，不会这样做， 
 * 所以这里更多的是处理普通文件的批量上传。 
 * 这里主要处理文件上传 
 */  
public class PluploadUtil {  
    private static final int BUF_SIZE = 2 * 1024;  
    /**上传失败响应的成功状态码*/  
    public static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";  
    /**上传失败响应的失败状态码*/  
    public static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";  
      
    /** 
     * 用于Plupload插件的文件上传,自动生成唯一的文件保存名 
     * @param plupload - 存放上传所需参数的bean 
     * @param dir - 保存目标文件目录 
     * @throws IllegalStateException 
     * @throws IOException 
     */  
    public static void upload(Plupload plupload, File dir) throws IllegalStateException, IOException {  
    	//获取后缀名
    	String file = plupload.getName();
    	String prefix = file.substring(file.lastIndexOf(".") + 1);
    	
    	//生成唯一的文件名  
    	String filename = new Date().getTime() + StringUtils.getRandomString(6) + "." +prefix;
    	plupload.setImagePath(filename);
        upload(plupload, dir, filename);  
    }  
    
    
    /** 
     * 用于Plupload插件的文件上传 
     * @param plupload - 存放上传所需参数的bean 
     * @param dir - 保存目标文件目录 
     * @param filename - 保存的文件名 
     * @throws IllegalStateException 
     * @throws IOException 
     */  
    public static void upload(Plupload plupload, File dir, String filename) throws IllegalStateException, IOException {  
        int chunks = plupload.getChunks();  //获取总的碎片数  
        int chunk = plupload.getChunk();    //获取当前碎片(从0开始计数)  
          
          
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();   
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();  
          
        if(map != null) {  
            if (!dir.exists()) dir.mkdirs();    //如果目标文件夹不存在则创建新的文件夹  
              
            //事实上迭代器中只存在一个值,所以只需要返回一个值即可  
            Iterator<String> iter = map.keySet().iterator();  
            while(iter.hasNext()) {  
                String str = (String) iter.next();  
                List<MultipartFile> fileList =  map.get(str);  
                for(MultipartFile multipartFile : fileList) {  
                    //因为只存在一个值,所以最后返回的既是第一个也是最后一个值  
                    plupload.setMultipartFile(multipartFile);  
                      
                    //创建新目标文件  
                    File targetFile = new File(dir.getPath()+ "/" + filename);  
                      
                    //当chunks>1则说明当前传的文件为一块碎片，需要合并  
                    if (chunks > 1) {  
                        //需要创建临时文件名，最后再更改名称  
                        File tempFile = new File(dir.getPath()+ "/" + multipartFile.getName());  
                        //如果chunk==0,则代表第一块碎片,不需要合并  
                        saveUploadFile(multipartFile.getInputStream(), tempFile, chunk == 0 ? false : true);  
                          
                        //上传并合并完成，则将临时名称更改为指定名称  
                        if (chunks - chunk == 1) {  
                            tempFile.renameTo(targetFile);  
                        }  
                          
                    } else {  
                        //否则直接将文件内容拷贝至新文件  
                        multipartFile.transferTo(targetFile);  
                    }  
                }  
            }  
        }  
          
    }  
      
    /** 
     * 保存上传文件，兼合并功能 
     */  
    private static void saveUploadFile(InputStream input, File targetFile, boolean append) throws IOException {  
        OutputStream out = null;  
        try {  
            if (targetFile.exists() && append) {  
                out = new BufferedOutputStream(new FileOutputStream(targetFile, true), BUF_SIZE);  
            } else {  
                out = new BufferedOutputStream(new FileOutputStream(targetFile), BUF_SIZE);  
            }  
              
            byte[] buffer = new byte[BUF_SIZE];  
            int len = 0;  
            //写入文件  
            while ((len = input.read(buffer)) > 0) {  
                out.write(buffer, 0, len);  
            }  
        } catch (IOException e) {  
            throw e;  
        } finally {  
            //关闭输入输出流  
            if (null != input) {  
                try {  
                    input.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (null != out) {  
                try {  
                    out.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
      
    /** 
     * 判断是否全部上传完成 
     * 碎片需合并后才返回真 
     */  
    public static boolean isUploadFinish(Plupload plupload) {  
        return (plupload.getChunks() - plupload.getChunk() == 1);  
    }  
    
    /**
     * 两个数组去重复
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] arrContrast(String[] arr1, String[] arr2){
        List<String> list = new LinkedList<String>();
        for (String str : arr1) {                //处理第一个数组,list里面的值为1,2,3,4
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : arr2) {      //如果第二个数组存在和第一个数组相同的值，就删除
            if(list.contains(str)){
                list.remove(str);
            }
        }
        String[] result = {};   //创建空数组
        return list.toArray(result);    //List to Array
    }
    
    
}  


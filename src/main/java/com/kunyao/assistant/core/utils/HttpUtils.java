package com.kunyao.assistant.core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * Http 工具类
 * 
 * @author GeNing
 * @since 2016.05.12
 * 
 */
public class HttpUtils {
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return String 所代表远程资源的响应结果
	 */
	public static String getByUrlConnection(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);

			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();

			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			connection.setRequestProperty("Host", "app.cityjinying.com");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("Origin", "http://192.168.1.33:8100");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Mobile Safari/537.36");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("DNT", "1");
			connection.setRequestProperty("Referer", "http://192.168.1.33:8100/");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");


			
			// 建立实际的连接
			connection.connect();

			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

		//	result = in.readLine();
			String line = "";
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}

		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			// 使用finally块来关闭输入流
			try {
				if (in != null)
					in.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * HttpClient 发送POST 请求
	 * @param postUrl
	 * @param paramsMap
	 * @param encoding
	 * @return
	 */
	public static String postByHttpClient(String postUrl, Map<String, String> paramsMap, String encoding) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = null;
		CloseableHttpResponse response = null;
		
		try {
			HttpPost method = new HttpPost(postUrl);
			
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, encoding));
			}
			
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			
			if (entity != null)
                responseText = EntityUtils.toString(entity);
        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
            	response.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        return responseText;
	}
}

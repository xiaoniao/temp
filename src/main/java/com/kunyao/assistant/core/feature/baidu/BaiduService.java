package com.kunyao.assistant.core.feature.baidu;


import com.kunyao.assistant.core.entity.baidu.BDLocationResult;
import com.kunyao.assistant.core.utils.HttpUtils;

import net.sf.json.JSONObject;

public class BaiduService {

	/**
	 * 根据坐标获取城市名
	 * @param lng
	 * @param lat
	 * @return
	 */
	public static String getCityByLoc(String latLngs) {
		
		String url = BaiduSettings.GEOLOC_URL
				.replace("{latLngs}", ""+ latLngs)
				.replace("{ak}", BaiduSettings.API_KEY);
		
		String data = HttpUtils.getByUrlConnection(url);
		JSONObject jsonObject = null;
		String cityName = null;
		try {
			jsonObject = JSONObject.fromObject(data);
			cityName = ((JSONObject)((JSONObject)jsonObject.get("result")).get("addressComponent")).getString("city");
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return cityName;
	}
	
	/**
	 * 转换为百度坐标
	 * @param latLngs
	 * @return
	 */
	public static BDLocationResult getBDLocation(String latLngs) {
		
		BDLocationResult location = null;
		
		String [] locations = latLngs.split(",");
		String lng = locations[1];
		String lat = locations[0];
		
		String url = BaiduSettings.CONVERT_LOCATION
			.replace("{longitude}", lng)
			.replace("{latitude}", lat)
			.replace("{ak}", BaiduSettings.API_KEY);
		
		String data = HttpUtils.getByUrlConnection(url);
		
		JSONObject jsonObject = null;
		
		try {
			jsonObject = JSONObject.fromObject(data);
			if (jsonObject.getInt("status") == 0) {
				location = new BDLocationResult();
				location.setLng(Double.parseDouble(JSONObject.fromObject(jsonObject.getJSONArray("result").get(0)).getString("x")));
				location.setLat(Double.parseDouble(JSONObject.fromObject(jsonObject.getJSONArray("result").get(0)).getString("y")));
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return location;
	}
}

package com.kunyao.assistant.core.feature.baidu;

public class BaiduSettings {
	
	public static final String API_KEY = "ojB5cwflUf8iwvxQus8CvdLT2vB6tPCg";
	
	// 根据关键字搜索给定范围(城市)内地点信息
	public static final String PLACE_URL = "http://api.map.baidu.com/place/v2/search?q={query}&region={region}&page_size=20&output=json&ak={ak}";
	
	// 根据当前坐标所搜附近地标信息
	public static final String LANDMARK_URL = "http://api.map.baidu.com/place/v2/search?q={keyWords}&location={lat},{lng}&radius=3000&page_size=20&output=json&filter=sort_name:distance|sort_rule:1&ak={ak}";
	
	// 根据坐标定位城市
	public static final String GEOLOC_URL = "http://api.map.baidu.com/geocoder/v2/?location={latLngs}&output=json&ak={ak}";
	
	public static final String CONVERT_LOCATION = "http://api.map.baidu.com/geoconv/v1/?coords={longitude},{latitude}&from=1&to=5&ak={ak}";
}

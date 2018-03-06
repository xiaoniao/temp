package com.kunyao.assistant.core.dto;

import java.util.ArrayList;
import java.util.List;

import com.kunyao.assistant.core.model.CrossInfo;

public class GroupCrossInfo {

	private String cityName;

	private List<CrossInfo> crossInfos;

	public GroupCrossInfo(String cityName) {
		this.cityName = cityName;
		this.crossInfos = new ArrayList<>();
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<CrossInfo> getCrossInfos() {
		return crossInfos;
	}

	public void setCrossInfos(List<CrossInfo> crossInfos) {
		this.crossInfos = crossInfos;
	}

	@Override
	public String toString() {
		return "group:" + cityName + ", crossInfos=" + crossInfos + "]";
	}
}

package com.kunyao.assistant.core.entity.baidu;

public class BDLocationResult {

	private String name;
	private Double lng;
	private Double lat;
	private String address;

	public BDLocationResult() { }

	public BDLocationResult(String name, double lng, double lat, String address) {
		this.name = name;
		this.lng = lng;
		this.lat = lat;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

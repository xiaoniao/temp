package com.kunyao.assistant.core.entity.wx;

import java.util.HashMap;
import java.util.Map;

public class WxTemplate {

	private String template_id;
	private String touser;
	private String url;
	private String topcolor;
	private Map<String, WXTemplateData> data;

	public WxTemplate() {

	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, WXTemplateData> getData() {
		return data;
	}

	public void setData(Map<String, WXTemplateData> data) {
		this.data = data;
	}

	public static WxTemplate createSample() {
		Map<String, WXTemplateData> data = new HashMap<String, WXTemplateData>();

		WXTemplateData remark = new WXTemplateData();
		remark.setColor("#000000");
		remark.setValue("");
		data.put("remark", remark);

		WxTemplate template = new WxTemplate();
		template.setUrl("");
		template.setTouser("WxOpenId");
		template.setTopcolor("#000000");
		template.setTemplate_id("qHntmsUJK1sfH5rnAOQ2CJCO2dWSSSlShKDsrx4iicg");
		template.setData(data);

		return template;
	}
}

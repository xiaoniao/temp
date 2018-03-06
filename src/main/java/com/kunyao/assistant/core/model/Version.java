package com.kunyao.assistant.core.model;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 版本更新
 */
public class Version implements GenericModel{
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 6825327402375211869L;

	@ModelMeta(fieldType = ModelFieldType.ID)
    private Integer id;

    /**
     * 平台 android | iOS */
    private String platform;

    /**
     * 热更新版本 */
    private Double hotVersionCode;

    /**
     * apk更新版本 */
    private Double apkVersionCode;

    /**
     * 是否apk更新 */
    private String apkUpdate;

    /**
     * 更新链接 */
    private String url;
    
    /**
     * app名称 */
    private String appName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Double getHotVersionCode() {
        return hotVersionCode;
    }

    public void setHotVersionCode(Double hotVersionCode) {
        this.hotVersionCode = hotVersionCode;
    }

    public Double getApkVersionCode() {
        return apkVersionCode;
    }

    public void setApkVersionCode(Double apkVersionCode) {
        this.apkVersionCode = apkVersionCode;
    }

    public String getApkUpdate() {
		return apkUpdate;
	}

	public void setApkUpdate(String apkUpdate) {
		this.apkUpdate = apkUpdate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	@Override
	public String getTablePrefixName() {
		return "t";
	}
}

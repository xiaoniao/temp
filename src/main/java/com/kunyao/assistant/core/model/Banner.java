package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.lang.String;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 广告
 */
public class Banner implements GenericModel{
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -5356466558820574084L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 标题 */
    private String title;

    /**
     * 图片地址 */
    private String image;

    /**
     * 1显示 0不显示 */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String getTablePrefixName() {
		return "t";
	}
}

package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 推送消息
 */
public class Messages implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 4873898904669515463L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 用户id */
    private Integer userId;

    /**
     * 标题 */
    private String title;

    /**
     * 内容 */
    private String content;

    /**
     * 0未读 1已读 */
    private Integer isRead;
    
    /**
     * 创建时间 */
    private Date createTime;

    public Messages() {
    	
	}
    
    public Messages(Integer userId, String title, String content) {
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.isRead = 0;
		this.createTime = new Date();
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String getTablePrefixName() {
		return "t";
	}

	@Override
	public String toString() {
		return "Messages [id=" + id + ", userId=" + userId + ", title=" + title + ", content=" + content + ", isRead="
				+ isRead + ", createTime=" + createTime + "]";
	}
	
}

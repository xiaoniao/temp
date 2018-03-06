package com.kunyao.assistant.core.model;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 分享邀请注册
 */
public class Share implements GenericModel{
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -4889220999432557485L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 分享人 */
    private Integer shareUserId;

    /**
     * 被分享人 可以分享给多人，以逗号分割 */
    private String sharedUserId;

    /**
     * 邀请码 分享网页时追加到网页链接后缀*/
    private String code;

    /**
     * 分享人是否下过单 0未下过 1下过 */
    private Integer isOrder;
    
    /*****************VO********************/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String sourceMobile;				//	分享来源手机号
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String sourceName;					//	分享来源姓氏

    public Share() {
    	
	}
    
    public Share(Integer shareUserId, Integer isOrder) {
		this.shareUserId = shareUserId;
		this.isOrder = isOrder;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Integer shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getSharedUserId() {
        return sharedUserId;
    }

    public void setSharedUserId(String sharedUserId) {
        this.sharedUserId = sharedUserId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(Integer isOrder) {
        this.isOrder = isOrder;
    }

	public String getSourceMobile() {
		return sourceMobile;
	}

	public void setSourceMobile(String sourceMobile) {
		this.sourceMobile = sourceMobile;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Override
	public String getTablePrefixName() {
		return "t";
	}
}

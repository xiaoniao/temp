package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 活动
 */
public class Activity implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -901977810838855633L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 活动名称 */
    private String name;

    /**
     * 活动开始时间 */
    private Date startTime;

    /**
     * 活动结束时间 */
    private Date endTime;

    /**
     * 活动状态 */
    private Integer status;

    /**
     * 创建时间 */
    private Date createTime;
    
    /**
     * token列表 【限制参加次数，参加活动需要附带上此token】*/
    private String tokens;
    
    /**
     * 已使用token */
    private String usedTokens;
    
    /**
     * VO
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String startDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = DateUtils.parseFullTime(startDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = DateUtils.parseFullTime(endDate);
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseFullTime(createDate);
	}

	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	public String getUsedTokens() {
		return usedTokens;
	}

	public void setUsedTokens(String usedTokens) {
		this.usedTokens = usedTokens;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}
}

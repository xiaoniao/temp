package com.kunyao.assistant.core.model;

import java.lang.Integer;
import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 金鹰花费时间
 */
public class CrossTimes implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -2350208310591229708L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 金鹰id */
    private Integer userId;

    /**
     * 日期 */
    private Date time;

    /**
     * 0闲置 1预约 */
    private Integer status;
    
    
    /*****************************************VO**************************************/
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String stayDate;       // 开始时间
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;        // 结束时间
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String specialCharacter;     // 特殊字符处理
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String timeDate;    // 前端显示日期
    
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	@Override
	public String getTablePrefixName() {
		return "u";
	}

	public String getStayDate() {
		return stayDate;
	}

	public void setStayDate(Date stayDate) {
		this.stayDate = DateUtils.parseYMDTime(stayDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = DateUtils.parseYMDTime(endDate);
	}

	public String getSpecialCharacter() {
		return specialCharacter;
	}

	public void setSpecialCharacter(String specialCharacter) {
		this.specialCharacter = specialCharacter;
	}

	public String getTimeDate() {
		return timeDate;
	}

	public void setTimeDate(Date timeDate) {
		this.timeDate = DateUtils.parseYMDTime(timeDate);
	}
}

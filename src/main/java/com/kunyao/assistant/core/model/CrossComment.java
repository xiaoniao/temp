package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 金鹰评价 用户评价金鹰
 */
public class CrossComment implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 4741962068531892316L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 会员id */
    private Integer userId;

    /**
     * 金鹰id */
    private Integer crossUserId;

    /**
     * 星级 根据 精神气质、能力素养、服务态度、行为礼仪计算出平均值*/
    private Double star;
    
    /**
     * 精神气质 */
    private Double star1;
    
    /**
     * 能力素养 */
    private Double star2;
    
    /**
     * 服务态度 */
    private Double star3;
    
    /**
     * 行为礼仪 */
    private Double star4;

    /**
     * 评论内容 */
    private String content;

    /**
     * 创建时间 */
    private Date createTime;

    /**
     * 审核状态 */
    private Integer status;
    
    /*************VO***********/
    
    /** 会员名   */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String name;
    
    /** 会员电话   */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String bankMobile;
    
    /** 会员头像 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String memberHeader;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String workName;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer cityId;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String cityName;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String starDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    
    public CrossComment() {
	}

	/** 评价金鹰构造 */
    public CrossComment(Integer userId, Integer crossUserId, Double star, Double star1, Double star2, Double star3,
			Double star4, String content, Date createTime) {
		this.userId = userId;
		this.crossUserId = crossUserId;
		this.star = star;
		this.star1 = star1;
		this.star2 = star2;
		this.star3 = star3;
		this.star4 = star4;
		this.content = content;
		this.createTime = createTime;
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

    public Integer getCrossUserId() {
        return crossUserId;
    }

    public void setCrossUserId(Integer crossUserId) {
        this.crossUserId = crossUserId;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Double getStar1() {
		return star1;
	}

	public void setStar1(Double star1) {
		this.star1 = star1;
	}

	public Double getStar2() {
		return star2;
	}

	public void setStar2(Double star2) {
		this.star2 = star2;
	}

	public Double getStar3() {
		return star3;
	}

	public void setStar3(Double star3) {
		this.star3 = star3;
	}

	public Double getStar4() {
		return star4;
	}

	public void setStar4(Double star4) {
		this.star4 = star4;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseFullTime(createDate);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBankMobile() {
		return bankMobile;
	}
	
	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}
	
	public String getMemberHeader() {
		return memberHeader;
	}
	
	public void setMemberHeader(String memberHeader) {
		this.memberHeader = memberHeader;
	}
	
	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStarDate() {
		return starDate;
	}

	public void setStarDate(String starDate) {
		this.starDate = starDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public void dto() {
		if (star1 == null) star1 = 0.0;
		if (star2 == null) star2 = 0.0;
		if (star3 == null) star3 = 0.0;
		if (star4 == null) star4 = 0.0;
		this.star = (star1 + star2 + star3 + star4) / 4;
	}
}

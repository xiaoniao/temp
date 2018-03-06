package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 评价用户 金鹰评价用户
 */
public class MemberComment implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 4741962068531892316L;

	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

	/**
	 * 金鹰id
	 */
	private Integer crossUserId;
	
	/**
	 * 会员id
	 */
	private Integer userId;

	/**
	 * 订单id
	 */
	private Integer orderId;
	
	/**
	 * 星级
	 */
	private Double star;

	/**
	 * 文明有礼
	 */
	private Double star1;

	/**
	 * 遵信守诺
	 */
	private Double star2;

	/**
	 * 友好宽容
	 */
	private Double star3;

	/**
	 * 信赖支持
	 */
	private Double star4;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/************* VO ***********/
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
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String bankMobile;
    
	/**
	 * 会员名
	 */
	@ModelMeta(fieldType = ModelFieldType.VO)
	private String name;

	public MemberComment() {
		
	}
	
	public MemberComment(Integer orderId, Integer crossUserId, Integer userId, Double star1, Double star2, 
			Double star3, Double star4,String content) {
		if (star1 == null) star1 = 0.0;
		if (star2 == null) star2 = 0.0;
		if (star3 == null) star3 = 0.0;
		if (star4 == null) star4 = 0.0;
		
		this.orderId = orderId;
		this.crossUserId = crossUserId;
		this.userId = userId;
		this.star1 = star1;
		this.star2 = star2;
		this.star3 = star3;
		this.star4 = star4;
		this.star = (star1 + star2 + star3 + star4) / 4;
		this.content = content;
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

	public Integer getCrossUserId() {
		return crossUserId;
	}

	public void setCrossUserId(Integer crossUserId) {
		this.crossUserId = crossUserId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
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
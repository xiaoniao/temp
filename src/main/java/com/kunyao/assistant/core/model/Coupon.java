package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.CouponEnum;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 优惠券
 */
public class Coupon implements GenericModel {
	
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 4859348344122506290L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 优惠券名称 */
    private String name;

    /**
     * 开始时间 */
    private Date startTime;

    /**
     * 结束时间 */
    private Date endTime;
    
    /**
     * 会员id */
    private Integer userId;
    
    /**
     * 优惠金额 */
    private Double money;

    /**
     * 使用时间 */
    private Date useTime;
    
    /** 来源 0 注册 1 下单 2 活动 */
    private Integer source;
    
    /** 被分享人  [用途：客户端展示我分享给谁]（A是分享人，B是被分享人。A分享给B, A获得优惠券）*/
    private Integer sharedUserId;
    
    /**
     * 0未使用 1已使用 2已失效[客户端展示] */
    private Integer status;
    
    
    /** VO **/
    /**
     * 用户姓名*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String memberName;
    /**
     * 用户帐号*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String mobile;
    /**
     * 到期时间 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer endDay;
    /**
     * 开始使用时间 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String startUseTime;
    /**
     * 结束是使用时间 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endUseTime;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String startDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String useDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String statusStr;
    
    public Coupon() {
    	
	}

	public Coupon(String name, Date startTime, Date endTime, Integer userId, Double money, Integer source) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userId = userId;
		this.money = money;
		this.source = source;
		this.status = CouponEnum.UN_USER.getValue();
	}

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

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStartUseTime() {
		return startUseTime;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public void setStartUseTime(String startUseTime) {
		this.startUseTime = startUseTime;
	}

	public String getEndUseTime() {
		return endUseTime;
	}

	public void setEndUseTime(String endUseTime) {
		this.endUseTime = endUseTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(Date date) {
		this.startDate = DateUtils.parseFullTime(date);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date date) {
		this.endDate = DateUtils.parseFullTime(date);
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(Date date) {
		this.useDate = DateUtils.parseFullTime(date);
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getSharedUserId() {
		return sharedUserId;
	}

	public void setSharedUserId(Integer sharedUserId) {
		this.sharedUserId = sharedUserId;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}
	
	public void dto() {
		if (this.status == 0) {
			this.statusStr = "已发放";
		} else if (this.status == 1) {
			this.statusStr = "已使用";
		}
	}
}

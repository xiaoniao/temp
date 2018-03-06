package com.kunyao.assistant.core.model;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 会员
 */
public class MemberInfo implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -7528726347500295424L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 姓名[4~8个字符，不可修改] */
    private String name;

    /**
     * 性别[0先生 1女士，不可修改] */
    private Integer sex;

    /**
     * 头像 */
    private String header;
    
    /**
     * 工作地 */
    private String address;

    /**
     * 身份证号码 */
    private String idcard;

    /**
     * 身份证正面 */
    private String idcardPic1;

    /**
     * 身份证反面 */
    private String idcardPic2;

    /**
     * 身份证审核状态 0未审核 1审核通过 2审核不通过 */
    private Integer idcardCheckPassStatus;

    /**
     * 持卡人 */
    private String bankPeople;

    /**
     * 银行卡号 */
    private String bankCard;

    /**
     * 卡类型 */
    private String bankCardType;

    /**
     * 手机号 */
    private String bankMobile;

    /**
     * 籍贯 */
    private String nativePlace;

    /**
     * 现居地 */
    private String livePlace;

    /**
     * 行业 */
    private String business;

    /**
     * 公司 */
    private String company;

    /**
     * 职位 */
    private String position;

    /**
     * 出差频率[1-3次/月、4=7次/月、7次以上/月] */
    private String travelFrequency;
    
    /**
     * 喜好 */
    private String habit;
    
    /**
     * 忌讳 */
    private String taboo;
    
    /**
     * 微信唯一标识  */
    private String openId;
    
    /**
     * 个推 */
    private String getuiId;
    
    /**
     * userid */
    private Integer userId;
    
    /*** VO ***/
    /**
     * 联系电话 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String mobile;
    
    /**登录账号*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String userName;
    
    /**注册时间*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    
    /**用户条件查询*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String statusBy;
    
    /**最后登录时间*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String lastLoginDate;
    
    /**状态*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String status;
    
    /**最小注册时间**/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String registrationTimeMin;
    
    /**最大注册时间**/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String registrationTimeMax;
    
    /**最小最后登录时间**/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String lastLoginTimeMin;
    
    /**最大最后登录时间**/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String lastLoginTimeMax;
    
    /** 余额 **/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double balance;
    
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcardPic1() {
        return idcardPic1;
    }

    public void setIdcardPic1(String idcardPic1) {
        this.idcardPic1 = idcardPic1;
    }

    public String getIdcardPic2() {
        return idcardPic2;
    }

    public void setIdcardPic2(String idcardPic2) {
        this.idcardPic2 = idcardPic2;
    }

    public Integer getIdcardCheckPassStatus() {
        return idcardCheckPassStatus;
    }

    public void setIdcardCheckPassStatus(Integer idcardCheckPassStatus) {
        this.idcardCheckPassStatus = idcardCheckPassStatus;
    }

    public String getBankPeople() {
        return bankPeople;
    }

    public void setBankPeople(String bankPeople) {
        this.bankPeople = bankPeople;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getLivePlace() {
        return livePlace;
    }

    public void setLivePlace(String livePlace) {
        this.livePlace = livePlace;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTravelFrequency() {
        return travelFrequency;
    }

    public void setTravelFrequency(String travelFrequency) {
        this.travelFrequency = travelFrequency;
    }
    
	public String getHabit() {
		return habit;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}

	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getGetuiId() {
		return getuiId;
	}

	public void setGetuiId(String getuiId) {
		this.getuiId = getuiId;
	}

	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatusBy() {
		return statusBy;
	}

	public void setStatusBy(String statusBy) {
		this.statusBy = statusBy;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegistrationTimeMin() {
		return registrationTimeMin;
	}

	public void setRegistrationTimeMin(String registrationTimeMin) {
		this.registrationTimeMin = registrationTimeMin;
	}

	public String getRegistrationTimeMax() {
		return registrationTimeMax;
	}

	public void setRegistrationTimeMax(String registrationTimeMax) {
		this.registrationTimeMax = registrationTimeMax;
	}

	public String getLastLoginTimeMin() {
		return lastLoginTimeMin;
	}

	public void setLastLoginTimeMin(String lastLoginTimeMin) {
		this.lastLoginTimeMin = lastLoginTimeMin;
	}

	public String getLastLoginTimeMax() {
		return lastLoginTimeMax;
	}

	public void setLastLoginTimeMax(String lastLoginTimeMax) {
		this.lastLoginTimeMax = lastLoginTimeMax;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}

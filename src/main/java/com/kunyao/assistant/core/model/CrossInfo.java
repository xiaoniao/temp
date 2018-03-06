package com.kunyao.assistant.core.model;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 金鹰
 */
public class CrossInfo implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -711080721744686027L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 工号 */
    private String crossNumber;

    /**
     * 姓名 */
    private String name;
    
    /**
     * 工作名 */
    private String workName;

    /**
     * 工作照 */
    private String pic;

    /**
     * 轮播图 */
    private String banners;

    /**
     * 身高 */
    private Double height;

    /**
     * 籍贯 */
    private String nativePlace;

    /**
     * 属相 */
    private String yearBirth;

    /**
     * 毕业院校 */
    private String school;

    /**
     * 学历 */
    private String education;

    /**
     * 星座 */
    private String constellation;

    /**
     * 工龄（月） */
    private Integer workAge;
    
    /**
     * 省id 
     */
    private Integer provinceId;
    
    /**
     * 城市id */
    private Integer cityId;
    
    /**
     * 个推 */
    private String getuiId;
    
    /**
     * 纬度 */
    private String lat;
    
    /**
     * 经度 */
    private String lng;
    
    /**
     * userid */
    private Integer userId;
    
    /**
     * 籍贯省id
     */
    private Integer nativePlaceProvinceId;
    
    /**
     * 籍贯市id
     */
    private Integer nativePlaceCityId;
    
    /**
     * 联系手机号
     */
    private String contactPhone;
    
    /**
     * 性别
     */
    private Integer sex;
    
    /**
     * 特长
     */
    private String specialty;
    
    // ********************************VO
    /**
     * 评价平均分 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double commentAvg;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double commentStar1;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double commentStar2;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double commentStar3;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double commentStar4;
    
    /**
     * 评价数量 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer commentAmount;
    
    /**
     * 出勤天数 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer bookedDayCount;
    
    /**
     * 可预约天数 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer feeDayCount;
    
    /**
     * 登录手机号码
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String mobile;   
    
    /**
     * 状态值
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer status;
    
    /**
     * 服务城市名称
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String cityName;
    
    /**
     * 加密后的密码
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String encryptionPassword;
    
    /**
     * 综合均分
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double avgStar;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCrossNumber() {
        return crossNumber;
    }

    public void setCrossNumber(String crossNumber) {
        this.crossNumber = crossNumber;
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

	public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBanners() {
        return banners;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }
    
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getYearBirth() {
		return yearBirth;
	}

	public void setYearBirth(String yearBirth) {
		this.yearBirth = yearBirth;
	}

	public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public Integer getWorkAge() {
        return workAge;
    }

    public void setWorkAge(Integer workAge) {
        this.workAge = workAge;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

	public String getGetuiId() {
		return getuiId;
	}

	public void setGetuiId(String getuiId) {
		this.getuiId = getuiId;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getCommentAvg() {
		return commentAvg;
	}

	public void setCommentAvg(Double commentAvg) {
		this.commentAvg = commentAvg;
	}

	public Double getCommentStar1() {
		return commentStar1;
	}

	public void setCommentStar1(Double commentStar1) {
		this.commentStar1 = commentStar1;
	}

	public Double getCommentStar2() {
		return commentStar2;
	}

	public void setCommentStar2(Double commentStar2) {
		this.commentStar2 = commentStar2;
	}

	public Double getCommentStar3() {
		return commentStar3;
	}

	public void setCommentStar3(Double commentStar3) {
		this.commentStar3 = commentStar3;
	}

	public Double getCommentStar4() {
		return commentStar4;
	}

	public void setCommentStar4(Double commentStar4) {
		this.commentStar4 = commentStar4;
	}

	public Integer getCommentAmount() {
		return commentAmount;
	}

	public void setCommentAmount(Integer commentAmount) {
		this.commentAmount = commentAmount;
	}

	public Integer getBookedDayCount() {
		return bookedDayCount;
	}

	public void setBookedDayCount(Integer bookedDayCount) {
		this.bookedDayCount = bookedDayCount;
	}

	public Integer getFeeDayCount() {
		return feeDayCount;
	}

	public void setFeeDayCount(Integer feeDayCount) {
		this.feeDayCount = feeDayCount;
	}

	@Override
	public String getTablePrefixName() {
		return "u";
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getNativePlaceProvinceId() {
		return nativePlaceProvinceId;
	}

	public void setNativePlaceProvinceId(Integer nativePlaceProvinceId) {
		this.nativePlaceProvinceId = nativePlaceProvinceId;
	}

	public Integer getNativePlaceCityId() {
		return nativePlaceCityId;
	}

	public void setNativePlaceCityId(Integer nativePlaceCityId) {
		this.nativePlaceCityId = nativePlaceCityId;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getEncryptionPassword() {
		return encryptionPassword;
	}

	public void setEncryptionPassword(String encryptionPassword) {
		this.encryptionPassword = encryptionPassword;
	}

	public Double getAvgStar() {
		return avgStar;
	}

	public void setAvgStar(Double avgStar) {
		this.avgStar = avgStar;
	}
}

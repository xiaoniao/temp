package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.BaseEnum;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 用户
 */
public class User implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 5512478514760719538L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 用户名 */
    private String username;

    /**
     * 手机号 */
    private String mobile;

    /**
     * 密码 */
    private String password;

    /**
     * 登录成功后分配的 token */
	private String token;
	
	/**
     * 获取Token的时间 */
	private Long getTokenTime;

	/**
     * 关联用户详细信息表 */
    private Integer userinfoId;

    /**
     * 创建时间 */
    private Date createTime;
    
    /**
     * 最后登录时间 */
    private Date lastLoginTime;
    
    /**
     * 用户类型     值为Constant.ROLE_SIGN_权限类型 */
    private String type;

    /**
     * 用户状态 1可用 0禁用 */
    private Integer status;
    
    /*****************************VO***********************/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String lastLoginDate;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String  formKey;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String  captcha;
    
    public User() {
    	
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

    public User(String username, String mobile, String password, Date createTime, String type) {
		this.username = username;
		this.mobile = mobile;
		this.password = password;
		this.createTime = createTime;
		this.type = type;
		this.status = BaseEnum.Status.BASE_STATUS_ENABLE.getValue();
	}

	public Integer getId() {
        return id;
    }

	public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getGetTokenTime() {
		return getTokenTime;
	}

	public void setGetTokenTime(Long getTokenTime) {
		this.getTokenTime = getTokenTime;
	}

	public Integer getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(Integer userinfoId) {
        this.userinfoId = userinfoId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseCNMDTime(createDate);
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date createDate) {
		this.lastLoginDate = DateUtils.parseCNMDTime(createDate);
	}

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}

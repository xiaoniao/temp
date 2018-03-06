package com.kunyao.assistant.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;
import com.kunyao.assistant.core.utils.PrimaryGenerater;
import com.kunyao.assistant.core.utils.StringUtils;

/**
 * 订单  （一个订单{@link Order} 关联多个行程单{@link OrderTravel}，一个行程单关联多个消费单{@link OrderCost}）
 */
public class Order implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = 4765493619289704769L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

	/**
     * 订单编号 */
    private String orderCard;
    
    /**
     * 会员id */
    private Integer userId;

    /**
     * 金鹰id */
    private Integer crossUserId;

    /**
     * 城市id */
    private Integer cityId;

    /**
     * 开始时间 */
    private Date startTime;

    /**
     * 结束时间 */
    private Date endTime;
    
    /**
     * 指派时间 */
    private Date assignTime;
    
    /**
     * 服务费 */
    private Double serviceMoney;
    
    /**
     * 服务编码 */
    private String serviceCode;

    /**
     * 优惠券(多个数值用";"分隔) */
    private String couponId;
    
    /**
     * 支付方式(0预存款支付 1微信支付 2支付宝支付 3银行卡支付 */
    private Integer payMethod;

    /**
     * 支付金额 */
    private Double payMoney;

    /**
     * 是否需要发票 */
    private Integer isNeedInvoice;

    /**
     * 发票抬头 */
    private String invoiceTitle;

    /**
     * 邮寄地址 */
    private String invoiceAddress;

    /**
     * 邮寄收件人 */
    private String invoicePeople;

    /**
     * 邮寄联系方式 */
    private String invoiceMobile;

    /**
     * 联系方式(0金鹰跟我联系 1我主动跟金鹰联系 2自己填写) */
    private Integer contactMethod;

    /**
     * 付款方式(0 自己支付 1金鹰代付[从余额中扣，如果没有余额金鹰会提示充值] */
    private Integer costPayMethod;

    /**
     * 行程说明[接机、送机....] */
    private String travelDescId;
    
    /**
     * 行程要求是否已填写 */
    private Integer travelRequireFlag;
    
    /**
     * 行程要求文字 */
    private String travelRequireText;

    /**
     * 行程要求语音 */
    private String travelRequireVoice;

    /**
     * 订单创建时间 */
    private Date createTime;
    
    /**
     * 完成行程单时间 */
    private Date travelFinishTime;
    
    /**
     * 微信用户唯一标识 */
    private String openId;
    
    /**
     * 订单状态 */
    private Integer status;
    
   /************VO*************/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String createDate; 
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String startDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String assignDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String travelFinishDate;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer crossId;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String crossPic;
    
    /**
     * 金鹰工号
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String crossNumber;
    
    /**
     * 金鹰工作名
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String workName;
    
    /**
     * 金鹰姓名
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String crossName;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String crossContactPhone;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private List<Map<String, Object>> orderTravelList;
    @ModelMeta(fieldType = ModelFieldType.VO)
	private String crossBanners;
    
    /**
     * 会员姓名 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String name;
    /**
     * 会员头像 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String header;
    /**
     * 会员性别 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer sex;
    /**
     * 会员手机号 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String bankMobile;
    /**
     * 会员余额 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double balance;
    /**
     * 订单总花费（不包含服务费） */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double costTotal;
    /**
     * 行程说明  接机,送机*/
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String demand;
    /**
     * 服务城市  */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String cityName;
    
    /**
     * 用户账号  */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String mobile;
    
    /**
     * 用户姓名  */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String memberName;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String orderDate;     // 下单时间
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String travelDate;    // 出行时间
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String logmin;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String logmax;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String stateQuery;     // 后台条件查询
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private List<Integer> orderId;
    
    /**
     * 用于后台订单数据查看
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String appendDataA;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String appendDataB;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String appendDataC;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String appendDataD;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String appendDataE;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String appendDataF;
    
    /**
     * 后台订单状态显示
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String orderStatus;
    
    /**
     * 服务日期 例如[2016/12/07,2016/12/08,2016/12/09] */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private List<String> serviceDays;
    
    /**
     * 用户能量柱分数 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Double totalScore;
    
    /**
     * 消费天数 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer totalDays;
    
    /**
     * 服务列表  */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private List<TravelDesc> descList;
    
    /**
     * 金鹰是否评价过  */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Boolean isCrossCommented;
    
    /**
     * 后台前端用于显示
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer showAndHide;
    
    
    /** 是否需要支付延时费 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer delayNeedPay;
    
    public Order() {
    	
	}
    
    /**
     * 生成订单
     */
    public Order(Integer userId, Integer crossUserId, String startTime, String endTime, Double serviceMoney, Double payMoney, String couponId,
			Integer payMethod, Integer cityId, String openId, Integer costPayMethod, Integer contactMethod, Integer isNeedInvoice) {
    	this.orderCard = PrimaryGenerater.getInstance().generaterNextNumber();
    	this.serviceCode = StringUtils.getRandomString(5).toUpperCase();
    	this.userId = userId;
    	this.crossUserId = crossUserId;
    	this.startTime = DateUtils.parseFullDate(startTime + " 00:00:00");
    	this.endTime = DateUtils.parseFullDate(endTime + " 23:59:59");
    	this.serviceMoney = serviceMoney;
    	this.couponId = couponId;
    	this.payMethod = payMethod;
    	this.payMoney = payMoney;
    	this.cityId = cityId;
    	this.createTime = new Date();
    	this.status = OrderEnum.Status.NEW.getValue();
    	this.openId = openId;
    	this.costPayMethod = costPayMethod;
    	this.contactMethod = contactMethod;
    	this.isNeedInvoice = isNeedInvoice;
	}
    
    /**
     * 完善订单
     */
    public Order(Integer orderId, Integer isNeedInvoice, String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile, 
    		Integer contactMethod, Integer costPayMethod, String travelDescId, String travelRequireText, String travelRequireVoice) {
    	this.id = orderId;
    	this.isNeedInvoice = isNeedInvoice;
    	this.invoiceTitle = invoiceTitle;
    	this.invoiceAddress = invoiceAddress;
    	this.invoicePeople = invoicePeople;
    	this.invoiceMobile = invoiceMobile;
    	this.contactMethod = contactMethod;
    	this.costPayMethod = costPayMethod;
    	this.travelDescId = travelDescId;
    	this.travelRequireText = travelRequireText;
    	this.travelRequireVoice = travelRequireVoice;
    }
    
    
    /**
     * 更换金鹰重新生成订单
     */
    public Order(Integer userId, Integer crossUserId, Integer cityId, Date startTime, Date endTime, 
    		Date assignTime, String serviceCode, Double serviceMoney, String couponId, Integer payMethod, Double payMoney, Integer isNeedInvoice,
    		String invoiceTitle, String invoiceAddress, String invoicePeople, String invoiceMobile , Integer contactMethod, 
    		Integer costPayMethod, String travelDescId, String travelRequireText, String travelRequireVoice, Date createTime,
    		String openId, Integer status) {
    	this.orderCard = PrimaryGenerater.getInstance().generaterNextNumber();
    	this.userId = userId;
    	this.crossUserId = crossUserId;
    	this.cityId = cityId;
    	this.startTime = startTime;
    	this.endTime = endTime;
    	this.assignTime = assignTime;
    	this.serviceMoney = serviceMoney;
    	this.serviceCode = serviceCode;
    	this.couponId = couponId;
    	this.payMethod = payMethod;
    	this.payMoney = payMoney;
    	this.isNeedInvoice = isNeedInvoice;
    	this.invoiceTitle = invoiceTitle;
    	this.invoiceAddress = invoiceAddress;
    	this.invoicePeople = invoicePeople;
    	this.invoiceMobile = invoiceMobile;
    	this.contactMethod = contactMethod;
    	this.costPayMethod = costPayMethod;
    	this.travelDescId = travelDescId;
    	this.travelRequireText = travelRequireText;
    	this.travelRequireVoice = travelRequireVoice;
    	this.createTime = createTime;
    	this.openId = openId;
    	this.status = status;
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCard() {
		return orderCard;
	}

	public void setOrderCard(String orderCard) {
		this.orderCard = orderCard;
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

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
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

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public Double getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(Double serviceMoney) {
		this.serviceMoney = serviceMoney;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Integer getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(Integer isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getInvoicePeople() {
		return invoicePeople;
	}

	public void setInvoicePeople(String invoicePeople) {
		this.invoicePeople = invoicePeople;
	}

	public String getInvoiceMobile() {
		return invoiceMobile;
	}

	public void setInvoiceMobile(String invoiceMobile) {
		this.invoiceMobile = invoiceMobile;
	}

	public Integer getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(Integer contactMethod) {
		this.contactMethod = contactMethod;
	}

	public Integer getCostPayMethod() {
		return costPayMethod;
	}

	public void setCostPayMethod(Integer costPayMethod) {
		this.costPayMethod = costPayMethod;
	}

	public String getTravelDescId() {
		return travelDescId;
	}

	public void setTravelDescId(String travelDescId) {
		this.travelDescId = travelDescId;
	}

	public Integer getTravelRequireFlag() {
		return travelRequireFlag;
	}

	public void setTravelRequireFlag(Integer travelRequireFlag) {
		this.travelRequireFlag = travelRequireFlag;
	}

	public String getTravelRequireText() {
		return travelRequireText;
	}

	public void setTravelRequireText(String travelRequireText) {
		this.travelRequireText = travelRequireText;
	}

	public String getTravelRequireVoice() {
		return travelRequireVoice;
	}

	public void setTravelRequireVoice(String travelRequireVoice) {
		this.travelRequireVoice = travelRequireVoice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTravelFinishTime() {
		return travelFinishTime;
	}

	public void setTravelFinishTime(Date travelFinishTime) {
		this.travelFinishTime = travelFinishTime;
	}
	
	public String getTravelFinishDate() {
		return travelFinishDate;
	}

	public void setTravelFinishDate(Date travelFinishDate) {
		this.travelFinishDate = DateUtils.parseYMDTime(travelFinishDate);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String getTablePrefixName() {
		return "o";
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = DateUtils.parseYMDTime(createDate);
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = DateUtils.parseYMDTime(startDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = DateUtils.parseYMDTime(endDate);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(Double costTotal) {
		this.costTotal = costTotal;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCrossNumber() {
		return crossNumber;
	}

	public void setCrossNumber(String crossNumber) {
		this.crossNumber = crossNumber;
	}

	public String getAppendDataA() {
		return appendDataA;
	}

	public void setAppendDataA(String appendDataA) {
		this.appendDataA = appendDataA;
	}

	public String getAppendDataB() {
		return appendDataB;
	}

	public void setAppendDataB(String appendDataB) {
		this.appendDataB = appendDataB;
	}

	public String getAppendDataC() {
		return appendDataC;
	}

	public void setAppendDataC(String appendDataC) {
		this.appendDataC = appendDataC;
	}

	public String getAppendDataD() {
		return appendDataD;
	}

	public void setAppendDataD(String appendDataD) {
		this.appendDataD = appendDataD;
	}

	public String getAppendDataE() {
		return appendDataE;
	}

	public void setAppendDataE(String appendDataE) {
		this.appendDataE = appendDataE;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		if (status == OrderEnum.Status.NEW.getValue())
			this.orderStatus = OrderEnum.Status.NEW.getText();
		else if (status == OrderEnum.Status.CANCEL.getValue())
			this.orderStatus = OrderEnum.Status.CANCEL.getText();
		else if (status == OrderEnum.Status.WAIT_LOOP.getValue())
			this.orderStatus = OrderEnum.Status.WAIT_LOOP.getText();
		else if (status == OrderEnum.Status.CANCEL_AFTER_PAY.getValue())
			this.orderStatus = OrderEnum.Status.CANCEL_AFTER_PAY.getText();
		else if (status == OrderEnum.Status.FAIL.getValue())
			this.orderStatus = OrderEnum.Status.FAIL.getText();
		else if (status == OrderEnum.Status.WAIT_SERVICE.getValue())
			this.orderStatus = OrderEnum.Status.WAIT_SERVICE.getText();
		else if (status == OrderEnum.Status.SERVING.getValue())
			this.orderStatus = OrderEnum.Status.SERVING.getText();
		else if (status == OrderEnum.Status.WAIT_COMMENT.getValue())
			this.orderStatus = OrderEnum.Status.WAIT_COMMENT.getText();
		else if (status == OrderEnum.Status.FINISH.getValue())
			this.orderStatus = OrderEnum.Status.FINISH.getText();
		else if (status == OrderEnum.Status.EXCHANGE.getValue())
			this.orderStatus = OrderEnum.Status.EXCHANGE.getText();
		else if (status == OrderEnum.Status.CANCEL_BY_ADMIN.getValue())
			this.orderStatus = OrderEnum.Status.CANCEL_BY_ADMIN.getText();
		else if (status == OrderEnum.Status.WAIT_CONFIRM.getValue())
			this.orderStatus = OrderEnum.Status.WAIT_CONFIRM.getText();
		else if (status == OrderEnum.Status.WAIT_SERVICE_CODE.getValue())
			this.orderStatus = OrderEnum.Status.WAIT_SERVICE_CODE.getText();
		else
			this.orderStatus = "未知状态";
	}

	public String getAppendDataF() {
		return appendDataF;
	}

	public void setAppendDataF(String appendDataF) {
		this.appendDataF = appendDataF;
	}

	public String getCrossName() {
		return crossName;
	}

	public void setCrossName(String crossName) {
		this.crossName = crossName;
	}

	public Integer getCrossId() {
		return crossId;
	}

	public void setCrossId(Integer crossId) {
		this.crossId = crossId;
	}

	public String getCrossPic() {
		return crossPic;
	}

	public void setCrossPic(String crossPic) {
		this.crossPic = crossPic;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getCrossContactPhone() {
		return crossContactPhone;
	}

	public void setCrossContactPhone(String crossContactPhone) {
		this.crossContactPhone = crossContactPhone;
	}

	public List<Map<String, Object>> getOrderTravelList() {
		return orderTravelList;
	}

	public void setOrderTravelList(List<Map<String, Object>> orderTravelList) {
		this.orderTravelList = orderTravelList;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(Integer totalDays) {
		this.totalDays = totalDays;
	}

	public List<String> getServiceDays() {
		return serviceDays;
	}

	public void setServiceDays(List<String> serviceDays) {
		this.serviceDays = serviceDays;
	}
	
	public List<String> setServiceDays() {
		Date startTime = this.startTime;
		Date endTime = this.endTime;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		this.serviceDays = new ArrayList<>();
		int count = DateUtils.getBetweenDay(startTime, endTime);
		for(int i = 0; i < count; i++) {
			this.serviceDays.add(simpleDateFormat.format(startTime));
			startTime = new Date(startTime.getTime() + DateUtils.DAY);
		}
		return this.serviceDays;
	}
	
	// 是否有时间冲突
	public boolean isTimeConflict(Order mOrder) {
		for (String day : this.serviceDays) {
			for (String mday : mOrder.getServiceDays()) {
				if (day.equals(mday)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public String getLogmin() {
		return logmin;
	}

	public void setLogmin(String logmin) {
		this.logmin = logmin;
	}

	public String getLogmax() {
		return logmax;
	}

	public void setLogmax(String logmax) {
		this.logmax = logmax;
	}

	public String getStateQuery() {
		return stateQuery;
	}

	public void setStateQuery(String stateQuery) {
		this.stateQuery = stateQuery;
	}

	public List<Integer> getOrderId() {
		return orderId;
	}

	public void setOrderId(List<Integer> orderId) {
		this.orderId = orderId;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = DateUtils.parseFullTime(assignDate);
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public List<TravelDesc> getDescList() {
		return descList;
	}

	public void setDescList(List<TravelDesc> descList) {
		this.descList = descList;
	}

	public Boolean getIsCrossCommented() {
		return isCrossCommented;
	}

	public void setIsCrossCommented(Boolean isCrossCommented) {
		this.isCrossCommented = isCrossCommented;
	}

	public Integer getShowAndHide() {
		return showAndHide;
	}

	public void setShowAndHide(Integer showAndHide) {
		this.showAndHide = showAndHide;
	}

	public Integer getDelayNeedPay() {
		return delayNeedPay;
	}

	public void setDelayNeedPay(Integer delayNeedPay) {
		this.delayNeedPay = delayNeedPay;
	}

	public String getCrossBanners() {
		return crossBanners;
	}

	public void setCrossBanners(String crossBanners) {
		this.crossBanners = crossBanners;
	}

}

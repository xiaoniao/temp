package com.kunyao.assistant.core.model;

import java.util.Date;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.enums.OrderEnum;
import com.kunyao.assistant.core.enums.OrderEnum.Cost;
import com.kunyao.assistant.core.enums.OrderEnum.Status;
import com.kunyao.assistant.core.enums.OrderEnum.Travel;
import com.kunyao.assistant.core.generic.GenericModel;

/**
 * 消费单
 */
public class OrderCost implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -8878163690354133947L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 行程单id */
    private Integer orderTravelId;

    /**
     * 消费项id */
    private Integer costItemId;

    /**
     * 消费金额 */
    private Double money;

    /**
     * 备注 */
    private String remark;

    /**
     * 0初始化添加 1后续添加 2无效 */
    private Integer status;
    
    /** VO **/
    /**
     * 行程名称 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String title;
    /**
     * 消费项目 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String costName;
    /**
     * 开始时间 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Date startTime;
    
    /**
     * 结束时间 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Date endTime;
    
    /**
     * 行程状态 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer travelStatus;
    
    /**
     * 订单id */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer orderId;
    
    /**
     * 订单状态 */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private Integer orderStatus;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String costIcon;
    
    /**
     * 用户名
     */
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String username;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String statusName;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String orderStatusName;
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String travelStatusName;
    
    public OrderCost() {
    	
	}
    
    public OrderCost(Integer orderTravelId, Integer costItemId, Double money, String remark) {
		this.orderTravelId = orderTravelId;
		this.costItemId = costItemId;
		this.money = money;
		this.remark = remark;
		this.status = OrderEnum.Cost.INIT.getValue();
	}
    
    /**
     * 更换金鹰时添加
     * @param orderTravelId
     * @param costItemId
     * @param money
     * @param remark
     * @param status
     */
    public OrderCost(Integer orderTravelId, Integer costItemId, Double money, String remark, Integer status) {
  		this.orderTravelId = orderTravelId;
  		this.costItemId = costItemId;
  		this.money = money;
  		this.remark = remark;
  		this.status = status;
  	}
    
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderTravelId() {
        return orderTravelId;
    }

    public void setOrderTravelId(Integer orderTravelId) {
        this.orderTravelId = orderTravelId;
    }

    public Integer getCostItemId() {
        return costItemId;
    }

    public void setCostItemId(Integer costItemId) {
        this.costItemId = costItemId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public String getTablePrefixName() {
		return "o";
	}

	public Integer getTravelStatus() {
		return travelStatus;
	}

	public void setTravelStatus(Integer travelStatus) {
		this.travelStatus = travelStatus;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCostIcon() {
		return costIcon;
	}

	public void setCostIcon(String costIcon) {
		this.costIcon = costIcon;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getTravelStatusName() {
		return travelStatusName;
	}

	public void setTravelStatusName(String travelStatusName) {
		this.travelStatusName = travelStatusName;
	}

	public void dto() {
		if (this.status != null)
			for (Cost cost : OrderEnum.Cost.values()) {
				if (cost.getValue() == this.status) {
					statusName = cost.getText();
					break;
				}
			}
		if (this.travelStatus != null)
			for (Travel travel : OrderEnum.Travel.values()) {
				if (travel.getValue() == this.travelStatus) {
					this.travelStatusName = travel.getText();
					break;
				}
			}
		if (this.orderStatus != null)
			for (Status orderStatus : OrderEnum.Status.values()) {
				if (orderStatus.getValue() == this.orderStatus) {
					this.orderStatusName = orderStatus.getText();
					break;
				}
			}
	}
}

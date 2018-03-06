package com.kunyao.assistant.core.model;

import java.util.Date;
import java.util.List;

import com.kunyao.assistant.core.annotation.ModelMeta;
import com.kunyao.assistant.core.annotation.ModelMeta.ModelFieldType;
import com.kunyao.assistant.core.generic.GenericModel;
import com.kunyao.assistant.core.utils.DateUtils;

/**
 * 行程单
 */
public class OrderTravel implements GenericModel {
	@ModelMeta(fieldType = ModelFieldType.SERIALVERSION)
	private static final long serialVersionUID = -6262115369518249456L;
	
	@ModelMeta(fieldType = ModelFieldType.ID)
	private Integer id;

    /**
     * 订单id */
    private Integer orderId;

    /**
     * 开始时间 */
    private Date startTime;

    /**
     * 结束时间 */
    private Date endTime;

    /**
     * 行程名称 */
    private String title;

    /**
     * 0未开始 1待确认 2服务完成 3行程无效 */
    private Integer status;
    
    /********************************VO********************************/
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private List<OrderCost> costList;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String startDate;
    
    @ModelMeta(fieldType = ModelFieldType.VO)
    private String endDate;
    
    public OrderTravel() {
    	
	}
    
    public OrderTravel(Integer orderId, Date startTime, Date endTime, String title, Integer status) {
  		this.orderId = orderId;
  		this.startTime = startTime;
  		this.endTime = endTime;
  		this.title = title;
  		this.status = status;
  	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public List<OrderCost> getCostList() {
		return costList;
	}

	public void setCostList(List<OrderCost> costList) {
		this.costList = costList;
	}

	@Override
	public String getTablePrefixName() {
		return "o";
	}

	@Override
	public String toString() {
		return "OrderTravel [id=" + id + ", orderId=" + orderId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", title=" + title + ", status=" + status + "]";
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
	
}

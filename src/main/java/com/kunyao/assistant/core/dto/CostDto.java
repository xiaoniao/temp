package com.kunyao.assistant.core.dto;

import java.util.List;

import com.kunyao.assistant.core.model.OrderCost;

/**
 * 账单
 */
public class CostDto {

	private String date; // 日期
	private Integer travelStatus; // 状态
	private Double travelMoney;
	private Integer orderId;
	private List<CostItemDto> list;

	public CostDto(String date, List<CostItemDto> list) {
		this.date = date;
		this.list = list;
	}
	
	public CostDto(String date, Integer travelStatus, Double travelMoney, Integer orderId, List<CostItemDto> list) {
		this.date = date;
		this.travelStatus = travelStatus;
		this.travelMoney = travelMoney;
		this.orderId = orderId;
		this.list = list;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<CostItemDto> getList() {
		return list;
	}

	public void setList(List<CostItemDto> list) {
		this.list = list;
	}
	
	public Integer getTravelStatus() {
		return travelStatus;
	}

	public void setTravelStatus(Integer travelStatus) {
		this.travelStatus = travelStatus;
	}
	
	public Double getTravelMoney() {
		return travelMoney;
	}

	public void setTravelMoney(Double travelMoney) {
		this.travelMoney = travelMoney;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public static class CostItemDto {
		private String title;
		private Integer travelStatus;
		private String startDate;
		private String endDate;
		private Double costMoney;
		private List<OrderCost> costs;

		public CostItemDto(String title, Integer travelStatus, List<OrderCost> costs) {
			this.title = title;
			this.costs = costs;
		}
		
		public CostItemDto(String title, Double costMoney, String startDate, String endDate, List<OrderCost> costs) {
			this.title = title;
			this.costMoney = costMoney;
			this.startDate = startDate;
			this.endDate = endDate;
			this.costs = costs;
		}
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getTravelStatus() {
			return travelStatus;
		}

		public void setTravelStatus(Integer travelStatus) {
			this.travelStatus = travelStatus;
		}

		public List<OrderCost> getCosts() {
			return costs;
		}

		public void setCosts(List<OrderCost> costs) {
			this.costs = costs;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public Double getCostMoney() {
			return costMoney;
		}

		public void setCostMoney(Double costMoney) {
			this.costMoney = costMoney;
		}
	}

}

package com.res.order.dao;

public class CTable {
	private int tableId;
	private int tableStatus;
	private int taplecapacity;
	private int totalPeople;
	private String checkinTime;
	private String checkOutTime;
	public String getCheckinTime() {
		return checkinTime;
	}
	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	private String costumerName;
	private int customerid;
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public String getCostumerName() {
		return costumerName;
	}
	public void setCostumerName(String costumerName) {
		this.costumerName = costumerName;
	}
	public int getTotalPeople() {
		return totalPeople;
	}
	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}
	public int getTaplecapacity() {
		return taplecapacity;
	}
	public void setTaplecapacity(int taplecapacity) {
		this.taplecapacity = taplecapacity;
	}
	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	public int getTableStatus() {
		return tableStatus;
	}
	public void setTableStatus(int tableStatus) {
		this.tableStatus = tableStatus;
	}
	

}

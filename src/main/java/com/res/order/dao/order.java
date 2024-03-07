package com.res.order.dao;

public class order {
	private int  orderId;
	private int orderQuantity;
	private String orderItemName;
	private int OrderItemPrice;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getOrderItemName() {
		return orderItemName;
	}
	public void setOrderItemName(String orderItemName) {
		this.orderItemName = orderItemName;
	}
	public int getOrderItemPrice() {
		return OrderItemPrice;
	}
	public void setOrderItemPrice(int orderItemPrice) {
		OrderItemPrice = orderItemPrice;
	}
	

}

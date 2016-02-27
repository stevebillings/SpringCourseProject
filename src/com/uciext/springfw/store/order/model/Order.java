package com.uciext.springfw.store.order.model;

import java.util.Date;
import java.util.List;

public class Order {
	private int orderId;
	private Date orderCreated;
	private int totalAmount;
	private int confirmNumber;
	private String user;
	private List<ProductOrder> productOrders;

	public Order() {
		super();
		this.orderId = 0;
		this.orderCreated = new Date();
		this.totalAmount = 0;
		this.confirmNumber = 0;
		this.user = "<not set>";
		this.productOrders = null;
	}

	public Order(int orderId, Date orderCreated, int totalAmount, int confirmNumber, String user,
			List<ProductOrder> productOrders) {
		super();
		this.orderId = orderId;
		this.orderCreated = orderCreated;
		this.totalAmount = totalAmount;
		this.confirmNumber = confirmNumber;
		this.user = user;
		this.productOrders = productOrders;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderCreated() {
		return orderCreated;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public int getConfirmNumber() {
		return confirmNumber;
	}

	public String getUser() {
		return user;
	}

	public List<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setOrderCreated(Date orderCreated) {
		this.orderCreated = orderCreated;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setConfirmNumber(int confirmNumber) {
		this.confirmNumber = confirmNumber;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderCreated=" + orderCreated + ", totalAmount=" + totalAmount
				+ ", confirmNumber=" + confirmNumber + ", user=" + user + "]";
	}

}

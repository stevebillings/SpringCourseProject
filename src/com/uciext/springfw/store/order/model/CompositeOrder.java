package com.uciext.springfw.store.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompositeOrder extends Order {
	private List<ProductOrder> productOrders;

	public CompositeOrder() {
		super(0, new Date(), 0, 0, "<not set>");
		productOrders = new ArrayList<>();
	}

	public CompositeOrder(Order order, List<ProductOrder> productOrders) {
		super(order.getOrderId(), order.getOrderCreated(), order.getTotalAmount(), order.getConfirmNumber(),
				order.getUser());
		this.productOrders = productOrders;
	}

	public List<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public Order getOrder() {
		// TODO this does not seem right, but this whole class is going to go
		// away...
		return new Order(getOrderId(), getOrderCreated(), getTotalAmount(), getConfirmNumber(), getUser());
	}
}

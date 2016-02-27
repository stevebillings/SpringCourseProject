package com.uciext.springfw.store.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompositeOrder extends OrderOld {
	private List<ProductOrder> productOrders;

	public CompositeOrder() {
		super(0, new Date(), 0, 0, "<not set>");
		productOrders = new ArrayList<>();
	}

	public CompositeOrder(OrderOld order, List<ProductOrder> productOrders) {
		super(order.getOrderId(), order.getOrderCreated(), order.getTotalAmount(), order.getConfirmNumber(),
				order.getUser());
		this.productOrders = productOrders;
	}

	public List<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public OrderOld getOrder() {
		// TODO this does not seem right, but this whole class is going to go
		// away...
		return new OrderOld(getOrderId(), getOrderCreated(), getTotalAmount(), getConfirmNumber(), getUser());
	}
}

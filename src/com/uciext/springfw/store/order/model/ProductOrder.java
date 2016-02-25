package com.uciext.springfw.store.order.model;

import com.uciext.springfw.store.catalog.model.Product;

public class ProductOrder {

	private int productOrderId;
	private Order order;
	private Product product;
	private int orderAmount;

	public ProductOrder() {
		this.productOrderId = 0;
		this.order = null;
		this.product = null;
		this.orderAmount = 0;
	}

	public ProductOrder(int productOrderId, Order order, Product product, int orderAmount) {
		super();
		this.productOrderId = productOrderId;
		this.order = order;
		this.product = product;
		this.orderAmount = orderAmount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int order_amount) {
		this.orderAmount = order_amount;
	}

	public int getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(int productOrderId) {
		this.productOrderId = productOrderId;
	}

	@Override
	public String toString() {
		return "ProductOrder [productOrderId=" + productOrderId + ", order=" + order + ", product=" + product
				+ ", orderAmount=" + orderAmount + "]";
	}

}

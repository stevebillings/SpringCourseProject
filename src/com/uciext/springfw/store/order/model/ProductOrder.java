package com.uciext.springfw.store.order.model;

import com.uciext.springfw.store.catalog.model.Product;

public class ProductOrder {

	private int productOrderId;
	private int orderId;
	private Product product;
	private int orderAmount;

	public ProductOrder() {
		this.productOrderId = 0;
		this.orderId = 0;
		this.product = null;
		this.orderAmount = 0;
	}

	public ProductOrder(int productOrderId, int orderId, Product product, int orderAmount) {
		super();
		this.productOrderId = productOrderId;
		this.orderId = orderId;
		this.product = product;
		this.orderAmount = orderAmount;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
		return "ProductOrder [productOrderId=" + productOrderId + ", orderId=" + orderId + ", product=" + product
				+ ", orderAmount=" + orderAmount + "]";
	}

}

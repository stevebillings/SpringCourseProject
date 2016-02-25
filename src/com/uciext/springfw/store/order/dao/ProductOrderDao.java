package com.uciext.springfw.store.order.dao;

import java.util.List;

import com.uciext.springfw.store.order.model.ProductOrder;

public interface ProductOrderDao {
	ProductOrder addProductOrder(ProductOrder productOrder);

	ProductOrder editProductOrder(ProductOrder productOrder);

	void deleteProductOrder(ProductOrder productOrder);

	List<ProductOrder> getProductOrders();

	List<ProductOrder> getProductOrdersByOrderId(int orderId);

	ProductOrder getProductOrder(int productOrderId);
}

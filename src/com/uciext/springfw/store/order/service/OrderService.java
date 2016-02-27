package com.uciext.springfw.store.order.service;

import java.util.List;

import com.uciext.springfw.store.order.model.Order;
import com.uciext.springfw.store.order.model.ProductOrder;

public interface OrderService {

	// Order
	Order addOrder(Order order);

	Order editOrder(Order order);

	void deleteOrder(Order order);

	List<Order> getOrders();

	Order getOrder(int orderId);

	// ProductOrder
	ProductOrder addProductOrder(ProductOrder productOrder);

	ProductOrder editProductOrder(ProductOrder productOrder);

	void deleteProductOrder(ProductOrder productOrder);

	List<ProductOrder> getProductOrders();

	List<ProductOrder> getProductOrdersByOrderId(int orderId);

	ProductOrder getProductOrder(int productOrderId);
}

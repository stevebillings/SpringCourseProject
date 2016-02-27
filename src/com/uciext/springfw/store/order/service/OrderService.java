package com.uciext.springfw.store.order.service;

import java.util.List;

import com.uciext.springfw.store.order.model.OrderOld;
import com.uciext.springfw.store.order.model.ProductOrder;

public interface OrderService {

	// Order
	OrderOld addOrder(OrderOld order);

	OrderOld editOrder(OrderOld order);

	void deleteOrder(OrderOld order);

	List<OrderOld> getOrders();

	OrderOld getOrder(int orderId);

	// ProductOrder
	ProductOrder addProductOrder(ProductOrder productOrder);

	ProductOrder editProductOrder(ProductOrder productOrder);

	void deleteProductOrder(ProductOrder productOrder);

	List<ProductOrder> getProductOrders();

	List<ProductOrder> getProductOrdersByOrderId(int orderId);

	ProductOrder getProductOrder(int productOrderId);
}

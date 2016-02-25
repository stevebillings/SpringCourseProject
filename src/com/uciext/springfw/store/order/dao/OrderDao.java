package com.uciext.springfw.store.order.dao;

import java.util.List;

import com.uciext.springfw.store.order.model.Order;

public interface OrderDao {
	Order addOrder(Order order);

	Order editOrder(Order order);

	void deleteOrder(Order order);

	List<Order> getOrders();

	Order getOrder(int orderId);
}

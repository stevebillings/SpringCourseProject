package com.uciext.springfw.store.order.dao;

import java.util.List;

import com.uciext.springfw.store.order.model.OrderOld;

public interface OrderDao {
	OrderOld addOrder(OrderOld order);

	OrderOld editOrder(OrderOld order);

	void deleteOrder(OrderOld order);

	List<OrderOld> getOrders();

	OrderOld getOrder(int orderId);
}

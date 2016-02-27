package com.uciext.springfw.store.order.dao.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.uciext.springfw.store.common.Util;
import com.uciext.springfw.store.order.dao.OrderDao;
import com.uciext.springfw.store.order.model.Order;
import com.uciext.springfw.store.order.model.ProductOrder;
import com.uciext.springfw.store.order.service.OrderService;

public class JdbcOrderDao implements OrderDao {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// SQL statements
	private static final String SQL_INSERT_ORDER = "INSERT INTO orders (order_id, order_created, total_amount, confirm_number, user) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_ORDER = "update orders set total_amount=?,confirm_number=?,user=? where order_id=?";
	private static final String SQL_DELETE_ORDER = "delete from orders where order_id = ?";
	private static final String SQL_GET_ALL_ORDERS = "select * from orders";
	private static final String SQL_GET_ORDER_BY_ID = "select * from orders where order_id = ?";

	// Datastore Template
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Spring-wired orderService for get the ProductOrders for a given orderId
	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public Order addOrder(Order order) {
		order.setOrderId(Util.getRandomInt());
		logger.info("Inserting order: " + order);
		jdbcTemplate.update(SQL_INSERT_ORDER, order.getOrderId(), order.getOrderCreated(), order.getTotalAmount(),
				order.getConfirmNumber(), order.getUser());
		return order;
	}

	@Override
	public Order editOrder(Order order) {
		logger.info("Updating order: " + order);
		jdbcTemplate.update(SQL_UPDATE_ORDER, order.getTotalAmount(), order.getConfirmNumber(), order.getUser(),
				order.getOrderId());
		return order;
	}

	@Override
	public List<Order> getOrders() {
		logger.info("Getting orders");

		List<Order> orders = new ArrayList<Order>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_ALL_ORDERS);
		for (Map<String, Object> row : rows) {
			Order order = createOrderFromRow(row);
			logger.info("Fetched order: " + order);
			orders.add(order);
		}

		return orders;
	}

	@Override
	public void deleteOrder(Order order) {
		logger.info("Deleting order: " + order);
		jdbcTemplate.update(SQL_DELETE_ORDER, order.getOrderId());
	}

	@Override
	public Order getOrder(int orderId) {
		logger.info("Getting order for ID: " + orderId);

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_ORDER_BY_ID, orderId);
		if (rows.size() == 0) {
			return null;
		}
		Map<String, Object> row = rows.get(0);
		Order order = createOrderFromRow(row);
		logger.info("Fetched order: " + order);

		return order;
	}

	// order_id, order_created, total_amount, confirm_number, user
	private Order createOrderFromRow(Map<String, Object> row) {
		List<ProductOrder> productOrders = orderService.getProductOrdersByOrderId((Integer) row.get("order_id")); // get
		Order order = new Order(Integer.parseInt(String.valueOf(row.get("order_id"))), (Date) row.get("order_created"),
				(Integer) row.get("total_amount"), (Integer) row.get("confirm_number"), (String) row.get("user"),
				productOrders);
		return order;
	}
}

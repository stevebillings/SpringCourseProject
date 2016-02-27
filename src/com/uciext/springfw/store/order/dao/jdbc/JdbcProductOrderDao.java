package com.uciext.springfw.store.order.dao.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;
import com.uciext.springfw.store.common.Util;
import com.uciext.springfw.store.order.dao.ProductOrderDao;
import com.uciext.springfw.store.order.model.ProductOrder;
import com.uciext.springfw.store.order.service.OrderService;

public class JdbcProductOrderDao implements ProductOrderDao {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// SQL statements
	private static final String SQL_INSERT_PRODUCT_ORDER = "INSERT INTO product_orders (product_order_id, order_id, product_id, order_amount) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_PRODUCT_ORDER = "update product_orders set order_id=?, product_id=?, order_amount=? where product_order_id = ?";
	private static final String SQL_DELETE_PRODUCT_ORDER = "delete from product_orders where product_order_id = ?";
	private static final String SQL_GET_ALL_PRODUCT_ORDERS = "select * from product_orders";
	private static final String SQL_GET_PRODUCT_ORDERS_BY_ORDER_ID = "select * from product_orders where order_id = ?";
	private static final String SQL_GET_PRODUCT_ORDER_BY_ID = "select * from product_orders where product_order_id = ?";

	// Datastore Template
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Spring-wired catalogService, orderService for populating Product and
	// Order objects
	private CatalogService catalogService;

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	private OrderService orderService;

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	// product_order_id, order_id, product_id, order_amount
	@Override
	public ProductOrder addProductOrder(ProductOrder productOrder) {
		productOrder.setProductOrderId(Util.getRandomInt());
		logger.info("Inserting productOrder: " + productOrder);
		jdbcTemplate.update(SQL_INSERT_PRODUCT_ORDER, productOrder.getProductOrderId(), productOrder.getOrderId(),
				productOrder.getProduct().getProductId(), productOrder.getOrderAmount());
		return productOrder;
	}

	@Override
	public ProductOrder editProductOrder(ProductOrder productOrder) {
		logger.info("Updating productOrder: " + productOrder);
		jdbcTemplate.update(SQL_UPDATE_PRODUCT_ORDER, productOrder.getOrderId(),
				productOrder.getProduct().getProductId(), productOrder.getOrderAmount(),
				productOrder.getProductOrderId());
		return productOrder;
	}

	@Override
	public List<ProductOrder> getProductOrders() {
		logger.info("Getting productOrders");

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_ALL_PRODUCT_ORDERS);
		List<ProductOrder> productOrders = getProductOrdersFromRows(rows);
		return productOrders;
	}

	@Override
	public List<ProductOrder> getProductOrdersByOrderId(int orderId) {
		logger.info("Getting productOrders for order id " + orderId);

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_PRODUCT_ORDERS_BY_ORDER_ID, orderId);
		List<ProductOrder> productOrders = getProductOrdersFromRows(rows);
		return productOrders;
	}

	private List<ProductOrder> getProductOrdersFromRows(List<Map<String, Object>> rows) {
		List<ProductOrder> productOrders = new ArrayList<ProductOrder>();
		for (Map<String, Object> row : rows) {
			ProductOrder productOrder = createProductOrderFromRow(row);
			logger.info("Fetched productOrder: " + productOrder);
			productOrders.add(productOrder);
		}
		return productOrders;
	}

	@Override
	public void deleteProductOrder(ProductOrder productOrder) {
		logger.info("Deleting productOrder: " + productOrder);
		jdbcTemplate.update(SQL_DELETE_PRODUCT_ORDER, productOrder.getProductOrderId());
	}

	@Override
	public ProductOrder getProductOrder(int productOrderId) {
		logger.info("Getting productOrder for ID: " + productOrderId);

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_PRODUCT_ORDER_BY_ID, productOrderId);
		if (rows.size() == 0) {
			return null;
		}
		Map<String, Object> row = rows.get(0);
		ProductOrder productOrder = createProductOrderFromRow(row);
		logger.info("Fetched productOrder: " + productOrder);

		return productOrder;
	}

	// product_order_id, order_id, product_id, order_amount
	private ProductOrder createProductOrderFromRow(Map<String, Object> row) {
		ProductOrder productOrder = new ProductOrder(Integer.parseInt(String.valueOf(row.get("product_order_id"))),
				(Integer) row.get("order_id"), (Product) catalogService.getProduct((Integer) row.get("product_id")),
				(Integer) row.get("order_amount"));
		return productOrder;
	}

}

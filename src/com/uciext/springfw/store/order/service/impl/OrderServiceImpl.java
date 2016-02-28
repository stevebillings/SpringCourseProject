package com.uciext.springfw.store.order.service.impl;

import java.util.List;

import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;
import com.uciext.springfw.store.order.dao.OrderDao;
import com.uciext.springfw.store.order.dao.ProductOrderDao;
import com.uciext.springfw.store.order.model.Order;
import com.uciext.springfw.store.order.model.ProductOrder;
import com.uciext.springfw.store.order.service.OrderService;

public class OrderServiceImpl implements OrderService {
	// Catalog service (wired)
	private CatalogService catalogService;

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	// DAO objects (wired)
	private OrderDao orderDao;

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	private ProductOrderDao productOrderDao;

	public void setProductOrderDao(ProductOrderDao productOrderDao) {
		this.productOrderDao = productOrderDao;
	}

	// Services: Order

	@Override
	public Order addOrder(Order order) {
		System.out.println("--- addOrder() order=" + order);
		return orderDao.addOrder(order);
	}

	@Override
	public Order editOrder(Order order) {
		System.out.println("--- editOrder() order=" + order);
		return orderDao.editOrder(order);
	}

	@Override
	public void deleteOrder(Order order) {
		System.out.println("--- deleteOrder() order=" + order);
		orderDao.deleteOrder(order);
	}

	@Override
	public List<Order> getOrders() {
		System.out.println("--- getOrders()");
		return orderDao.getOrders();
	}

	@Override
	public Order getOrder(int orderId) {
		System.out.println("--- getOrder() orderId=" + orderId);
		return orderDao.getOrder(orderId);
	}

	/**
	 * Complete the order and return the confirmation # This includes deducting
	 * order quantities from available quantities.
	 */
	@Override
	public int completeOrder(int orderId) {
		Order order = orderDao.getOrder(orderId);
		for (ProductOrder productOrder : order.getProductOrders()) {
			Product product = productOrder.getProduct();
			int newQuantity = product.getAvailableQuantity() - productOrder.getOrderAmount();
			product.setAvailableQuantity(newQuantity);
			catalogService.editProduct(product);
		}
		return orderDao.completeOrder(orderId);
	}

	// Services: ProductOrder

	@Override
	public ProductOrder addProductOrder(ProductOrder productOrder) {
		System.out.println("--- addProductOrder() productOrder=" + productOrder);
		return productOrderDao.addProductOrder(productOrder);
	}

	@Override
	public ProductOrder editProductOrder(ProductOrder productOrder) {
		System.out.println("--- editProductOrder() productOrder=" + productOrder);
		return productOrderDao.editProductOrder(productOrder);
	}

	@Override
	public void deleteProductOrder(ProductOrder productOrder) {
		System.out.println("--- deleteProductOrder() productOrder=" + productOrder);
		productOrderDao.deleteProductOrder(productOrder);
	}

	@Override
	public List<ProductOrder> getProductOrders() {
		System.out.println("--- getProductOrders()");
		return productOrderDao.getProductOrders();
	}

	@Override
	public List<ProductOrder> getProductOrdersByOrderId(int orderId) {
		System.out.println("--- getProductOrdersByOrderId()");
		return productOrderDao.getProductOrdersByOrderId(orderId);
	}

	@Override
	public ProductOrder getProductOrder(int productOrderId) {
		System.out.println("--- getProductOrder() productOrderId=" + productOrderId);
		return productOrderDao.getProductOrder(productOrderId);
	}

}

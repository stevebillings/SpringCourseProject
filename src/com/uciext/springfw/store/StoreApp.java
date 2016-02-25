package com.uciext.springfw.store;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.uciext.springfw.store.catalog.model.Catalog;
import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;
import com.uciext.springfw.store.order.model.Order;
import com.uciext.springfw.store.order.model.ProductOrder;
import com.uciext.springfw.store.order.service.OrderService;

public class StoreApp {

	public static void main(String[] args) throws Exception {
		StoreApp app = new StoreApp();
		app.run();
	}

	public void run() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/StoreConfig.xml");
		CatalogService catalogService = (CatalogService) context.getBean("catalogService");
		OrderService orderService = (OrderService) context.getBean("orderService");

		// If no catalog exists: create one
		System.out.println("\n=== Checking to see if a catalog already exists");
		Catalog catalog;
		List<Catalog> catalogs = catalogService.getCatalogs();
		if (catalogs.size() > 0) {
			System.out.println("\n=== A catalog already exists; Using that");
			catalog = catalogs.get(0); // arbitrarily pick the first (for now)
		} else {
			// There is no catalog yet; create one
			System.out.println("\n=== There are no catalogs yet. Adding the Spring-configured default catalog");
			catalog = (Catalog) context.getBean("defaultCatalog");
			catalogService.addCatalog(catalog);
		}

		// Add the product we'll use to test editProduct
		String originalProductName = "Test Product";
		Product testProduct = new Product(0, catalog, "1001", originalProductName, 1, "items");
		// productId is populated in the product that addProduct() returns
		testProduct = catalogService.addProduct(testProduct);
		System.out.println("\n=== catalogService.addProduct() returned: " + testProduct);

		// Add a second product, which we'll mostly ignore
		Product secondProduct = new Product(0, catalog, "1002", "Ignored Product", 2, "items");
		secondProduct = catalogService.addProduct(secondProduct);

		// Used editProduct() to change some details on testProduct
		String newProductName = "New Product Name";
		int newAvailableQuantity = 0;
		System.out.println("\n=== Changing product name from " + testProduct.getProductName() + " to " + newProductName
				+ " and availableQuantity from " + testProduct.getAvailableQuantity() + " to " + newAvailableQuantity);
		testProduct.setProductName(newProductName);
		testProduct.setAvailableQuantity(newAvailableQuantity);
		testProduct = catalogService.editProduct(testProduct);
		System.out.println("\n=== catalogService.editProduct() returned: " + testProduct);

		// Get product (testProduct) by ID
		System.out.println("\n=== Getting the edited product using its ID: " + testProduct.getProductId());
		Product productFetchedById = catalogService.getProduct(testProduct.getProductId());
		if (!productFetchedById.getSku().equals(testProduct.getSku())
				|| (productFetchedById.getAvailableQuantity() != newAvailableQuantity)) {
			System.out.println("\n=== ERROR: catalogService.getProduct() returned the wrong product");
		}

		// Get Available products; should only be one
		System.out.println("\n=== Getting available products");
		List<Product> availableProducts = catalogService.getAvailableProducts();
		if (availableProducts.size() != 1) {
			System.out.println("\n=== ERROR: catalogService.getAvailableProducts() returned " + availableProducts.size()
					+ " products; expected 1");
		}

		System.out.println("\n=== Testing add, edit, and get of Orders");

		// Add an order
		Order order = new Order(0, new Date(), 1, 1, "Test User");
		orderService.addOrder(order);

		// Change that order
		order.setTotalAmount(2);
		orderService.editOrder(order);

		// Get order by ID
		Order orderFetchedById = orderService.getOrder(order.getOrderId());
		if ((orderFetchedById.getConfirmNumber() != order.getConfirmNumber()
				|| (orderFetchedById.getTotalAmount() != order.getTotalAmount()))) {
			System.out.println("\n=== ERROR: orderService.getOrder() returned the wrong order");
		}

		// Get all orders and verify the #
		List<Order> orders = orderService.getOrders();
		if (orders.size() != 1) {
			System.out
					.println("\n=== ERROR: orderService.getOrders() returned " + orders.size() + " orders; expected 1");
		}

		// ProductOrder
		System.out.println("\n=== Testing add, edit, get, and delete of ProductOrders");
		ProductOrder productOrder = new ProductOrder(0, orderFetchedById, productFetchedById, 3);
		productOrder = orderService.addProductOrder(productOrder);

		List<ProductOrder> productOrders = orderService.getProductOrders();
		if (productOrders.size() != 1) {
			System.out.println("\n=== ERROR: orderService.getProductOrders() returned " + productOrders.size()
					+ " productOrders; expected 1");
		}

		ProductOrder productOrderFetchedById = orderService.getProductOrder(productOrder.getProductOrderId());
		if (productOrderFetchedById.getOrderAmount() != productOrder.getOrderAmount()) {
			System.out.println("\n=== ERROR: orderService.getProductOrder() returned the wrong productOrder");
		}

		orderService.deleteProductOrder(productOrderFetchedById);
		productOrders = orderService.getProductOrders();
		if (productOrders.size() != 0) {
			System.out.println("\n=== ERROR: orderService.deleteProductOrder() didn't leave zero productOrders; left "
					+ orders.size());
		}

		// Delete all productOders, in case there were any leftover from
		// previous tests
		for (ProductOrder productOrderToDelete : productOrders) {
			orderService.deleteProductOrder(productOrderToDelete);
		}

		System.out.println("\n=== Testing delete of Orders");

		// Delete the (only) order, and then verify that there are zero
		orderService.deleteOrder(orderFetchedById);
		orders = orderService.getOrders();
		if (orders.size() != 0) {
			System.out
					.println("\n=== ERROR: orderService.deleteOrder() didn't leave zero orders; left " + orders.size());
		}

		// In case we've left orders behind from previous tests, remove any
		// left-over orders
		for (Order orderToDelete : orders) {
			orderService.deleteOrder(orderToDelete);
		}

		// Get and remove all products
		System.out.println("\n=== Getting and removing all products");
		List<Product> allProducts = catalogService.getProducts();
		if (allProducts.size() != 2) {
			System.out.println("\n=== ERROR: Expected 2 products, but there are " + allProducts.size());
		}
		for (Product productToDelete : allProducts) {
			catalogService.deleteProduct(productToDelete);
		}

		// Make sure there are zero products
		allProducts = catalogService.getProducts();
		if (allProducts.size() > 0) {
			System.out.println("\n=== ERROR: Not all products were deleted; there are " + allProducts.size() + " left");
		} else {
			System.out.println("\n=== All products have been deleted");
		}

	}

}

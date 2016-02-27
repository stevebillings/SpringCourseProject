package com.uciext.springfw.store;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.uciext.springfw.store.catalog.model.Catalog;
import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;
import com.uciext.springfw.store.order.model.OrderOld;
import com.uciext.springfw.store.order.model.ProductOrder;
import com.uciext.springfw.store.order.service.OrderService;

public class StorePopulator {

	public static void main(String[] args) throws Exception {
		StorePopulator app = new StorePopulator();
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

		// Add an order
		OrderOld order = new OrderOld(0, new Date(), 1, 1, "Test User");
		orderService.addOrder(order);

		// ProductOrder
		System.out.println("\n=== Testing add, edit, get, and delete of ProductOrders");
		ProductOrder productOrder = new ProductOrder(0, order.getOrderId(), testProduct, 3);
		productOrder = orderService.addProductOrder(productOrder);

	}

}

package com.uciext.springfw.store.catalog.dao;

import java.util.List;

import com.uciext.springfw.store.catalog.model.Product;

public interface ProductDao {
	Product addProduct(Product product);

	Product editProduct(Product product);

	List<Product> getProducts();

	List<Product> getAvailableProducts();

	void deleteProduct(Product product);

	void deleteProduct(int productId);

	Product getProduct(int productId);
}

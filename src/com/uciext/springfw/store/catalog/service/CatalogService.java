package com.uciext.springfw.store.catalog.service;

import java.util.List;

import com.uciext.springfw.store.catalog.model.Catalog;
import com.uciext.springfw.store.catalog.model.Product;

public interface CatalogService {

	List<Catalog> getCatalogs();

	Catalog getCatalog(int catalogId);

	Catalog addCatalog(Catalog catalog);

	Product addProduct(Product product);

	Product editProduct(Product product);

	void deleteProduct(Product product);

	void deleteProduct(int productId);

	Product getProduct(int productId);

	List<Product> getProducts();

	List<Product> getAvailableProducts();
}

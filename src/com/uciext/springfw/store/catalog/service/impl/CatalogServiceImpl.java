package com.uciext.springfw.store.catalog.service.impl;

import java.util.List;

import com.uciext.springfw.store.catalog.dao.CatalogDao;
import com.uciext.springfw.store.catalog.dao.ProductDao;
import com.uciext.springfw.store.catalog.model.Catalog;
import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;

public class CatalogServiceImpl implements CatalogService {

	// DAO objects (wired)
	private CatalogDao catalogDao;

	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	// Services

	@Override
	public Catalog addCatalog(Catalog catalog) {
		System.out.println("--- addCatalog() catalog=" + catalog);
		return catalogDao.addCatalog(catalog);
	}

	@Override
	public Product addProduct(Product product) {
		System.out.println("--- addProduct() product=" + product);
		return productDao.addProduct(product);
	}

	@Override
	public List<Product> getProducts() {
		System.out.println("--- getProducts()");
		return productDao.getProducts();
	}

	@Override
	public List<Catalog> getCatalogs() {
		System.out.println("--- getCatalogs()");
		return catalogDao.getCatalogs();
	}

	@Override
	public Product editProduct(Product product) {
		System.out.println("--- editProduct() product=" + product);
		return productDao.editProduct(product);
	}

	@Override
	public void deleteProduct(Product product) {
		System.out.println("--- deleteProduct() product=" + product);
		productDao.deleteProduct(product);

	}

	@Override
	public void deleteProduct(int productId) {
		System.out.println("--- deleteProduct() productId=" + productId);
		productDao.deleteProduct(productId);

	}

	@Override
	public List<Product> getAvailableProducts() {
		System.out.println("--- getAvailableProducts()");
		return productDao.getAvailableProducts();
	}

	@Override
	public Product getProduct(int productId) {
		System.out.println("--- getProduct() ID=" + productId);
		return productDao.getProduct(productId);
	}

	@Override
	public Catalog getCatalog(int catalogId) {
		System.out.println("--- getCatalog()");
		return catalogDao.getCatalog(catalogId);
	}

}

package com.uciext.springfw.store.catalog.dao.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.uciext.springfw.store.catalog.dao.ProductDao;
import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;
import com.uciext.springfw.store.common.Util;

public class JdbcProductDao implements ProductDao {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// SQL statements
	private static final String SQL_INSERT_PRODUCT = "INSERT INTO products (product_id, catalog_id, sku, product_name, available_quantity, uom) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_PRODUCT = "update products set sku=?,product_name=?,available_quantity=?,uom=? where product_id = ?";
	private static final String SQL_DELETE_PRODUCT = "delete from products where product_id = ?";
	private static final String SQL_GET_ALL_PRODUCTS = "select * from products";
	private static final String SQL_GET_PRODUCT_BY_ID = "select * from products where product_id = ?";
	// Datastore Template
	private SimpleJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Spring-wired catalogService for populating Catalog objects
	private CatalogService catalogService;

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	// =================================================
	// DB methods

	@Override
	public Product addProduct(Product product) {
		product.setProductId(Util.getRandomInt());
		logger.info("Inserting product: " + product);
		jdbcTemplate.update(SQL_INSERT_PRODUCT, product.getProductId(), product.getCatalog().getCatalogId(),
				product.getSku(), product.getProductName(), product.getAvailableQuantity(), product.getUom());
		return product;
	}

	@Override
	public Product editProduct(Product product) {
		logger.info("Updating product: " + product);
		jdbcTemplate.update(SQL_UPDATE_PRODUCT, product.getSku(), product.getProductName(),
				product.getAvailableQuantity(), product.getUom(), product.getProductId());
		return product;
	}

	@Override
	public List<Product> getProducts() {
		logger.info("Getting products");

		List<Product> products = new ArrayList<Product>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_ALL_PRODUCTS);
		for (Map<String, Object> row : rows) {
			Product product = createProductFromRow(row);
			logger.info("Fetched product: " + product);
			products.add(product);
		}

		return products;
	}

	@Override
	public List<Product> getAvailableProducts() {
		logger.info("Getting products");

		List<Product> products = new ArrayList<Product>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_ALL_PRODUCTS);
		for (Map<String, Object> row : rows) {
			// Creating a Product is expensive (fetches Catalog object), so
			// avoid it (use data from row) if its not necessary
			if (getAvailableQuantityFromRow(row) > 0) {
				Product product = createProductFromRow(row);
				logger.info("Fetched product: " + product);
				products.add(product);
			} else {
				logger.info("Skipping product " + getProductNameFromRow(row) + " because availableQuantity is 0");
			}
		}

		return products;
	}

	@Override
	public void deleteProduct(Product product) {
		logger.info("Deleting product: " + product);
		int productId = product.getProductId();
		removeProduct(productId);
	}

	@Override
	public void deleteProduct(int productId) {
		logger.info("Deleting product with ID: " + productId);
		removeProduct(productId);
	}

	private void removeProduct(int productId) {
		jdbcTemplate.update(SQL_DELETE_PRODUCT, productId);
	}

	@Override
	public Product getProduct(int productId) {
		logger.info("Getting product for ID: " + productId);

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_GET_PRODUCT_BY_ID, productId);
		if (rows.size() == 0) {
			return null;
		}
		Map<String, Object> row = rows.get(0);
		Product product = createProductFromRow(row);
		logger.info("Fetched product: " + product);

		return product;
	}

	private Product createProductFromRow(Map<String, Object> row) {
		Product product = new Product(Integer.parseInt(String.valueOf(row.get("product_id"))),
				catalogService.getCatalog((Integer) row.get("catalog_id")), (String) row.get("sku"),
				getProductNameFromRow(row), getAvailableQuantityFromRow(row), (String) row.get("uom"));
		return product;
	}

	private String getProductNameFromRow(Map<String, Object> row) {
		return (String) row.get("product_name");
	}

	private Integer getAvailableQuantityFromRow(Map<String, Object> row) {
		return (Integer) row.get("available_quantity");
	}

}

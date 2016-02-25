package com.uciext.springfw.store.catalog.model;

import javax.validation.constraints.Size;

public class Product {
	private int productId;
	private Catalog catalog;
	@Size(min = 4, max = 20, message = "SKU must be between 4 and 20 characters long.")
	private String sku;
	@Size(min = 1, max = 20, message = "Product name must be between 1 and 20 characters long.")
	private String productName;
	private int availableQuantity;
	@Size(min = 1, max = 20, message = "Unit of Measure must be between 1 and 20 characters long.")
	private String uom;

	public Product() {
		this.productId = 0;
		this.catalog = null;
		this.sku = "<not set>";
		this.productName = "<not set>";
		this.availableQuantity = 0;
		this.uom = "<not set>";
	}

	public Product(int productId, Catalog catalog, String sku, String productName, int availableQuantity, String uom) {
		this.productId = productId;
		this.catalog = catalog;
		this.sku = sku;
		this.productName = productName;
		this.availableQuantity = availableQuantity;
		this.uom = uom;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", catalog=" + catalog + ", sku=" + sku + ", productName="
				+ productName + ", availableQuantity=" + availableQuantity + ", uom=" + uom + "]";
	}

}

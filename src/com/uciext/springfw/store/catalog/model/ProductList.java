package com.uciext.springfw.store.catalog.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "productList")
public class ProductList {
	private final List<Product> products;

	public ProductList(List<Product> products) {
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

}

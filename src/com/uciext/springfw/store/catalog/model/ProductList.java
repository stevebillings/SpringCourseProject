package com.uciext.springfw.store.catalog.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "productList")
public class ProductList {
	private List<Product> products;

	public ProductList() {
		this.products = new ArrayList<Product>();
	}

	public ProductList(List<Product> products) {
		this.products = products;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}

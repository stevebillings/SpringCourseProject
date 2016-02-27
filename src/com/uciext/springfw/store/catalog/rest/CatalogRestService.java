package com.uciext.springfw.store.catalog.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.model.ProductList;
import com.uciext.springfw.store.catalog.service.CatalogService;

@Controller
@RequestMapping("/")
public class CatalogRestService {

	private CatalogService catalogService;

	@Inject
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	// VIEW LIST OF PRODUCTS

	@RequestMapping(value = "/products", headers = "Accept=*/*", method = RequestMethod.GET)
	public @ResponseBody ProductList getProducts() {
		System.out.println("======= in getProducts (REST)");
		List<Product> products = catalogService.getProducts();

		// Convert
		ProductList productList = new ProductList(products);
		return productList;
	}

}

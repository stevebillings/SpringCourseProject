package com.uciext.springfw.store.catalog.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.model.ProductList;
import com.uciext.springfw.store.catalog.model.QuantityIncrement;
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

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public @ResponseBody ProductList getProducts() {
		System.out.println("======= in getProducts (REST)");
		List<Product> products = catalogService.getProducts();

		// Convert
		ProductList productList = new ProductList(products);
		return productList;
	}

	// ADD QUANTITY TO A PRODUCT

	@RequestMapping(value = "/products/{productId}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addToProductQuantity(@PathVariable int productId,
			@Valid @RequestBody QuantityIncrement quantityIncrement) {
		System.out.println("======= in addToProductQuantity (REST): adding: " + quantityIncrement.getAmount());
		Product product = catalogService.getProduct(productId);
		int quantityBefore = product.getAvailableQuantity();
		product.setAvailableQuantity(quantityBefore + quantityIncrement.getAmount());
		catalogService.editProduct(product);
	}

}

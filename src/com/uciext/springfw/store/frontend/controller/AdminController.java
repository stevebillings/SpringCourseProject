package com.uciext.springfw.store.frontend.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uciext.springfw.store.catalog.model.Catalog;
import com.uciext.springfw.store.catalog.model.Items;
import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private CatalogService catalogService;

	@Inject
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	private Catalog defaultCatalog;

	@Inject
	public void setDefaultCatalog(Catalog defaultCatalog) {
		this.defaultCatalog = defaultCatalog;
	}

	// ===================================================
	// ===================== Admin =======================
	// ===================================================

	// VIEW LIST OF PRODUCTS

	@RequestMapping("/admin")
	public ModelAndView adminHome(Model model) {
		System.out.println("======= in adminHome");
		List<Product> productList = catalogService.getProducts();
		model.addAttribute("productList", productList);
		model.addAttribute("selectedProducts", new Items());
		return new ModelAndView("admin/adminHome");
	}

	// VIEW PRODUCT DETAILS

	@RequestMapping("/view")
	public ModelAndView productView(@RequestParam("productId") int productId, Model model) {
		System.out.println("======= in productView");
		Product product = catalogService.getProduct(productId);
		model.addAttribute("product", product);
		return new ModelAndView("admin/viewProduct");
	}

	// ADD PRODUCT

	// Before showing Add Product Form
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProduct(Model model) {
		System.out.println("======= in addProduct");
		model.addAttribute(new Product(0, getCatalog().getCatalogId(), "", "", 0, ""));
		return "admin/addProduct";
	}

	// After submitting Add Product Form
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductSave(@Valid Product product, BindingResult bindingResult) {
		System.out.println("======= in addProductSave");
		if (bindingResult.hasErrors()) {
			return "admin/addProduct";
		}
		System.out.println("Got product: " + product);
		product.setCatalogId(getCatalog().getCatalogId());
		catalogService.addProduct(product);
		return "redirect:/admin/admin.html";
	}

	// EDIT PRODUCT

	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.GET)
	public String editProduct(@PathVariable int productId, Model model) {
		System.out.println("======= in editProduct");
		Product product = catalogService.getProduct(productId);
		model.addAttribute("product", product);
		return "admin/editProduct";
	}

	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.POST)
	public String editProductSave(@PathVariable int productId, @Valid Product product, BindingResult bindingResult) {
		System.out.println("======= in editProductSave");
		if (bindingResult.hasErrors()) {
			return "admin/editProduct";
		}
		catalogService.editProduct(product);
		return "redirect:/admin/admin.html";
	}

	// DELETE PRODUCTS

	// After submitting Delete from Product List (Admin Home) Form
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteProducts(@ModelAttribute Items selectedProducts) {
		System.out.println("======= in deleteProducts");
		for (String productIdStr : selectedProducts.getItemList()) {
			System.out.println("delete product id=" + productIdStr);
			int productId = Integer.parseInt(productIdStr);
			catalogService.deleteProduct(productId);
		}
		return "redirect:/admin/admin.html";
	}

	private Catalog getCatalog() {
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
			catalog = defaultCatalog; // TODO: cleanup: (Catalog)
										// context.getBean("defaultCatalog");
			catalogService.addCatalog(catalog);
		}
		return catalog;
	}

}

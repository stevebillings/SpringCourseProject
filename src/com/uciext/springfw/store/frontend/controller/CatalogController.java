package com.uciext.springfw.store.frontend.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.uciext.springfw.store.order.model.Order;
import com.uciext.springfw.store.order.model.ProductOrder;
import com.uciext.springfw.store.order.service.OrderService;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
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

	// orderService is only used for buyer operations
	private OrderService orderService;

	@Inject
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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
		return new ModelAndView("catalog/adminHome");
	}

	// VIEW PRODUCT DETAILS

	@RequestMapping("/view")
	public ModelAndView productView(@RequestParam("productId") int productId, Model model) {
		System.out.println("======= in productView");
		Product product = catalogService.getProduct(productId);
		model.addAttribute("product", product);
		return new ModelAndView("catalog/viewProduct");
	}

	// ADD PRODUCT

	// Before showing Add Product Form
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addProduct(Model model) {
		System.out.println("======= in addProduct");
		model.addAttribute(new Product(0, getCatalog(), "", "", 0, ""));
		return "catalog/addProduct";
	}

	// After submitting Add Product Form
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductSave(@Valid Product product, BindingResult bindingResult) {
		System.out.println("======= in addProductSave");
		if (bindingResult.hasErrors()) {
			return "catalog/addProduct";
		}
		System.out.println("Got product: " + product);
		product.setCatalog(getCatalog());
		catalogService.addProduct(product);
		return "redirect:/catalog/admin.html";
	}

	// EDIT PRODUCT

	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.GET)
	public String editProduct(@PathVariable int productId, Model model) {
		System.out.println("======= in editProduct");
		Product product = catalogService.getProduct(productId);
		model.addAttribute("product", product);
		return "catalog/editProduct";
	}

	@RequestMapping(value = "/edit/{productId}", method = RequestMethod.POST)
	public String editProductSave(@PathVariable int productId, @Valid Product product, BindingResult bindingResult) {
		System.out.println("======= in editProductSave");
		if (bindingResult.hasErrors()) {
			return "catalog/editProduct";
		}
		catalogService.editProduct(product);
		return "redirect:/catalog/admin.html";
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
		return "redirect:/catalog/admin.html";
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

	// ===================================================
	// ===================== Buyer =======================
	// ===================================================

	// VIEW LIST OF AVAILABLE PRODUCTS

	@RequestMapping("/shop")
	public ModelAndView buyerHome(Model model) {
		System.out.println("======= in buyerHome");
		List<Product> productList = catalogService.getAvailableProducts();
		model.addAttribute("productList", productList);
		model.addAttribute("selectedProducts", new Items());
		return new ModelAndView("catalog/buyerHome");
	}

	// CREATE ORDER

	// After submitting Buy from Shop (Buyer Home) Form
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	public String buyProducts(@ModelAttribute Items selectedProducts, Model model) {
		System.out.println("======= in buyProducts");
		Order order = new Order(0, new Date(), 0, 0, "Guest", null); // TODO:
		// set
		// real
		// user
		order = orderService.addOrder(order);

		for (String productIdStr : selectedProducts.getItemList()) {
			System.out.println("buy product id=" + productIdStr);
			int productId = Integer.parseInt(productIdStr);
			// TBD: Add product to order
			System.out.println("*** Adding product w ID " + productId + " to order");
			Product product = catalogService.getProduct(productId);
			ProductOrder productOrder = new ProductOrder(0, order.getOrderId(), product, 0);
			orderService.addProductOrder(productOrder);
		}

		return "redirect:/catalog/loadOrder.html?orderId=" + order.getOrderId();
	}

	// LOAD ORDER

	@RequestMapping("/loadOrder")
	public ModelAndView loadOrder(@RequestParam("orderId") int orderId, Model model) {
		System.out.println("======= in loadOrder");
		Order order = orderService.getOrder(orderId);
		System.out.println("*** loaded Order for ID: " + order.getOrderId());
		model.addAttribute("order", order);
		List<ProductOrder> productOrders = orderService.getProductOrdersByOrderId(orderId);
		order.setProductOrders(productOrders);
		model.addAttribute(order);
		return new ModelAndView("catalog/editOrder");
	}

	// COMPLETE ORDER

	// After submitting order
	@RequestMapping(value = "/completeOrder", method = RequestMethod.POST)
	public ModelAndView completeOrder(@ModelAttribute Order order, Model model) {
		System.out.println("======= in completeOrder");
		System.out.println("*** order: " + order);
		List<ProductOrder> fullProductOrders = new ArrayList<>(order.getProductOrders().size());
		for (ProductOrder partialProductOrder : order.getProductOrders()) {
			System.out.println("Order form productOrder: " + partialProductOrder);
			ProductOrder fullProductOrder = orderService.getProductOrder(partialProductOrder.getProductOrderId());
			fullProductOrder.setOrderAmount(partialProductOrder.getOrderAmount());
			System.out.println("Full product order: " + fullProductOrder);
			orderService.editProductOrder(fullProductOrder);
			fullProductOrders.add(fullProductOrder);
		}
		order.setProductOrders(fullProductOrders);
		// TODO: orderService should have a method to complete an order (and
		// auto-generate a confirmation #)
		System.out.println("*** Before setting confirmationNumber: Order: " + order);
		order.setConfirmNumber(999); // TODO temp
		// TODO Set totalAmount
		orderService.editOrder(order);
		model.addAttribute(order);
		return new ModelAndView("catalog/orderConfirmation");
	}

	// VIEW LIST OF AVAILABLE PRODUCTS

	@RequestMapping("/orderConfirmation")
	public ModelAndView orderConfirmation(@ModelAttribute Order order, Model model) {
		System.out.println("======= in orderConfirmation: Order" + order);
		model.addAttribute("order", order);
		return new ModelAndView("catalog/buyerHome");
	}

}

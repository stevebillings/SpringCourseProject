package com.uciext.springfw.store.frontend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uciext.springfw.store.catalog.model.Items;
import com.uciext.springfw.store.catalog.model.Product;
import com.uciext.springfw.store.catalog.service.CatalogService;
import com.uciext.springfw.store.order.model.Order;
import com.uciext.springfw.store.order.model.ProductOrder;
import com.uciext.springfw.store.order.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
	private OrderService orderService;

	@Inject
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	private CatalogService catalogService;

	@Inject
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	// VIEW LIST OF ORDERS

	@RequestMapping("/manageOrders")
	public ModelAndView manageOrders(Model model) {
		System.out.println("======= in manageOrders");
		List<Order> orderList = orderService.getOrders();
		model.addAttribute("orderList", orderList);
		return new ModelAndView("orders/manageOrders");
	}

	// VIEW ORDER

	@RequestMapping("/viewOrder")
	public ModelAndView viewOrder(@RequestParam("orderId") int orderId, Model model) {
		System.out.println("======= in viewOrder");
		Order order = orderService.getOrder(orderId);
		System.out.println("loaded Order for ID: " + order.getOrderId());
		model.addAttribute("order", order);
		List<ProductOrder> productOrders = orderService.getProductOrdersByOrderId(orderId);
		order.setProductOrders(productOrders);
		model.addAttribute(order);
		return new ModelAndView("orders/viewOrder");
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

		return "redirect:/orders/loadOrder.html?orderId=" + order.getOrderId();
	}

	// LOAD ORDER

	@RequestMapping("/loadOrder")
	public ModelAndView loadOrder(@RequestParam("orderId") int orderId, Model model) {
		System.out.println("======= in loadOrder");
		Order order = orderService.getOrder(orderId);
		System.out.println("loaded Order for ID: " + order.getOrderId());
		model.addAttribute("order", order);
		List<ProductOrder> productOrders = orderService.getProductOrdersByOrderId(orderId);
		order.setProductOrders(productOrders);
		model.addAttribute(order);
		return new ModelAndView("orders/editOrder");
	}

	// COMPLETE ORDER

	// After submitting order
	@RequestMapping(params = "complete", value = "/completeOrder", method = RequestMethod.POST)
	public ModelAndView completeOrder(@ModelAttribute Order order, Model model) {
		System.out.println("======= in completeOrder");
		System.out.println("Order: " + order);
		setOrderQuantities(order); // Update order Qtys in DB based on form
									// input
		System.out.println("Order after adjusting quantities: " + order);
		orderService.completeOrder(order.getOrderId());
		order = orderService.getOrder(order.getOrderId()); // Read from DB to be
															// sure
		model.addAttribute(order);
		return new ModelAndView("orders/orderConfirmation");
	}

	// SAVE ORDER FOR LATER

	// After submitting order
	@RequestMapping(params = "save", value = "/completeOrder", method = RequestMethod.POST)
	public String saveOrder(@ModelAttribute Order order, Model model) {
		System.out.println("======= in saveOrder");
		System.out.println("Order: " + order);
		setOrderQuantities(order); // Update order Qtys in DB based on form
									// input
		System.out.println("Order after adjusting quantities: " + order);

		return "redirect:/catalog/shop.html";
	}

	/**
	 * Set order and productOrder quantities in DB based on input from form
	 * 
	 * @param order
	 */
	private void setOrderQuantities(Order order) {
		int totalQuantity = 0;
		List<ProductOrder> fullProductOrders = new ArrayList<>(order.getProductOrders().size());
		for (ProductOrder partialProductOrderFromForm : order.getProductOrders()) {
			System.out.println("Order form productOrder: " + partialProductOrderFromForm);
			ProductOrder fullProductOrderFromDb = orderService
					.getProductOrder(partialProductOrderFromForm.getProductOrderId());
			int productOrderAmount = getAdjustedProductOrderQty(partialProductOrderFromForm, fullProductOrderFromDb);
			fullProductOrderFromDb.setOrderAmount(productOrderAmount);
			totalQuantity += productOrderAmount;
			System.out.println("Full product order: " + fullProductOrderFromDb);
			orderService.editProductOrder(fullProductOrderFromDb);
			fullProductOrders.add(fullProductOrderFromDb);
		}
		order.setProductOrders(fullProductOrders);
		order.setTotalAmount(totalQuantity);
	}

	/**
	 * Limit order to what's actually available
	 */
	private int getAdjustedProductOrderQty(ProductOrder partialProductOrderFromForm,
			ProductOrder fullProductOrderFromDb) {
		int productOrderAmount = partialProductOrderFromForm.getOrderAmount();
		if (productOrderAmount > fullProductOrderFromDb.getProduct().getAvailableQuantity()) {
			productOrderAmount = fullProductOrderFromDb.getProduct().getAvailableQuantity();
		}
		return productOrderAmount;
	}
}

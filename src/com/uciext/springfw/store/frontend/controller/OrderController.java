package com.uciext.springfw.store.frontend.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
}

package com.uciext.springfw.store.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AuthController {

	// Home
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		System.out.println("======= in home");
		ModelAndView model = new ModelAndView();
		model.setViewName("common/home");
		return model;
	}

	// Access Denied (no privilege)
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		System.out.println("======= in accessDenied");

		ModelAndView model = new ModelAndView();
		model.addObject("msg", "You don't have sufficient privilege to access that page.");
		model.setViewName("/common/home");

		return model;
	}
}

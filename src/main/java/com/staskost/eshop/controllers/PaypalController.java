package com.staskost.eshop.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.staskost.eshop.model.User;
import com.staskost.eshop.paypal.PayPalClient;
import com.staskost.eshop.services.UserService;

@RestController
@RequestMapping("/paypal")
public class PaypalController {

	private final PayPalClient payPalClient;

	private UserService userService;

	PaypalController(PayPalClient payPalClient, UserService userService) {
		this.payPalClient = payPalClient;
		this.userService = userService;
	}

	@PostMapping(value = "/make/payment")
	public Map<String, Object> makePayment(@RequestParam("sum") String sum) {
		User user = userService.getAuthenticatedUser();
		Double priceAfterDiscount = userService.getTotalAfterDiscount(Double.valueOf(sum), user);
		return payPalClient.createPayment(String.valueOf(priceAfterDiscount));
	}

	@PostMapping(value = "/complete/payment")
	public Map<String, Object> completePayment(HttpServletRequest request) {
		return payPalClient.completePayment(request);
	}

}

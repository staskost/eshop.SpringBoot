package com.staskost.eshop.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staskost.eshop.model.User;
import com.staskost.eshop.services.ProductService;
import com.staskost.eshop.services.UserService;

@RestController
@RequestMapping("secured/admin")
public class AdminController {

	private UserService userService;

	private ProductService productService;

	public AdminController(UserService userService, ProductService productService) {
		super();
		this.userService = userService;
		this.productService = productService;
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(@RequestHeader(value = "staskost") String alphanumeric) {
		List<User> users = userService.getAll();
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@PostMapping("/set/price/{id}/{price}")
	public ResponseEntity<String> setProductPrice(@PathVariable int id, @PathVariable double price) {
		productService.setProductPrice(price, id);
		return ResponseEntity.status(HttpStatus.OK).body("Price was set to " + price);
	}

}

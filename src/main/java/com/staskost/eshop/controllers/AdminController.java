package com.staskost.eshop.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staskost.eshop.model.Product;
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
	public ResponseEntity<List<User>> getAllUsers(@RequestHeader(value = "staskost") String alphanumeric) {
		List<User> users = userService.getAll();
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@PostMapping("create/product")
	public ResponseEntity<String> createProduct(@RequestHeader(value = "staskost") String alphanumeric,
			@RequestBody Product product) {
		productService.saveProduct(product);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Product  " + product.getName() + " was successfully created.");
	}

	@PutMapping("update/product/{id}")
	public ResponseEntity<String> updateProduct(@RequestHeader(value = "staskost") String alphanumeric,
			@PathVariable int id, @RequestBody Product product) {
		productService.updateProduct(id, product);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Product  " + product.getName() + " was successfully updated.");
	}

	@PostMapping("/set/price/{id}/{price}")
	public ResponseEntity<String> setProductPrice(@RequestHeader(value = "staskost") String alphanumeric,
			@PathVariable int id, @PathVariable double price) {
		productService.setProductPrice(price, id);
		return ResponseEntity.status(HttpStatus.OK).body("Price was set to " + price);
	}

	@PostMapping("/add/product")
	public ResponseEntity<String> addNewProduct(@RequestHeader(value = "staskost") String alphanumeric,
			@RequestBody Product product) {
		productService.addProduct(product);
		return ResponseEntity.status(HttpStatus.OK).body("Product " + product.getName() + " was successfully added.");
	}

	@DeleteMapping("/remove/product/{id}")
	public ResponseEntity<String> removeProduct(@RequestHeader(value = "staskost") String alphanumeric,
			@PathVariable int id) {
		productService.removeProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body("Product was removed successfully.");
	}

	@PostMapping("/add/items/{items}/{id}")
	public ResponseEntity<String> addItems(@RequestHeader(value = "staskost") String alphanumeric,
			@PathVariable int items, @PathVariable int id) {
		productService.addItemToProductCount(items, id);
		return ResponseEntity.status(HttpStatus.OK).body("Items added successfully.");
	}

	@PostMapping("/remove/items/{items}/{id}")
	public ResponseEntity<String> removeItems(@RequestHeader(value = "staskost") String alphanumeric,
			@PathVariable int items, @PathVariable int id) {
		productService.removeItemfromProductCount(items, id);
		return ResponseEntity.status(HttpStatus.OK).body("Items removed successfully.");
	}

}

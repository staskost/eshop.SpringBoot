package com.staskost.eshop.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staskost.eshop.jms.SendTextMessage;
import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.User;
import com.staskost.eshop.services.ProductService;
import com.staskost.eshop.services.UserService;

@RestController
@RequestMapping("guest")
public class GuestController {

	private ProductService productService;

	private UserService userService;
	
	private SendTextMessage sendTextMessageService;

	public GuestController(ProductService productService, UserService userService, SendTextMessage sendTextMessageService) {
		this.productService = productService;
		this.userService = userService;
		this.sendTextMessageService = sendTextMessageService;
	}

	@PostMapping("/save")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		User user2 = userService.findByEmail(user.getEmail());
		if (user2 == null) {
			String password = user.retrievePassword();
			String secret = UUID.randomUUID().toString();
			user.setSecret(secret);
			String sha256hex = DigestUtils.sha256Hex(password + secret);
			user.setPassword(sha256hex);
			userService.save(user);
			return ResponseEntity.status(HttpStatus.OK)
					.body("User " + user.getFirstName() + " " + user.getLastName() + "was successfully registered.");
		} else {
			return new ResponseEntity<>("Email Already exists", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("product/{id}")
		public ResponseEntity<Product> getProductById(@PathVariable int id){
		Product product = productService.getById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping("product/by/price/{price}")
	public ResponseEntity<List<Product>> findProductByPriceBetween(@PathVariable double priceMin,
			@PathVariable double priceMax) {
		List<Product> products = productService.findByPriceBetween(priceMin, priceMax);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("product/by/category/{category}")
	public ResponseEntity<List<Product>> findProductByCategory(@PathVariable String category) {
		List<Product> products = productService.findByCategory(category);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("product/by/name/{name}")
	public ResponseEntity<Product> findProductByName(@PathVariable String name) {
		Product product = productService.findByName(name);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping("product/by/name/starts/{name}")
	public ResponseEntity<List<Product>> findProductByNameStarsWith(@PathVariable String name) {
		List<Product> products = productService.findByNameStartsWith(name);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("product/by/name/like/{name}")
	public ResponseEntity<List<Product>> findProductByNameLike(@PathVariable String name) {
		List<Product> products = productService.findByNameLike(name);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	@GetMapping("hello")
	public String hello() {
		sendTextMessageService.sendTextMessage("hello");
		return "hello";
	}
}

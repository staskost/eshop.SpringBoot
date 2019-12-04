package com.staskost.eshop.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.staskost.eshop.model.Product;
import com.staskost.eshop.model.RequestResult;
import com.staskost.eshop.model.User;
import com.staskost.eshop.services.ProductService;
import com.staskost.eshop.services.UserService;

@RestController
@RequestMapping("guest")
public class GuestController {

	private ProductService productService;

	private UserService userService;

	public GuestController(ProductService productService, UserService userService) {
		this.productService = productService;
		this.userService = userService;
	}

	@PostMapping("save")
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
	public ResponseEntity<Product> getProductById(@PathVariable int id) {
		Product product = productService.getById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping("product/by/category/price/between")
	public ResponseEntity<RequestResult<Product>> findProductByCategoryAndPriceBetween(@PathVariable String category,
			@PathVariable double priceMin, @PathVariable double priceMax, @RequestParam int page,
			@RequestParam int size) {
		int count = productService.findCategoryProductsCount(category);
		List<Product> products = productService.findByCategoryAndPriceBetween(category, priceMin, priceMax,
				PageRequest.of(page, size));
		return new ResponseEntity<>(new RequestResult<Product>(count, products), HttpStatus.OK);
	}

	@GetMapping("product/by/category/{category}")
	public ResponseEntity<RequestResult<Product>> findProductByCategory(@PathVariable String category,
			@RequestParam int page, @RequestParam int size) {
		int count = productService.findCategoryProductsCount(category);
		List<Product> products = productService.findByCategory(category, PageRequest.of(page, size));
		return new ResponseEntity<>(new RequestResult<Product>(count, products), HttpStatus.OK);
	}

	@GetMapping("product/by/category/price/less/{price}")
	public ResponseEntity<RequestResult<Product>> findProductByCategoryAndPriceLessThan(@PathVariable String category,
			@PathVariable double price, @RequestParam int page, @RequestParam int size) {
		int count = productService.findCategoryProductsCountWithPriceLessThan(category, price);
		List<Product> products = productService.findByCategoryAndPriceLessThan(category, price,
				PageRequest.of(page, size));
		return new ResponseEntity<>(new RequestResult<Product>(count, products), HttpStatus.OK);
	}

	@GetMapping("product/by/category/price/greater/{price}")
	public ResponseEntity<RequestResult<Product>> findProductByCategoryAndPriceGreaterThan(
			@PathVariable String category, @PathVariable double price, @RequestParam int page, @RequestParam int size) {
		int count = productService.findCategoryProductsCountWithPriceGreaterThan(category, price);
		List<Product> products = productService.findByCategoryAndPriceGreaterThan(category, price,
				PageRequest.of(page, size));
		return new ResponseEntity<>(new RequestResult<Product>(count, products), HttpStatus.OK);
	}

	@GetMapping("product/by/category/sorted/{category}/{price}")
	public ResponseEntity<RequestResult<Product>> findProductByCategorySortedByPrice(@PathVariable String category,
			@RequestParam int page, @RequestParam int size) {
		int count = productService.findCategoryProductsCount(category);
		List<Product> products = productService.findByCategory(category, PageRequest.of(page, size, Sort.by("price")));
		return new ResponseEntity<>(new RequestResult<Product>(count, products), HttpStatus.OK);
	}

	@GetMapping("product/by/category/sorted//desc/{category}/{price}")
	public ResponseEntity<RequestResult<Product>> findProductByCategorySortedByPriceDesc(@PathVariable String category,
			@RequestParam int page, @RequestParam int size) {
		int count = productService.findCategoryProductsCount(category);
		List<Product> products = productService.findByCategory(category,
				PageRequest.of(page, size, Sort.by("price").descending()));
		return new ResponseEntity<>(new RequestResult<Product>(count, products), HttpStatus.OK);
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

}

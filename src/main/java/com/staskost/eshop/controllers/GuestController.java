package com.staskost.eshop.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staskost.eshop.model.Product;
import com.staskost.eshop.services.ProductService;

@RestController
@RequestMapping("guest")
public class GuestController {

	private ProductService productService;

	public GuestController(ProductService productService) {
		this.productService = productService;
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

}

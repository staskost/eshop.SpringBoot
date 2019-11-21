package com.staskost.eshop.controllers;

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

import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

@RestController
@RequestMapping("/secured/app")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

//	@GetMapping("/by-token")//not used
//	public User getUserFromToken(@RequestHeader(value = "staskost") String alphanumeric) {
//		User user = userService.getUserFromToken(alphanumeric);
//		return user;
//
//	}

	@GetMapping("/logged-user")
	public ResponseEntity<User> getAuthenticatedUser() {
		User user = userService.getAuthenticatedUser();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping
	public String sayHello() {
		return "Hello";
	}
	
	@PostMapping("/checkout/{userId}/{cartId}")
	public ResponseEntity<String> checkout(@PathVariable int userId, @PathVariable int cartId){
		userService.checkout(userId, cartId);
		return ResponseEntity.status(HttpStatus.OK)
				.body("Your transaction was successful.");
		
	}

}

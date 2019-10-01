package com.staskost.eshop.controllers;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

import io.jsonwebtoken.Jwts;

//@Path("/users")
@RestController
@RequestMapping("secured/users")
public class UserController {

	private UserService userService;

	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/save")
	public void registerUser(@RequestBody User user) {
		User user2 = userService.findByEmail(user.getEmail());
		if (user2 == null) {
			String password = user.retrievePassword();
			String secret = UUID.randomUUID().toString();
			user.setSecret(secret);
			String sha256hex = DigestUtils.sha256Hex(password + secret);
			user.setPassword(sha256hex);
			userService.createUser(user);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already Exists");
		}

	}

	private String getEmailFromToken(String token) {
		String claims;
		try {
			claims = Jwts.parser().setSigningKey("123#&*zcvAWEE999").parseClaimsJws(token).getBody().get("sub",
					String.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return claims;
	}

	private String getRoleFromToken(String token) {
		String claims;
		try {
			claims = Jwts.parser().setSigningKey("123#&*zcvAWEE999").parseClaimsJws(token).getBody().get("role",
					String.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return claims;
	}

	@GetMapping("/by-token")
	public User getUserFromToken(@RequestHeader(value = "staskost") String token) {
		String email = getEmailFromToken(token);
		User user = userService.findByEmail(email);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
		}
		return user;

	}

	@GetMapping("/role")
	public String getUserRoleFromToken(@RequestHeader(value = "staskost") String token) {

		String role = getRoleFromToken(token);

		return role;

	}
}

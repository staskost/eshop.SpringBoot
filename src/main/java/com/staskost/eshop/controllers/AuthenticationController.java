package com.staskost.eshop.controllers;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.Login;
import com.staskost.eshop.model.Token;
import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	private UserService userService;

	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<Token> login(@RequestBody Login login) {
		User u = userService.findByEmail(login.getEmail());
		if (u == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Email");

		}
		String password = login.getPassword();
		String secret = u.retrieveSecret();
		String hashedPassword = DigestUtils.sha256Hex(password + secret);
		User user = userService.findByEmailAndPassword(u.getEmail(), hashedPassword);
		if (user != null) {
			return new ResponseEntity<>(new Token(Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
					.claim("role", user.getRole().getName()).signWith(SignatureAlgorithm.HS256, "123#&*zcvAWEE999")
					.compact()), HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Password");

		}
	}

}

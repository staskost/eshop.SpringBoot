package com.staskost.eshop.controllers;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.AuthenticationToken;
import com.staskost.eshop.model.Login;
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
	public ResponseEntity<AuthenticationToken> login(@RequestBody Login login) {
		Map<String, Object> claims = new TreeMap<String, Object>();
		User u = userService.findByEmail(login.getEmail());
		if (u == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Email");
		}
		String password = login.getPassword();
		String secret = u.retrieveSecret();
		String hashedPassword = DigestUtils.sha256Hex(password + secret);
		User user = userService.findByEmailAndPassword(u.getEmail(), hashedPassword);
		
		if (user != null) {
			claims.put("user", user);
			return new ResponseEntity<>(
					new AuthenticationToken(Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 864_000_000L))
							.setClaims(claims)
							.signWith(SignatureAlgorithm.HS256, "123#&*zcvAWEE999").compact()),
					HttpStatus.OK);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Password");

		}
	}

}

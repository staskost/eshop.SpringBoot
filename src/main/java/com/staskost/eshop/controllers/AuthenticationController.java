package com.staskost.eshop.controllers;

import java.util.Date;

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

	private static final String SECRET_KEY = "123#&*zcvAWEE999";

	private UserService userService;

	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationToken> login(@RequestBody Login login) {
		User u = userService.findByEmail(login.getEmail());
		String password = login.getPassword();
		String secret = u.retrieveSecret();
		String hashedPassword = DigestUtils.sha256Hex(password + secret);
		User user = userService.findByEmailAndPassword(u.getEmail(), hashedPassword);
		if (user.getIsActive() == 1) {
			return new ResponseEntity<>(new AuthenticationToken(Jwts.builder().setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 864_000_000L)).setSubject(user.getEmail())
					.claim("id", user.getId()).claim("role", user.getRole())
					.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()), HttpStatus.OK);

		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your account is locked..Try again later");
		}
	}
}

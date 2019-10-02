package com.staskost.eshop.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staskost.eshop.model.User;

import io.jsonwebtoken.Jwts;

public class Authorization {

	public Authorization() {
		super();
	}

	private static Object getClaimFromToken(String token) {
		Object user = Jwts.parser().setSigningKey("123#&*zcvAWEE999").parseClaimsJws(token).getBody().get("user");
		return user;
	}

	public static User getUserFromToken(String token) {
		Object obj = getClaimFromToken(token);
		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.convertValue(obj, User.class);
		if(user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
		}
		return user;
	}

	public static void validateTokenForAdmin(String token) {
		User user = getUserFromToken(token);
		if ((user.getRole().getId() != 2) || (user == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Authorized");
		}
	}
}

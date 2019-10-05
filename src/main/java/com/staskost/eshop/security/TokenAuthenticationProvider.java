package com.staskost.eshop.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.staskost.eshop.model.ApplicationUser;
import com.staskost.eshop.model.AuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component 
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

//	private final static String SECURITY_KEY = "!@asdsadJ780";

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
		String token = authenticationToken.getToken();
		Claims claim = Jwts.parser().setSigningKey("123#&*zcvAWEE999").parseClaimsJws(token).getBody();
		return new ApplicationUser();
	}

}


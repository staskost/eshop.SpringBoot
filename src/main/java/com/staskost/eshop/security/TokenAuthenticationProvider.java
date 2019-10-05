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
	
	private static final String SECRET_KEY = "123#&*zcvAWEE999";

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
		String token = authenticationToken.getToken();
		Claims claim = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		return new ApplicationUser(claim.getSubject());

	}

}

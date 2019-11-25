package com.staskost.eshop.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

public class LoginFailureEventHandler implements ApplicationListener<LoginFailureEvent>{
	
	private UserService userService;

	@Override
	public void onApplicationEvent(LoginFailureEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		updateUserAccount(authentication);
	}
	
	private void updateUserAccount(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();
		User user = userService.findByEmail(email);
		
		if(user != null) {
			user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
			
			if(user.getFailedLoginAttempts() > 5) {
				user.setIsActive(0);
			}
			
			userService.save(user);
		}
	}

}

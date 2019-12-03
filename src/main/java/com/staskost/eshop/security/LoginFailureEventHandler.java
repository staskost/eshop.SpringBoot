package com.staskost.eshop.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import com.staskost.eshop.jms.SendTextMessage;
import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

public class LoginFailureEventHandler implements ApplicationListener<LoginFailureEvent> {

	private UserService userService;

	private SendTextMessage sendTextMessageService;

	public LoginFailureEventHandler(UserService userService, SendTextMessage sendTextMessageService) {
		super();
		this.userService = userService;
		this.sendTextMessageService = sendTextMessageService;
	}

	@Override
	public void onApplicationEvent(LoginFailureEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		updateUserAccount(authentication);
	}

	private void updateUserAccount(Authentication authentication) {
		User user = userService.getAuthenticatedUser();
		if (user != null) {
			user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

			if (user.getFailedLoginAttempts() > 5) {
				user.setIsActive(0);
			}
			sendTextMessageService.sendTextMessage("The account of user " + user.getFirstName() + " "
					+ user.getLastName() + " was locked after 6 failed login attempts");
			userService.save(user);
		}
	}

}

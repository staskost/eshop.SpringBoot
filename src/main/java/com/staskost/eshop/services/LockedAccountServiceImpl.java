package com.staskost.eshop.services;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.staskost.eshop.model.User;

@Service
public class LockedAccountServiceImpl implements LockedAccountService {

	private UserService userService;

	public LockedAccountServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Scheduled(fixedRate = 60000)
	@Override
	public void resetFailedLogins() {

		List<User> users = userService.getAll();
		for (User user : users) {
			if (user.getIsActive() == 0 && user.getFailedLoginAttempts() > 5) {
				user.setIsActive(1);
				user.setFailedLoginAttempts(0);
				userService.save(user);
			}
		}

	}

}

package com.staskost.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

@Aspect
@Configuration
public class AuthorizationAspect {

	private UserService userService;

	public AuthorizationAspect(UserService userService) {
		this.userService = userService;
	}

	@Pointcut("execution(* com.staskost.eshop.controllers.AdminController.*(..))")
	private void pointCutForAdminController() {

	}

	@Before("pointCutForAdminController())")
	private void adviceForValidationOfTokenForAdmin(JoinPoint theJoinPoint) {
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		System.out.println("Method called :" + methodSig);
		validateTokenForAdmin();

	}

	private void validateTokenForAdmin() {
		User user = userService.getAuthenticatedUser();
		if ((!user.getRole().getName().equals("ADMIN")) || (user == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Authorized");
		}
	}
}

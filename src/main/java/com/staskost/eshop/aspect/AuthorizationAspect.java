package com.staskost.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.staskost.eshop.model.User;
import com.staskost.eshop.services.UserService;

@Aspect
@Configuration
public class AuthorizationAspect {

	@Autowired
	UserService userService;

	@Pointcut("execution(* com.staskost.eshop.controllers.AdminController.*(..))")
	private void pointCutForAdminController() {

	}

	@Before("pointCutForAdminController())")
	private void adviceForValidationOfTokenForAdmin(JoinPoint theJoinPoint) {
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		System.out.println("Method called :" + methodSig);
		String[] parameterNames = methodSig.getParameterNames();
		Object[] arguments = theJoinPoint.getArgs();
		for (int i = 0; i < parameterNames.length; i++) {
			if (parameterNames[i].equals("alphanumeric")) {
				String alphanumeric = (String) arguments[i];
				validateTokenForAdmin(alphanumeric);
			}
		}
	}

	private void validateTokenForAdmin(String token) {
		User user = userService.getUserFromToken(token);
		if ((user.getRole().getId() != 2) || (user == null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Authorized");
		}
	}
}

package com.staskost.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import com.staskost.eshop.security.Authorization;

@Aspect
@Configuration
public class AuthorizationAspect {
	
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
				Authorization.validateTokenForAdmin(alphanumeric);
			}
		}
	}
}

package com.staskost.eshop.security;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class LoginFailureEventPublisher implements ApplicationEventPublisherAware{
	
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publish(LoginFailureEvent event) {
		this.applicationEventPublisher.publishEvent(event);
	}

}

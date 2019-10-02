package com.staskost.eshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.staskost.eshop.security.JwtFilter;

@Configuration
public class JwtConfig {
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Bean
	public FilterRegistrationBean<JwtFilter> filterRegistrationBean(){
		FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(jwtFilter);
		filterRegistrationBean.addUrlPatterns("/secured/*");
		return filterRegistrationBean;
		
	}

}

package com.staskost.eshop.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.staskost.eshop.security.TokenAuthenticationFilter;
import com.staskost.eshop.security.TokenAuthenticationProvider;
import com.staskost.eshop.security.TokenAuthenticationSuccessHandler;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@Configuration
@EnableScheduling
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private TokenAuthenticationProvider tokenAuthenticationProvider;

	public SecurityConfig(TokenAuthenticationProvider tokenAuthenticationProvider) {
		this.tokenAuthenticationProvider = tokenAuthenticationProvider;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(tokenAuthenticationProvider);
	}

	@Bean
	public TokenAuthenticationFilter authenticationTokenFilter() throws Exception {
		TokenAuthenticationFilter filter = new TokenAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationSuccessHandler(new TokenAuthenticationSuccessHandler());
		return filter;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/secured/**").authenticated().and().exceptionHandling()
				.authenticationEntryPoint((request, response, exception) -> response
						.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401 - UNAUTHORIZED"))
				.and().addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}

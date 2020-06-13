//package com.longbridge.greendemo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//	private static final String[] AUTH_WHITELIST = {
//			// -- swagger ui
//			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
//			"/configuration/security", "/swagger-ui.html", "/webjars/**","/authenticate","/hello"
//			// other public endpoints of your API may be appended to this array
//	};
//
//
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
////		httpSecurity
////	    .httpBasic().disable()
////        .csrf().disable();
//		
//		httpSecurity
//		.authorizeRequests()
//		.antMatchers(AUTH_WHITELIST).permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.httpBasic().disable()
//	    .csrf().disable();
//	}
//	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//				.withUser("user").password("password").roles("USER");
//	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	};
//
//
//}
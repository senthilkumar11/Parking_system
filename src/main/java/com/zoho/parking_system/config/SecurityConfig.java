//package com.zoho.parking_system.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().withUser("admin")
//				.password("admin").roles("USER");
//	}
//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//	    return super.authenticationManagerBean();
//	}
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//	
//		http.csrf().disable().authorizeRequests()
//	     .antMatchers("/**").authenticated().and().httpBasic();
////		http.authorizeHttpRequests().antMatchers("/**").permitAll().anyRequest()
////		
////		.authenticated().and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
//	}
//	@Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/healthcheck","/login");
//    }
//}
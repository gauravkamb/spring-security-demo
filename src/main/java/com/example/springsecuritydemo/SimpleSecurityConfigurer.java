package com.example.springsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springsecuritydemo.jpa.jwt.filter.MyJwtAuthFilter;


@EnableWebSecurity
public class SimpleSecurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private MyJwtAuthFilter	 jwtRequestFilter;

	
	// does authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	// does authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//		http.authorizeRequests()
		//		.antMatchers("/admin").hasRole("ADMIN")
		//		.antMatchers("/user").hasAnyRole("USER", "ADMIN")
		//		.antMatchers("/").permitAll()
		//		.antMatchers("/h2-console/**").permitAll()
		//		.and().formLogin();

		http.csrf().disable()
		.authorizeRequests().antMatchers("/authenticate").permitAll().
		anyRequest().authenticated().and().
		exceptionHandling().and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

}

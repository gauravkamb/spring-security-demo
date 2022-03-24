package com.example.springsecuritydemo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SimpleSecurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource datasource;
	
	// does authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(datasource);
//		.withDefaultSchema()
//		.withUser(
//				User.withUsername("user")
//				.password("pass")
//				.roles("USER")
//		)
//		.withUser(
//				User.withUsername("admin")
//				.password("pass")
//				.roles("ADMIN")
//		);
		
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	// does authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/user").hasAnyRole("USER", "ADMIN")
		.antMatchers("/").permitAll()
		.antMatchers("/h2-console/**").permitAll()
		.and().formLogin();
		
		http.csrf().ignoringAntMatchers("/h2-console/**");
	}

}

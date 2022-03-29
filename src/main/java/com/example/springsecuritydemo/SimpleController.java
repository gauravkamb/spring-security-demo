package com.example.springsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecuritydemo.jpa.MyUserDetailService;
import com.example.springsecuritydemo.jpa.jwt.models.AuthenticationRequest;
import com.example.springsecuritydemo.jpa.jwt.models.AuthenticationResponse;
import com.example.springsecuritydemo.jpa.jwt.util.JwtUtil;

@RestController
public class SimpleController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailService userDetails;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	@RequestMapping("/")
	public String returnHelloMessage() {
		return "<h1>Welcome</h1>";
	}
	
	@RequestMapping("/user")
	public String returnUser() {
		return "<h1>Welcome...User</h1>";
	}
	
	@RequestMapping("/admin")
	public String returnAdmin() {
		return "<h1>Welcome...Admin</h1>";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		Authentication auth = null;
		try {
			auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		
		UserDetails myUserDetails = (UserDetails) auth.getPrincipal();
		
		final String jwt = jwtTokenUtil.generateToken(myUserDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}

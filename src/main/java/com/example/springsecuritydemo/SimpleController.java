package com.example.springsecuritydemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
	
	
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

}

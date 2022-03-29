package com.example.springsecuritydemo.jpa.jwt.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
	
	private String jwt;

}

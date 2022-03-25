package com.example.springsecuritydemo.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springsecuritydemo.jpa.model.MyUserDetails;
import com.example.springsecuritydemo.jpa.model.User;

@Service
public class MyUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userRepo = userRepository.findByUserName(username);
		userRepo.orElseThrow(() -> new UsernameNotFoundException("Not found" + username));
		return userRepo.map(MyUserDetails::new).get();
	}

}

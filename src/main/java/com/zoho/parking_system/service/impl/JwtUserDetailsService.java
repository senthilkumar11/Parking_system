package com.zoho.parking_system.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zoho.parking_system.model.User;
import com.zoho.parking_system.repo.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> users=userRepository.findByUsername(username);
		if (users.isPresent()) {
			return UserDetailsImpl.build(users.get());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
package com.zoho.parking_system.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoho.parking_system.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
		Optional<User> findByUsername(String username);
}

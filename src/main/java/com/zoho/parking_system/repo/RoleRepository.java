package com.zoho.parking_system.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoho.parking_system.model.ERole;
import com.zoho.parking_system.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	List<Role> findByName(ERole name);

}

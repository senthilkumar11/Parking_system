package com.zoho.parking_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoho.parking_system.model.Fee;
import com.zoho.parking_system.model.VehicleType;

public interface FeeRepository extends JpaRepository<Fee, VehicleType> {

}

package com.zoho.parking_system.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zoho.parking_system.model.ParkingLot;
import com.zoho.parking_system.request.model.ParkingSystem;

public interface ParkingLotRepository extends PagingAndSortingRepository<ParkingLot, Integer> {



}

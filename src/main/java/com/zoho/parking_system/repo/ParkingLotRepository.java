package com.zoho.parking_system.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zoho.parking_system.model.ParkingLot;
import com.zoho.parking_system.request.model.ParkingSystem;

public interface ParkingLotRepository extends PagingAndSortingRepository<ParkingLot, Integer> {

	@Query(value="select top 1 p.parking_space from parking_lot p",nativeQuery=true)
	long findSlotCount();



}

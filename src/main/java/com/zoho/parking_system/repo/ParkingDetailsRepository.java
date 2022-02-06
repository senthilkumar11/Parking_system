package com.zoho.parking_system.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zoho.parking_system.model.ParkingDetails;

public interface ParkingDetailsRepository extends PagingAndSortingRepository<ParkingDetails, Integer> {

	@Query(value="Select top 1 * from parking_details p where p.vehicle_registration_number=?1 and availabilty=1",nativeQuery=true)
	ParkingDetails findByVehichle(String vehicleNum);

}

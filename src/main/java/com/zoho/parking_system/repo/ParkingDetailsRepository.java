package com.zoho.parking_system.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zoho.parking_system.model.ParkingDetails;

public interface ParkingDetailsRepository extends PagingAndSortingRepository<ParkingDetails, Integer> {

	@Query(value="Select top 1 * from parking_details p where p.vehicle_registration_number=?1 and availabilty=1",nativeQuery=true)
	ParkingDetails findByVehichle(String vehicleNum);

	Page<ParkingDetails> findAll(Specification<ParkingDetails> spec, Pageable paging);
//	List<ParkingDetails> findAll(Specification<ParkingDetails> spec);
	@Query(value="Select sum(p.final_fee) from parking_details p where p.vehicle_type=?1 and availabilty=0 and p.final_fee is not null",nativeQuery=true)
	double sumAllFee(String string);
	@Query(value="Select count(*) from parking_details p where p.vehicle_type=?1 and availabilty=0",nativeQuery=true)
	long countAllCompletedTransaction(String string);

}
